package org.eugenpaul.javaengine.core.scheduler.job;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eugenpaul.javaengine.core.clock.Clock;
import org.eugenpaul.javaengine.core.data.SimpleSortedList;

/**
 * Synchronized Job Container to save Jobs
 * 
 * @author Eugen Paul
 *
 */
class JobContainer {
  private Map<String, JobElement> jobs;

  private SimpleSortedList<JobElement> timeLine;
  
  private final Clock clock;

  public JobContainer(Clock clock) {
    jobs = new HashMap<>();

    timeLine = new SimpleSortedList<>();
    
    this.clock = clock;
  }

  /**
   * Add Job to Scheduler. Use Clock time to set timeOfFirstStart.
   * 
   * @param job
   * @param timeToSart        - startToStart(ns)
   * @param timeBetweenStarts - time between starts (ns)
   * @return true - OK<br>
   *         false - job.name not unique
   */
  public synchronized boolean addJob(Job job, long timeOfFirstStart, long timeBetweenStarts) {
    JobElement newJob = new JobElement(timeOfFirstStart, timeBetweenStarts, job);

    return addJob(newJob);
  }

  /**
   * Add Job to Scheduler
   * 
   * @param newJob
   * @return true - OK<br>
   *         false - job.name not unique
   */
  public synchronized boolean addJob(JobElement newJob) {
    if (jobs.containsKey(newJob.getClass().getName())) {
      return false;
    }

    jobs.put(newJob.getJob().getName(), newJob);

    timeLine.add(newJob);

    newJob.getJob().prepare();

    return true;
  }

  /**
   * remove job from scheduler
   * 
   * @param name - name of job
   * @return job<br>
   *         null - not found
   */
  public synchronized JobElement removeJob(String name) {
    JobElement response = null;
    if (!jobs.containsKey(name)) {
      return null;
    }

    response = jobs.remove(name);

    timeLine.remove(response);

    response.getJob().stop();

    return response;
  }

  /**
   * remove all jobs and send stop to each job.
   */
  public synchronized void removeAll() {
    Iterator<JobElement> iterator = jobs.values().iterator();
    while (iterator.hasNext()) {
      JobElement job = iterator.next();
      job.getJob().stop();
    }

    timeLine.clear();
  }

  /**
   * get Job, that must be executed and remove it from scheduler
   * 
   * @return job - job<br>
   *         null - nothing must be executed jet
   */
  public synchronized JobElement getJobToExecute() {
    long timeNow = clock.time();

    JobElement nextElement;
    try {
      nextElement = timeLine.getFirst();
    } catch (NoSuchElementException e) {
      return null;
    }

    if (timeNow < nextElement.getTimeOfNextStart()) {
      return null;
    }

    timeLine.remove(0);

    jobs.remove(nextElement.getJob().getName());

    return nextElement;
  }

  /**
   * Start time of next job
   * 
   * @return
   */
  public synchronized long timeOfNextJob() {
    JobElement nextElement;
    try {
      nextElement = timeLine.getFirst();
    } catch (NoSuchElementException e) {
      return Long.MAX_VALUE;
    }

    return nextElement.getTimeOfNextStart();
  }
}
