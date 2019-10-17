package org.eugenpaul.javaengine.core.multithreading.scheduler;

import lombok.Getter;
import lombok.Setter;

/**
 * All Data of one Job, that can be executed from {@link JobThread}
 * 
 * @author Eugen Paul
 *
 */
public class JobElement implements Comparable<JobElement> {

  @Getter
  @Setter
  private Long timeOfNextStart;
  @Getter
  @Setter
  private Long timeBetweenStart;
  @Getter
  private Job job;
  @Getter
  private boolean deleted = false;

  private JobStopCallback stopCallback;

  /**
   * C'tor
   * 
   * @param timeOfNextStart
   * @param timeBetweenStart
   * @param job
   */
  public JobElement(Long timeOfNextStart, Long timeBetweenStart, Job job) {
    this.timeOfNextStart = timeOfNextStart;
    this.timeBetweenStart = timeBetweenStart;
    this.job = job;

    this.deleted = false;
    this.stopCallback = null;
  }

  /**
   * C'tor
   * 
   * @param timeOfNextStart
   * @param timeBetweenStart
   * @param job
   * @param stopCallback
   */
  public JobElement(Long timeOfNextStart, Long timeBetweenStart, Job job, JobStopCallback stopCallback) {
    this(timeOfNextStart, timeBetweenStart, job);
    this.stopCallback = stopCallback;
  }

  @Override
  public int compareTo(JobElement o) {
    if (getTimeOfNextStart() > o.getTimeOfNextStart()) {
      return 1;
    } else if (getTimeOfNextStart() < o.getTimeOfNextStart()) {
      return -1;
    }
    return 0;
  }

  /**
   * execute Job and Update time of nextStart.
   * 
   * @return
   */
  public boolean execute() {
    boolean response = job.execute();

    timeOfNextStart += timeBetweenStart;

    return response;
  }

  /**
   * Set the job as deleted. The job will not be executed any more. Cann't be undone.
   */
  public void setDeleted() {
    deleted = true;
  }

  public void callStop() {
    stopCallback.lastRun(job);
  }

}
