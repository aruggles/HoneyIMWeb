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
 * Created on Nov 30, 2010 at 5:45:53 PM 
 */
@Entity
@Table(name="wepawet_errors")
public class WepawetError implements Serializable {
	private static final long serialVersionUID = 5394675846571789826L;
	private String code;
	@Id
    @GeneratedValue
	private Integer id;
	private String message;
	/**
	 * Returns code.
	 * @return the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * Returns id.
	 * @return the id.
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Returns message.
	 * @return the message.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Sets code.
	 * @param code the code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * Sets id.
	 * @param id the id to set.
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * Sets message.
	 * @param message the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
