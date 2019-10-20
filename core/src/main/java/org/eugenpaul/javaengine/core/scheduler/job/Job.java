package org.eugenpaul.javaengine.core.scheduler.job;

/**
 * Job, that can be added to {@link JobSchedulerThreaded}}
 * 
 * @author Eugen Paul
 *
 */
public interface Job {

  /**
   * Prepare the job to be executed. The function will be called just once for the first execution.
   */
  public void prepare();

  /**
   * Execute a Job
   * 
   * @return - true - job must be executed one more time<br>
   *         false - job is done
   */
  public boolean execute();

  /**
   * The function will be called if the job will not be called any more, after last execute-call (execute return false) or if the JobScheduler will be stoped.
   */
  public void stop();

  /**
   * Get Jobsname
   * 
   * @return
   */
  public String getName();

}
