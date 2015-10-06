package ch.sourcepond.paxcdi.nonosgi;

import static java.util.Collections.unmodifiableSet;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;

import org.ops4j.pax.cdi.api.OsgiService;

/**
 * @author rolandhauser
 *
 * @param <T>
 */
final class ConvertedInjectionTarget<T> implements InjectionTarget<T> {
	private final InjectionTarget<T> delegate;
	private final Set<InjectionPoint> ips;

	/**
	 * @param pDelegate
	 */
	ConvertedInjectionTarget(final InjectionTarget<T> pDelegate) {
		delegate = pDelegate;
		final Set<InjectionPoint> tmpIps = new HashSet<>();
		for (final InjectionPoint ip : pDelegate.getInjectionPoints()) {
			final OsgiService service = getServiceAnnotation(ip);
			if (service != null) {
				tmpIps.add(new ConvertedInjectionPoint(ip, service));
			} else {
				tmpIps.add(ip);
			}
		}
		ips = unmodifiableSet(tmpIps);
	}

	/**
	 * @param pIp
	 * @return
	 */
	private OsgiService getServiceAnnotation(final InjectionPoint pIp) {
		for (final Annotation a : pIp.getQualifiers()) {
			if (OsgiService.class.equals(a.annotationType())) {
				return (OsgiService) a;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.enterprise.inject.spi.Producer#produce(javax.enterprise.context.spi
	 * .CreationalContext)
	 */
	@Override
	public T produce(final CreationalContext<T> ctx) {
		return delegate.produce(ctx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.Producer#dispose(java.lang.Object)
	 */
	@Override
	public void dispose(final T instance) {
		delegate.dispose(instance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.Producer#getInjectionPoints()
	 */
	@Override
	public Set<InjectionPoint> getInjectionPoints() {
		return ips;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.InjectionTarget#inject(java.lang.Object,
	 * javax.enterprise.context.spi.CreationalContext)
	 */
	@Override
	public void inject(final T instance, final CreationalContext<T> ctx) {
		delegate.inject(instance, ctx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.InjectionTarget#postConstruct(java.lang.
	 * Object)
	 */
	@Override
	public void postConstruct(final T instance) {
		delegate.postConstruct(instance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.enterprise.inject.spi.InjectionTarget#preDestroy(java.lang.Object)
	 */
	@Override
	public void preDestroy(final T instance) {
		delegate.preDestroy(instance);
	}

}
