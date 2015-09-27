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
