package tree;

enum Age{
	YOUNG,MIDDLE,OLD;
	public String toString() {
		switch(this) {
		case YOUNG:
			return "青";
		case MIDDLE:
			return "中";
		case OLD:
			return "老";
		default:
			return "空";
		}
	}
}

enum Income{
	LOW,MIDDLE,HIGH;
	public String toString() {
		switch(this) {
		case LOW:
			return "低";
		case MIDDLE:
			return "中";
		case HIGH:
			return "高";
		default:
			return "空";
		}
	}
}

enum Credit{
	FAIR,GREAT;
	public String toString() {
		switch(this) {
		case FAIR:
			return "良";
		case GREAT:
			return "优";
		default:
			return "空";
		}
	}
}

public class Customer {
	private Age age;  
	private Income income;  
	private Boolean student;
	private Credit credit;
	private Boolean buy;
	public Customer() {};
	public Customer(Age age,Income income,Boolean student,Credit credit,Boolean buy) {
		this.age=age;
		this.income=income;
		this.student=student;
		this.credit=credit;
		this.buy=buy;
	}
	
	public Age getAge() {
		return this.age;
	}
	public Income getIncome() {
		return this.income;
	}
	public Boolean getStudent() {
		return this.student;
	}
	public Credit getCredit() {
		return this.credit;
	}
	public Boolean getBuy() {
		return this.buy;
	}
	
	public String toString() {
		return "Customer[Age:"+getAge()+" Income:"+getIncome()+" Student?:"
				+getStudent()+" Credit:"+getCredit()+" Buy?:"+getBuy()+"]";
	}
}
