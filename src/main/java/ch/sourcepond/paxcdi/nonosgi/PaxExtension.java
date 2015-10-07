/*Copyright (C) 2015 Roland Hauser, <sourcepond@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package ch.sourcepond.paxcdi.nonosgi;

import java.lang.annotation.Annotation;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessInjectionPoint;

import org.ops4j.pax.cdi.api.OsgiService;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;

/**
 * @author rolandhauser
 *
 */
public class PaxExtension implements Extension {

	public PaxExtension() {
		System.out.println("test");
	}

	/**
	 * @param pInjectionPoint
	 */
	public <T, X> void convertInjectionPoint(@Observes final ProcessInjectionPoint<T, X> pInjectionPoint) {
		final InjectionPoint originalInjectionPoint = pInjectionPoint.getInjectionPoint();
		final OsgiService serviceAnnotation = getServiceAnnotation(originalInjectionPoint);
		if (serviceAnnotation != null) {
			pInjectionPoint.setInjectionPoint(new ConvertedInjectionPoint(originalInjectionPoint, serviceAnnotation));
		}
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
}
