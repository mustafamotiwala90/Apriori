import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

class DataStructure {
	public DataStructure(int type, ArrayList<Integer> indexPos,
			ArrayList<Integer> zeroOne, ArrayList<String> fullString,
			ArrayList<String> upDown,int supportCount) {
		super();
		this.type = type;
		this.indexPos = indexPos;
		this.zeroOne = zeroOne;
		this.fullString = fullString;
		this.upDown = upDown;
		this.supportCount = supportCount;
	}
	int type;
	ArrayList<Integer>indexPos;
	ArrayList<Integer>zeroOne;
	ArrayList<String>fullString;
	ArrayList<String>upDown;
	int supportCount;
}

class DataStructureCon {
	public DataStructureCon(ArrayList<String> head, ArrayList<String> body) {
		super();
		this.head = head;
		this.body = body;
	}
	ArrayList<String>head;
	ArrayList<String>body;
	int confidence;
}
public class Apriori {
	/**
	 * @param args
	 */
	public static String fileData="";
	static int upArray[]=new int[101];
	static int downArrary[] = new int[101];
	public static int sampleData[][] = new int [101][101];

	public static void getInitialCount(StringTokenizer tokens) {
		int count=0;
		int sampleCount=0;
		while(tokens.hasMoreTokens())
		{		
			count++;
			String token = tokens.nextToken();
			if(token.contains("Sample"))
			{
				count = 0;
				sampleCount++;
			}
			if(token.equalsIgnoreCase("up"))
			{
				upArray[count] = upArray[count]+1;
				sampleData[sampleCount][count] = 0;
			}
			else if(token.equalsIgnoreCase("down"))
			{
				downArrary[count] = downArrary[count]+1;
				sampleData[sampleCount][count] = 1;
			}
		}
	}
	public static void readFromFile(String filePath) {
		try {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			DataInputStream dis = null;
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			while (dis.available() != 0)
			{
				String number = dis.readLine();
				fileData+=number;
				fileData+=" ";
			}
			fis.close();
			bis.close();
			dis.close();
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}		
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Enter the file path");
        InputStreamReader input1 = new InputStreamReader(System.in);
        BufferedReader reader1 = new BufferedReader(input1);
        String filePath = reader1.readLine();

        System.out.println("Enter confidence, eg 0.5");
        float confidence = Float.valueOf(reader1.readLine().trim()).floatValue();
        int confidenceCount = (int)(confidence*100);
       
        System.out.println("Enter support, eg 0.4");
        float support1 = Float.valueOf(reader1.readLine().trim()).floatValue();
        int supportCount = (int)(support1*100);

		ArrayList<DataStructure>l1 = new ArrayList<DataStructure>();
		ArrayList<DataStructure>mainList = new ArrayList<DataStructure>();

		int counter = 0;
		for(int i=0;i<=100;i++) {
			upArray[i] = 0;
			downArrary[i] = 0;
		}
		readFromFile(filePath);
		StringTokenizer tokens = new StringTokenizer(fileData);
		getInitialCount(tokens);
		for(int i=1;i<=100;i++) {
			if(upArray[i]>=supportCount) {
				ArrayList<Integer>indexPos = new ArrayList<Integer>();
				ArrayList<Integer>zeroOne = new ArrayList<Integer>();;
				ArrayList<String>fullString = new ArrayList<String>();;
				ArrayList<String>upDown =  new ArrayList<String>();;
				indexPos.add(i);
				zeroOne.add(0);
				fullString.add(i+"u");
				upDown.add("u");
				l1.add(new DataStructure(1,indexPos,zeroOne,fullString,upDown,upArray[i]));
				counter++;
			}
			if(downArrary[i]>=supportCount) {
				ArrayList<Integer>indexPos = new ArrayList<Integer>();
				ArrayList<Integer>zeroOne = new ArrayList<Integer>();
				ArrayList<String>fullString = new ArrayList<String>();
				ArrayList<String>upDown =  new ArrayList<String>();
				indexPos.add(i);
				zeroOne.add(1);
				fullString.add(i+"d");
				upDown.add("d");
				counter++;
				l1.add(new DataStructure(1,indexPos,zeroOne,fullString,upDown,downArrary[i]));
			}
		}
		for(int i=0;i<l1.size();i++) {
			//System.out.println(l1.get(i).supportCount + " " +l1.get(i).fullString);
		}
		int oneCount = l1.size();
		mainList.addAll(l1);
		boolean endCondition = true;
		int loopCounter=1;
		while(endCondition) {
			endCondition = false;
			ArrayList<DataStructure>temp = new ArrayList<DataStructure>();
			temp=l1;
			Map<Integer,ArrayList<String>>myMap = new HashMap<Integer,ArrayList<String>>();
			Map<Integer,ArrayList<Integer>>indexPosMap = new HashMap<Integer,ArrayList<Integer>>();
			Map<Integer,ArrayList<Integer>>zeroOneMap = new HashMap<Integer,ArrayList<Integer>>();
			Map<Integer,ArrayList<String>>upDownMap = new HashMap<Integer,ArrayList<String>>();
			for(int i=0;i<temp.size();i++) {
				ArrayList<Integer>indexPos = new ArrayList<Integer>();
				ArrayList<Integer>zeroOne = new ArrayList<Integer>();
				ArrayList<String>fullString = new ArrayList<String>();
				ArrayList<String>upDown =  new ArrayList<String>();								
				indexPos.addAll(temp.get(i).indexPos);
				fullString.addAll(temp.get(i).fullString);
				zeroOne.addAll(temp.get(i).zeroOne);
				//upDown.add(temp.get(i).upDown.get(j-1));

				myMap.put(i, fullString);
				indexPosMap.put(i,indexPos);
				zeroOneMap.put(i, zeroOne);
				upDownMap.put(i,upDown);
			}
			Map<Integer,ArrayList<String>>myMap2 = new HashMap<Integer,ArrayList<String>>();
			Map<Integer,ArrayList<Integer>>indexPosMap2 = new HashMap<Integer,ArrayList<Integer>>();
			Map<Integer,ArrayList<Integer>>zeroOneMap2 = new HashMap<Integer,ArrayList<Integer>>();
			Map<Integer,ArrayList<String>>upDownMap2 = new HashMap<Integer,ArrayList<String>>();
			Map<Integer,Integer>support = new HashMap<Integer,Integer>();
			int myMap2Counter = 0;
			for(int m=0;m<myMap.size();m++) {
				ArrayList<String>fullString2 = myMap.get(m);
				ArrayList<Integer>indexPos2 = indexPosMap.get(m);
				ArrayList<Integer>zeroOne2 = zeroOneMap.get(m);
				ArrayList<String>upDown2 =   upDownMap.get(m);
				for(int i=m+1;i<myMap.size();i++) {
					ArrayList<String>fullString1 = myMap.get(i);
					ArrayList<Integer>indexPos1 = indexPosMap.get(i);
					ArrayList<Integer>zeroOne1 = zeroOneMap.get(i);
					ArrayList<String>upDown1 =   upDownMap.get(i);
					boolean check = true;
					for(int a=1;a<loopCounter;a++) {
						check=false;
						if(fullString2.get(a-1).equalsIgnoreCase(fullString1.get(a-1)))
							check=true;
						else
							break;
					}

					if(check) {

						ArrayList<String>toBeInsertedFullString = new ArrayList<String>();
						toBeInsertedFullString.addAll(fullString2);
						toBeInsertedFullString.add(fullString1.get(fullString1.size()-1));

						ArrayList<Integer>toBeInsertedIndexPos = new ArrayList<Integer>();
						toBeInsertedIndexPos.addAll(indexPos2);
						toBeInsertedIndexPos.add(indexPos1.get(indexPos1.size()-1));

						ArrayList<Integer>toBeInsertedZeroOne = new ArrayList<Integer>();
						toBeInsertedZeroOne.addAll(zeroOne2);
						toBeInsertedZeroOne.add(zeroOne1.get(zeroOne1.size()-1));	


						int counterL = 0;
						for(int s=1;s<=100;s++) {
							boolean check2=true;
							for(int f=0;f<toBeInsertedFullString.size();f++) {
								check2=false;
								if(sampleData[s][toBeInsertedIndexPos.get(f)]==toBeInsertedZeroOne.get(f))
									check2=true;
								else 
									break;
							}
							if(check2)
								counterL++;
						}
						if(counterL>=supportCount) {
							counter++;
							indexPosMap2.put(myMap2Counter,toBeInsertedIndexPos);
							zeroOneMap2.put(myMap2Counter,toBeInsertedZeroOne);  
							support.put(myMap2Counter, counterL);
							myMap2.put(myMap2Counter++, toBeInsertedFullString);
							endCondition = true;
						}

					}
				}
			}

			l1.clear();
			for(int m2=0;m2<myMap2.size();m2++) {
				ArrayList<String>values = myMap2.get(m2);
				ArrayList<Integer>index = indexPosMap2.get(m2);
				ArrayList<Integer>zo = zeroOneMap2.get(m2);
				ArrayList<String>ud = upDownMap2.get(m2);
				DataStructure ds = new DataStructure(loopCounter+1,index,zo,values,ud,support.get(m2));
				mainList.add(ds);
				l1.add(ds);
				// System.out.print(support.get(m2)+" ");
				for(int v=0;v<values.size();v++) {

					  System.out.print(values.get(v)+" ");
				}

				  System.out.println("");
			}
			loopCounter++;

		}
		System.out.println("Total number of sets "+mainList.size());
		int c =0;
		ArrayList<DataStructureCon> dscon = new ArrayList<DataStructureCon>();
		for(int i=oneCount;i<mainList.size();i++) {

			DataStructure ds = mainList.get(i);
			int totalCount = ds.supportCount;
			int setSize = mainList.get(i).fullString.size();
			int finalValue = (int)(Math.pow(2,setSize));
			String bvalue = "";
			for (int j = 0; j < finalValue; j++) {
				bvalue = Integer.toBinaryString(j);
				int bValueSize = bvalue.length();
				for (int k = 0; k < (setSize - bValueSize); k++) {
					bvalue = "0" + bvalue;
				}
				ArrayList<String>head = new ArrayList<String>();
				ArrayList<Integer>headPos = new ArrayList<Integer>();
				ArrayList<Integer>headZeroOne = new ArrayList<Integer>();
				for (int m = 0; m < setSize; m++) {
					if (bvalue.charAt(m) == '1') {
						head.add(ds.fullString.get(m));
						headPos.add(ds.indexPos.get(m));
						headZeroOne.add(ds.zeroOne.get(m));
					}
				}

				if(head.size()!=ds.fullString.size() && head.size()!=0) {
					ArrayList<String>st = new ArrayList<String>();
					ArrayList<Integer>stPos = new ArrayList<Integer>();
					ArrayList<Integer>stZeroOne = new ArrayList<Integer>();
					for(int o=0;o<ds.fullString.size();o++) {
						String s = ds.fullString.get(o);
						if(!head.contains(s)) {
							st.add(s);
							stPos.add(ds.indexPos.get(o));
							stZeroOne.add(ds.zeroOne.get(o));
						}
					}
					int confidenceSeen = 0;
					for(int s=1;s<=100;s++) {
						boolean check2=true;
						for(int f=0;f<head.size();f++) {
							check2=false;
							if(sampleData[s][headPos.get(f)]==headZeroOne.get(f))
								check2=true;
							else 
								break;
						}
						if(check2)
							confidenceSeen++;
					}	
					//System.out.println(totalCount+" "+confidenceSeen);
					if(((((float)totalCount)/confidenceSeen)*100)>confidenceCount) {
						DataStructureCon dscon1 = new DataStructureCon(st,head);
						dscon.add(dscon1);
					}
				}
			}
		}
		System.out.println(dscon.size());
		for(int i=0;i<dscon.size();i++) {
			printData(dscon.get(i));
		}
//		boolean inputCon = true;
		
			System.out.println("Enter a template or  exit to Exit");
			InputStreamReader input = new InputStreamReader(System.in);
			BufferedReader reader = new BufferedReader(input);
			String string = "";
			input = new InputStreamReader(System.in);
			reader = new BufferedReader(input);

			try {
				string = reader.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			if(string.equals("exit") || string.equals("EXIT"))
//				break;
//			if (string.contentEquals("") || string.contentEquals("\r")
//					|| string.contentEquals("\n"))
//				break;// exiting
//			else
//				System.out.println("You typed: " + string);

			int finalCount = TemplatesAssociationRule(string,dscon);
			System.out.println("Found anObject" +finalCount +" number of rules");

		
	}


	private static int TemplatesAssociationRule(String string,ArrayList<DataStructureCon>dscon)
	{
		int finalCounter =0;
		SortedSet<String> returnSet = new TreeSet<String>();
		if (string.contains("AND")) {
            int c=0;
			c = TemplatesAssociationRule(string.substring(0,string.indexOf(" AND ")),dscon);
			c += TemplatesAssociationRule(string.substring(string.indexOf(" AND ") + 5),dscon);
			return c;
		}
		else if(string.contains("OR"))
		{
			int c=0;
			c = TemplatesAssociationRule(string.substring(0,string.indexOf(" OR ")),dscon);
			c+=TemplatesAssociationRule(string.substring(string.indexOf(" OR ") + 4),dscon);
			return c;
		}
		else if(string.contains("SizeOf"))
		{
			StringTokenizer st1 = new StringTokenizer(string);
			st1.nextToken();
			st1.nextToken();
			int ruleBodyInt = Integer.parseInt(st1.nextToken().toString());

			if (string.contains("SizeOf(BODY)")) 
			{
				StringTokenizer st = new StringTokenizer(string,">=");
				st.nextToken();
				int number = Integer.parseInt(st.nextToken().trim());
				for(int i=0;i<dscon.size();i++) {
					DataStructureCon ds = dscon.get(i);
					if((ds.body.size())>=number) {
						printData(ds);
						finalCounter++;
					}
				}
			}
			else if (string.contains("SizeOf(HEAD)"))
			{
				StringTokenizer st = new StringTokenizer(string,">=");
				st.nextToken();
				int number = Integer.parseInt(st.nextToken().trim());
				for(int i=0;i<dscon.size();i++) {
					DataStructureCon ds = dscon.get(i);
					if((ds.head.size())>=number) {
						printData(ds);
						finalCounter++;
					}
					
				}

			}
			else if (string.contains("SizeOf(RULE)"))
			{
				StringTokenizer st = new StringTokenizer(string,">=");
				st.nextToken();
				int number = Integer.parseInt(st.nextToken().trim());
				for(int i=0;i<dscon.size();i++) {
					DataStructureCon ds = dscon.get(i);
					if((ds.body.size() + ds.head.size())>=number)
					{
						printData(ds);
						finalCounter++;
					}
				}

			}
			else {
				System.err.println("##FORMAT ERROR##");
			}
		}
		else
		{
			String itemString = string.substring(string.lastIndexOf("(") + 1,string.lastIndexOf(")"));
			ArrayList<String> itemSet = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(itemString, ",");
			while (st.hasMoreElements())
			{
				itemSet.add(st.nextToken());
			}
			
			int numberCondition = 0;
			st = new StringTokenizer(string);
			st.nextToken();
			st.nextToken();
			String tempString = st.nextToken();
			if (tempString.equals("ANY")) {
				numberCondition = -2;
			} else if (tempString.equals("NONE")) {
				numberCondition = -4;
			} else {
				numberCondition = Integer.parseInt(tempString);
			}
			int type =0;
			if(string.contains("RULE")) {
				type=1;
			}
			if(string.contains("BODY")) {
				type=2;
				System.out.println("inhere");
			}
			for(int i=0;i<dscon.size();i++) {
				DataStructureCon ds = dscon.get(i);
				ArrayList<String>toBeChecked = new ArrayList<String>();
				if(type==0) {
					toBeChecked.addAll(ds.head);
				}
				if(type==2) {
					toBeChecked.addAll(ds.body);
				}
				if(type==1) {
					toBeChecked.addAll(ds.body);
					toBeChecked.addAll(ds.head);
				}
				boolean present = false;
				int counter = 0;
				for(int a=0;a<itemSet.size();a++) {

					for(int b=0;b<toBeChecked.size();b++) {
						if(itemSet.get(a).equals(toBeChecked.get(b)))
						{
							
							present = true;
							counter++;
						}
					}
				}
				if(!present && numberCondition==-4) {
					printData(dscon.get(i));
					finalCounter++;
				}

				else if(present && numberCondition==-2) {
					printData(dscon.get(i));
					finalCounter++;
				}
				else if(present && numberCondition==counter) {
					printData(dscon.get(i));
					finalCounter++;
				}

			}
		}
		return finalCounter;
	}
	
	public static void printData(DataStructureCon ds) {
		ArrayList<String>body = ds.body;
		ArrayList<String>head = ds.head;
		for(int j =0;j<body.size();j++)
			System.out.print(body.get(j)+" ");
		System.out.print("--->");
		for(int j =0;j<head.size();j++)
			System.out.print(head.get(j)+" ");
		System.out.println("");
	}
}