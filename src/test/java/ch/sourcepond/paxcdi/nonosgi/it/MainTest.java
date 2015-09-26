package ch.sourcepond.paxcdi.nonosgi.it;

import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author rolandhauser
 *
 */
public class MainTest {
	private ContainerLifecycle lifecycle = null;

	/**
	 * 
	 */
	@Before
	public void setup() {
		lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
		lifecycle.startApplication(null);
	}

	/**
	 * 
	 */
	@After
	public void shutdown() {
		lifecycle.stopApplication(null);
	}

	@Test
	public void dummy() {

	}
}
