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
package honeypot.actions;

import honeypot.models.WepawetError;
import honeypot.models.WepawetProcessing;
import honeypot.models.WepawetResult;
import honeypot.services.WepawetService;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Wepawet service analytics action.
 * @author Adam
 * @version $Id$
 * 
 * Created on Dec 4, 2010 at 11:51:49 AM 
 */
public class WepawetAction extends ActionSupport {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 5619885732818833602L;
	/**
	 * The Wepawet Service.
	 */
	private WepawetService wepawetService;
	private List<WepawetError> errorList = new ArrayList<WepawetError>();
	private List<WepawetProcessing> procList = new ArrayList<WepawetProcessing>();
	private List<WepawetResult> resultList = new ArrayList<WepawetResult>();
	private List<String> test = new ArrayList<String>();
	/**
	 * Retrieves the lists of queues for the wepawet service.
	 * {@inheritDoc}
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
    public String execute() {
    	errorList = wepawetService.getErrors();
    	procList = wepawetService.getProcessing();
    	resultList = wepawetService.getResults();
    	return SUCCESS;
    }
	
	/**
	 * Sets wepawetService.
	 * @param wepawetService the wepawetService to set.
	 */
	public void setWepawetService(WepawetService wepawetService) {
		this.wepawetService = wepawetService;
	}
	/**
	 * Returns test.
	 * @return the test.
	 */
	public List<String> getTest() {
		return test;
	}

	/**
	 * Returns errorList.
	 * @return the errorList.
	 */
	public List<WepawetError> getErrorList() {
		return errorList;
	}

	/**
	 * Returns procList.
	 * @return the procList.
	 */
	public List<WepawetProcessing> getProcList() {
		return procList;
	}

	/**
	 * Returns resultList.
	 * @return the resultList.
	 */
	public List<WepawetResult> getResultList() {
		return resultList;
	}
}
