package org.hawaiiframework.web.client;

import org.hawaiiframework.async.http.HawaiiHttpComponentsClientHttpRequestFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * The RestTemplate assembler creates RestTemplates to execute REST calls with logging and messages and error converters.
 * <p>
 * The factory takes care of the common interceptors(etc) wiring for rest calls.
 */
public class HawaiiRestTemplateBuilder {

    /**
     * The default (Spring) rest template assembler.
     */
    private final RestTemplateBuilder builder;

    /**
     * The HTTP client request factory.
     */
    private final BufferingClientHttpRequestFactory requestFactory =
        new BufferingClientHttpRequestFactory(new HawaiiHttpComponentsClientHttpRequestFactory());

    /**
     * The form message converter.
     */
    private final FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();

    /**
     * List of default interceptors.
     */
    private final List<ClientHttpRequestInterceptor> defaultInterceptors;

    /**
     * The error handler.
     */
    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    /**
     * List of additional interceptors.
     */
    private List<ClientHttpRequestInterceptor> additionalRequestInterceptors;

    /**
     * list of message converters, this trumps additional converters.
     */
    private List<HttpMessageConverter<?>> httpMessageConverters;

    /**
     * list of additional message converters.
     */
    private List<HttpMessageConverter<?>> additionalHttpMessageConverters;

    /**
     * The basic authorization to use.
     */
    private BasicAuthorization basicAuthorization;

    /**
     * The request factory supplier.
     */
    private final Supplier<ClientHttpRequestFactory> requestFactorySupplier = () -> requestFactory;

    /**
     * Builder to create rest templates.
     *
     * @param builder the spring rest template assembler used to create the rest templates internally,
     */
    public HawaiiRestTemplateBuilder(
        final RestTemplateBuilder builder,
        final HttpRequestInterceptorsFactory httpRequestInterceptorsFactory) {
        this.builder = requireNonNull(builder);
        this.defaultInterceptors = httpRequestInterceptorsFactory.getDefaultRequestInterceptors();

        additionalRequestInterceptors = null;
        httpMessageConverters = null;
        additionalHttpMessageConverters = null;
        basicAuthorization = null;
    }


    /**
     * Adds additional interceptors.
     *
     * @param additionalInterceptors additional interceptors.
     * @return a new assembler instance with the @code{additionalInterceptors} set.
     */
    public HawaiiRestTemplateBuilder additionalInterceptors(final List<ClientHttpRequestInterceptor> additionalInterceptors) {
        this.additionalRequestInterceptors = additionalInterceptors;
        return this;
    }


    /**
     * Adds additional interceptors.
     *
     * @param additionalInterceptors additional interceptors.
     * @return a new assembler instance with the @code{additionalInterceptors} set.
     */
    public HawaiiRestTemplateBuilder additionalInterceptors(final ClientHttpRequestInterceptor... additionalInterceptors) {
        return additionalInterceptors(Arrays.asList(additionalInterceptors));
    }

    /**
     * Add additional http message converters.
     *
     * @param additionalHttpMessageConverters additional http message converters.
     * @return a new assembler instance with the @code{additionalHttpMessageConverters} set.
     */
    public HawaiiRestTemplateBuilder additionalMessageConverters(final List<HttpMessageConverter<?>> additionalHttpMessageConverters) {
        this.additionalHttpMessageConverters = additionalHttpMessageConverters;
        return this;
    }

    /**
     * Add additional http message converters.
     *
     * @param additionalHttpMessageConverters additional http message converters.
     * @return a new assembler instance with the @code{additionalHttpMessageConverters} set.
     */
    public HawaiiRestTemplateBuilder additionalMessageConverters(final HttpMessageConverter<?>... additionalHttpMessageConverters) {
        return additionalMessageConverters(Arrays.asList(additionalHttpMessageConverters));
    }

