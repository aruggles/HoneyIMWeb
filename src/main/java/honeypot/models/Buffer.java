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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * TODO comment
 * @author Adam
 * @version $Id$
 * 
 * Created on Nov 28, 2010 at 8:21:22 PM 
 */
@Entity
@Table(name="buffer")
public class Buffer implements Serializable {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -5687439841677679292L;
	@Id
    @GeneratedValue
	private Integer id;
	@Lob
	private String message;
	@Column(name = "m_date")
	private Date messageDate;
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
	 * Returns message.
	 * @return the message.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Sets message.
	 * @param message the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Returns messageDate.
	 * @return the messageDate.
	 */
	public Date getMessageDate() {
		return messageDate;
	}
	/**
	 * Sets messageDate.
	 * @param messageDate the messageDate to set.
	 */
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}
}
