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
package honeypot.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TODO comment
 * @author Adam
 * @version $Id$
 * 
 * Created on Nov 30, 2010 at 5:43:12 PM 
 */
@Entity
@Table(name="wepawet_processing")
public class WepawetProcessing implements Serializable {
	private static final long serialVersionUID = 1371372989284142210L;
	@Id
    @GeneratedValue
	private Integer id;
	private String hash;
	private String status;
	/**
	 * Returns id.
	 * @return the id.
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets id.
	 * @param id the id to set.
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * Returns hash.
	 * @return the hash.
	 */
	public String getHash() {
		return hash;
	}
	/**
	 * Sets hash.
	 * @param hash the hash to set.
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	/**
	 * Returns status.
	 * @return the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets status.
	 * @param status the status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
