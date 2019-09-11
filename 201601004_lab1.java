import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.ArrayList;

class node implements Comparable<node> {
	public node(Integer a2, Integer b2, Integer c2) {
		a = a2;
		b = b2;
		c = c2;
	}

	int a, b, c;

	@Override
	public int compareTo(node arg0) {
		// TODO Auto-generated method stub
		return b - arg0.getb();
	}

	public int getb() {
		return b;
	}

	public int geta() {
		return a;
	}

	public int getc() {
		return c;
	}

	public void setb(int b1) {
		b = b1;
	}

	public void seta(int a1) {
		a = a1;
	}

	public void setc(int c1) {
		c = c1;
	}

}

public class lab1 {
	public static void Print_run(int Current_Time, int processRunning) {
		System.out.print("<System Current_Time " + Current_Time + "> Job " + (processRunning) + " is running\n");
	}

	public static void Print_fin(int Current_Time, int processRunning) {
		System.out
				.print("<System Current_Time " + Current_Time + "> Job " + (processRunning) + " is finished.......\n");
	}

	public static void printLast(float a, float b, float c) {
		System.out.println();
		System.out.println("*************************************************************");
		System.out.println("Average waiting time for the Jobs is " + (a));
		System.out.println("Average response time for the Jobs is " + (b));
		System.out.println("Average turnaround time for the Jobs is " + (c));
		System.out.println("*************************************************************");
	}

	public static class ShortestJob implements Comparator<node> {

		@Override
		public int compare(node o1, node o2) {
			return o1.c - o2.c;

		}
	}

	public static void main(String[] arguments) {
		try {// System.out.println(f);

			File ptr = new File(arguments[0]);
			try {

				int counter = 0;
				Scanner sc = new Scanner(ptr);
				while (sc.hasNextInt()) {
					counter++;
					sc.nextInt();
				}
				System.out.println("No of jobs is " + counter / 3);
				List<node> Jobs = new ArrayList<>();
				Scanner s1 = new Scanner(ptr);
				int Total = 0;
				for (int i = 0; i < counter / 3; i++) {
					int p = s1.nextInt();
					int q = s1.nextInt();	
					int r = s1.nextInt();
					Total += r;
					// System.out.println(Total);
					Jobs.add(new node(p, q, r));
				}
				// System.out.println([1]);

				Collections.sort(Jobs);
				for (node n : Jobs)
					System.out.println(n.geta() + " " + n.getb() + " " + n.getc());
				if (arguments[1].equals("FCFS")) {
					System.out.println("Schdeuling algorithm: FCFS");
					FirstComeFirstServe(Jobs, Total);
				} else if (arguments[1].equals("SJF")) {
					System.out.println("Schdeuling algorithm: SJF");
					ShortestJobFirst(Jobs, Total);
				} else if (arguments[1].equals("RR")) {
					System.out.println("Schdeuling algorithm: RR");
					Round_Robin(Jobs, Total, arguments[2]);
				}

				else
					System.out.println("Invalid");
				return;

			} catch (FileNotFoundException exception) {
				System.out.println("File doesn't exist!");

			}

		} catch (Exception exception) {
			System.out.println("Exception occurred");

		}

	}

	public static void ShortestJobFirst(List<node> Jobs, int Total) {
		int Current_Time = 0, Most_Recently_Added = 0, Jobs_Size = Jobs.size();
		// System.out.println(Total);
		float Respone_Time = 0, Turn_Around_Time = 0;

		PriorityQueue<node> SJFQ = new PriorityQueue<node>(Jobs_Size, new ShortestJob());

		int cur = Most_Recently_Added;

		while (true) {
			if (cur < Jobs_Size && Jobs.get(cur).getb() <= Current_Time) {
				SJFQ.add(Jobs.get(cur));
				cur++;
				Most_Recently_Added++;
			} else {
				if (SJFQ.isEmpty() && Jobs.get(cur).getb() > Current_Time) {
					System.out.print("<System Current_Time " + Current_Time + "> Waiting for Jobs...\n");
					Current_Time++;
				} else if (SJFQ.isEmpty()) {
					System.out.print("<System Current_Time " + Current_Time + "> Waiting for Jobs...\n");
					Current_Time++;
					break;
				} else
					break;

			}
		}
		List<node> processTemp = new ArrayList<>();
		while (SJFQ.size() != 0) {
			int running = SJFQ.peek().geta() - 1;
			Respone_Time += (Current_Time - SJFQ.peek().getb());
			for (int l = 0; l < Jobs.get(running).getc(); l++) {
				Print_run(Current_Time, Jobs.get(running).geta());
				Current_Time++;
				cur = Most_Recently_Added;
				while (true) {
					if (cur < Jobs_Size && Jobs.get(cur).getb() <= Current_Time) {
						processTemp.add(Jobs.get(cur));
						cur++;
						Most_Recently_Added++;
					} else {
						if (SJFQ.isEmpty() && Jobs.get(cur).getb() > Current_Time) {
							System.out.print("<System Current_Time " + Current_Time + "> Waiting for Jobs...\n");
							Current_Time++;
						} else if (SJFQ.isEmpty()) {
							System.out.print("<System Current_Time " + Current_Time + "> Waiting for Jobs...\n");
							Current_Time++;
							break;
						} else
							break;

					}
				}
			}
			Turn_Around_Time += (Current_Time - Jobs.get(running).getb());
			Print_fin(Current_Time, Jobs.get(running).geta());
			SJFQ.remove();
			for (int l = 0; l < processTemp.size(); l++)
				SJFQ.add(processTemp.get(l));

			processTemp.clear();
		}
		System.out.print("<System Current_Time " + Current_Time + "> All processes finish....................\n");

		printLast(Respone_Time / Jobs_Size, Respone_Time / Jobs_Size, Turn_Around_Time / Jobs_Size);

	}

