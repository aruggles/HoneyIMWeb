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

import honeypot.models.WepawetProcessing;
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
 * Created on Nov 30, 2010 at 8:12:35 PM 
 */
public class WepawetTask extends TimerTask {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private WepawetService wepawetService;
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * {@inheritDoc}
	 * @see java.util.TimerTask#run()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		log.info("Starting the Wepawet task");
		List<WepawetProcessing> wepawetProcessingList = (List<WepawetProcessing>) entityManager
			.createQuery("from WepawetProcessing as p")
			.getResultList();
		for (WepawetProcessing processing : wepawetProcessingList) {
			log.info("Checking {}", processing.getHash());
			// Send to each service for processing.
			wepawetService.checkQueue(processing.getHash());
		}
		log.info("Ending the Wepawet task");
	}
	/**
	 * Sets wepawetService.
	 * @param wepawetService the wepawetService to set.
	 */
	public void setWepawetService(WepawetService wepawetService) {
		this.wepawetService = wepawetService;
	}
	/**
	 * Sets entityManager.
	 * @param entityManager the entityManager to set.
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
