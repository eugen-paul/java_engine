package org.eugenpaul.javaengine.core.multithreading.scheduler;

/**
 * Job, that cann be added to Jobscheduler
 * 
 * @author Eugen Paul
 *
 */
public interface Job {

  /**
   * Execute a Job
   * 
   * @return - true - job must be executed one more time<br>
   *         false - job is done
   */
  public boolean execute();

  /**
   * Get Jobsname
   * 
   * @return
   */
  public String getName();

}