	public static void FirstComeFirstServe(List<node> Jobs, int Total) {

		int Current_Time = 0, processRunning = 0, Jobs_Size = Jobs.size();
		float Turn_Around_Time = 0, Respone_Time = 0;
		while (Current_Time < Jobs.get(0).b) {
			System.out.print("<System Current_Time " + Current_Time + "> Waiting for Jobs...\n");
			Current_Time++;
		}
		for (int i = 0; i < Total; i++) {

			Jobs.get(processRunning).setc(Jobs.get(processRunning).getc() - 1);
			if (Jobs.get(processRunning).getc() < 0) {
				Print_fin(Current_Time, Jobs.get(processRunning).geta());
				Turn_Around_Time += (Current_Time - Jobs.get(processRunning).getb());
				processRunning++;
				while (Jobs.get(processRunning).getb() > Current_Time) {
					System.out.print("<System Current_Time " + Current_Time + "> Waiting for Jobs...\n");
					Current_Time++;
				}
				Respone_Time += (Current_Time - Jobs.get(processRunning).getb());
				Jobs.get(processRunning).setc(Jobs.get(processRunning).getc() - 1);
				Print_run(Current_Time, Jobs.get(processRunning).geta());
			} else {
				Print_run(Current_Time, Jobs.get(processRunning).geta());
			}
			Current_Time++;
		}
		Print_fin(Current_Time, Jobs_Size);
		System.out.print("<System Current_Time " + Current_Time + "> All processes finish....................\n");
		Turn_Around_Time += (Current_Time - Jobs.get(Jobs_Size - 1).getb());

		printLast(Respone_Time / Jobs_Size, Respone_Time / Jobs_Size, Turn_Around_Time / Jobs_Size);
	}

	public static void Round_Robin(List<node> Jobs, int Total, String Unit) {

		float Respone_Time = 0, Turn_Around_Time = 0, Previous = 0, Wait_Time = 0;

		int Time_Slice = Integer.valueOf(Unit), Current_Time = 0, i, Most_Recently_Added = 0,
				Jobs_Size = Jobs.size();

		int[] Temp = new int[Jobs_Size];
		int[] lastTime = new int[Jobs_Size];

		Queue<Integer> rrq = new LinkedList<>();

		for (int ii = 0; ii < Jobs_Size; ii++)
			Temp[ii] = Time_Slice;

		for (int ii = 0; ii < Jobs_Size; ii++)
			lastTime[ii] = -1;

		for (i = 0; i < Total; i++) {

			int cur = Most_Recently_Added;

			while (true) {
				if (cur < Jobs_Size && Jobs.get(cur).getb() <= Current_Time && cur < Jobs_Size) {

					for (Integer rr : rrq)
						Respone_Time += (Math.min(Temp[rr], Time_Slice));

					rrq.add(Jobs.get(cur).geta() - 1);
					cur++;
					Most_Recently_Added++;
				} else {
					if (rrq.isEmpty() && Jobs.get(cur).getb() > Current_Time) {
						System.out.println("<System Current_Time " + Current_Time + "> Waiting for Jobs...");
						Current_Time++;
					} else if (rrq.isEmpty()) {
						System.out.println("<System Current_Time " + Current_Time + "> Waiting for Jobs...");
						Current_Time++;
						break;
					} else {
						break;
					}
				}
			}
			if (!rrq.isEmpty()) {
				if (Temp[rrq.peek()] != 0 && Jobs.get(rrq.peek()).getc() != 0) {
					Temp[rrq.peek()] -= 1;
					Jobs.get(rrq.peek()).setc(Jobs.get(rrq.peek()).getc() - 1);
					Print_run(Current_Time, rrq.peek() + 1);
				} else if (Jobs.get(rrq.peek()).getc() == 0) {
					Print_fin(Current_Time, rrq.peek() + 1);
					int completed = rrq.remove();
					Turn_Around_Time += (Current_Time - Jobs.get(completed).getb());
					if (!rrq.isEmpty()) {
						if (lastTime[rrq.peek()] == -1) {
							Wait_Time += (Current_Time - Jobs.get(rrq.peek()).getb());
						} else {
							Wait_Time += (Current_Time - lastTime[rrq.peek()]);
						}
						Jobs.get(rrq.peek()).setc(Jobs.get(rrq.peek()).getc() - 1);
						Temp[rrq.peek()] -= 1;
						Previous = rrq.peek();
						Print_run(Current_Time, rrq.peek() + 1);
					}
				} else if (Temp[rrq.peek()] == 0) {
					int Popped = rrq.remove();
					lastTime[Popped] = Current_Time;
					Temp[Popped] = Time_Slice;
					rrq.add(Popped);
					if (!rrq.isEmpty()) {
						if (lastTime[rrq.peek()] == -1) {
							Wait_Time += (Current_Time - Jobs.get(rrq.peek()).getb());
						} else {
							Wait_Time += (Current_Time - lastTime[rrq.peek()]);
						}
						Jobs.get(rrq.peek()).setc(Jobs.get(rrq.peek()).getc() - 1);
						Temp[rrq.peek()] -= 1;
						Previous = rrq.peek();
						Print_run(Current_Time, rrq.peek() + 1);
					}
				}
				Current_Time++;
			}
		}
		Print_fin(Current_Time, Jobs_Size);
		Turn_Around_Time += (Current_Time - Jobs.get((int) Previous).getb());
		System.out.print("<System Current_Time " + Current_Time + "> All processes finish....................\n");
		printLast(Wait_Time / Jobs_Size, Respone_Time / Jobs_Size, Turn_Around_Time / Jobs_Size);
	}

}