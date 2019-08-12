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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * Model class that represents the Hawaii logging configuration properties.
 *
 * @author Rutger Lubbers
 */
@Component
@ConfigurationProperties(prefix = "hawaii.logging.filters")
@SuppressWarnings("PMD.DataClass")
public class ContainerNameHeaderConfigurationProperties {

    /**
     * Configuration properties for the container name log filter.
     */
    @NestedConfigurationProperty
    private ContainerNameHttpHeaderProperties containerName;

    @SuppressWarnings("PMD.CommentRequired")
    public ContainerNameHttpHeaderProperties getContainerName() {
        return containerName;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setContainerName(final ContainerNameHttpHeaderProperties containerName) {
        this.containerName = containerName;
    }
}
