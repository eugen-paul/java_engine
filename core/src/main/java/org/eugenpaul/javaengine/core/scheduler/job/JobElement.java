package org.eugenpaul.javaengine.core.scheduler.job;

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

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
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

}
