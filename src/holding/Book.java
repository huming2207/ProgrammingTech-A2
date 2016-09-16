package holding;

import lms.model.util.*;

public class Book extends Holding
{
	private int numPages;
	private boolean activeStatus = true;
	private DateTime dateBorrowed;
	private DateTime dateReturned;
	private boolean onLoan = false;
	private final double loanFee = 10;
	private final int loanPeriod = 28;
	private final double latePenalty = 2;
	
	public Book(String holdingId, String title, double loanFee, int numPages)
	{
		super(holdingId, title);
		this.numPages = numPages;
	}
	
	
	public double calculateLateFee(DateTime dateReturned)
	{
		this.dateReturned = dateReturned;
		int diffDayResult = DateTime.diffDays(this.dateReturned, this.dateBorrowed);
		if (diffDayResult <= this.loanPeriod)
		{
			// The loan does not expire, so there is no late penalty.
			return 0.0;
		}
		else
		{
			return (diffDayResult * this.latePenalty);
		}
	}
	
	public boolean borrowHolding(DateTime dateBorrowed)
	{
		// The item can only borrowed if it's available.
		if (this.activeStatus && !this.onLoan)
		{
			this.dateBorrowed = dateBorrowed;
			this.onLoan = true;
			return true;
		}
		else
		{
			// Return false if it cannot be borrowed.
			return false;
		}
	}
	
	public boolean returnHolding(DateTime dateReturned)
	{
		int diffDayResult = DateTime.diffDays(dateReturned, dateBorrowed);
		
		/* If: 
		 * 		1. Return date is not later than the borrow date;
		 * 		2. It's not set to onLoan;
		 * 		3. It's not active,
		 * 
		 * ... then the loan is invalid, cannot return.
		 * 
		 * */
		  
		if (diffDayResult < 1 || !onLoan || !activeStatus)
		{
			if(!onLoan)
			{
				System.out.println("Error: Item is not on loan!!");
			}
			if(!activeStatus)
			{
				System.out.println("Error: Item is not active!!");
			}
			return false;
		}
		else
		{
			this.onLoan = false;
			return true;
		}
		
	}
	
	public String toString()
	{
		String activeStr = new String();
		
		if (activeStatus)
		{
			activeStr = "active";
		}
		else
		{
			activeStr = "deactive";
		}
		
		// Book holding format -->
		// id:title:number_of_pages:loan_date:standard_loan_fee:max_loan_period:active
		
		String bookStr = super.getId() + ":" 
						+ super.getTitle() + ":"  
						+ this.numPages + ":" 
						+ this.dateBorrowed.getFormattedDate() + ":"
						+ this.loanFee + ":" 
						+ this.loanPeriod + ":" 
						+ activeStr;
		
		return bookStr;
	}
	
	public boolean activate()
	{
		this.onLoan = true;
		return true;
	}
	
	public boolean deactivate()
	{
		this.onLoan = false;
		return true;
	}
	
	// In this line below is all those getter methods.
	
	public int getNumPages()
	{
		return numPages;
	}
	
	public double getLoanFee()
	{
		return loanFee;
	}
	
	public int getLoanPeriod()
	{
		return loanPeriod;
	}
	
	public double getLatePenalty()
	{
		return latePenalty;
	}
	
	public boolean getActiveStatus()
	{
		return activeStatus;
	}
}
