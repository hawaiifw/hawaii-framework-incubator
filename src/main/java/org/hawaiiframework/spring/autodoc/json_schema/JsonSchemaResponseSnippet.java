package org.hawaiiframework.spring.autodoc.json_schema;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * A Spring Auto Doc JSON Schema snippet for a web resource's response.
 */
public class JsonSchemaResponseSnippet extends AbstractJsonSchemaSnippet {

    /**
     * The snippet's name.
     */
    public static final String AUTO_JSON_SCHEMA_RESPONSE = "auto-json-schema-response";

    /**
     * Spring paging class.
     */
    private static final String SPRING_DATA_PAGE_CLASS = "org.springframework.data.domain.Page";

    /**
     * The constructor.
     */
    public JsonSchemaResponseSnippet() {
        super(AUTO_JSON_SCHEMA_RESPONSE, null);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"PMD.LawOfDemeter", "checkstyle:returncount"})
    @Override
    protected Type getType(final HandlerMethod method) {
        final Class<?> returnType = method.getReturnType().getParameterType();
        if (returnType == ResponseEntity.class) {
            return firstGenericType(method.getReturnType());
        } else if (returnType == HttpEntity.class) {
            return firstGenericType(method.getReturnType());
        } else if (SPRING_DATA_PAGE_CLASS.equals(returnType.getCanonicalName())) {
            return firstGenericType(method.getReturnType());
        } else if (isCollection(returnType)) {
            return (GenericArrayType) () -> firstGenericType(method.getReturnType());
        } else if ("void".equals(returnType.getName())) {
            return null;
        } else {
            return returnType;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeaderKey() {
        return "json-schema-response";
    }

}
