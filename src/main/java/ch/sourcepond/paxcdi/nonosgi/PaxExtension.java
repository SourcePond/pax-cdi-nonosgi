package ch.sourcepond.paxcdi.nonosgi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

import org.ops4j.pax.cdi.api.OsgiServiceProvider;

/**
 * @author rolandhauser
 *
 */
public class PaxExtension implements Extension {

	/**
	 * @param pType
	 */
	public <X> void convertProvider(@Observes final ProcessAnnotatedType<X> pType) {
		final AnnotatedType<X> type = pType.getAnnotatedType();
		final OsgiServiceProvider providerAnnotation = type.getAnnotation(OsgiServiceProvider.class);
		if (providerAnnotation != null) {
			pType.setAnnotatedType(new ConvertedSingletonType<>(type, providerAnnotation));
		}
	}

	/**
	 * @param pTarget
	 */
	public <X> void convertInjectionTarget(@Observes final ProcessInjectionTarget<X> pTarget) {
		pTarget.setInjectionTarget(new ConvertedInjectionTarget<>(pTarget.getInjectionTarget()));
	}
}
