package com.commercehub.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
  @Bean
  Job catalogImportJob(JobRepository jobRepository, Step catalogImportStep) {
    return new JobBuilder("catalogImportJob", jobRepository).start(catalogImportStep).build();
  }

  @Bean
  Step catalogImportStep(JobRepository jobRepository, PlatformTransactionManager txManager) {
    return new StepBuilder("catalogImportStep", jobRepository)
        .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED, txManager)
        .build();
  }
}
