package com.programming;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class TestClass {
	
	public void memoriseME() throws Exception {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		int size=Integer.parseInt(br.readLine());
		int a[] = new int[1001];
		String[] array=br.readLine().split(" ");
		int noOfQueries=Integer.parseInt(br.readLine());
		int query;
		for(int i=0; i<size; i++) {
			a[Integer.parseInt(array[i])]++;
		}
		for(int i=0; i<noOfQueries; ++i) {
			query=Integer.parseInt(br.readLine());	
			if(a[query]==0) System.out.println("NOT PRESENT");
			else System.out.println(a[query]);
		}
	}
	public void binaryQueries() throws Exception {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		String[]  inputline=br.readLine().split(" ");
		int n=Integer.parseInt(inputline[0]);
		int noOfQueries=Integer.parseInt(inputline[1]);
		int binaryArray[]= new int[n];
		String[]  binaryArrayInput=br.readLine().split(" ");
		for(int i=0; i<n; i++) {
			binaryArray[i]=Integer.parseInt(binaryArrayInput[i]);
		}
		String num="";
		for(int i=0; i<noOfQueries; ++i) {
		String[] query=br.readLine().split(" ");
		if(Integer.parseInt(query[0])==1) {
			int j=Integer.parseInt(query[1])-1;
			System.out.println(binaryArray[j]);
			binaryArray[j]=binaryArray[j];
			System.out.println(binaryArray[j]);
			}
		if(Integer.parseInt(query[0])==0) {
			for(int j=Integer.parseInt(query[1])-1;j<Integer.parseInt(query[2]); j++) {
				num+=binaryArray[j];
			}
		}
		System.out.println(num);
		}
	}
	public void welcomeProblem() throws Exception {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		int size=Integer.parseInt(br.readLine());
		String[]  arrayofA=br.readLine().split(" ");
		String[]  arrayofB=br.readLine().split(" ");
		int arrayofC[]= new int [size];
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<size; i++) {
			arrayofC[i]=Integer.parseInt(arrayofA[i])+Integer.parseInt(arrayofB[i]);
			sb.append(arrayofC[i]+" ");
		}
		System.out.println(sb);
	}
	public void Hamiltonian() throws Exception {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		int size=Integer.parseInt(br.readLine());
		String[] array=br.readLine().split(" ");
		int largest=Integer.parseInt(array[size-1]);
		int c;
		String output=""+largest;
		for(int i=size-2; i>=0; --i) {
			c=Integer.parseInt(array[i]);
			if(c>largest) {
				largest=c;
				output=largest+" "+output;
				}
			
		}
		System.out.println(output);
		
	}

	public static void main(String args[] ) throws Exception {
		
		/*
		 * //TestClass tc= new TestClass(); tc.Hamiltonian(); List<String> list1 = new
		 * ArrayList<String>(); list1.add("nayeem"); list1.add("john"); List<String>
		 * list2=new ArrayList<String>(); list2.add("nayeem"); list2.add("john");
		 * list2.add("CDSS"); boolean flag=false; list2.add(0, String.valueOf(flag));
		 * System.out.println(list2);
		 * System.out.println(Boolean.parseBoolean(list2.get(0)));
		 * 
		 * System.out.println(list2.removeAll(list1)); System.out.println(list2);
		 * System.out.println(list1); String name= " JOhn ";
		 * 
		 * System.out.println(name.equalsIgnoreCase(name.trim()));
		 */
		 
		/*
		 * JSONObject mediaJSON=new JSONObject(); mediaJSON.put("optionCGI", "HJJJJJ");
		 * mediaJSON.put("optionType", true); mediaJSON.put("answerOption","subType");
		 * System.out.println(mediaJSON.toString(2));
		 */
		String s1= "The following diagram shows an action potential being initiated at the base of an axon and moving down toward the terminal. The plus and minus signs indicate the membrane potentials along the membrane. The base of the axon is on the left, and the axon terminal is on the right.In the diagram, an action potential has just been triggered in Step 1. Answer the following questions about how an action potential spreads.   Step 1: The action potential causes a sudden positive spike in membrane potential due to the influx of sodium ions.Step 2: The action potential at the base leads to which of the following?";
		String s2= "The following diagram shows an action potential being initiated at the base of an axon and moving down toward the terminal. The plus and minus signs indicate the membrane potentials along the membrane. The base of the axon is on the left, and the axon terminal is on the right.In the diagram, an action potential has just been triggered in Step 1. Answer the following questions about how an action potential spreads.  	Step 1: The action potential causes a sudden positive spike in membrane potential due to the influx of sodium ions.Step 2: The action potential at the base leads to which of the following?";
		System.out.println(s1.contains(s2));
		System.out.println(s2.contains(s1));
		s1="   ";
		s2="	";
		System.out.println(s1.isEmpty());
		System.out.println("Teimmed"+s1.trim()+".");
		System.out.println(s1.length());
		System.out.println(s2.length());
		
		boolean valid=true;
		valid &=true;
		System.out.println(valid);
		valid &=false;
		System.out.println(valid);
	 }


}
