package co.jware.multitenancy.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public @interface DatabaseRouting {
}
