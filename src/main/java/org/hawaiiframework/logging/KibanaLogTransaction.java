package org.hawaiiframework.logging;

import org.apache.commons.lang3.StringUtils;
import org.hawaiiframework.logging.model.KibanaLogFieldNames;
import org.hawaiiframework.logging.model.KibanaLogFields;

import java.util.UUID;

import static org.hawaiiframework.logging.model.KibanaLogFieldNames.TX_ID;

/**
 * Auto closeable utility to start a new transaction.
 */
public class KibanaLogTransaction implements AutoCloseable {

    /**
     * Flag to indicate that there is already a transaction going on.
     */
    private final boolean hasTx;

    /**
     * Start a new log transaction.
     *
     * @param transactionType The transaction type.
     */
    public KibanaLogTransaction(final String transactionType) {
        final String txId = KibanaLogFields.get(TX_ID);
        hasTx = StringUtils.isNotBlank(txId);
        if (!hasTx) {
            KibanaLogFields.tag(TX_ID, createTxId());
        }
        KibanaLogFields.tag(KibanaLogFieldNames.TX_TYPE, transactionType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (!hasTx) {
            KibanaLogFields.clear();
        }
    }

    private String createTxId() {
        return createTxId(UUID.randomUUID());
    }

    private String createTxId(final UUID uuid) {
        return uuid.toString();
    }
}
