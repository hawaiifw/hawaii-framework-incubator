package org.hawaiiframework.spring.autodoc.mandatory_optional_fields;

import capital.scalable.restdocs.payload.JacksonResponseFieldSnippet;
import capital.scalable.restdocs.util.TemplateFormatting;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.util.Map;

/**
 * {@link JacksonResponseFieldSnippet} with M/O for mandatory/optional, instead of optional false / true.
 */
public class MandatoryOptionalResponseFieldSnippet extends JacksonResponseFieldSnippet {

    /**
     * This creates the model with optional as M/O for the given descriptor.
     */
    private final MandatoryOptionalModelForDescriptorCreator mandatoryOptionalModelForDescriptorCreator;

    /**
     * Constructor.
     */
    public MandatoryOptionalResponseFieldSnippet() {
        super();
        this.mandatoryOptionalModelForDescriptorCreator = new MandatoryOptionalModelForDescriptorCreator();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected Map<String, Object> createModelForDescriptor(final FieldDescriptor descriptor,
        final TemplateFormatting templateFormatting) {
        return mandatoryOptionalModelForDescriptorCreator.createModelForDescriptor(descriptor, templateFormatting);
    }


}
