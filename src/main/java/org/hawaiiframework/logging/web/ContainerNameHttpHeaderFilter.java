package org.hawaiiframework.logging.web;


import org.hawaiiframework.logging.web.filter.AbstractGenericFilterBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter class that will be added in the Tomcat filter chain to add a http response header to every response.
 * This response header will be the value of the $HOSTNAME environment variable, to show in the frontend in which container this
 * application is running.
 */
public class ContainerNameHttpHeaderFilter extends AbstractGenericFilterBean {

    /**
     * The Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerNameHttpHeaderFilter.class);

    /**
     * The header name to set.
     */
    private final String headerName;

    /**
     * The hostname to set in the header.
     */
    private final String hostname;

    /**
     * Constructor.
     *
     * @param config The configuration.
     */
    public ContainerNameHttpHeaderFilter(final ContainerNameHttpHeaderProperties config) {
        super();
        this.headerName = config.getHttpHeader();
        this.hostname = config.getHostname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
        throws ServletException, IOException {

        LOGGER.debug("Set '{}' with value '{}'.", headerName, hostname);

        if (!response.containsHeader(headerName)) {
            response.addHeader(headerName, hostname);
        }

        filterChain.doFilter(request, response);
    }
}
