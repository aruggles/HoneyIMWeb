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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	/**
	 * JPA Entity Manager, used to save and retrieve data from the data store.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	private void handleProcessMsg(final InputStream input) {
		try {
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
				entityManager.persist(wepawetProcessing);
			} else {
				// Failure.
				final Element error = (Element) doc.getElementsByTagName("error").item(0);
				WepawetError wepawetError = new WepawetError();
				wepawetError.setCode(error.getAttribute("code"));
				wepawetError.setMessage(error.getAttribute("message"));
				// Save the error to the database.
				entityManager.persist(wepawetError);
			}
		} catch (final ParserConfigurationException parserEx) {
			log.error("Parser Exception.", parserEx);
		} catch (SAXException saxEx) {
			log.error("Parsing Exception.", saxEx);
		} catch (IOException ioEx) {
			log.error("I/O Exception.", ioEx);
		}
	}
	private void handleQueryMsg(final InputStream input, String hash) {
		try {
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
					entityManager.persist(wepawetResult);
				}
			} else {
				// Failure.
				final Element error = (Element) doc.getElementsByTagName("error").item(0);
				WepawetError wepawetError = new WepawetError();
				wepawetError.setCode(error.getAttribute("code"));
				wepawetError.setMessage(error.getAttribute("message"));
				// Save the error to the database.
				entityManager.persist(wepawetError);
				// Remove the processing record.
				WepawetProcessing wepawetProcessing = (WepawetProcessing) entityManager
					.createNamedQuery("FROM WepawetProcessing p WHERE p.hash = :hash")
					.setParameter("hash", hash)
					.getSingleResult();
				entityManager.remove(wepawetProcessing);
			}
		} catch (final ParserConfigurationException parserEx) {
			log.error("Parser Exception.", parserEx);
		} catch (SAXException saxEx) {
			log.error("Parsing Exception.", saxEx);
		} catch (IOException ioEx) {
			log.error("I/O Exception.", ioEx);
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
		}

	}
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
