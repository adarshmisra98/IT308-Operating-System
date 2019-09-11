import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class lab3Q1 {		
	
	
	public static void main(String args[])
	{
		String s1,s2,s3,s4;
		Scanner sc=new Scanner(System.in);
			
		s1=sc.next();
		 if(s1.compareTo("vmsim")!=0){
			    System.out.println("Command not found!\n");
			    System.exit(0);
			  }
		 
		s2=sc.next();
		s3=sc.next();
		s4=sc.next();
		
		 
		  int framesize = Integer.valueOf(s2);
		  String filename = s3,algo=s4;
			File fptr = new File(filename);
	
			try {				
				if(framesize<1||framesize>100){
				    System.out.println("Please enter a valid size of physical memory frame! (Between 1 and 100 (both inclusive))\n\n");
				    System.exit(0);
				  }
				else if(algo.compareTo("fifo")!=0 && algo.compareTo("lru")!=0&&algo.compareTo("opt")!=0){
				    System.out.println("Please enter a valid algorithm name!\nYou can choose among the following:\n\n 1.) fifo \n 2.) opt \n 3.) lru \n\n");
				    System.exit(0);
				  }
				int counter=0;
				   sc = new Scanner(fptr);
					while (sc.hasNextInt()) {						counter++;			
					sc.nextInt();
					}
				ArrayList<Integer> num=new ArrayList<Integer>();
				ArrayList<Integer> FIFO=new ArrayList<Integer>();
			for(int i=0;i<counter;i++)
				{ num.add(0); FIFO.add(0);}

			   Scanner sc2 = new Scanner(fptr);
						int pos=0;
						while (sc2.hasNextInt()) {
						int cur=sc2.nextInt();
						num.set(pos,cur);
						FIFO.set(pos,cur); pos++;
					
						}
						//System.out.println(num);
			if(algo.compareTo("fifo")==0)
			{
				FIFO(framesize,counter,FIFO);
			}
			else if(algo.compareTo("opt")==0)
			
			{
				opt(framesize,counter,num);
			}
			else if(algo.compareTo("lru")==0)
			{
				lru(framesize,counter,num);
			}
							 
		}
		catch (FileNotFoundException exception) {
					System.out.println("File doesn't exist!");
					System.exit(0);
				}	
}

	private static void lru(int framesize, int counter, ArrayList<Integer> num) {
		int miss=0,hit=0;
		ArrayList<Integer> frame=new ArrayList<Integer>();
		for(int i=0;i<num.size();i++)
		{			System.out.print(num.get(i)+ "  ");

			boolean f=true;
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
			{	f=false;
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
			System.out.print("[");
			for(int j=0;j<frame.size()-1;j++)
				System.out.print(frame.get(j)+"|");
			System.out.print(frame.get(frame.size()-1));
			System.out.print("]");
			if(i>=framesize && f==false) {System.out.print("F");}			
			System.out.println();
		}
//		miss-=framesize;
//		double missrate=(double)(miss*100)/framesize;
//		System.out.print("Miss rate=" + miss + "/"+ counter+" = " +missrate+"%");
		double missrate=(double)(Math.max(miss-framesize,0)*100)/Math.max(counter-framesize,1);
		System.out.println("Miss rate=" + Math.max(miss-framesize,0) + "/"+ Math.max(counter-framesize,0)+" = " +missrate+"%");

	}


	private static void opt(int framesize, int counter, ArrayList<Integer> num) {
		// TODO Auto-generated method stub
		int miss=0,hit=0;
		HashSet<Integer> frame=new HashSet<Integer>();
		
		for(int i=0;i<num.size();i++)
		{	boolean f=true;

		System.out.print(num.get(i)+ " ");
			if(frame.contains(num.get(i))==true) hit++;
			
			else
			{	f=false;
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

			
		System.out.print("[");
//			for(Integer k: frame)
//				System.out.print(k+"|");
//			System.out.print("]");
//			System.out.println();
//			System.out.print("[");
			ArrayList<Integer> ff=new ArrayList<Integer>(frame);
			
			for(int j=0;j<ff.size()-1;j++)
				System.out.print(ff.get(j)+"|");
			System.out.print(ff.get(ff.size()-1));
			System.out.print("]");
			if(i>=framesize && f==false) {System.out.print("F");}			

			System.out.println();
		}
		System.out.println("");
		//miss-=framesize;
		
//		double missrate=(double)miss/framesize*100;
//		System.out.print("Miss rate=" + miss + "/"+ counter+" = " +missrate+"%");
		double missrate=(double)(Math.max(miss-framesize,0)*100)/Math.max(counter-framesize,1);
		System.out.println("Miss rate=" + Math.max(miss-framesize,0) + "/"+ Math.max(counter-framesize,0)+" = " +missrate+"%");

	}
	
	public static void FIFO(int framesize,int counter,ArrayList<Integer> num)
	{	
		int miss=0,hit=0;
		ArrayList<Integer> frame=new ArrayList<Integer>();
		for(int i=0;i<num.size();i++)
		{	boolean f=true;
		System.out.print(num.get(i)+ " ");

			if(frame.contains(num.get(i))==true) 
				{	
					hit++;
				}
				
			else
			{	f=false;
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
				System.out.print("[");
				for(int j=0;j<frame.size()-1;j++)
					System.out.print(frame.get(j)+"|");
				System.out.print(frame.get(frame.size()-1));
				System.out.print("]");
				
				if(i>=framesize && f==false) {System.out.print("F");}			

				System.out.println();

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
		
	}
}