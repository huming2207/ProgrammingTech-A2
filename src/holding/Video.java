package holding;

import lms.model.util.DateTime;

public class Video extends Holding
{
	private double runningTime;
	private double loanFee;
	private int loanPeriod = 7;
	private double latePenalty;
	private boolean activeStatus = true;
	private DateTime dateBorrowed;
	private DateTime dateReturned;
	private boolean onLoan = false;

	
	public Video(String holdingId, String title, double loanFee, double runningTime)
	{
		super(holdingId, title);
		
		this.runningTime = runningTime;
		
		// Videos: late fee = number of late days x 50% of the standard loan fee.
		this.latePenalty = loanFee * 0.5;
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
		int diffDayResult = DateTime.diffDays(this.dateReturned, this.dateBorrowed);
		if (diffDayResult <= this.loanPeriod)
		{
			// The loan does not expire, so there is no late penalty.
			if (diffDayResult < 1)
			{
				return false;
			}
			else
			{
				this.onLoan = false;
				return true;
			}
		}
		else
		{
			calculateLateFee(dateReturned);
			this.onLoan = false;
			return true;
		}
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
		
		// Video holding format -->
		// id:title:runningTime:loan_date:standard_loan_fee:max_loan_period:active
	
		String bookStr = super.getId() + ":" 
						+ super.getTitle() + ":"  
						+ this.runningTime + ":" 
						+ this.dateBorrowed.getFormattedDate() + ":"
						+ this.loanFee + ":" 
						+ this.loanPeriod + ":" 
						+ activeStr;
		
		return bookStr;
	}
	
	// In this line below is all those getter methods.

	public double getRunningTime()
	{
		return runningTime;
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
