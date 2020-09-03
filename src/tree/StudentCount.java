package tree;

import java.util.HashMap;
import java.util.Iterator;

public class StudentCount extends Count{
	
	HashMap<Boolean, Integer> studentCount = new HashMap<Boolean, Integer>();
	
	public int getStudentCount() {
		return studentCount.get(true);
	}
	public int getNotStudentCount() {
		return studentCount.get(false);
	}
	@Override
	public int[] setSum() {
		// TODO Auto-generated method stub
		int[] sum=new int[2];
		sum[0]=getStudentCount();
		sum[1]=getNotStudentCount();
		return sum;

	}
	
	@Override
	public void gather(HashMap records) {
		// TODO Auto-generated method stub
		studentCount.clear();
		Iterator it = records.keySet().iterator();
		Customer c = new Customer();
		
		int count=0;
		int not=0;
		while(it.hasNext()) {
			c = (Customer) it.next();
			if(c.getStudent())
				count += (int)records.get(c);
			else
				not += (int)records.get(c);
				
		}
		
		studentCount.put(true,count);
		studentCount.put(false,not);
	}
	@Override
	public HashMap<Customer, Integer> seperate(String value, HashMap<Customer, Integer> rc) {
		// TODO Auto-generated method stub
		Boolean s = Boolean.valueOf(value);
		HashMap<Customer,Integer> hm = new HashMap<Customer,Integer>();
		
		Iterator it = rc.keySet().iterator();
		Customer c=new Customer();
		int count=0;
		
		while(it.hasNext()) {
			c=(Customer) it.next();
			if(c.getStudent()==s) {
				hm.put(c, rc.get(c));  //按指定值划分集合
				count += rc.get(c);
			}
		}
		studentCount.replace(s, count);
		return hm;
	}
	@Override
	public String getAttrName() {
		// TODO Auto-generated method stub
		return "Student";
	}
	@Override
	public String[] getAttrValue() {
		// TODO Auto-generated method stub
		String[] a = {"true","false"};
		return a;
	}
	
	
}