    /**
     * Set the http message converters.
     *
     * @param httpMessageConverters the http message converters.
     * @return a new assembler instance with the @code{httpMessageConverters} set.
     */
    public HawaiiRestTemplateBuilder withMessageConverters(final List<HttpMessageConverter<?>> httpMessageConverters) {
        this.httpMessageConverters = httpMessageConverters;
        return this;
    }

    /**
     * Set the http message converters.
     *
     * @param httpMessageConverters the http message converters.
     * @return a new assembler instance with the @code{httpMessageConverters} set.
     */
    public HawaiiRestTemplateBuilder withMessageConverters(final HttpMessageConverter<?>... httpMessageConverters) {
        return withMessageConverters(Arrays.asList(httpMessageConverters));
    }


    /**
     * Set basic authorization.
     *
     * @param username the basic authorization username.
     * @param password the basic authorization password.
     * @return a new assembler instance with the basic authorization of @code{username} and @code{password} set.
     */
    public HawaiiRestTemplateBuilder withBasicAuthorization(final String username, final String password) {
        final BasicAuthorization basicAuthorization =
            new BasicAuthorization(requireNonNull(username, "Username is not set."), requireNonNull(password, "Password is not set."));
        return withBasicAuthorization(basicAuthorization);
    }

    /**
     * Set basic authorization.
     *
     * @param basicAuthorization the basic authorization.
     * @return a new assembler instance with the basic @code{authorization} set.
     */
    public HawaiiRestTemplateBuilder withBasicAuthorization(final BasicAuthorization basicAuthorization) {
        this.basicAuthorization = basicAuthorization;
        return this;
    }

    /**
     * Set the error handler.
     *
     * @param errorHandler the error handler
     * @return a new assembler instance with the error handler set
     */
    public HawaiiRestTemplateBuilder withErrorHandler(final ResponseErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    /**
     * Create a rest template.
     *
     * @return the rest template created based on the built properties.
     */
    public RestTemplate build() {
        RestTemplateBuilder restTemplateBuilder = builder;

        restTemplateBuilder = restTemplateBuilder.errorHandler(errorHandler);

        restTemplateBuilder = restTemplateBuilder.requestFactory(requestFactorySupplier);

        restTemplateBuilder = addInterceptors(restTemplateBuilder);

        restTemplateBuilder = addMessageConverters(restTemplateBuilder);

        restTemplateBuilder = addBasicAuthentication(restTemplateBuilder);

        return restTemplateBuilder.build();
    }

    private RestTemplateBuilder addBasicAuthentication(final RestTemplateBuilder builder) {
        if (basicAuthorization == null) {
            return builder;
        }

        return builder.basicAuthentication(basicAuthorization.getUsername(), basicAuthorization.getPassword());
    }

    private RestTemplateBuilder addInterceptors(final RestTemplateBuilder builder) {
        RestTemplateBuilder restTemplateBuilder = builder;

        if (!CollectionUtils.isEmpty(additionalRequestInterceptors)) {
            restTemplateBuilder = restTemplateBuilder.additionalInterceptors(additionalRequestInterceptors);
        }
        restTemplateBuilder = restTemplateBuilder.additionalInterceptors(defaultInterceptors);

        return restTemplateBuilder;
    }

    private RestTemplateBuilder addMessageConverters(final RestTemplateBuilder builder) {
        RestTemplateBuilder restTemplateBuilder = builder;

        if (CollectionUtils.isEmpty(httpMessageConverters)) {
            /*
             * First set the form message converter.
             */
            restTemplateBuilder = restTemplateBuilder.additionalMessageConverters(formHttpMessageConverter);

            /*
             * Then set the additional message converters
             */
            if (!CollectionUtils.isEmpty(additionalHttpMessageConverters)) {
                restTemplateBuilder = restTemplateBuilder.additionalMessageConverters(additionalHttpMessageConverters);
            }
        } else {
            restTemplateBuilder = restTemplateBuilder.messageConverters(httpMessageConverters);
        }

        return restTemplateBuilder;
    }
}
