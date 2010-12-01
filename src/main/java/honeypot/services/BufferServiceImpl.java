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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import honeypot.models.Buffer;

/**
 * TODO comment
 * @author Adam
 * @version $Id$
 * 
 * Created on Nov 30, 2010 at 8:22:09 PM 
 */
public class BufferServiceImpl implements BufferService {
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * {@inheritDoc}
	 * @see honeypot.services.BufferService#remove(honeypot.models.Buffer)
	 */
	@Transactional
	public void remove(Integer id) {
		Buffer buffer = entityManager.find(Buffer.class, id);
		entityManager.remove(buffer);
	}
	/**
	 * Sets entityManager.
	 * @param entityManager the entityManager to set.
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
