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
package ch.sourcepond.paxcdi.nonosgi.it;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.webbeans.spi.BDABeansXmlScanner;
import org.apache.webbeans.spi.ScannerService;

public class TestScannerService implements ScannerService {

	@Override
	public void init(final Object object) {
		// noop
	}

	@Override
	public void scan() {
		// noop
	}

	@Override
	public void release() {
		// noop
	}

	@Override
	public Set<URL> getBeanXmls() {
		final Set<URL> urls = new HashSet<>();
		urls.add(getClass().getResource("/beans.xml"));
		return urls;
	}

	@Override
	public Set<Class<?>> getBeanClasses() {
		final Set<Class<?>> beanClasses = new HashSet<>();
		beanClasses.add(TestService.class);
		beanClasses.add(DependentService.class);
		return beanClasses;
	}

	@Override
	public boolean isBDABeansXmlScanningEnabled() {
		return false;
	}

	@Override
	public BDABeansXmlScanner getBDABeansXmlScanner() {
		return null;
	}

}
