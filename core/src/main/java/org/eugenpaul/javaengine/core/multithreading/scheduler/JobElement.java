package org.eugenpaul.javaengine.core.multithreading.scheduler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class JobElement implements Comparable<JobElement> {

  private Long timeOfNextStart;
  private Long timeBetweenStart;
  private Job job;

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

}
