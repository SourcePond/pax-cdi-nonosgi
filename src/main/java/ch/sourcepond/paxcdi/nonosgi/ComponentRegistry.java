package ch.sourcepond.paxcdi.nonosgi;

import java.util.Collection;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;

import org.ops4j.pax.cdi.api.OsgiService;
import org.ops4j.pax.cdi.api.Properties;

/**
 * @author rolandhauser
 *
 */
public interface ComponentRegistry {

	/**
	 * @param pComponent
	 * @param pPropertiesOrNull
	 */
	<T> void addComponent(Bean<T> pComponent, Properties pPropertiesOrNull);

	/**
	 * @param pService
	 * @param pIp
	 */
	void addDependency(OsgiService pService, InjectionPoint pIp);

	/**
	 * @return
	 */
	Collection<Bean<?>> resolveDependencies();

	/**
	 * @return
	 */
	Collection<Exception> getExceptions();
}
