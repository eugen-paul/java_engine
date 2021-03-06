package org.eugenpaul.javaengine.core.scheduler.job;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.eugenpaul.javaengine.core.clock.Clock;

/**
 * Thread to run all Jobs.
 * 
 * @author Eugen Paul
 *
 */
class JobThread implements Runnable {

  private ReentrantLock lock;

  private Condition condition;

  private JobContainer cont;

  private boolean stopped = false;
  
  private final Clock clock;

  /**
   * C'tor
   * 
   * @param scheduler
   */
  public JobThread(JobContainer cont, Clock clock) {
    lock = new ReentrantLock();
    condition = lock.newCondition();

    this.cont = cont;
    this.clock = clock;
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
      JobElement nextJob = waitForJob();
      executeOneJob(nextJob);
    }

    cont.removeAll();
  }

  /**
   * 
   * @param nextJob
   */
  private void executeOneJob(JobElement nextJob) {
    if (null != nextJob) {
      // check before execution
      if (nextJob.isDeleted()) {
        nextJob.getJob().stop();
        return;
      }
      if (nextJob.execute()) {
        // check after execution
        if (nextJob.isDeleted()) {
          nextJob.getJob().stop();
          return;
        }
        // add job to queue again
        cont.addJob(nextJob);
      } else {
        nextJob.getJob().stop();
      }
    }
  }

  /**
   * 
   * @return
   */
  private long timeToNextJob() {
    long timeNow = clock.time();
    long startTime = cont.timeOfNextJob();
    return startTime - timeNow;
  }

  /**
   * 
   * @return
   */
  private JobElement waitForJob() {
    long nanosRemaining = timeToNextJob();
    while (nanosRemaining > 0) {
      lock.lock();
      try {
        nanosRemaining = condition.awaitNanos(nanosRemaining);
      } catch (InterruptedException e) {
//        e.printStackTrace();
        System.out.println("Interrupt JobExecuter");
        stopped = true;
        Thread.currentThread().interrupt();
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
    return cont.getJobToExecute();
  }

  /**
   * Set thread to stop. Executer cann't be started again.
   */
  public void stop() {
    stopped = true;
    update();
  }

}
