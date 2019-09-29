package org.eugenpaul.javaengine.core.multithreading.scheduler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread to run all Jobs.
 * 
 * @author Eugen Paul
 *
 */
class JobExecuter implements Runnable {

  private ReentrantLock lock;

  private Condition condition;

  private JobScheduler scheduler;

  private boolean stopped = false;

  /**
   * C'tor
   * 
   * @param scheduler
   */
  public JobExecuter(JobScheduler scheduler) {
    lock = new ReentrantLock();
    condition = lock.newCondition();

    this.scheduler = scheduler;
  }

  /**
   * Signal to thread: jobs are updated
   */
  public void update() {
    lock.lock();
    condition.signal();
    lock.unlock();
  }

  @Override
  public void run() {
    while (!stopped) {
      JobElement nextJob = waitOfJob();
      if (null != nextJob) {
        if (nextJob.execute()) {
          // add job to queue again
          scheduler.addJob(nextJob);
        }
      }
    }
  }

  private long timeToNextJob() {
    long timeNow = System.nanoTime();
    long startTime = scheduler.timeOfNextJob();
    long timeToStart = startTime - timeNow;
    return timeToStart;
  }

  private JobElement waitOfJob() {
    long nanosRemaining = timeToNextJob();
    while (nanosRemaining > 0) {
      lock.lock();
      try {
        nanosRemaining = condition.awaitNanos(nanosRemaining);
      } catch (InterruptedException e) {
        e.printStackTrace();
        System.out.println("Interrup JobExecuter");
        return null;
      } finally {
        lock.unlock();
      }

      if (nanosRemaining <= 0) {
        break;
      }

      nanosRemaining = timeToNextJob();
    }

    // get and return new Job
    return scheduler.getJobToExecute();
  }

  /**
   * Set thread to stop. Executer cann't be started again.
   */
  public void stop() {
    stopped = true;
    update();
  }

}
