package de.thmshmm.airplus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.sql.DataSource;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(BatchConfiguration.class);

    @Inject
    private JobBuilderFactory jobBuilderFactory;

    @Inject
    private StepBuilderFactory stepBuilderFactory;

    @Inject
    private DataSource dataSource;

    @Inject
    private Environment env;

    @Bean
    public Job job(Step step1) throws Exception {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(FlatFileItemReader<AirplusTransaction> reader, ItemProcessor<AirplusTransaction, AirplusTransaction> processor, JdbcBatchItemWriter<AirplusTransaction> writer) {
        return stepBuilderFactory.get("step1")
                .<AirplusTransaction, AirplusTransaction>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<AirplusTransaction> reader() {
        return new AirplusTransactionFileReader()
                .setImportFile(env.getProperty("app.import.file"))
                .setHeaderLines(Integer.parseInt(env.getProperty("app.import.headerLines")))
                .setDatePattern(env.getProperty("app.import.datePattern"))
                .setFieldSeparator(env.getProperty("app.import.fieldSeparator"))
                .setThousandsSeparator(env.getProperty("app.import.thousandsSeparator").charAt(0))
                .setDecimalSeparator(env.getProperty("app.import.decimalSeparator").charAt(0))
                .init();
    }

    @Bean
    public ItemProcessor<AirplusTransaction, AirplusTransaction> processor() {
        return new AirplusTransactionProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<AirplusTransaction> writer() {
        return new AirplusTransactionJdbcWriter(dataSource).init();
    }
}
