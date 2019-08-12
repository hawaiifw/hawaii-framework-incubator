package org.hawaiiframework.rule;

import java.util.List;

/**
 * Factory for checkout rule chains.
 *
 * @param <C> The rule context.
 */
public interface RuleChainFactory<C> {

    /**
     * Create a new instance.
     *
     * @return a new {@link RuleChain}
     */
    RuleChain<C> create();

    /**
     * Retrieve the rules.
     */
    List<Rule<C>> getRules();
}
