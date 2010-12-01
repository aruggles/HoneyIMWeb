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
 * Created on Nov 30, 2010 at 5:40:07 PM 
 */
@Entity
@Table(name="wepawet_results")
public class WepawetResult implements Serializable {
	private static final long serialVersionUID = -5500757628232109259L;
	@Id
    @GeneratedValue
	private Integer id;
	private String report;
	private String result;
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
	 * Returns report.
	 * @return the report.
	 */
	public String getReport() {
		return report;
	}
	/**
	 * Sets report.
	 * @param report the report to set.
	 */
	public void setReport(String report) {
		this.report = report;
	}
	/**
	 * Returns result.
	 * @return the result.
	 */
	public String getResult() {
		return result;
	}
	/**
	 * Sets result.
	 * @param result the result to set.
	 */
	public void setResult(String result) {
		this.result = result;
	}
}
