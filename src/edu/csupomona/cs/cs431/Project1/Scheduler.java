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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Schedulerwill run the program, testing the 4 different scheduling algorithms
 * on each of the three testing files.
 * 
 * @author David Scianni
 * 
 */
public class Scheduler {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		run("testdata1.txt", 1); // test file 1
		run("testdata2.txt", 2); // test file 2
		run("testdata3.txt", 3); // test file 3
	}

	/**
	 * Run will take in a file name, and will retrieve all the data stored in
	 * that file and will save it to an array list of Jobs. It will then call
	 * the 4 different scheduling methods for those jobs.
	 * 
	 * @param fileName
	 *            the name of the file
	 * @param runNum
	 *            which test run it is (1, 2, or 3)
	 */
	public static void run(String fileName, int runNum) {
		File myFile; // used to store the file
		Scanner iFile = null; // used to read the file
		myFile = new File(fileName);
		ArrayList<Job> jobs = new ArrayList<Job>();

		try {
			iFile = new Scanner(myFile);
			while (iFile.hasNext()) {
				jobs.add(new Job());
				jobs.get(jobs.size() - 1).setName(iFile.nextLine());
				jobs.get(jobs.size() - 1).setBurstTime(
						Integer.parseInt(iFile.nextLine()));
			}
			iFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Run of testfile" + runNum + ": \n\n");
		FCFS(cloneList(jobs));
		SJF(cloneList(jobs));
		RR(cloneList(jobs), 3);
		RR(cloneList(jobs), 5);
	}

	/**
	 * This will take a list of Jobs, and return a new list containing the
	 * cloned versions of those jobs.
	 * 
	 * @param jobs
	 *            the original arraylist
	 * @return a new array list, clone
	 */
	private static ArrayList<Job> cloneList(ArrayList<Job> jobs) {
		ArrayList<Job> clone = new ArrayList<Job>(jobs.size());
		for (Job job : jobs) {
			clone.add((Job) job.clone());
		}
		return clone;
	}

	/**
	 * Calls the First Come First Served method, which simulated the FCFS
	 * algorithm by going through each job as it arrives, and calculates its
	 * data, including response time, waiting time, and turnaround time.
	 * 
	 * @param jobs
	 *            an array list of the all the jobs.
	 */
	public static void FCFS(ArrayList<Job> jobs) {
		String[] gannt;
		int ganntSize = 0, responseTime = 0;
		for (Job job : jobs) {
			ganntSize += job.getBurstTime();
			job.addResponseTime(responseTime);
			job.addWaitingTime(responseTime);
			responseTime += job.getBurstTime();
			job.setEndTime(responseTime);
			job.addTurnaroundTime(responseTime);
		}
		gannt = new String[ganntSize];
		for (int i = 0; i < gannt.length; i++) {
			gannt[i] = null;
		}
		for (Job job : jobs) {
			gannt[job.getResponseTime()] = job.getName();
		}
		print(jobs, gannt, "FCFS");
	}

	/**
	 * Calls the Shortest Job First method, which simulated the SJF algorithm by
	 * first sorting the list by the shortest burst time first, then it will go
	 * through each job in the list and calculate its data, including response
	 * time, waiting time, and turnaround time.
	 * 
	 * @param jobs
	 *            an array list of the all the jobs.
	 */
	public static void SJF(ArrayList<Job> jobs) {
		Collections.sort(jobs);
		String[] gannt;
		int ganntSize = 0, responseTime = 0;
		for (Job job : jobs) {
			ganntSize += job.getBurstTime();
			job.addResponseTime(responseTime);
			job.addWaitingTime(responseTime);
			responseTime += job.getBurstTime();
			job.setEndTime(responseTime);
			job.addTurnaroundTime(responseTime);
		}
		gannt = new String[ganntSize];
		for (int i = 0; i < gannt.length; i++) {
			gannt[i] = null;
		}
		for (Job job : jobs) {
			gannt[job.getResponseTime()] = job.getName();
		}
		print(jobs, gannt, "SJF");
	}

	/**
	 * Calls the Round Robin method, which simulated the RR algorithm by going
	 * through each job, but stopping when either the job runs out of burst
	 * time, or when the time queantum is used up. It then calculates its data,
	 * including response time, waiting time, and turnaround time.
	 * 
	 * @param jobs
	 *            an array list of the all the jobs.
	 * @param TQ
	 *            the time quantum
	 */
	public static void RR(ArrayList<Job> jobs, int TQ) {
		String[] gannt;
		int[] bTimes = new int[jobs.size()];
		LinkedList<Job> rr = new LinkedList<Job>();
		int ganntSize = 0, responseTime = 0, counter = 0;
		for (Job job : jobs) {
			ganntSize += job.getBurstTime();
			rr.add(job);
			bTimes[counter++] = job.getBurstTime();
		}
		gannt = new String[ganntSize];
		for (int i = 0; i < gannt.length; i++) {
			gannt[i] = null;
		}
		while (!rr.isEmpty()) {
			Job temp = rr.pop();
			if (!temp.isStarted()) {
				temp.addResponseTime(responseTime);
				temp.setStarted(true);
			}
			gannt[responseTime] = temp.getName();
			temp.addWaitingTime(responseTime - temp.getEndTime());
			if (temp.getBurstTime() > TQ) {
				temp.setBurstTime(temp.getBurstTime() - TQ);
				responseTime += TQ;
				temp.setEndTime(responseTime);
				rr.add(temp);
			} else {
				responseTime += temp.getBurstTime();
				temp.setEndTime(responseTime);
				temp.addTurnaroundTime(responseTime);
			}
		}
		for (int i = 0; i < jobs.size(); i++) {
			jobs.get(i).setBurstTime(bTimes[i]);
		}
		print(jobs, gannt, "RR-" + TQ);
	}

	/**
	 * Prints out all the information including a gannt chart, and each job's
	 * burst time end time response time waiting time and turnaround time. It
	 * also displays the average waiting time and the average turnaround time
	 * for the whole scheduling problem.
	 * 
	 * @param jobs
	 *            the list of jobs
	 * @param gannt
	 *            an array representation of the gannt chart
	 * @param sched
	 *            what type of schedule was used
	 */
	public static void print(ArrayList<Job> jobs, String[] gannt, String sched) {
		System.out.print(sched + ": ");
		for (int i = 0; i < gannt.length; i++) {
			if (gannt[i] == null) {
				System.out.print(" ");
			} else {
				System.out.print("|" + gannt[i]);
			}
		}
		System.out.println("|");

		for (int i = 0; i < sched.length(); i++) {
			System.out.print(" ");
		}
		System.out.print("  ");
		for (int i = 0; i < gannt.length; i++) {
			if (gannt[i] == null) {
				System.out.print(" ");
			} else {
				System.out.print(i);
				if (i < 10) {
					for (int j = 0; j < gannt[i].length(); j++) {
						System.out.print(" ");
					}
				} else if (i < 100) {
					for (int j = 0; j < gannt[i].length() - 1; j++) {
						System.out.print(" ");
					}
				} else {
					for (int j = 0; j < gannt[i].length() - 2; j++) {
						System.out.print(" ");
					}
				}
			}
		}
		System.out.println(gannt.length);

		float avgWaitTime = 0, avgTATime = 0;
		for (Job job : jobs) {
			System.out.println(job.getName() + ": ");
			System.out.println("\tBurst Time: " + job.getBurstTime() + " ms");
			System.out.println("\tEnd Time: " + job.getEndTime() + " ms");
			System.out.println("\tResponse Time: " + job.getResponseTime()
					+ " ms");
			System.out.println("\tWaiting Time: " + job.getWaitingTime()
					+ " ms");
			System.out.println("\tTurnaround Time: " + job.getTurnaroundTime()
					+ " ms" + "\n");
			avgWaitTime += job.getWaitingTime();
			avgTATime += job.getTurnaroundTime();
		}
		System.out.printf("Average Waiting Time: %.2f ms\n",
				avgWaitTime / jobs.size());
		System.out.printf("Average Turnaround Time: %.2f ms\n\n\n", avgTATime
				/ jobs.size());
	}
}
