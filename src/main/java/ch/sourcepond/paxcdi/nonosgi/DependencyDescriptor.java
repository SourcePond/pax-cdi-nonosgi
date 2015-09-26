package ch.sourcepond.paxcdi.nonosgi;

import javax.enterprise.inject.spi.InjectionPoint;

import org.osgi.framework.Filter;

/**
 * @author rolandhauser
 *
 */
final class DependencyDescriptor {
	private final Filter filter;
	private final InjectionPoint ip;

	/**
	 * @param pIp
	 */
	public DependencyDescriptor(final Filter pFilter, final InjectionPoint pIp) {
		filter = pFilter;
		ip = pIp;
	}

	public Filter getFilter() {
		return filter;
	}

	/**
	 * @return
	 */
	public InjectionPoint getIp() {
		return ip;
	}

}
