/**
 * CS 431: Operating Systems
 * Professor: G. S. Young
 *
 * Programming Assignment #1
 *
 * The program implements the following CPU scheduling algorithms. 
 * 1.	First-Come-First-Serve (FCFS)
 * 2.	Shortest-Job-First (SJF)
 * 3.	Round-Robin with time slice = 3 (RR-3)
 * 4.	Round-Robin with time slice = 5 (RR-5)
 *
 * David Scianni
 */
package edu.csupomona.cs.cs431.Project1;

/**
 * Job represents a single job in the scheduling queue. It keeps track of all
 * the elements of that job.
 * 
 * @author David Scianni
 * 
 */
public class Job implements Comparable<Job>, Cloneable {

	/**
	 * The name of the job
	 */
	private String name;
	/**
	 * The burst time for this job
	 */
	private int burstTime;
	/**
	 * The response time
	 */
	private int responseTime;
	/**
	 * The waiting time
	 */
	private int waitingTime;
	/**
	 * the turnaround time
	 */
	private int turnaroundTime;
	/**
	 * the time when the job is completed
	 */
	private int endTime;
	/**
	 * Checks to see if this job has been started yet for round robin
	 */
	private boolean started;

	/**
	 * The constructor sets everything to its base case.
	 */
	public Job() {
		name = null;
		burstTime = 0;
		responseTime = 0;
		waitingTime = 0;
		turnaroundTime = 0;
		endTime = 0;
		started = false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the burstTime
	 */
	public int getBurstTime() {
		return burstTime;
	}

	/**
	 * @param burstTime
	 *            the burstTime to set
	 */
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	/**
	 * @return the responseTime
	 */
	public int getResponseTime() {
		return responseTime;
	}

	/**
	 * @param responseTime
	 *            the responseTime to set
	 */
	public void addResponseTime(int responseTime) {
		this.responseTime += responseTime;
	}

	/**
	 * @return the waitingTime
	 */
	public int getWaitingTime() {
		return waitingTime;
	}

	/**
	 * @param waitingTime
	 *            the waitingTime to set
	 */
	public void addWaitingTime(int waitingTime) {
		this.waitingTime += waitingTime;
	}

	/**
	 * @return the turnaroundTime
	 */
	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	/**
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the started
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * @param started
	 *            the started to set
	 */
	public void setStarted(boolean started) {
		this.started = started;
	}

	/**
	 * @param turnaroundTime
	 *            the turnaroundTime to set
	 */
	public void addTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime += turnaroundTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Job arg0) {
		if (burstTime < arg0.getBurstTime()) {
			return -1;
		} else if (burstTime > arg0.getBurstTime()) {
			return 1;
		} else {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			return null;
		}
	}
}
