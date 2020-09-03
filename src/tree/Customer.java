package tree;

enum Age{
	YOUNG,MIDDLE,OLD;
	public String toString() {
		switch(this) {
		case YOUNG:
			return "��";
		case MIDDLE:
			return "��";
		case OLD:
			return "��";
		default:
			return "��";
		}
	}
}

enum Income{
	LOW,MIDDLE,HIGH;
	public String toString() {
		switch(this) {
		case LOW:
			return "��";
		case MIDDLE:
			return "��";
		case HIGH:
			return "��";
		default:
			return "��";
		}
	}
}

enum Credit{
	FAIR,GREAT;
	public String toString() {
		switch(this) {
		case FAIR:
			return "��";
		case GREAT:
			return "��";
		default:
			return "��";
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
