package de.thmshmm.airplus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class AirplusTransactionProcessor implements ItemProcessor<AirplusTransaction, AirplusTransaction> {

    private static final Logger LOG = LoggerFactory.getLogger(AirplusTransactionProcessor.class);

    @Override
    public AirplusTransaction process(AirplusTransaction transaction) throws Exception {

        LOG.debug("Processing transaction: {}", transaction);

        if (transaction.getDebitCredit() == 'S') {
            transaction.setDebitCredit('D');
        } else if (transaction.getDebitCredit() == 'H') {
            transaction.setDebitCredit('C');
        } else {
            LOG.error("Could not transform debit/credit flag.");
        }

        if (transaction.getDebitCredit() == 'C') {
            transaction.setAmount(transaction.getAmount().multiply(new BigDecimal(-1.)));
        }

        LOG.debug("Processed transaction: {}", transaction);

        return transaction;
    }
}
