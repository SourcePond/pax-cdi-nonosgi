package ch.sourcepond.paxcdi.nonosgi;

import static com.google.common.collect.HashMultimap.create;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.osgi.framework.Constants.OBJECTCLASS;
import static org.osgi.framework.FrameworkUtil.createFilter;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;

import org.ops4j.pax.cdi.api.OsgiService;
import org.ops4j.pax.cdi.api.Properties;
import org.ops4j.pax.cdi.api.Property;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;

import com.google.common.collect.SetMultimap;

/**
 * @author rolandhauser
 *
 */
final class DefaultComponentRegistry implements ComponentRegistry {
	private final SetMultimap<Type, ComponentDescriptor> components = create();
	private final Collection<DependencyDescriptor> dependencies = new LinkedList<>();
	private final Collection<Exception> exceptions = new LinkedList<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.sourcepond.paxcdi.nonosgi.registry.ComponentRegistry#addComponent(
	 * javax. enterprise.inject.spi.Bean, org.ops4j.pax.cdi.api.Properties)
	 */
	@Override
	public <T> void addComponent(final Bean<T> pComponent, final Properties pPropertiesOrNull) {
		final Map<String, String> properties = new HashMap<>();
		if (pPropertiesOrNull != null) {
			for (final Property prop : pPropertiesOrNull.value()) {
				properties.put(prop.name(), prop.value());
			}
		}
		for (final Type type : pComponent.getTypes()) {
			final Class<?> cl = (Class<?>) type;
			properties.put(OBJECTCLASS, cl.getName());
			components.put(type, new ComponentDescriptor(pComponent, new HashMap<>(properties)));
		}
	}

	private String toFilter(final String pFilter, final Class<?> pType) {
		final StringBuilder builder = new StringBuilder().append("(").append(Constants.OBJECTCLASS).append("=")
				.append(pType.getName()).append(")");
		if (!isEmpty(pFilter)) {
			builder.insert(0, "(&").append(")(&").append("(").append(pFilter).append("))");
		}
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.sourcepond.paxcdi.nonosgi.registry.ComponentRegistry#addDependency(
	 * javax. enterprise.inject.spi.InjectionPoint)
	 */
	@Override
	public void addDependency(final OsgiService pService, final InjectionPoint pIp) {
		try {
			dependencies.add(
					new DependencyDescriptor(createFilter(toFilter(pService.filter(), (Class<?>) pIp.getType())), pIp));
		} catch (final InvalidSyntaxException e) {
			exceptions.add(e);
		}
	}

	/**
	 * @param pFilter
	 * @param pDescriptors
	 * @return
	 */
	private Bean<?> findMatchingComponent(final Filter pFilter, final Collection<ComponentDescriptor> pDescriptors) {
		for (final ComponentDescriptor desc : pDescriptors) {
			if (pFilter.matches(desc.getProperties())) {
				return desc.getBean();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.sourcepond.paxcdi.nonosgi.registry.ComponentRegistry#
	 * resolveDependencies( )
	 */
	@Override
	public Collection<Bean<?>> resolveDependencies() {
		final Collection<Bean<?>> matchingComponents = new LinkedList<>();
		for (final DependencyDescriptor dependency : dependencies) {
			final Collection<ComponentDescriptor> descriptors = components.get(dependency.getIp().getType());
			if (!descriptors.isEmpty()) {
				final Bean<?> matchingComponentOrNull = findMatchingComponent(dependency.getFilter(), descriptors);
				if (matchingComponentOrNull != null) {
					matchingComponents.add(matchingComponentOrNull);
				}
			}
		}
		return matchingComponents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.sourcepond.paxcdi.nonosgi.registry.ComponentRegistry#getExceptions()
	 */
	@Override
	public Collection<Exception> getExceptions() {
		return exceptions;
	}
}
