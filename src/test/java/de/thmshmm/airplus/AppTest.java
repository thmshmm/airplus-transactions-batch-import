package de.thmshmm.airplus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest {

    private static final Logger LOG = LoggerFactory.getLogger(AppTest.class);

    @Inject
    private DataSource dataSource;

    @Inject
    private Environment env;

    public AirplusTransaction debitTransaction;

    public AirplusTransaction creditTransaction;

    @Before
    public void before() {

        debitTransaction = new AirplusTransaction();
        debitTransaction.setCardNo("ABCxxxx123");
        debitTransaction.setDebitCredit(new Character('S'));
        debitTransaction.setAmount(new BigDecimal(1000));
        debitTransaction.setInvoiceNo("112233");
        debitTransaction.setInvoiceItemNo(1);
        debitTransaction.setCurrency(Currency.getInstance("EUR"));
        debitTransaction.setEntryDate(LocalDate.of(2018, Month.JANUARY, 2));
        debitTransaction.setInvoiceDate(LocalDate.of(2018, Month.FEBRUARY, 10));
        debitTransaction.setPurchaseDate(LocalDate.of(2018, Month.JANUARY, 1));
        debitTransaction.setServiceProvider("Some provider");
        debitTransaction.setServiceDescription("Expensive stuff");

        creditTransaction = new AirplusTransaction();
        creditTransaction.setCardNo("ABCxxxx123");
        creditTransaction.setDebitCredit(new Character('H'));
        creditTransaction.setAmount(new BigDecimal(5));
        creditTransaction.setInvoiceNo("555555");
        creditTransaction.setInvoiceItemNo(2);
        creditTransaction.setCurrency(Currency.getInstance("EUR"));
        creditTransaction.setEntryDate(LocalDate.of(2017, Month.DECEMBER, 20));
        creditTransaction.setInvoiceDate(LocalDate.of(2018, Month.FEBRUARY, 12));
        creditTransaction.setPurchaseDate(LocalDate.of(2017, Month.DECEMBER, 23));
        creditTransaction.setServiceDescription("Another provider");
        creditTransaction.setServiceProvider("Cheap one");
    }

    @Test
    public void testAirplusTransaction() {
        assertEquals("ABCxxxx123", debitTransaction.getCardNo());
        assertEquals(new BigDecimal(1000), debitTransaction.getAmount());
        assertEquals(true, debitTransaction.getInvoiceDate().equals(LocalDate.of(2018, Month.FEBRUARY, 10)));
    }

    @Test
    public void testAirplusTransactionFileReaderBuilder() {

        AirplusTransactionFileReader airplusTransactionFileReader = new AirplusTransactionFileReader()
                .setImportFile(env.getProperty("app.import.file"))
                .setHeaderLines(Integer.parseInt(env.getProperty("app.import.headerLines")))
                .setDatePattern(env.getProperty("app.import.datePattern"))
                .setFieldSeparator(env.getProperty("app.import.fieldSeparator"))
                .setThousandsSeparator(env.getProperty("app.import.thousandsSeparator").charAt(0))
                .setDecimalSeparator(env.getProperty("app.import.decimalSeparator").charAt(0))
                .init();

        ArrayList<AirplusTransaction> transactions = new ArrayList<>();

        try {
            airplusTransactionFileReader.open(new ExecutionContext());

            while (true) {
                AirplusTransaction transaction;
                transaction = airplusTransactionFileReader.read();

                if (transaction == null) {
                    break;
                } else {
                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            airplusTransactionFileReader.close();
        }

        assertEquals(7, transactions.size());
        assertEquals(0, transactions.get(0).getAmount().compareTo(new BigDecimal(1005.00)));
        assertEquals(LocalDate.of(2018, Month.JANUARY, 2), transactions.get(0).getInvoiceDate());
    }

    @Test
    public void testAirplusTransactionProcessor() throws Exception {

        AirplusTransactionProcessor airplusTransactionProcessor = new AirplusTransactionProcessor();

        AirplusTransaction processedDebitTransaction = airplusTransactionProcessor.process(debitTransaction);
        AirplusTransaction processedCreditTransaction = airplusTransactionProcessor.process(creditTransaction);

        assertEquals(new Character('D'), processedDebitTransaction.getDebitCredit());
        assertEquals(new BigDecimal(1000), processedDebitTransaction.getAmount());
        assertEquals(new Character('C'), processedCreditTransaction.getDebitCredit());
        assertEquals(new BigDecimal(-5.), processedCreditTransaction.getAmount());
    }

    @Test
    public void testAirplusTransactionJdbcWriter() throws Exception {

        AirplusTransactionJdbcWriter jdbcWriter = new AirplusTransactionJdbcWriter(dataSource).init();

        List<AirplusTransaction> transactions = new ArrayList<>();
        transactions.add(debitTransaction);
        transactions.add(creditTransaction);

        jdbcWriter.write(transactions);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<AirplusTransaction> result = jdbcTemplate.query(
                "SELECT * FROM creditcard_transactions where card_no ='ABCxxxx123' ORDER BY invoice_item_no",
                new RowMapper<AirplusTransaction>() {
                    @Override
                    public AirplusTransaction mapRow(ResultSet resultSet, int row) throws SQLException {
                        AirplusTransaction transaction = new AirplusTransaction();
                        transaction.setCardNo(resultSet.getString("card_no"));
                        transaction.setInvoiceItemNo(resultSet.getInt("invoice_item_no"));
                        return transaction;
                    }
                });

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getInvoiceItemNo());
        assertEquals(2, result.get(1).getInvoiceItemNo());
    }
}