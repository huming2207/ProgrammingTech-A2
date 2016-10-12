package holding;

import lms.model.util.DateTime;

public class Video extends Holding
{
	private double runningTime;
	private double loanFee;
	private static final int LOAN_PERIOD = 7;
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
		if (diffDayResult <= LOAN_PERIOD)
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
		if(diffDayResult <= LOAN_PERIOD)
		{
			if (diffDayResult < 0 || !onLoan || !activeStatus)
			{
				if (!onLoan)
				{
					System.out.println("Error: Item is not on loan!!");
				}
				if (!activeStatus)
				{
					System.out.println("Error: Item is not active!!");
				}
				
				if (diffDayResult < 0)
				{
					System.out.println("Error: Date record seems to be wrong!!");
				}
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
			this.lateFee = calculateLateFee(dateReturned);
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
			String printStr = "\n\nID:\t\t" + super.getId() 
				+ "\nTitle:\t\t" + super.getTitle()
				+ "\nRunning Time:\t" + this.runningTime
				+ "\nLoan Fee:\t" + this.loanFee
				+ "\nMax Loan Period: " + LOAN_PERIOD
				+ "\nOn Loan:\t" + "Yes"
				+ "\nDate of Loan:\t" + dateBorrowed.getFormattedDate()
				+ "\nSystem Status:\t" + activeStr
				+ "\n";
			
			return printStr;
		}
		else
		{
			String printStr = "\n\nID:\t\t" + super.getId() 
				+ "\nTitle:\t\t" + super.getTitle()
				+ "\nRunning Time:\t" + this.runningTime
				+ "\nLoan Fee:\t" + this.loanFee
				+ "\nMax Loan Period: " + LOAN_PERIOD
				+ "\nOn Loan:\t" + "No" 
				+ "\nSystem Status:\t" + activeStr
				+ "\n";
			
			return printStr;
		}
		
	}
	
	public String toString()
	{
		
		// Video holding format -->
		// id:title:runningTime:loan_date:standard_loan_fee:max_loan_period:active
	
		String bookStr = super.getId() + ":" 
						+ super.getTitle() + ":"  
						+ this.runningTime + ":" 
						+ this.dateBorrowed.getFormattedDate() + ":"
						+ this.loanFee + ":" 
						+ LOAN_PERIOD + ":" 
						+ getActiveStatusStr();		
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

	public void setDateBorrowed(DateTime dateBorrowed) 
	{
		this.dateBorrowed = dateBorrowed;
	}
	
	public DateTime getDateBorrowed()
	{
		return this.dateBorrowed;
	}
	
	public double getLength()
	{
		return this.runningTime;
	}
	
	public int getPages()
	{
		return 0;
	}
	
	public String getActiveStatusStr()
	{
		if(this.activeStatus)
		{
			return "active";
		}
		else
		{
			return "deactive";
		}
	}
	
}
