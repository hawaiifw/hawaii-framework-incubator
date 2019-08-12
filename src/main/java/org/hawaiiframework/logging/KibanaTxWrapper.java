package org.hawaiiframework.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static java.lang.String.format;

/**
 * Helper to start a hawaii transaction, for logging purposes.
 */
public final class KibanaTxWrapper {

    /**
     * The logger to use.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KibanaTxWrapper.class);

    /**
     * Utility constructor.
     */
    private KibanaTxWrapper() {
        // Do nothing.
    }

    /**
     * Wrap the call with a kibana transaction.
     *
     * @param endPoint The endpoint's name.
     * @param txName   The call's name.
     * @param supplier The actual code to invoke.
     * @param <T>      The return type.
     * @return The value returned by the {@code supplier}.
     */
    @SuppressWarnings({"unused", "try", "PMD.AvoidCatchingThrowable", "PMD.LawOfDemeter"})
    public static <T> T kibanaTx(final String endPoint, final String txName, final Supplier<T> supplier) {
        try (KibanaLogTransaction kibanaLogTransaction = new KibanaLogTransaction(format("%s.%s", endPoint, txName))) {
            return supplier.get();
        } catch (Throwable throwable) {
            LOGGER.error("Got exception '{}' with message '{}'.", throwable.getClass().getSimpleName(), throwable.getMessage(), throwable);
            throw throwable;
        }
    }

}
