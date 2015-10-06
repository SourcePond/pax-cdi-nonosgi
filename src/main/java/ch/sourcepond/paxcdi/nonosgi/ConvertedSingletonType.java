package ch.sourcepond.paxcdi.nonosgi;

import static java.util.Collections.unmodifiableSet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;

import org.ops4j.pax.cdi.api.OsgiServiceProvider;

/**
 * @author rolandhauser
 *
 * @param <X>
 */
final class ConvertedSingletonType<X> implements AnnotatedType<X> {
	private final AnnotatedType<X> delegate;
	private final Set<Annotation> annotations;

	/**
	 * @param pDelegate
	 * @param pProviderAnnotation
	 */
	public ConvertedSingletonType(final AnnotatedType<X> pDelegate, final OsgiServiceProvider pProviderAnnotation) {
		delegate = pDelegate;
		final Set<Annotation> tmpAnnotations = new HashSet<>();
		tmpAnnotations.addAll(pDelegate.getAnnotations());
		tmpAnnotations.remove(pProviderAnnotation);
		tmpAnnotations.add(new SingletonLiteral());
		annotations = unmodifiableSet(tmpAnnotations);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.Annotated#getBaseType()
	 */
	@Override
	public Type getBaseType() {
		return delegate.getBaseType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.Annotated#getTypeClosure()
	 */
	@Override
	public Set<Type> getTypeClosure() {
		return delegate.getTypeClosure();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.Annotated#getAnnotation(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
		if (annotationType != null) {
			for (final Annotation a : annotations) {
				if (annotationType.equals(a.annotationType())) {
					return (T) a;
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.Annotated#getAnnotations()
	 */
	@Override
	public Set<Annotation> getAnnotations() {
		return annotations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.Annotated#isAnnotationPresent(java.lang.
	 * Class)
	 */
	@Override
	public boolean isAnnotationPresent(final Class<? extends Annotation> annotationType) {
		return getAnnotation(annotationType) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.AnnotatedType#getJavaClass()
	 */
	@Override
	public Class<X> getJavaClass() {
		return delegate.getJavaClass();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.AnnotatedType#getConstructors()
	 */
	@Override
	public Set<AnnotatedConstructor<X>> getConstructors() {
		return delegate.getConstructors();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.AnnotatedType#getMethods()
	 */
	@Override
	public Set<AnnotatedMethod<? super X>> getMethods() {
		return delegate.getMethods();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.enterprise.inject.spi.AnnotatedType#getFields()
	 */
	@Override
	public Set<AnnotatedField<? super X>> getFields() {
		return delegate.getFields();
	}

}
