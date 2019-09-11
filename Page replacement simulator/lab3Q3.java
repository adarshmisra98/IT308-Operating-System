import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class lab3Q3 {
	public static void main(String args[]) throws IOException
	{
		String s1,fname;
		Scanner sc=new Scanner(System.in);
			//vmstats 5 40 10 vmrefs.dat
		s1=sc.next();Integer l,r,u; 
		l=sc.nextInt();
		r=sc.nextInt();
		u=sc.nextInt();
		//System.out.println(s1);
		 if(s1.compareTo("vmstats")!=0){
			    System.out.println("Command not found!\n");
			    System.exit(0);
			  }
		
		fname=sc.next();
		File fptr = new File(fname);
				int counter=0;
		   try {
			sc = new Scanner(fptr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			while (sc.hasNextInt()) {						counter++;			
			sc.nextInt();
			}
		ArrayList<Integer> num=new ArrayList<Integer>();
				for(int i=0;i<counter;i++) 		 num.add(0); 

	   Scanner sc2 = null;
	try {
		sc2 = new Scanner(fptr);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
				int pos=0;
				while (sc2.hasNextInt()) {
				int cur=sc2.nextInt();
				num.set(pos,cur); pos++;				
										 }
				fname="vmrates.txt";
		        List<Double> l1 = new ArrayList<Double>(); 
		        File f = new File("user.dir/tmp",fname); 
				BufferedWriter wr=null;
				try {
				//	System.out.println(fname);
					wr = new BufferedWriter(new FileWriter(fname));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				wr.write("Frames ");
				for(int framesize=l;framesize<=r;framesize+=u)
				wr.write(framesize+" ");
				

				wr.write('\n');

				wr.write("Opt ");
				for(int framesize=l;framesize<=r;framesize+=u)
				{	
					System.out.print("opt, "+framesize+" frames :");
					double rr=opt(framesize,counter,num);
					double rrr = Double.parseDouble(String.format("%.2f", rr));
					wr.write(rrr+" ");
					
				}
				wr.write('\n');

				wr.write("Lru ");
		for(int framesize=l;framesize<=r;framesize+=u)
		{
			System.out.print("lru, "+framesize+" frames :");
			
			double rr=lru(framesize,counter,num);
			double rrr = Double.parseDouble(String.format("%.2f", rr));
			wr.write(rrr+" ");
		}

		wr.write('\n');

		wr.write("Fifo ");
		for(int framesize=l;framesize<=r;framesize+=u)
		{
			System.out.print("fifo, "+framesize+" frames :");
			double rr=FIFO(framesize,counter,num);
			double rrr = Double.parseDouble(String.format("%.2f", rr));
			wr.write(rrr+" ");
		}

		wr.write('\n');
		wr.close();
	}
	
	
	private static double lru(int framesize, int counter, ArrayList<Integer> num) {
		int miss=0,hit=0;
		ArrayList<Integer> frame=new ArrayList<Integer>();
		for(int i=0;i<num.size();i++)
		{
			if(frame.contains(num.get(i))==true) 
				{	
					hit++;
					ArrayList<Integer> newframe=new ArrayList<Integer>();
					for(int j=0;j<frame.size();j++)
					{
						if(frame.get(j)==num.get(i))
							continue;
						newframe.add(frame.get(j));
					}
					newframe.add(num.get(i));
					//System.out.println(frame.size()+","+newframe.size());
					frame=newframe;

					//System.out.println(frame.size());
				}
				
			else
			{
				miss++;
				if(frame.size()<framesize)
				{
					frame.add(num.get(i));					
				}
				else
				{
					//int remove=frame.remove(0);
					ArrayList<Integer> newframe=new ArrayList<Integer>();
					for(int j=1;j<frame.size();j++)
					{
						newframe.add(frame.get(j));
					}
					frame=newframe;
					frame.add(num.get(i));
				}
				//vmsim 3 input.dat lru
			}
//			System.out.print("[");
//			for(int j=0;j<frame.size()-1;j++)
//				System.out.print(frame.get(j)+"|");
//			System.out.print(frame.get(frame.size()-1));
//			System.out.print("]");
//			
//			System.out.println();
		}
		double missrate=(double)(Math.max(miss-framesize,0)*100)/Math.max(counter-framesize,1);
		System.out.println("Miss rate=" + Math.max(miss-framesize,0) + "/"+ Math.max(counter-framesize,0)+" = " +missrate+"%");
return missrate;
	}


	private static double opt(int framesize, int counter, ArrayList<Integer> num) {
		// TODO Auto-generated method stub
		int miss=0,hit=0;
		HashSet<Integer> frame=new HashSet<Integer>();
		
		for(int i=0;i<num.size();i++)
		{
			if(frame.contains(num.get(i))==true) hit++;
			
			else
			{
				miss++;
				if(frame.size()<framesize)
				{
					frame.add(num.get(i)); 
				}
				else
				{
					HashSet<Integer> cur=new HashSet<Integer>(frame);
	
				for(int j=i+1;j<num.size() && cur.size()>1;j++)
						if(cur.contains(num.get(j)))
						cur.remove(num.get(j));
					
			//vmsim 3 input.dat opt
				int remove=-1;
				for (Integer k: cur)
				 remove=k;
				if(remove!=-1)
				frame.remove(remove);
				
				frame.add(num.get(i));
				}
				
			}

			
		//System.out.print("[");
//			for(Integer k: frame)
//				System.out.print(k+"|");
//			System.out.print("]");
//			System.out.println();
//			System.out.print("[");
//			ArrayList<Integer> ff=new ArrayList<Integer>(frame);
//			
//			for(int j=0;j<ff.size()-1;j++)
//				System.out.print(ff.get(j)+"|");
//			System.out.print(ff.get(ff.size()-1));
//			System.out.print("]");
//			
//			System.out.println();
		}
		//System.out.println("");
		double missrate=(double)(Math.max(miss-framesize,0)*100)/Math.max(counter-framesize,1);
		System.out.println("Miss rate=" + Math.max(miss-framesize,0) + "/"+ Math.max(counter-framesize,0)+" = " +missrate+"%");
return missrate;
	}
	
	public static double FIFO(int framesize,int counter,ArrayList<Integer> num)
	{	
		int miss=0,hit=0;
		ArrayList<Integer> frame=new ArrayList<Integer>();
		for(int i=0;i<num.size();i++)
		{
			if(frame.contains(num.get(i))==true) 
				{	
					hit++;
				}
				
			else
			{
				miss++;
				if(frame.size()<framesize)
				{
					frame.add(num.get(i));					
				}
				else
				{
					//int remove=frame.remove(0);
					ArrayList<Integer> newframe=new ArrayList<Integer>();
					for(int j=1;j<frame.size();j++)
					{
						newframe.add(frame.get(j));
					}
					frame=newframe;
					frame.add(num.get(i));
				}

			}
//			System.out.print("[");
//			for(int j=0;j<frame.size()-1;j++)
//				System.out.print(frame.get(j)+"|");
//			System.out.print(frame.get(frame.size()-1));
//			System.out.print("]");
//			
//			System.out.println();
		}
		double missrate=(double)(Math.max(miss-framesize,0)*100)/Math.max(counter-framesize,1);
		System.out.println("Miss rate=" + Math.max(miss-framesize,0) + "/"+ Math.max(counter-framesize,0)+" = " +missrate+"%");
		return missrate;
	}
}
