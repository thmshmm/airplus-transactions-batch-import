package de.thmshmm.airplus;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class AirplusTransactionFileReader extends FlatFileItemReader<AirplusTransaction> {

    private String importFile;

    private Integer headerLines;

    private String fieldSeparator;

    private Character thousandsSeparator;

    private Character decimalSeparator;

    private String datePattern;

    public AirplusTransactionFileReader() {
    }

    public AirplusTransactionFileReader init() {
        setLinesToSkip(headerLines);
        setResource(new FileSystemResource(importFile));
        setLineMapper(new DefaultLineMapper<AirplusTransaction>() {{
            setLineTokenizer(new DelimitedLineTokenizer(fieldSeparator) {{
                setNames(new String[]{"cardNo", "invoiceNo", "invoiceDate", "invoiceItemNo", "purchaseDate", "entryDate", "serviceProvider", "serviceDescription", "currency", "amount", "debitCredit",});
                setStrict(false);
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<AirplusTransaction>() {{

                setTargetType(AirplusTransaction.class);

                HashMap<Object, PropertyEditorSupport> customEditors = new HashMap<>();
                customEditors.put(LocalDate.class, new LocalDateEditor().setPattern(datePattern));
                customEditors.put(BigDecimal.class, new BigDecimalCurrencyEditor().setThousandsSeparator(thousandsSeparator).setDecimalSeparator(decimalSeparator));

                setCustomEditors(customEditors);
            }});
        }});

        return this;
    }

    public String getImportFile() {
        return importFile;
    }

    public AirplusTransactionFileReader setImportFile(String importFile) {
        this.importFile = importFile;
        return this;
    }

    public Integer getHeaderLines() {
        return headerLines;
    }

    public AirplusTransactionFileReader setHeaderLines(Integer headerLines) {
        this.headerLines = headerLines;
        return this;
    }

    public String getFieldSeparator() {
        return fieldSeparator;
    }

    public AirplusTransactionFileReader setFieldSeparator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
        return this;
    }

    public Character getThousandsSeparator() {
        return thousandsSeparator;
    }

    public AirplusTransactionFileReader setThousandsSeparator(Character thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
        return this;
    }

    public Character getDecimalSeparator() {
        return decimalSeparator;
    }

    public AirplusTransactionFileReader setDecimalSeparator(Character decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
        return this;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public AirplusTransactionFileReader setDatePattern(String datePattern) {
        this.datePattern = datePattern;
        return this;
    }
}
