package org.hawaiiframework.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Default implementation of a rule chain factory.
 * <p>
 * Implementations must sub class this basic implementation.
 *
 * @param <C> The rule's context to use.
 */
public abstract class RuleChainFactoryImpl<C> implements RuleChainFactory<C> {

    /**
     * The order comparator.
     */
    private static final Comparator<Ordered> ORDER_COMPARATOR = Comparator.comparing(Ordered::getOrder);

    /**
     * The list of cart item rules.
     */
    private final List<Rule<C>> rules;

    /**
     * Constructor.
     */
    @Autowired
    public RuleChainFactoryImpl(final List<Rule<C>> rules) {
        if (rules != null) {
            rules.sort(ORDER_COMPARATOR);
        }
        this.rules = rules;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuleChain<C> create() {
        return new RuleChainImpl<>(getRules());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Rule<C>> getRules() {
        return unmodifiableList(rules);
    }

}
