package member;

import holding.Holding;
import lms.model.util.DateTime;

public abstract class Member 
{
	private String memberID;
	private String fullName;
	private double credit;
	
	public Member(String memberID, String fullName, double credit)
	{
		this.memberID = memberID;
		this.fullName = fullName;
		this.credit = credit;
	}
	
	public abstract boolean borrowHolding(Holding holding);
	
	public abstract boolean returnHolding(Holding holding, DateTime returnDate);
	
	public abstract boolean resetCredit();
	
	public abstract boolean deactivate();
	
	public abstract boolean activate();
	
	public abstract String toString();
	
	public abstract String print();
	
	public abstract String getId();
	
	public abstract String getName();
	
	public abstract void setCredit(double credit);
	
	public abstract void addCredit(double creditAddValue);
	
	public abstract void substractCredit(double creditSubstractValue);
	
	public abstract double getCredit();
	
}
