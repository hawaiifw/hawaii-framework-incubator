package org.hawaiiframework.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Jackson serializer with default UTC values.
 */
public class UtcZonedDateTimeSerializer extends ZonedDateTimeSerializer {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -8725046154841876822L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(final ZonedDateTime value, final JsonGenerator generator, final SerializerProvider provider) throws IOException {
        super.serialize(value.withZoneSameInstant(ZoneOffset.UTC), generator, provider);
    }
}
