package org.eugenpaul.javaengine.core.multithreading.scheduler;

/**
 * Job, that cann be added to Jobscheduler
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
   * The function will be called if the job will not be called any more oder after last execute-call (execute return false).
   */
  public void stop();

  /**
   * Get Jobsname
   * 
   * @return
   */
  public String getName();

}
