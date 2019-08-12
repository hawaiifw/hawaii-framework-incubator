/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawaiiframework.logging.web;

import org.hawaiiframework.logging.config.HttpHeaderLoggingFilterProperties;
import org.hawaiiframework.logging.config.LoggingFilterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;

/**
 * The configuration for header providers.
 */
@Configuration
public class ContainerNameHeaderProviderConfiguration {

    /**
     * The set of all dispatcher types.
     */
    private static final EnumSet<DispatcherType> ALL_DISPATCHER_TYPES = EnumSet.allOf(DispatcherType.class);

    /**
     * The the logging configuration properties.
     */
    private final ContainerNameHeaderConfigurationProperties configurationProperties;

    /**
     * Autowired constructor.
     *
     * @param configurationProperties the logging configuration properties
     */
    @Autowired
    public ContainerNameHeaderProviderConfiguration(final ContainerNameHeaderConfigurationProperties configurationProperties) {
        this.configurationProperties = configurationProperties;
    }

    /**
     * Create the {@link ContainerNameHttpHeaderFilter} bean.
     *
     * @return the {@link ContainerNameHttpHeaderFilter} bean
     */
    @Bean
    @ConditionalOnProperty(prefix = "hawaii.logging.filters.container-name", name = "enabled")
    public ContainerNameHttpHeaderFilter containerNameHttpHeaderFilter() {
        final ContainerNameHttpHeaderProperties filterProperties = configurationProperties.getContainerName();
        return new ContainerNameHttpHeaderFilter(filterProperties);
    }


    /**
     * Register the {@link ContainerNameHttpHeaderFilter} bean.
     *
     * @param filter the filter to set.
     * @return the {@link #containerNameHttpHeaderFilter()} bean, wrapped in a {@link ContainerNameHttpHeaderFilter}
     */
    @Bean
    @ConditionalOnProperty(prefix = "hawaii.logging.filters.container-name", name = "enabled")
    public FilterRegistrationBean<Filter> requestIdFilterRegistration(final ContainerNameHttpHeaderFilter filter) {
        final HttpHeaderLoggingFilterProperties filterProperties = configurationProperties.getContainerName();
        return createFilterRegistrationBean(filter, filterProperties, ALL_DISPATCHER_TYPES);
    }

    /**
     * Helper method to wrap a filter in a {@link FilterRegistrationBean} with the configured order.
     *
     * @param filter           the filter
     * @param filterProperties the configuration properties
     * @param dispatcherTypes  the request dispatcher types the filter is used for
     * @return the wrapped filter
     */
    private FilterRegistrationBean<Filter> createFilterRegistrationBean(
        final Filter filter,
        final LoggingFilterProperties filterProperties,
        final EnumSet<DispatcherType> dispatcherTypes) {
        final FilterRegistrationBean<Filter> result = new FilterRegistrationBean<>(filter);
        result.setOrder(filterProperties.getOrder());
        result.setDispatcherTypes(dispatcherTypes);
        return result;
    }

}
