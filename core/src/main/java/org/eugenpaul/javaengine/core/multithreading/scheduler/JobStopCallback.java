package org.eugenpaul.javaengine.core.multithreading.scheduler;

/**
 * Will be called after last execution or deleting the job.
 * 
 * @author Eugen Paul
 *
 */
public interface JobStopCallback {

  /**
   * Callback
   * 
   * @param job
   */
  public void lastRun(Job job);
}
