package org.hawaiiframework.spring.autodoc.mandatory_optional_fields;

import capital.scalable.restdocs.util.TemplateFormatting;
import org.apache.commons.lang3.StringUtils;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static capital.scalable.restdocs.constraints.ConstraintReader.CONSTRAINTS_ATTRIBUTE;
import static capital.scalable.restdocs.constraints.ConstraintReader.DEFAULT_VALUE_ATTRIBUTE;
import static capital.scalable.restdocs.constraints.ConstraintReader.DEPRECATED_ATTRIBUTE;
import static capital.scalable.restdocs.constraints.ConstraintReader.OPTIONAL_ATTRIBUTE;
import static capital.scalable.restdocs.i18n.SnippetTranslationResolver.translate;
import static capital.scalable.restdocs.javadoc.JavadocUtil.convertFromJavadoc;
import static capital.scalable.restdocs.util.FormatUtil.addDot;
import static capital.scalable.restdocs.util.FormatUtil.join;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Handle mandatory optional fields common to hawaii projects documentation.
 */
public class MandatoryOptionalModelForDescriptorCreator {

    /**
     * Two new lines.
     */
    private static final String NEW_LINES = "\n\n";
    /**
     * A dot.
     */
    private static final String DOT = ".";

    /**
     * Creates the model for {@code descriptor} similar to the way {@link capital.scalable.restdocs.snippet.StandardTableSnippet} does it,
     * however, uses M/O for mandatory/optional, instead of (optional) false/true.
     *
     * @param descriptor         the descriptor.
     * @param templateFormatting the template formatting.
     * @return the model.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    protected Map<String, Object> createModelForDescriptor(final FieldDescriptor descriptor,
        final TemplateFormatting templateFormatting) {
        final String path = descriptor.getPath();
        final String type = toString(descriptor.getType());
        final String methodComment = resolveComment(descriptor);
        final String deprecatedComment = resolveDeprecated(descriptor);
        final String completeComment = join("<p>", deprecatedComment, methodComment);
        String description = convertFromJavadoc(completeComment, templateFormatting);

        final String optionalMessage = resolveOptional(descriptor, templateFormatting)
            .replaceAll("true", "O")
            .replaceAll("false", "M");

        final List<String> constraints = resolveConstraints(descriptor);
        final String defaultValue = resolveDefaultValue(descriptor);
        description = joinAndFormat(description, constraints, defaultValue, templateFormatting);

        final Map<String, Object> model = new HashMap<>();
        model.put("path", path);
        model.put("type", type);
        model.put("optional", optionalMessage);
        model.put("description", description);
        return model;
    }

    private String defaultValuePrefix(final String description) {
        // if we have no description, we don't want to add new lines
        return StringUtils.isEmpty(description) ? StringUtils.EMPTY : NEW_LINES;
    }

    @SuppressWarnings({"unchecked", "PMD.LawOfDemeter"})
    private List<String> resolveConstraints(final FieldDescriptor descriptor) {
        return (List<String>) descriptor.getAttributes()
            .get(CONSTRAINTS_ATTRIBUTE);
    }

    @SuppressWarnings({"unchecked", "PMD.LawOfDemeter"})
    private String resolveOptional(final FieldDescriptor descriptor,
        final TemplateFormatting templateFormatting) {
        final List<String> optionalMessages = (List<String>) descriptor.getAttributes()
            .get(OPTIONAL_ATTRIBUTE);
        return join(templateFormatting.getLineBreak(), optionalMessages.toArray());
    }


    private String resolveComment(final FieldDescriptor descriptor) {
        return capitalize(addDot(toString(descriptor.getDescription())));
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private String resolveDeprecated(final FieldDescriptor descriptor) {
        final Object deprecated = descriptor.getAttributes().get(DEPRECATED_ATTRIBUTE);
        if (deprecated != null) {
            return addDot(translate("tags-deprecated", capitalize(toString(deprecated))));
        } else {
            return "";
        }
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private String resolveDefaultValue(final FieldDescriptor descriptor) {
        final Object defaultValue = descriptor.getAttributes().get(DEFAULT_VALUE_ATTRIBUTE);
        if (defaultValue != null) {
            return addDot(translate("default-value", toString(defaultValue)));
        } else {
            return "";
        }
    }

    private String toString(final Object value) {
        if (value != null) {
            return trimToEmpty(value.toString());
        } else {
            return "";
        }
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private String joinAndFormat(final String description, final List<String> constraints,
        final String defaultValue, final TemplateFormatting templateFormatting) {
        final StringBuilder res = new StringBuilder(description);
        if (!description.isEmpty() && !description.endsWith(DOT)) {
            res.append('.');
        }

        final StringBuilder constr = formatConstraints(constraints, templateFormatting.getLineBreak());
        if (res.length() > 0 && constr.length() > 0) {
            res.append(NEW_LINES);
        }

        res.append(constr.toString());
        if (StringUtils.isNotEmpty(defaultValue)) {
            res.append(defaultValuePrefix(description)).append(defaultValue);
        }

        return res.toString().replace("|", "\\|");
    }

    private StringBuilder formatConstraints(final List<String> constraints, final String forcedLineBreak) {
        final StringBuilder res = new StringBuilder();
        for (final String constraint : constraints) {
            if (StringUtils.isBlank(constraint)) {
                continue;
            }
            if (res.length() > 0) {
                res.append(forcedLineBreak);
            }
            res.append(constraint.trim());
            if (!constraint.endsWith(DOT)) {
                res.append('.');
            }
        }
        return res;
    }
}
