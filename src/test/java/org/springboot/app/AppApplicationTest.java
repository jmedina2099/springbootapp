package org.springboot.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author jmedina
 *
 */
public class AppApplicationTest {

	@Test
	public void testAppApplication() {
		String[] args = new String[] { "" };
		AppApplication.main(args);
		assertEquals("", args[0]);
	}

}