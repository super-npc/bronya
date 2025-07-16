package bronya.admin.autotable;

import org.dromara.autotable.core.interceptor.AutoTableAnnotationInterceptor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;
@Component
public class MyAutoTableAnnotationInterceptor implements AutoTableAnnotationInterceptor {
    @Override
    public void intercept(Set<Class<? extends Annotation>> includeAnnotations, Set<Class<? extends Annotation>> excludeAnnotations) {

    }
}
