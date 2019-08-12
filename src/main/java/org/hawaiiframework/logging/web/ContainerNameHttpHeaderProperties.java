package org.hawaiiframework.logging.web;

import org.hawaiiframework.logging.config.HttpHeaderLoggingFilterProperties;

/**
 * Extension of {@link HttpHeaderLoggingFilterProperties} with extra properties.
 */
public class ContainerNameHttpHeaderProperties extends HttpHeaderLoggingFilterProperties {

    /**
     * The hostname to log.
     */
    private String hostname;

    @SuppressWarnings("PMD.CommentRequired")
    public String getHostname() {
        return hostname;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setHostname(final String hostname) {
        this.hostname = hostname;
    }
}
