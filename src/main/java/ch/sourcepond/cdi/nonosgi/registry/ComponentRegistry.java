package ch.sourcepond.cdi.nonosgi.registry;

import java.util.Collection;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;

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
	 * @param pIp
	 */
	void addDependency(InjectionPoint pIp);

	/**
	 * @return
	 */
	Collection<Bean<?>> resolveDependencies();
}
