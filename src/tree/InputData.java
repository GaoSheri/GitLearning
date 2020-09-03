package tree;

import java.util.HashMap;
import java.util.Iterator;

public class InputData {
	static HashMap<Customer,Integer> records = new HashMap<Customer,Integer>();
	HashMap<Customer,Integer> Buy = new HashMap<Customer,Integer>();
	HashMap<Customer,Integer> NotBuy = new HashMap<Customer,Integer>();
	
	public InputData(){
		records.put(new Customer(Age.YOUNG,Income.HIGH,false,Credit.FAIR,false),64);
		records.put(new Customer(Age.YOUNG,Income.HIGH,false,Credit.GREAT,false),64);
		records.put(new Customer(Age.MIDDLE,Income.HIGH,false,Credit.FAIR,true),128);
		records.put(new Customer(Age.OLD,Income.MIDDLE,false,Credit.FAIR,true),60);
		records.put(new Customer(Age.OLD,Income.LOW,true,Credit.FAIR,true),64);
		records.put(new Customer(Age.OLD,Income.LOW,true,Credit.GREAT,false),64);
		records.put(new Customer(Age.MIDDLE,Income.LOW,true,Credit.GREAT,true),64);
		records.put(new Customer(Age.YOUNG,Income.MIDDLE,false,Credit.FAIR,false),128);
		records.put(new Customer(Age.YOUNG,Income.LOW,true,Credit.FAIR,true),64);
		records.put(new Customer(Age.OLD,Income.MIDDLE,true,Credit.FAIR,true),132);
		records.put(new Customer(Age.YOUNG,Income.MIDDLE,true,Credit.GREAT,true),64);
		records.put(new Customer(Age.MIDDLE,Income.MIDDLE,false,Credit.GREAT,true),32);
		records.put(new Customer(Age.MIDDLE,Income.HIGH,true,Credit.FAIR,true),32);
		records.put(new Customer(Age.OLD,Income.MIDDLE,false,Credit.GREAT,false),63);
		records.put(new Customer(Age.OLD,Income.MIDDLE,false,Credit.GREAT,true),1);
	}
	
	public static int getSum() {
		int sum = 0;
		for(Object key : records.keySet()) {
			sum += records.get(key);
		}
		return sum;
	}
	
	public void print(HashMap<Customer,Integer> records) {
		for(Object key : records.keySet()) {
			System.out.println(key+" count:"+records.get(key));
		}
	}
	
	public void Seperate() {
		for(Customer c:records.keySet()) {
			if(c.getBuy()) {
				Buy.put(c, records.get(c));
			}else {
				NotBuy.put(c, records.get(c));
			}
		}
	}
	
	public int countBuy(HashMap<Customer,Integer>source) {
		
		Customer c = new Customer();
		int count=0;
		Iterator it = source.keySet().iterator();
		while(it.hasNext()) {
			c=(Customer) it.next();
			if(c.getBuy())
				count += source.get(c);
		}
		return count;
		
	}
	
	public String[] attrValue(int attrIndex) {
		switch(attrIndex) {
		case 0:
			return new String[] {"YOUNG","MIDDLE","OLD"};
		case 1:
			return new String[] {"HIGH","MIDDLE","LOW"};
		case 2:
			return new String[] {"true","false"};
		case 3:
			return new String[] {"FAIR","GREAT"};
		default:
			return null;
		}
		
	}
	
	public String attrName(int Index) {
		switch(Index) {
		case 0:
			return "Age";
		case 1:
			return "Income";
		case 2:
			return "Student";
		case 3:
			return "Credit";
		default:
			return "ERROR";
		}
	}
}
