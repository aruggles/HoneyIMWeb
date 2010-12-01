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
package honeypot.tasks;

import honeypot.models.Buffer;
import honeypot.services.BufferService;
import honeypot.services.WepawetService;

import java.util.List;
import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TODO comment
 * @author Adam
 * @version $Id$
 * 
 * Created on Nov 28, 2010 at 8:05:26 PM 
 */
public class QueueTask extends TimerTask {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private WepawetService wepawetService;
	private BufferService bufferService;
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * {@inheritDoc}
	 * @see java.util.TimerTask#run()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		log.info("Starting the queue task");
		List<Buffer> bufferList = (List<Buffer>) entityManager
			.createQuery("from Buffer as b ORDER BY b.messageDate ASC")
			.getResultList();
		for (Buffer buffer : bufferList) {
			log.info("Processing {}", buffer.getMessage());
			// Send to each service for procesing.
			wepawetService.process(buffer.getMessage());
			// Delete the record after it has been processed.
			bufferService.remove(buffer.getId());
		}
		log.info("Ending the queue task");
	}
	/**
	 * Sets wepawetService.
	 * @param wepawetService the wepawetService to set.
	 */
	public void setWepawetService(final WepawetService wepawetService) {
		this.wepawetService = wepawetService;
	}
	/**
	 * Sets entityManager.
	 * @param entityManager the entityManager to set.
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	/**
	 * Sets bufferService.
	 * @param bufferService the bufferService to set.
	 */
	public void setBufferService(BufferService bufferService) {
		this.bufferService = bufferService;
	}

}
