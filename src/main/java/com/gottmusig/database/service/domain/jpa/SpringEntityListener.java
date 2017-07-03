package com.gottmusig.database.service.domain.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.persistence.EntityListeners;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;

/**
 * Entity listener which allows dependency injection inside entities.
 * The listener can be registered via {@link EntityListeners} annotation.
 * <p>
 * Dependency injection annotations like {@link Autowired} are supported.
 *
 * @author Leon Gottschick
 * @since 0.0.1
 */
public class SpringEntityListener {

    private static final Logger LOG = LoggerFactory.getLogger(SpringEntityListener.class);

    private static final SpringEntityListener INSTANCE = new SpringEntityListener();

    private volatile AutowireCapableBeanFactory beanFactory;

    public static SpringEntityListener get() {
        return INSTANCE;
    }

    private AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @PostLoad
    @PostPersist
    public void inject(Object object) {
        AutowireCapableBeanFactory factory = get().getBeanFactory();
        if (factory == null) {
            LOG.debug("Bean Factory not set! Depdendencies will not be injected into: '{}'", object);
            return;
        }
        LOG.debug("Injecting dependencies into entity: '{}'", object);
        factory.autowireBean(object);
    }

}