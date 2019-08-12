package org.hawaiiframework.rule;

import org.springframework.core.Ordered;

import javax.annotation.Nonnull;

/**
 * Rule for the given context.
 * <p>
 * Note, implementations must be stateless.
 *
 * @param <C> The rule context.
 */
@SuppressWarnings("PMD.ShortClassName")
public interface Rule<C> extends Ordered {

    /**
     * Execute the rule.
     *
     * @param ruleChain The chain of rules to apply.
     * @param context   The checkout rule context.
     */
    void execute(@Nonnull RuleChain<C> ruleChain, @Nonnull C context);

}
