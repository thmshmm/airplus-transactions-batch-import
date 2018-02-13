package de.thmshmm.airplus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import javax.sql.DataSource;


public class AirplusTransactionJdbcWriter extends JdbcBatchItemWriter<AirplusTransaction> {

    private static final Logger LOG = LoggerFactory.getLogger(AirplusTransactionJdbcWriter.class);

    private DataSource dataSource;

    AirplusTransactionJdbcWriter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public AirplusTransactionJdbcWriter init() {
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<AirplusTransaction>());
        this.setSql("INSERT INTO creditcard_transactions (card_no, invoice_no, invoice_date, invoice_item_no, "
                + "purchase_date, entry_date, service_provider, service_description, currency, amount) "
                + "VALUES (:cardNo, :invoiceNo, :invoiceDate, :invoiceItemNo, :purchaseDate, :entryDate, "
                + ":serviceProvider, :serviceDescription, :currencyStr, :amount)");
        this.setDataSource(dataSource);
        this.afterPropertiesSet();
        return this;
    }
}
