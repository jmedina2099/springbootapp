package org.springboot.app;

import java.net.InetAddress;

import org.springframework.boot.web.server.LocalServerPort;

/**
 * @author jmedina
 *
 */
public class AppTestHelper {

	@LocalServerPort
	private String port;

	protected String getUrlBase() {
		String hostAddress = "";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "http://" + hostAddress + ":" + port + "/";
	}
}