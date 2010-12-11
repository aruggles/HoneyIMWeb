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

/**
 * Status Object.
 * @author Adam
 * @version $Id$
 * 
 * Created on Dec 4, 2010 at 11:58:26 AM 
 */
public class Status {
	/**
	 * The number of events that are currently being processed.
	 */
	private long processing;
	/**
	 * The number of errors that have occurred.
	 */
	private long errors;
	/**
	 * The number of events that have been processed successfully and have results.
	 */
	private long results;
	/**
	 * Returns processing.
	 * @return the processing.
	 */
	public long getProcessing() {
		return processing;
	}
	/**
	 * Sets processing.
	 * @param processing the processing to set.
	 */
	public void setProcessing(long processing) {
		this.processing = processing;
	}
	/**
	 * Returns errors.
	 * @return the errors.
	 */
	public long getErrors() {
		return errors;
	}
	/**
	 * Sets errors.
	 * @param errors the errors to set.
	 */
	public void setErrors(long errors) {
		this.errors = errors;
	}
	/**
	 * Returns results.
	 * @return the results.
	 */
	public long getResults() {
		return results;
	}
	/**
	 * Sets results.
	 * @param results the results to set.
	 */
	public void setResults(long results) {
		this.results = results;
	}
}
