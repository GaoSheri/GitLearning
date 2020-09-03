package tree;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;

public class AgeCount extends Count{
	
	EnumMap<Age,Integer> ageCount = new EnumMap<Age, Integer>(Age.class);
	
	
	public int getYoungAgeCount() {
		return ageCount.get(Age.YOUNG);
	}
	public int getMiddleAgeCount() {
		return ageCount.get(Age.MIDDLE);
	}
	public int getOldAgeCount() {
		return ageCount.get(Age.OLD);
	}
	public int[] setSum() {
		int[] sum=new int[3];
		sum[0]=getYoungAgeCount();
		sum[1]=getMiddleAgeCount();
		sum[2]=getOldAgeCount();
		return sum;
	}

	public void gather(HashMap records) {
		// TODO Auto-generated method stub
		ageCount.clear();
		Iterator it = records.keySet().iterator();
		Customer c = new Customer();
		
		int young=0;
		int middle=0;
		int old=0;
		while(it.hasNext()) {
			c = (Customer) it.next();
			switch(c.getAge()) {
			case YOUNG:
				young += (int)records.get(c);
				break;
			case MIDDLE:
				middle += (int)records.get(c);
				break;
			case OLD:
				old += (int)records.get(c);
				break;
			default:break;
			}	
		}
		
		ageCount.put(Age.YOUNG,young);
		ageCount.put(Age.MIDDLE,middle);
		ageCount.put(Age.OLD,old);
	}
	@Override
	public HashMap<Customer, Integer> seperate(String value,HashMap<Customer, Integer> rc) {
		// TODO Auto-generated method stub
		Age a = Enum.valueOf(Age.class, value);
		HashMap<Customer,Integer> hm = new HashMap<Customer,Integer>();
		
		Iterator it = rc.keySet().iterator();
		Customer c=new Customer();
		int count=0;
		
		while(it.hasNext()) {
			c=(Customer) it.next();
			if(c.getAge().equals(a)) {
				hm.put(c, rc.get(c));  //按指定值划分集合
				count += rc.get(c);
			}
		}
		ageCount.replace(a, count);
		return hm;
	}
	@Override
	public String getAttrName() {
		// TODO Auto-generated method stub
		return "Age";
	}
	public String[] getAttrValue() {
		// TODO Auto-generated method stub
		String[] a = {"YOUNG","MIDDLE","OLD"};
		return a;
	}
	
}
