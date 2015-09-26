package ch.sourcepond.paxcdi.nonosgi.it;

import javax.inject.Inject;

import org.ops4j.pax.cdi.api.OsgiService;

/**
 * @author rolandhauser
 *
 */
public class DependentService {
	private final TestService testSrv;

	@Inject
	public DependentService(final @OsgiService TestService pTestSrv) {
		testSrv = pTestSrv;
	}
}
