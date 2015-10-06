package ch.sourcepond.paxcdi.nonosgi;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Singleton;

@SuppressWarnings("serial")
public class SingletonLiteral extends AnnotationLiteral<Singleton>implements Singleton {

}
