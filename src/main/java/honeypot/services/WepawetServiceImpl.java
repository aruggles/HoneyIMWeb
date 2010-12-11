/**
 * Copyright 2010 Adam Ruggles.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package honeypot.services;

import honeypot.models.Status;
import honeypot.models.WepawetError;
import honeypot.models.WepawetProcessing;
import honeypot.models.WepawetResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * TODO comment
 * @author Adam
 * @version $Id$
 * 
 * Created on Nov 30, 2010 at 2:24:56 PM 
 */
public class WepawetServiceImpl implements WepawetService {
	/**
	 * JPA Entity Manager, used to save and retrieve data from the data store.
	 */
	@PersistenceContext
	private EntityManager entityManager;
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 * @see honeypot.services.WepawetService#checkQueue(java.lang.String)
	 */
	@Transactional
	public void checkQueue(String hash) {
		try {
			String parameters = "resource_type=js&hash=" + URLEncoder.encode(hash, "UTF-8");
			URL url = new URL("http://wepawet.cs.ucsb.edu/services/query.php?" + parameters);
			HttpURLConnection connection = (HttpURLConnection)	url.openConnection();

			connection.setDoOutput( true );
			connection.setRequestMethod("GET");

			//Send request
			connection.connect();
			if( HttpURLConnection.HTTP_OK == connection.getResponseCode() )
			{
				InputStream is = connection.getInputStream();
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				int data;
				while((data=is.read()) != -1)
				{
					os.write(data);
				}
				is.close();
				// Process the XML message.
				handleQueryMsg(new ByteArrayInputStream(os.toByteArray()), hash);
				os.close();
			}
			connection.disconnect();
		} catch (final Exception e) {
			log.error("Exception occured querying the hash.", e);
			WepawetError wepawetError = new WepawetError();
			wepawetError.setCode("-1");
			wepawetError.setMessage(e.getMessage());
			wepawetError.setCreated(new Date());
			// Save the error to the database.
			entityManager.persist(wepawetError);
		}
		
	}
	/**
	 * {@inheritDoc}
	 * @see honeypot.services.WepawetService#getErrors()
	 */
	@Transactional @SuppressWarnings("unchecked")
	public List<WepawetError> getErrors() {
		return (List<WepawetError>) entityManager
			.createQuery("from WepawetError as e order by e.created")
			.getResultList();
	}
	/**
	 * {@inheritDoc}
	 * @see honeypot.services.WepawetService#getProcessing()
	 */
	@Transactional @SuppressWarnings("unchecked")
	public List<WepawetProcessing> getProcessing() {
		return (List<WepawetProcessing>) entityManager
			.createQuery("from WepawetProcessing as p order by p.created")
			.getResultList();
	}
	/**
	 * {@inheritDoc}
	 * @see honeypot.services.WepawetService#getResults()
	 */
	@Transactional @SuppressWarnings("unchecked")
	public List<WepawetResult> getResults() {
		return (List<WepawetResult>) entityManager
			.createQuery("from WepawetResult as r order by r.created")
			.getResultList();
	}
	/**
	 * {@inheritDoc}
	 * @see honeypot.services.WepawetService#getStatus()
	 */
	@Transactional
	public Status getStatus() {
		Status status = new Status();
		// Errors
		Query query = entityManager.createQuery("SELECT COUNT(e.id) FROM WepawetError e");
		status.setErrors((Long) query.getSingleResult());
		// Processing
		query = entityManager.createQuery("SELECT COUNT(p.id) FROM WepawetProcessing p");
		status.setProcessing((Long) query.getSingleResult());
		// Results
		query = entityManager.createQuery("SELECT COUNT(r.id) FROM WepawetResult r");
		status.setResults((Long) query.getSingleResult());
		return status;
	}
	/**
	 * Parses the processing messages into the correct queues.
	 * @param input The input stream containing the response from the Wepawet processing service.
	 * @throws ParserConfigurationException If an error occurs parsing the XML response.
	 * @throws SAXException If an error occurs processing the XML response.
	 * @throws IOException If an I/O Error occurs.
	 */
	private void handleProcessMsg(final InputStream input) throws ParserConfigurationException, SAXException,
		IOException {
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final Document doc = db.parse(input);
		doc.getDocumentElement().normalize();
		Element response = doc.getDocumentElement();
		String state = response.getAttribute("state");
		if ("ok".equals(state)) {
			// Success.
			final Element hash = (Element) doc.getElementsByTagName("hash").item(0);
			WepawetProcessing wepawetProcessing = new WepawetProcessing();
			wepawetProcessing.setHash(hash.getTextContent());
			wepawetProcessing.setStatus("queued");
			wepawetProcessing.setCreated(new Date());
			entityManager.persist(wepawetProcessing);
		} else {
			// Failure.
			final Element error = (Element) doc.getElementsByTagName("error").item(0);
			WepawetError wepawetError = new WepawetError();
			wepawetError.setCode(error.getAttribute("code"));
			wepawetError.setMessage(error.getAttribute("message"));
			wepawetError.setCreated(new Date());
			// Save the error to the database.
			entityManager.persist(wepawetError);
		}
	}
	/**
	 * Parses the queue messages into the correct queues.
	 * @param input The input stream containing the response from the Wepawet queue service.
	 * @param hash The hash from the Wepawet processing service.  A way to uniquely identify the query.
	 * @throws ParserConfigurationException If an error occurs parsing the XML response.
	 * @throws SAXException If an error occurs processing the XML response.
	 * @throws IOException If an I/O Error occurs.
	 */
	private void handleQueryMsg(final InputStream input, final String hash) throws ParserConfigurationException,
		SAXException, IOException {
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final Document doc = db.parse(input);
		doc.getDocumentElement().normalize();
		Element response = doc.getDocumentElement();
		String state = response.getAttribute("state");
		if ("ok".equals(state)) {
			// Success.
			final Element statusEl = (Element) doc.getElementsByTagName("status").item(0);
			final String status = statusEl.getTextContent();
			if (!"queued".equals(status)) {
				final Element reportEl = (Element) doc.getElementsByTagName("report_url").item(0);
				final Element resultEl = (Element) doc.getElementsByTagName("result").item(0);
				// Remove the processing record.
				WepawetProcessing wepawetProcessing = (WepawetProcessing) entityManager
				.createQuery("FROM WepawetProcessing p WHERE p.hash = :hash")
				.setParameter("hash", hash)
				.getSingleResult();
				entityManager.remove(wepawetProcessing);
				WepawetResult wepawetResult = new WepawetResult();
				wepawetResult.setReport(reportEl.getTextContent());
				wepawetResult.setResult(resultEl.getTextContent());
				wepawetResult.setCreated(new Date());
				entityManager.persist(wepawetResult);
			} else {
				log.info("The status for {} is {}.", hash, status);
			}
		} else {
			// Failure.
			final Element error = (Element) doc.getElementsByTagName("error").item(0);
			WepawetError wepawetError = new WepawetError();
			wepawetError.setCode(error.getAttribute("code"));
			wepawetError.setMessage(error.getAttribute("message"));
			wepawetError.setCreated(new Date());
			// Save the error to the database.
			entityManager.persist(wepawetError);
			// Remove the processing record.
			WepawetProcessing wepawetProcessing = (WepawetProcessing) entityManager
			.createNamedQuery("FROM WepawetProcessing p WHERE p.hash = :hash")
			.setParameter("hash", hash)
			.getSingleResult();
			entityManager.remove(wepawetProcessing);
		}

	}
	/**
	 * {@inheritDoc}
	 * @see honeypot.services.WepawetService#process(java.lang.String)
	 */
	@Transactional
	public void process(final String message) {
		try {
			URL url = new URL("http://wepawet.cs.ucsb.edu/services/upload.php");
			String parameters = "resource_type=js&url=" + URLEncoder.encode(message, "UTF-8");
			HttpURLConnection connection = (HttpURLConnection)	url.openConnection();

			connection.setDoOutput( true );
			connection.setRequestMethod("POST");

			//Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();

			if( HttpURLConnection.HTTP_OK == connection.getResponseCode() )
			{
				InputStream is = connection.getInputStream();
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				int data;
				while((data=is.read()) != -1)
				{
					os.write(data);
				}
				is.close();
				// Process the XML message.
				handleProcessMsg(new ByteArrayInputStream(os.toByteArray()));
				os.close();
			}
		} catch (final Exception e) {
			log.error("Exception occured processing the message.", e);
			WepawetError wepawetError = new WepawetError();
			wepawetError.setCode("-1");
			wepawetError.setMessage(e.getMessage());
			wepawetError.setCreated(new Date());
			// Save the error to the database.
			entityManager.persist(wepawetError);
		}

	}
	/**
	 * Sets entityManager.
	 * @param entityManager the entityManager to set.
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
