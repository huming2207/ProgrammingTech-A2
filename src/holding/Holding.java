package holding;

import lms.model.util.DateTime;

public abstract class Holding 
{
	private String holdingId;
	private String title;
	
	public Holding(String holdingId, String title)
	{
		this.holdingId = holdingId;
		this.title = title;
	}
	
	
	public abstract double calculateLateFee(DateTime dateReturned);
	
	public abstract boolean borrowHolding(DateTime dateBorrowed);
	
	public abstract boolean returnHolding(DateTime dateReturned);
	
	public abstract String print();
	
	public abstract String toString();
	
	public abstract boolean activate();
	
	public abstract boolean deactivate();
	
	public abstract double getLatePenalty();
	
	public abstract double getLoanFee();
	
	/* In this line below is all those getters and setters. */

	public String getId()
	{
		return holdingId;
	}
	
	public String getTitle()
	{
		return title;
	}
	

	
}
