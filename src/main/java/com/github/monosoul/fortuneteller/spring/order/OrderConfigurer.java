package com.github.monosoul.fortuneteller.spring.order;

import java.util.function.Consumer;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public final class OrderConfigurer implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Assert.state(configurableListableBeanFactory instanceof DefaultListableBeanFactory,
                "BeanFactory needs to be a DefaultListableBeanFactory");
        val beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;

        val orderConfigPreProcessor = orderConfigPreProcessor(beanFactory);
        val orderConfigProcessor = orderConfigProcessor(beanFactory);

        beanFactory.getBeansOfType(OrderConfig.class).values().stream()
                   .peek(orderConfigPreProcessor::accept)
                   .forEach(orderConfigProcessor::accept);
    }

    Consumer<OrderConfig<?>> orderConfigPreProcessor(final BeanDefinitionRegistry beanDefinitionRegistry) {
        return new OrderConfigPreProcessor(beanDefinitionRegistry);
    }

    Consumer<OrderConfig<?>> orderConfigProcessor(final BeanDefinitionRegistry beanDefinitionRegistry) {
        return new OrderConfigProcessor(beanDefinitionRegistry, new BeanDefinitionProvider());
    }
}
