package com.example.springbootbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.plugin2.message.Message;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(step1()).on("COMPLETED").to(step2())
                .from(step2()).on("COMPLETED").to(step3())
                .from(step3()).end().build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1").tasklet(
                (stepContribution, chunkContext) -> {
                    System.out.println("spring batch step1");
                    return RepeatStatus.FINISHED;
                }
        ).build();

    }

    @Bean
    public Step step2(){
        return stepBuilderFactory.get("step2").tasklet(
                new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                       System.out.println("spring boot step2");
                        return RepeatStatus.FINISHED;
                    }
                }
        ).build();

    }

    @Bean
    public Step step3(){
        return stepBuilderFactory.get("step3").tasklet(
                new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("spring boot step3");
                        return RepeatStatus.FINISHED;
                    }
                }
        ).build();
    }

    @Bean
    public Step step4(){
        int CHUNK_SIZE=50;
        return  stepBuilderFactory.get("step4")
                .<Message,Message>chunk(CHUNK_SIZE)
                .build();
    }


}
