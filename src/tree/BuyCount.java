package tree;

import java.util.HashMap;
import java.util.Iterator;

public class BuyCount extends Count{
	HashMap<Boolean,Integer> buyCount = new HashMap<Boolean,Integer>();
	int bsum,nsum;
	
	public int getBuyCount() {
		return buyCount.get(true);
	}
	public int getNotBuyCount() {
		return buyCount.get(false);
	}
	@Override
	public int[] setSum() {
		// TODO Auto-generated method stub
		int[] sum=new int[2];
		sum[0]=getBuyCount();
		sum[1]=getNotBuyCount();
		return sum;
	}
	public void gather(HashMap records) {
		
		buyCount.clear();
		Iterator it = records.keySet().iterator();
		Customer c = new Customer();
		
		int count=0;
		int not=0;
		while(it.hasNext()) {
			c = (Customer) it.next();
			if(c.getBuy())
				count += (int)records.get(c);
			else
				not += (int)records.get(c);
		}
		
		buyCount.put(true,count);
		buyCount.put(false,not);
	}
	@Override
	public HashMap<Customer, Integer> seperate(String value, HashMap<Customer, Integer> rc) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getAttrName() {
		// TODO Auto-generated method stub
		return "Buy";
	}
	
	public String[] getAttrValue() {
		// TODO Auto-generated method stub
		String[] a = {"true","false"};
		return a;
	}
	
}
