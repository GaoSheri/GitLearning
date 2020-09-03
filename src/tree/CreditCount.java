package tree;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;

public class CreditCount extends Count{
	
	EnumMap<Credit,Integer> creditCount = new EnumMap<Credit, Integer>(Credit.class);
	
	public int getFairCreditCount() {
		return creditCount.get(Credit.FAIR);
	}
	public int getGreatCreditCount() {
		return creditCount.get(Credit.GREAT);
	}
	@Override
	public int[] setSum() {
		// TODO Auto-generated method stub
		int[] sum=new int[2];
		sum[0]=getFairCreditCount();
		sum[1]=getGreatCreditCount();
		return sum;
	}
	
	@Override
	public void gather(HashMap records) {
		// TODO Auto-generated method stub
		creditCount.clear();
		Iterator it = records.keySet().iterator();
		Customer c = new Customer();
		
		int fair=0;
		int great=0;
		while(it.hasNext()) {
			c = (Customer) it.next();
			if(c.getCredit()==Credit.FAIR) {
				fair += (int)records.get(c);
			}else {
				great += (int)records.get(c);
			}
		}
		
		creditCount.put(Credit.FAIR,fair);
		creditCount.put(Credit.GREAT,great);
	}
	@Override
	public HashMap<Customer, Integer> seperate(String value, HashMap<Customer, Integer> rc) {
		// TODO Auto-generated method stub
		Credit cd = Enum.valueOf(Credit.class, value);
		HashMap<Customer,Integer> hm = new HashMap<Customer,Integer>();
		
		Iterator it = rc.keySet().iterator();
		Customer c=new Customer();
		int count=0;
		
		while(it.hasNext()) {
			c=(Customer) it.next();
			if(c.getCredit().equals(cd)) {
				hm.put(c, rc.get(c));  //按指定值划分集合
				count += rc.get(c);
			}
		}
		creditCount.replace(cd, count);
		return hm;
	}
	@Override
	public String getAttrName() {
		// TODO Auto-generated method stub
		return "Credit";
	}
	public String[] getAttrValue() {
		// TODO Auto-generated method stub
		String[] a = {"FAIR","GREAT"};
		return a;
	}
	
}
