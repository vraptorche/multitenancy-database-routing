package co.jware.multitenancy.util;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(200)
public class DatebaseRoutingAspect {

    @Autowired
    private Map dataSources;

    @Pointcut("@annotation(DatabaseRouting))")
    public void databaseRouting() {
    }

    @Pointcut("execution(* *(..))")
    public void atExecution() {
    }

    @Around("atExecution() && databaseRouting()")
    public Object switchDatabase(ProceedingJoinPoint pjp) throws Throwable {
        String tenantId = retrieveTenantId(pjp);
        try {
            if (null != tenantId) {
                if (!dataSources.containsKey(tenantId)) {
                    throw new IllegalArgumentException("Unknown database key: " + tenantId);
                }
                TenantIdHolder.set(tenantId);
            }
            return pjp.proceed();
        } finally {
            TenantIdHolder.clear();
        }
    }

    private String retrieveTenantId(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        Object[] args = pjp.getArgs();
        int idx = 0;
        Object tenantId = null;
        for (Annotation[] annotation : parameterAnnotations) {
            tenantId = args[idx++];
            for (Annotation annot : annotation) {
                if (TenantId.class.equals(annot.annotationType())) {
                    break;
                }
            }
        }
        return (String) tenantId;
    }

}
