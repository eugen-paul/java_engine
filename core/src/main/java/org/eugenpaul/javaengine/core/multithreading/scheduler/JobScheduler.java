package org.eugenpaul.javaengine.core.multithreading.scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.eugenpaul.javaengine.core.data.SimpleSortedList;

/**
 * Class to start jobs one or more time.
 * 
 * @author Eugen Paul
 *
 */
public class JobScheduler {
  private Map<String, JobElement> jobs;

  private SimpleSortedList<JobElement> timeLine;

  private Object syncObj = new Object();

  private JobExecuter jobObject = null;
  private Thread jobThread = null;

  /**
   * C'tor
   */
  public JobScheduler() {
    jobs = new HashMap<>();

    timeLine = new SimpleSortedList<>();

    jobObject = new JobExecuter(this);
  }

  /**
   * Start scheduler
   * 
   * @return true - OK<br>
   *         false - already started or not stopped
   */
  public boolean startScheduler() {
    if (null != jobThread) {
      return false;
    }
    jobThread = new Thread(jobObject);
    jobThread.start();
    return true;
  }

  /**
   * Stop scheduler and without wait for scheduler to die.
   */
  public void stopScheduler() {
    if (null == jobThread) {
      return;
    }
    jobObject.stop();
    jobThread.interrupt();
    jobThread = null;
  }

  /**
   * Stop scheduler and wait for scheduler to die.
   */
  public void stopAndWaitScheduler() {
    if (null == jobThread) {
      return;
    }
    jobObject.stop();
    jobThread.interrupt();
    try {
      jobThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    jobThread = null;
  }

  /**
   * Stop scheduler and wait at most millis milliseconds for scheduler to die.
   * 
   * @param millis
   */
  public void stopAndWaitScheduler(int millis) {
    if (null == jobThread) {
      return;
    }
    jobObject.stop();
    jobThread.interrupt();
    try {
      jobThread.join(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    jobThread = null;
  }

  /**
   * Add Job to Scheduler
   * 
   * @param job
   * @param timeToSart        - System.nanoTime + startToStart(ns)
   * @param timeBetweenStarts - time between starts (ns)
   * @return true - OK<br>
   *         false - job.name not unique
   */
  public boolean addJob(Job job, long timeOfFirstStart, long timeBetweenStarts) {
    JobElement newJob = new JobElement(timeOfFirstStart, timeBetweenStarts, job);
    synchronized (syncObj) {
      if (jobs.containsKey(job.getName())) {
        return false;
      }

      jobs.put(job.getName(), newJob);

      timeLine.add(newJob);
    }

    jobObject.update();
    return true;
  }

  /**
   * Add Job to Scheduler
   * 
   * @param newJob
   * @return true - OK<br>
   *         false - job.name not unique
   */
  public boolean addJob(JobElement newJob) {
    synchronized (syncObj) {
      if (jobs.containsKey(newJob.getClass().getName())) {
        return false;
      }

      jobs.put(newJob.getJob().getName(), newJob);

      timeLine.add(newJob);
    }

    jobObject.update();
    return true;
  }

  /**
   * remove job from scheduler
   * 
   * @param name - name of job
   * @return job<br>
   *         null - not found
   */
  public JobElement removeJob(String name) {
    JobElement response = null;
    synchronized (syncObj) {
      if (!jobs.containsKey(name)) {
        return null;
      }

      response = jobs.remove(name);

      timeLine.remove(response);
    }

    jobObject.update();
    return response;
  }

  /**
   * get Job, that must be executed and remove it from scheduler
   * 
   * @return job - job<br>
   *         null - nothing must be executed jet
   */
  public JobElement getJobToExecute() {
    long timeNow = System.nanoTime();

    synchronized (syncObj) {
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
  }

  /**
   * Start time of next job
   * 
   * @return
   */
  public long timeOfNextJob() {
    synchronized (syncObj) {
      JobElement nextElement;
      try {
        nextElement = timeLine.getFirst();
      } catch (NoSuchElementException e) {
        return Long.MAX_VALUE;
      }

      return nextElement.getTimeOfNextStart();
    }
  }
}
