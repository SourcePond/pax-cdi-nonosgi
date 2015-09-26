package ch.sourcepond.paxcdi.nonosgi;

import static org.slf4j.LoggerFactory.getLogger;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

import org.ops4j.pax.cdi.api.OsgiService;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.ops4j.pax.cdi.api.Properties;
import org.slf4j.Logger;

/**
 * @author rolandhauser
 *
 */
public class NonOsgiExtension implements Extension {
	private static final Logger LOG = getLogger(NonOsgiExtension.class);
	private final ComponentRegistry registry;

	/**
	 * @param pRegistry
	 */
	public NonOsgiExtension() {
		this(new DefaultComponentRegistry());
	}

	/**
	 * @param pRegistry
	 */
	NonOsgiExtension(final ComponentRegistry pRegistry) {
		registry = pRegistry;
	}

	/**
	 * @param event
	 */
	public <T> void processBean(@Observes final ProcessBean<T> event) {
		final Bean<T> bean = event.getBean();
		LOG.debug("processBean {}", bean);

		final OsgiServiceProvider qualifier = event.getAnnotated().getAnnotation(OsgiServiceProvider.class);
		if (qualifier != null) {
			registry.addComponent(bean, event.getAnnotated().getAnnotation(Properties.class));
		}
	}

	/**
	 * ProcessInjectionTarget observer which scans for OSGi service injection
	 * points.
	 *
	 * @param event
	 */
	public <T> void processInjectionTarget(@Observes final ProcessInjectionTarget<T> event) {
		LOG.debug("processInjectionTarget");

		for (final InjectionPoint ip : event.getInjectionTarget().getInjectionPoints()) {
			final OsgiService qualifier = ip.getAnnotated().getAnnotation(OsgiService.class);
			if (qualifier != null) {
				registry.addDependency(qualifier, ip);
			}
		}
	}

	/**
	 * @param event
	 * @param beanManager
	 */
	public void afterBeanDiscovery(@Observes final AfterBeanDiscovery event, final BeanManager beanManager) {
		LOG.debug("afterBeanDiscovery");

		for (final Bean<?> beanToRegister : registry.resolveDependencies()) {
			event.addBean(beanToRegister);
		}

		for (final Exception e : registry.getExceptions()) {
			event.addDefinitionError(e);
		}
	}
}
