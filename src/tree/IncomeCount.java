package tree;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;

public class IncomeCount extends Count{
	EnumMap<Income,Integer> incomeCount = new EnumMap<Income, Integer>(Income.class);
	
	public int getHighIncomeCount() {
		return incomeCount.get(Income.HIGH);
	}
	public int getMiddleIncomeCount() {
		return incomeCount.get(Income.MIDDLE);
	}
	public int getLowIncomeCount() {
		return incomeCount.get(Income.LOW);
	}
	@Override
	public int[] setSum() {
		// TODO Auto-generated method stub
		int[] sum=new int[3];
		sum[0]=getHighIncomeCount();
		sum[1]=getMiddleIncomeCount();
		sum[2]=getLowIncomeCount();
		return sum;
	}
	
	@Override
	public void gather(HashMap records) {
		// TODO Auto-generated method stub
		incomeCount.clear();
		Iterator it = records.keySet().iterator();
		Customer c = new Customer();
		
		int high=0;
		int low=0;
		int middle=0;
		while(it.hasNext()) {
			c = (Customer) it.next();
			switch(c.getIncome()) {
			case HIGH:
				high += (int)records.get(c);
				break;
			case LOW:
				low += (int)records.get(c);
				break;
			default:
				middle += (int)records.get(c);
				break;
			}	
		}
		
		incomeCount.put(Income.HIGH,high);
		incomeCount.put(Income.LOW,low);
		incomeCount.put(Income.MIDDLE,middle);
	}
	
	@Override
	public HashMap<Customer, Integer> seperate(String value, HashMap<Customer, Integer> rc) {
		// TODO Auto-generated method stub
		Income i = Enum.valueOf(Income.class, value);
		HashMap<Customer,Integer> hm = new HashMap<Customer,Integer>();
		
		if(rc==null) {
			return null;
		}
		
		Iterator it = rc.keySet().iterator();
		Customer c=new Customer();
		int count=0;
		
		while(it.hasNext()) {
			c=(Customer) it.next();
			if(c.getIncome().equals(i)) {
				hm.put(c, rc.get(c));  //按指定值划分集合
				count += rc.get(c);
			}
		}
		incomeCount.replace(i, count);
		return hm;
	}
	@Override
	public String getAttrName() {
		// TODO Auto-generated method stub
		return "Income";
	}
	public String[] getAttrValue() {
		// TODO Auto-generated method stub
		String[] a = {"HIGH","MIDDLE","LOW"};
		return a;
	}
	
}
