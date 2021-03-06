package org.eugenpaul.javaengine.core.scheduler.job;

import org.eugenpaul.javaengine.core.clock.Clock;

/**
 * Class to start jobs one or more time. The Jobs will be started in job-thread thread.
 * 
 * @author Eugen Paul
 *
 */
public class JobSchedulerThreaded {
  private JobContainer cont = null;

  private JobThread executer = null;
  private Thread jobThread = null;
  
  private final Clock clock;

  /**
   * C'tor
   */
  public JobSchedulerThreaded(Clock clock) {
    cont = new JobContainer(clock);
    this.clock = clock;
  }

  /**
   * Start scheduler
   * 
   * @return true - OK<br>
   *         false - already started
   */
  public boolean startScheduler() {
    synchronized (this) {
      if (null != jobThread) {
        return false;
      }
      executer = new JobThread(cont, clock);
      jobThread = new Thread(executer);
    }
    jobThread.start();
    return true;
  }

  /**
   * Stop scheduler without waiting for scheduler to die.
   */
  public void stopScheduler() {
    synchronized (this) {
      if (null == jobThread) {
        return;
      }
      executer.stop();
      jobThread.interrupt();
      jobThread = null;
    }
  }

  /**
   * Stop scheduler and wait for scheduler to die. <br>
   * Check "Interrupt" state after function call.
   */
  public void stopAndWaitScheduler() {
    synchronized (this) {
      if (null == jobThread) {
        return;
      }
      executer.stop();
      jobThread.interrupt();
      try {
        jobThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
      jobThread = null;
    }
  }

  /**
   * Stop scheduler and wait at most millis milliseconds for scheduler to die. <br>
   * Check "Interrupt" state after function call.
   * 
   * @param millis
   */
  public void stopAndWaitScheduler(int millis) {
    synchronized (this) {
      if (null == jobThread) {
        return;
      }
      executer.stop();
      jobThread.interrupt();
      try {
        jobThread.join(millis);
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
      jobThread = null;
    }
  }

  /**
   * Add Job to Scheduler.
   * 
   * @param job               - job to be executed
   * @param timeToSart        - timeToStart(ns) (Clock time)
   * @param timeBetweenStarts - time between starts (ns)
   * @return true - OK<br>
   *         false - name of the job is not unique
   */
  public boolean addJob(Job job, long timeOfFirstStart, long timeBetweenStarts) {
    if (!cont.addJob(job, timeOfFirstStart, timeBetweenStarts)) {
      return false;
    }
    if (null != executer) {
      executer.update();
    }

    return true;
  }

  /**
   * Add Job to Scheduler
   * 
   * @param newJob - job to be executed
   * @return true - OK<br>
   *         false - name of the job is not unique
   */
  public boolean addJob(JobElement newJob) {
    if (!cont.addJob(newJob)) {
      return false;
    }
    if (null != executer) {
      executer.update();
    }

    return true;
  }

  /**
   * remove job from scheduler
   * 
   * @param name - name of job
   * @return true - job remove<br>
   *         false - job not found
   */
  public boolean removeJob(String name) {
    JobElement response = cont.removeJob(name);
    if (null == response) {
      return false;
    }

    if (null != executer) {
      executer.update();
    }

    return true;
  }

}
