package org.hawaiiframework.spring.autodoc.json_schema;

import capital.scalable.restdocs.section.SectionSupport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import org.hawaiiframework.web.UtcZonedDateTimeSerializer;
import org.springframework.core.MethodParameter;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.snippet.TemplatedSnippet;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static capital.scalable.restdocs.OperationAttributeHelper.getHandlerMethod;
import static capital.scalable.restdocs.i18n.SnippetTranslationResolver.translate;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * Base class for generating JSON schema snippets.
 */
@SuppressWarnings("PMD.EmptyCatchBlock")
abstract class AbstractJsonSchemaSnippet extends TemplatedSnippet implements SectionSupport {

    /**
     * The snippet's file name.
     */
    private static final String SNIPPET_TEMPLATE_NAME = "auto-json-schema";

    /**
     * Key for the snippet's template.
     */
    private static final String JSON_SCHEMA = "jsonSchema";

    /**
     * Key for the snippet's template.
     */
    private static final String HAS_CONTENT = "hasContent";

    /**
     * Key for the snippet's template.
     */
    private static final String NO_CONTENT = "noContent";

    /**
     * Collection class in Scala.
     */
    private static Class<?> scalaTraversable;

    /**
     * The object mapper to use.
     */
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        /*
         * ISO8601 date times:
         */
        objectMapper.disable(WRITE_DATES_AS_TIMESTAMPS);

        final JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(ZonedDateTime.class, new UtcZonedDateTimeSerializer());
        objectMapper.registerModule(module);
        objectMapper.setDateFormat(new StdDateFormat());

        try {
            scalaTraversable = Class.forName("scala.collection.Traversable");
        } catch (ClassNotFoundException ignored) {
            // It's fine to not be available outside of Scala projects.
        }
    }

    /**
     * The constructor.
     */
    protected AbstractJsonSchemaSnippet(final String snippetName, final Map<String, Object> attributes) {
        super(snippetName, SNIPPET_TEMPLATE_NAME, attributes);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"checkstyle:returncount", "PMD.LawOfDemeter", "PMD.AvoidCatchingGenericException"})
    @Override
    protected Map<String, Object> createModel(final Operation operation) {
        final HandlerMethod handlerMethod = getHandlerMethod(operation);
        final Map<String, Object> model = defaultModel();
        if (handlerMethod == null) {
            return model;
        }
        for (final String key : getTranslationKeys()) {
            model.put(key, translate(key));
        }

        final Type type = getType(handlerMethod);
        if (type != null) {
            try {
                final String className = type.getTypeName();
                final Class<?> typeClass = Class.forName(className);

                final JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);
                final JsonNode jsonNode = jsonSchemaGenerator.generateJsonSchema(typeClass);

                final String jsonSchema = objectMapper.writeValueAsString(jsonNode);

                final boolean hasContent = hasContent(operation);
                model.put(JSON_SCHEMA, jsonSchema);
                model.put(HAS_CONTENT, hasContent);
                model.put(NO_CONTENT, !hasContent);

                return model;
            } catch (Exception ignored) {
                model.put(JSON_SCHEMA, ignored.getMessage());
            }
        }
        return model;
    }

    private Map<String, Object> defaultModel() {
        final Map<String, Object> model = new HashMap<>();
        model.put(JSON_SCHEMA, "");
        model.put(HAS_CONTENT, false);
        model.put(NO_CONTENT, true);
        return model;
    }

    /**
     * @param param the method parameter.
     * @return the first generic type the method parameter.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    protected Type firstGenericType(final MethodParameter param) {
        final Type type = param.getGenericParameterType();
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            return Object.class;
        }
    }

    /**
     * @param method the handler method.
     * @return the type to render for the handler method.
     */
    protected abstract Type getType(HandlerMethod method);

    /**
     * @param type the class type.
     * @return whether the class @code{type} is a collection.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    protected boolean isCollection(final Class<?> type) {
        return Collection.class.isAssignableFrom(type)
            || scalaTraversable != null && scalaTraversable.isAssignableFrom(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFileName() {
        return getSnippetName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasContent(final Operation operation) {
        return getType(getHandlerMethod(operation)) != null;
    }

    /**
     * <p>
     * These keys' values will be used in the template's rendering.
     *
     * @return the keys from the translation resource bundle to add to the template's model.
     */
    protected String[] getTranslationKeys() {
        return new String[] {
            "no-params",
        };
    }
}
