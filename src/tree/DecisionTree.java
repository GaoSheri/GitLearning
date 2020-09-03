/*
 * 年龄、收入、是否为学生、信誉情况四个因素对于是否购买电脑的决策树
 * */
package tree;

import java.util.ArrayList;
import java.util.HashMap;

public class DecisionTree {
	public static void main(String[] args) {
		InputData data = new InputData();
		//引入数据
		
	//	data.print(data.records);
		System.out.println("总数："+InputData.getSum()); 
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
		}//获得初始数据键值对与总数
		
		////计算比例
		double[][] fp = calPercent(c,data);
		
		////计算决策属性的信息熵
		double dis = Calculate.I(fp[4]);
		System.out.println("决策属性（买/不买）信息熵："+dis);
		
		////计算条件属性的信息熵
		data.Seperate();    //分为Buy与NotBuy两个HashMap
	//	data.print(data.Buy);
	//	data.print(data.NotBuy);
		//checked
		
		
		ArrayList<Count> countList = (ArrayList<Count>) c.clone();
		countList.remove(4);  //移除BuyCount
		double[][] divp = new double[4][3]; //[age/income/student/credit][value]只算买
		temp=0;
		for(Count eachC : countList) {
			divp[temp++] = eachC.calCdtPer(data.Buy);   //买中各属性的划分
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
			//	System.out.println("属性"+data.attrName(j)+":"+value[i]+"的信息熵"+is[i]);
			}
			
			double e = Calculate.E(fp[j],is);
			double gain = Calculate.G(dis, e);
			isSet[j]=is;
			eSet[j]=e;
			gSet[j]=gain;
			
		//	System.out.println("属性"+data.attrName(j)+"   E:"+e+"    G:"+gain+"\n");
		
		}
		
		int rootAttr = Calculate.maxIndex(gSet);
		System.out.println("Root:"+c.get(rootAttr).getAttrName());
		
		//////根节点计算完成
		
		////所有记录根据父节点各取值划分
		Count root = c.get(rootAttr) ;   //age
		ArrayList<HashMap<Customer,Integer>> branch = 
				new ArrayList<HashMap<Customer,Integer>>();
		ArrayList<HashMap<Customer,Integer>> node = 
				new ArrayList<HashMap<Customer,Integer>>();
		String[] value = data.attrValue(rootAttr);
		for(int i=0;i<value.length;i++)
			branch.add(root.seperate(value[i],data.records));
		//移除已入树的决策属性
		countList.remove(rootAttr);
		
		
	//	for(int i=0;i<value.length;i++) {
	//		System.out.println(branch.get(i));
	//	}//young  middle  old
		
		//暂时存放branch[i]下按testAttr的不同value划分的map
		HashMap<Customer,Integer> tmp = new HashMap<Customer,Integer>();
		int[] vsum = root.setSum(); //父属性各value个数
		int[] cdtSum = root.setSum(); //初始化
		String[] rootValue = value;
		String child=new String();
		ArrayList<Count> lvl=new ArrayList<Count>();
		
	//	branch.add(data.records);
		do{
			//属性未分配完毕执行循环
			int bestChild=0;			
			for(int j=0;j<branch.size();j++) {	
				//branch内循环
			//	System.out.println(branch.get(j));
				System.out.println("-branch"+(j+1)+":"+rootValue[j]);
				
				if(data.countBuy(branch.get(j))==vsum[j]) {
					//全买
					child="buy(leaf)";
				}else if(data.countBuy(branch.get(j))==0) {
					//全不买
					child="notBuy(leaf)";
				}else {
				
					double[][] e=new double[branch.size()][countList.size()];
					double[][] g=new double[branch.size()][countList.size()];
					
					for(int attr=0;attr<countList.size();attr++) {
						
						//attr内循环
					//	System.out.println("-----calculate:"+countList.get(attr).getAttrName()+"------");
						value = countList.get(attr).getAttrValue(); 
						
						int[] buy =new int[value.length]; //buy
						countList.get(attr).gather(branch.get(j));  //当前attr在当前branch下各取值划分
						cdtSum = countList.get(attr).setSum();  //branch内attr各取值的sum
						
						double[] p=new double[value.length];
						double[] is=new double[value.length];
						
						for(int i=0;i<value.length;i++) {
							//value内循环
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
						
					}//获得信息增益数组
					bestChild=Calculate.maxIndex(g[j]);
					lvl.add(countList.get(bestChild));
					child=countList.get(bestChild).getAttrName();
					countList.remove(bestChild);//移除bestChild属性后计算下一分支
					node.add(branch.get(j));
				}//非叶节点求bestChild
				System.out.println("--childNode:"+child+"\n");
			}//一节点分支计算完成
			
			if(!lvl.isEmpty()) {//非叶节点进一步分支
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
			//branch内循环
		//	System.out.println(branch.get(j));
			System.out.println("-branch"+(j+1)+":"+rootValue[j]);
			int buy=data.countBuy(branch.get(j));
			if(branch.get(j).isEmpty()) {
				System.out.println("--NOT EXIST\n");
				continue;
			}
			if(buy==vsum[j]) {
				//全买
				child="buy(leaf)";
			}else if(buy==0) {
				//全不买
				child="notBuy(leaf)";
			}else {
				child="buy"+buy+"/notBuy"+(vsum[j]-buy)+"(leaf)";
			}
			System.out.println("--childNode:"+child+"\n");
		}
	}
	
	private static double[][] calPercent(ArrayList<Count> c,InputData data){
		//按全部数据划分
		
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




