package org.hawaiiframework.spring.autodoc.json_schema;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * A Spring Auto Doc JSON Schema snippet for a web resource's request.
 */
public class JsonSchemaRequestSnippet extends AbstractJsonSchemaSnippet {

    /**
     * The snippet's name.
     */
    public static final String AUTO_JSON_SCHEMA_REQUEST = "auto-json-schema-request";

    /**
     * The constructor.
     */
    public JsonSchemaRequestSnippet() {
        super(AUTO_JSON_SCHEMA_REQUEST, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Type getType(final HandlerMethod method) {
        for (final MethodParameter param : method.getMethodParameters()) {
            if (isRequestBody(param) || isModelAttribute(param)) {
                return getType(param);
            }
        }
        return null;
    }

    private Type getType(final MethodParameter param) {
        if (isCollection(param.getParameterType())) {
            return (GenericArrayType) () -> firstGenericType(param);
        } else {
            return param.getParameterType();
        }
    }

    private boolean isRequestBody(final MethodParameter param) {
        return param.getParameterAnnotation(RequestBody.class) != null;
    }

    private boolean isModelAttribute(final MethodParameter param) {
        return param.getParameterAnnotation(ModelAttribute.class) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeaderKey() {
        return "json-schema-request";
    }

}
