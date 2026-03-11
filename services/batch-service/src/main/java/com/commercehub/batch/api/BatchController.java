package com.commercehub.batch.api;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batch/jobs")
public class BatchController {
  private final JobLauncher jobLauncher;
  private final Job catalogImportJob;

  public BatchController(JobLauncher jobLauncher, Job catalogImportJob) {
    this.jobLauncher = jobLauncher;
    this.catalogImportJob = catalogImportJob;
  }

  @PostMapping("/catalog-import/run")
  public String runCatalogImport() throws Exception {
    JobParameters params = new JobParametersBuilder().addLong("ts", System.currentTimeMillis()).toJobParameters();
    JobExecution execution = jobLauncher.run(catalogImportJob, params);
    return "catalogImportJob executionId=" + execution.getId();
  }
}
