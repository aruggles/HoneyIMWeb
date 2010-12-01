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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TODO comment
 * @author Adam
 * @version $Id$
 * 
 * Created on Nov 30, 2010 at 2:51:34 PM 
 */
public class WepawetServiceTest {


	/**
	 * Test method for {@link honeypot.services.WepawetServiceImpl#process(java.lang.String)}.
	 */
	//@Test
	public void testProcess() {
		WepawetService service = new WepawetServiceImpl();
		service.process("http://mvnrepository.com/artifact/org.springframework/spring-tx/2.5.4");
		assert(true);
	}

	/**
	 * Test method for {@link honeypot.services.WepawetServiceImpl#checkQueue(java.lang.String)}.
	 */
	@Test
	public void TestCheckQueue() {
		WepawetService service = new WepawetServiceImpl();
		service.checkQueue("11ef39eff51371c81d58680b7cabd0bb");
		assert(true);
	}
}
