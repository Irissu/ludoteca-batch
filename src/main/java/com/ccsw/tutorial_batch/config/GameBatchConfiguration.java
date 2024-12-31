package com.ccsw.tutorial_batch.config;

import com.ccsw.tutorial_batch.model.Game;
import com.ccsw.tutorial_batch.model.GameAvailability;
import com.ccsw.tutorial_batch.processor.GameItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class GameBatchConfiguration {

    @Bean
    public JdbcCursorItemReader<Game> gameReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Game>().dataSource(dataSource).name("gameReader").sql("SELECT id, title, age, stock FROM game").rowMapper(new BeanPropertyRowMapper<>(Game.class)).build();
    }

    @Bean
    public ItemProcessor<Game, GameAvailability> processorGame() {
        return new GameItemProcessor();
    }

    @Bean
    public ItemWriter<GameAvailability> writerGame() {
        return new FlatFileItemWriterBuilder<GameAvailability>().name("gameWriter").resource(new FileSystemResource("target/test-outputs/game-output.txt")).lineAggregator(new PassThroughLineAggregator<>()).build();
    }

    @Bean
    public Step step1Game(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcCursorItemReader<Game> gameReader, ItemProcessor<Game, GameAvailability> processorGame, ItemWriter<GameAvailability> writerGame) {
        return new StepBuilder("step1Game", jobRepository).<Game, GameAvailability>chunk(10, transactionManager).reader(gameReader).processor(processorGame).writer(writerGame).build();
    }

    @Bean
    public Job jobGame(JobRepository jobRepository, Step step1Game) {
        return new JobBuilder("jobGame", jobRepository).incrementer(new RunIdIncrementer()).flow(step1Game).end().build();
    }

}
