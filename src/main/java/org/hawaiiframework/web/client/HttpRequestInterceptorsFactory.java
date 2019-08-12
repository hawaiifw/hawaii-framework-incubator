package org.hawaiiframework.web.client;

import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.util.List;

/**
 * Factory for HTTP client request interceptors.
 */
public interface HttpRequestInterceptorsFactory {

    /**
     * The method to create the default list of client request interceptors.
     *
     * @return The, never {@code null} list of interceptors.
     */
    List<ClientHttpRequestInterceptor> getDefaultRequestInterceptors();
}
