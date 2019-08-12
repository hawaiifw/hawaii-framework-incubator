package org.hawaiiframework.rule;

import javax.annotation.Nonnull;


/**
 * Chain to run a list of rules in.
 *
 * @param <C> The rule context.
 */
public interface RuleChain<C> {

    /**
     * Execute the list of {@link Rule} within this chain.
     *
     * @param context The rule context.
     */
    void execute(@Nonnull C context);

}
