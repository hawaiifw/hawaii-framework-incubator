package org.hawaiiframework.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.ClassUtils.getShortName;

/**
 * Default implementation of {@link RuleChain}.
 *
 * @param <C> The rule context.
 */
public class RuleChainImpl<C> implements RuleChain<C> {

    /**
     * The logger to use.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleChainImpl.class);

    /**
     * The current rule's index.
     */
    private int currentRule;

    /**
     * The number of rules.
     */
    private final int numberOfRules;

    /**
     * The list of rules.
     */
    private final List<Rule<C>> rules = new ArrayList<>();

    /**
     * The constructor.
     *
     * @param rules The list of rules.
     */
    public RuleChainImpl(@Nonnull final List<Rule<C>> rules) {
        this.rules.addAll(requireNonNull(rules));
        numberOfRules = rules.size();
    }

    /**
     * Execute the list of {@link Rule}s within this chain.
     *
     * @param context The rule context.
     */
    @Override
    public void execute(@Nonnull final C context) {
        requireNonNull(context);

        LOGGER.trace("Invoking rule chain, number of rules is '{}', current rules is '{}'.", numberOfRules, currentRule);
        if (currentRule < numberOfRules) {
            final Rule<C> rule = rules.get(currentRule++);
            execute(context, rule);
        }
    }

    private void execute(@Nonnull final C context, final Rule<C> rule) {
        final String ruleName = getShortName(rule.getClass());
        LOGGER.debug("Executing rule '{}/{}' '{}' (with rank '{}').", currentRule, numberOfRules, ruleName, format("%,d", rule.getOrder()));
        rule.execute(this, context);
    }

}
