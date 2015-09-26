package ch.sourcepond.paxcdi.nonosgi;

import java.util.Map;

import javax.enterprise.inject.spi.Bean;

/**
 * @author rolandhauser
 *
 */
final class ComponentDescriptor {
	private final Bean<?> bean;
	private final Map<String, String> properties;

	public ComponentDescriptor(final Bean<?> pBean, final Map<String, String> pProperties) {
		bean = pBean;
		properties = pProperties;
	}

	public Bean<?> getBean() {
		return bean;
	}

	public Map<String, String> getProperties() {
		return properties;
	}
}
