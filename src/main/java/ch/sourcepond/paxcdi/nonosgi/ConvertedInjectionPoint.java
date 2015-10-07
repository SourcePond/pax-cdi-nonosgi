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

import static java.util.Collections.unmodifiableSet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;

import org.ops4j.pax.cdi.api.OsgiService;

/**
 * @author rolandhauser
 *
 */
final class ConvertedInjectionPoint implements InjectionPoint {
	private final InjectionPoint delegate;
	private final Set<Annotation> qualifiers;

	/**
	 * @param pDelegate
	 * @param pServiceIp
	 */
	ConvertedInjectionPoint(final InjectionPoint pDelegate, final OsgiService pServiceIp) {
		delegate = pDelegate;
		final Set<Annotation> tmpQualifiers = new HashSet<>(pDelegate.getQualifiers());
		tmpQualifiers.remove(pServiceIp);
		qualifiers = unmodifiableSet(tmpQualifiers);
	}

	/**
	 * @return
	 */
	@Override
	public Type getType() {
		return delegate.getType();
	}

	/**
	 * @return
	 */
	@Override
	public Set<Annotation> getQualifiers() {
		return qualifiers;
	}

	/**
	 * @return
	 */
	@Override
	public Bean<?> getBean() {
		return delegate.getBean();
	}

	/**
	 * @return
	 */
	@Override
	public Member getMember() {
		return delegate.getMember();
	}

	/**
	 * @return
	 */
	@Override
	public Annotated getAnnotated() {
		return delegate.getAnnotated();
	}

	/**
	 * @return
	 */
	@Override
	public boolean isDelegate() {
		return false;
	}

	/**
	 * @return
	 */
	@Override
	public boolean isTransient() {
		return delegate.isTransient();
	}

}
