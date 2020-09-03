package tree;

import java.util.HashMap;

public abstract class Count {
	
	public double[] calCdtPer(HashMap<Customer,Integer> record) {
		//����������
		int[] divSum,oriSum=setSum();
		int l=oriSum.length;
	//	System.out.println("����ȡֵ������"+l);
		double[] p=new double[l];
		gather(record);
		divSum=setSum();
		
		//print
	/*	System.out.println("oriSum   divSum");
		for(int i=0;i<oriSum.length;i++) 
			System.out.println(oriSum[i]+"        "+divSum[i]);*/
		
		//calculate
		for(int j=0;j<p.length;j++) {
			if(divSum[j]==oriSum[j]) {
				p[j]=1.0;
				continue;
			}
			p[j]=divSum[j]*1.0/oriSum[j];
		}
	//	divs=divSum;//��ȡ���ֺ����
		return p;
	}
//	public int[] countDiv(HashMap<Customer,Integer> record) {
		
//	}
	
	public abstract String getAttrName();
	public abstract String[] getAttrValue();
	public abstract HashMap<Customer,Integer> seperate(String value,HashMap<Customer, Integer> rc);
	public abstract void gather(HashMap records);  //���ϸ����Լ���
	public abstract int[] setSum();
	public double getPercent(int count) {
		return count*1.0/InputData.getSum();
	}
	
}
