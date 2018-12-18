## Hawaii Framework Incubator

The Hawaii Framework is a Java framework for developing Spring based applications.

## License
The Hawaii Framework is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0

## HawaiiTokenServices

This is an implementation of the `ResourceServerTokenServices` interface from the
`spring-security-oauth2-autoconfigure` package. It uses both a `JwkTokenStore` to validate
the access token and a `UserInfoTokenServices` to query the user info endpoint.

So, in contrast to the regular Spring Security OAuth2 behaviour, you can specify
both the `security.oauth2.resource.user-info-uri` and `security.oauth2.resource.jwk.key-set-uri`
properties.

To use this implemenation, define a class, eg `SecurityConfig` as follows:

    import org.hawaiiframework.security.oauth2.config.HawaiiResourceServerConfigurerAdapter;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;

    /**
     * Security configuration.
     */
    @Configuration
    public class SecurityConfig extends HawaiiResourceServerConfigurerAdapter {
    
        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(final HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/", "/index.html", "/public/**").permitAll()
                    .antMatchers("/private/**").authenticated();
        }
    
    }

You can then continue to use Spring Security OAuth2 as usual.
