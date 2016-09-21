package holding;

import lms.model.util.DateTime;

public class Video extends Holding
{
	private double runningTime;
	private double loanFee;
	private final int LOAN_PERIOD = 7;
	private double latePenalty;
	private boolean activeStatus = true;
	private DateTime dateBorrowed;
	private DateTime dateReturned;
	private boolean onLoan = false;
	private double lateFee = 0.0;

	
	public Video(String holdingId, String title, double loanFee, double runningTime)
	{
		super(holdingId, title);
		
		this.runningTime = runningTime;
		this.loanFee = loanFee;
		// Videos: late fee = number of late days x 50% of the standard loan fee.
		this.latePenalty = loanFee * 0.5;
	}

	public double calculateLateFee(DateTime dateReturned)
	{
		this.dateReturned = dateReturned;
		int diffDayResult = DateTime.diffDays(this.dateReturned, this.dateBorrowed);
		if (diffDayResult <= this.LOAN_PERIOD)
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
		if (diffDayResult <= this.LOAN_PERIOD)
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
			this.lateFee += calculateLateFee(dateReturned);
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
	
	@Override
	public String print() 
	{
		String activeStr = new String();
		
		
		if (activeStatus)
		{
			activeStr = "Active";
		}
		else
		{
			activeStr = "Deactive";
		}
		
		if (this.onLoan)
		{			
			String printStr = "\n\nID:\t" + super.getId() 
				+ "\nTitle:\t" + super.getTitle()
				+ "\nRunning Time:\t" + this.runningTime
				+ "\nLoan Fee:\t" + this.loanFee
				+ "\nMax Loan Period:\t" + LOAN_PERIOD
				+ "\nOn Loan:\t" + "Yes"
				+ "\nDate of Loan:\t" + dateBorrowed.getFormattedDate()
				+ "\nSystem Status:\t" + activeStr
				+ "\n";
			
			return printStr;
		}
		else
		{
			String printStr = "\n\nID:\t" + super.getId() 
				+ "\nTitle:\t" + super.getTitle()
				+ "\nRunning Time:\t" + this.runningTime
				+ "\nLoan Fee:\t" + this.loanFee
				+ "\nMax Loan Period:\t" + LOAN_PERIOD
				+ "\nOn Loan:\t" + "No" 
				+ "\nSystem Status:\t" + activeStr
				+ "\n";
			
			return printStr;
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
		
		// Video holding format -->
		// id:title:runningTime:loan_date:standard_loan_fee:max_loan_period:active
	
		String bookStr = super.getId() + ":" 
						+ super.getTitle() + ":"  
						+ this.runningTime + ":" 
						+ this.dateBorrowed.getFormattedDate() + ":"
						+ this.loanFee + ":" 
						+ this.LOAN_PERIOD + ":" 
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
		return LOAN_PERIOD;
	}
	
	public double getLatePenalty()
	{
		return this.lateFee;
	}
	
	public boolean getActiveStatus()
	{
		return activeStatus;
	}
}
