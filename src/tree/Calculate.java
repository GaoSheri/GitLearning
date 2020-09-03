package tree;

public class Calculate {
	
	private static double log2(double n) {
		return Math.log(n)/Math.log(2);
	}
	
	static double I(double[] p) {
		double result=0.0;
		for(double per:p) {
			if(per==0.0||per==1.0)
				continue;
			result += (-1)*per*log2(per);
		}
		return result;
	}
	
	static double I2(double p) {
		if(p==0.0||p==1.0) 
			return 0;
		return (-1)*p*log2(p)+(-1)*(1-p)*log2(1-p);
	}
	
	static double E(double[] op,double[] is) {
		double result=0.0;
		for(int i=0;i<op.length;i++) {
			result += op[i]*is[i];
		}
		return result;
	}
	
	static double G(double I,double E) {
		return I-E;
	}
	
	static int maxIndex(double[] arr) {  //double数组最大值的下标
		int maxIndex=0;
		for(int i=1;i<arr.length;i++)
			if(arr[i]>arr[maxIndex])
				maxIndex=i;
		return maxIndex;
	}
	
	static double arraySum(double[] arr) {
		double result=0.0;
		for(double d: arr) {
			result += d;
		}
		return result;
	}
	static int arraySum(int[] arr) {
		int result=0;
		for(int d: arr) {
			result += d;
		}
		return result;
	}
	double percent(int count,int sum) {
		
		return count*1.0/sum;
	}
	
	
	
}
