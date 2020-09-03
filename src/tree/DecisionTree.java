/*
 * ���䡢���롢�Ƿ�Ϊѧ������������ĸ����ض����Ƿ�����Եľ�����
 * */
package tree;

import java.util.ArrayList;
import java.util.HashMap;

public class DecisionTree {
	public static void main(String[] args) {
		InputData data = new InputData();
		//��������
		
	//	data.print(data.records);
		System.out.println("������"+InputData.getSum()); 
		ArrayList<Count> c = new ArrayList<Count>();
		c.add(new AgeCount());
		c.add(new IncomeCount());
		c.add(new StudentCount());
		c.add(new CreditCount());
		c.add(new BuyCount());
		
		int[][] fsum = new int[5][];
		int temp=0;
		for(Count eachC : c) {
			eachC.gather(data.records); 
			fsum[temp++]=eachC.setSum();
		}//��ó�ʼ���ݼ�ֵ��������
		
		////�������
		double[][] fp = calPercent(c,data);
		
		////����������Ե���Ϣ��
		double dis = Calculate.I(fp[4]);
		System.out.println("�������ԣ���/������Ϣ�أ�"+dis);
		
		////�����������Ե���Ϣ��
		data.Seperate();    //��ΪBuy��NotBuy����HashMap
	//	data.print(data.Buy);
	//	data.print(data.NotBuy);
		//checked
		
		
		ArrayList<Count> countList = (ArrayList<Count>) c.clone();
		countList.remove(4);  //�Ƴ�BuyCount
		double[][] divp = new double[4][3]; //[age/income/student/credit][value]ֻ����
		temp=0;
		for(Count eachC : countList) {
			divp[temp++] = eachC.calCdtPer(data.Buy);   //���и����ԵĻ���
		}
		
	/*	for(double[] cp :divp) {
			for(double d:cp)
				System.out.print(cp+" ");
			System.out.println();
		}*/
		
		double[][] isSet = new double[4][];
		double[] eSet=new double[4];
		double[] gSet=new double[4];
		
		for(int j=0;j<4;j++) {
			int l = divp[j].length;
			double[] is=new double[l];
			String[] value = data.attrValue(j);
			for(int i=0;i<l;i++) {
				is[i]=Calculate.I2 (divp[j][i]);
			//	System.out.println("����"+data.attrName(j)+":"+value[i]+"����Ϣ��"+is[i]);
			}
			
			double e = Calculate.E(fp[j],is);
			double gain = Calculate.G(dis, e);
			isSet[j]=is;
			eSet[j]=e;
			gSet[j]=gain;
			
		//	System.out.println("����"+data.attrName(j)+"   E:"+e+"    G:"+gain+"\n");
		
		}
		
		int rootAttr = Calculate.maxIndex(gSet);
		System.out.println("Root:"+c.get(rootAttr).getAttrName());
		
		//////���ڵ�������
		
		////���м�¼���ݸ��ڵ��ȡֵ����
		Count root = c.get(rootAttr) ;   //age
		ArrayList<HashMap<Customer,Integer>> branch = 
				new ArrayList<HashMap<Customer,Integer>>();
		ArrayList<HashMap<Customer,Integer>> node = 
				new ArrayList<HashMap<Customer,Integer>>();
		String[] value = data.attrValue(rootAttr);
		for(int i=0;i<value.length;i++)
			branch.add(root.seperate(value[i],data.records));
		//�Ƴ��������ľ�������
		countList.remove(rootAttr);
		
		
	//	for(int i=0;i<value.length;i++) {
	//		System.out.println(branch.get(i));
	//	}//young  middle  old
		
		//��ʱ���branch[i]�°�testAttr�Ĳ�ͬvalue���ֵ�map
		HashMap<Customer,Integer> tmp = new HashMap<Customer,Integer>();
		int[] vsum = root.setSum(); //�����Ը�value����
		int[] cdtSum = root.setSum(); //��ʼ��
		String[] rootValue = value;
		String child=new String();
		ArrayList<Count> lvl=new ArrayList<Count>();
		
	//	branch.add(data.records);
		do{
			//����δ�������ִ��ѭ��
			int bestChild=0;			
			for(int j=0;j<branch.size();j++) {	
				//branch��ѭ��
			//	System.out.println(branch.get(j));
				System.out.println("-branch"+(j+1)+":"+rootValue[j]);
				
				if(data.countBuy(branch.get(j))==vsum[j]) {
					//ȫ��
					child="buy(leaf)";
				}else if(data.countBuy(branch.get(j))==0) {
					//ȫ����
					child="notBuy(leaf)";
				}else {
				
					double[][] e=new double[branch.size()][countList.size()];
					double[][] g=new double[branch.size()][countList.size()];
					
					for(int attr=0;attr<countList.size();attr++) {
						
						//attr��ѭ��
					//	System.out.println("-----calculate:"+countList.get(attr).getAttrName()+"------");
						value = countList.get(attr).getAttrValue(); 
						
						int[] buy =new int[value.length]; //buy
						countList.get(attr).gather(branch.get(j));  //��ǰattr�ڵ�ǰbranch�¸�ȡֵ����
						cdtSum = countList.get(attr).setSum();  //branch��attr��ȡֵ��sum
						
						double[] p=new double[value.length];
						double[] is=new double[value.length];
						
						for(int i=0;i<value.length;i++) {
							//value��ѭ��
							tmp=countList.get(attr).seperate(value[i], branch.get(j));
						//	System.out.println(tmp);					
							buy[i]=data.countBuy(tmp);
							p[i]=cdtSum[i]*1.0/vsum[j];
							if(cdtSum[i]==0)
								is[i]=0.0;
							else
								is[i]=Calculate.I2(buy[i]*1.0/cdtSum[i]);
					//		System.out.println("p:"+p[i]+" is:"+is[i]);				
						}
						e[j][attr]=Calculate.E(p, is);
						double fis=isSet[rootAttr][j];
						g[j][attr]=Calculate.G(fis, e[j][attr]);
					//	System.out.println("\ne:"+e[j][attr]+"\ng:"+g[j][attr]+"\n\n");
						
					}//�����Ϣ��������
					bestChild=Calculate.maxIndex(g[j]);
					lvl.add(countList.get(bestChild));
					child=countList.get(bestChild).getAttrName();
					countList.remove(bestChild);//�Ƴ�bestChild���Ժ������һ��֧
					node.add(branch.get(j));
				}//��Ҷ�ڵ���bestChild
				System.out.println("--childNode:"+child+"\n");
			}//һ�ڵ��֧�������
			
			if(!lvl.isEmpty()) {//��Ҷ�ڵ��һ����֧
				root=lvl.get(0);				
				rootValue=root.getAttrValue();
				System.out.println("Node:"+root.getAttrName());
				vsum=root.setSum();
				branch.clear();
				for(int i=0;i<rootValue.length;i++)
					branch.add(root.seperate(rootValue[i],node.get(0)));
				lvl.remove(0);
				node.remove(0);
			//	System.out.println(countList);
			}
		}while(!countList.isEmpty());
		
	//	System.out.println("test");
		for(int j=0;j<branch.size();j++) {	
			//branch��ѭ��
		//	System.out.println(branch.get(j));
			System.out.println("-branch"+(j+1)+":"+rootValue[j]);
			int buy=data.countBuy(branch.get(j));
			if(branch.get(j).isEmpty()) {
				System.out.println("--NOT EXIST\n");
				continue;
			}
			if(buy==vsum[j]) {
				//ȫ��
				child="buy(leaf)";
			}else if(buy==0) {
				//ȫ����
				child="notBuy(leaf)";
			}else {
				child="buy"+buy+"/notBuy"+(vsum[j]-buy)+"(leaf)";
			}
			System.out.println("--childNode:"+child+"\n");
		}
	}
	
	private static double[][] calPercent(ArrayList<Count> c,InputData data){
		//��ȫ�����ݻ���
		
		double[][] p = new double[5][];
		
		for(int i=0;i<c.size();i++) {
			int[] sum=c.get(i).setSum();
			double[] pp=new double[sum.length];
			for(int j=0;j<pp.length;j++)
				pp[j]=c.get(0).getPercent(sum[j]);
			p[i]=pp;
		}
		return p;
	}

}




