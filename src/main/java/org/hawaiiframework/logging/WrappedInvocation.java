package org.hawaiiframework.logging;

/**
 * Interface to allow wrapping of a call.
 */
@FunctionalInterface
public interface WrappedInvocation {

    /**
     * The call to invoke.
     */
    void invoke();
}
