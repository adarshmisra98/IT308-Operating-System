import java.util.Scanner;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class lab3Q2 {
	public static void main(String args[])
	{
		String s1,fname;
		Scanner sc=new Scanner(System.in);
			//vmgen 10 200 vmrefs.dat
		s1=sc.next();Integer l,r; 
		l=sc.nextInt();
		r=sc.nextInt();
	//	System.out.println(s1);
		 if(s1.compareTo("vmgen")!=0){
			    System.out.println("Command not found!\n");
			    System.exit(0);
			  }
		
		fname=sc.next();
		File f = new File("user.dir/tmp",fname); 
		BufferedWriter wr=null;
		try {
			System.out.println("File created " +fname);
			wr = new BufferedWriter(new FileWriter(fname));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int prev=-1;
		//System.out.println("d");
		for(int i=0;i<r;i++)
		{
			Random rand = new Random(); 
			 int rs = rand.nextInt(l);
			 if(prev==rs)rs = rand.nextInt(l);
			 prev=rs;
			// System.out.println(rs);
			 String ss="";
//			 ss=Integer.toString(rs);
			 ss=rs+" ";
			 try {
				wr.write(ss);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace() ;
			}
		}
		try {
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
