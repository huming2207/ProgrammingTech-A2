package holding;

import lms.model.util.*;

public class Book extends Holding
{
	private int numPages;
	private boolean activeStatus = true;
	private DateTime dateBorrowed = new DateTime(1,1,1970);
	private DateTime dateReturned = new DateTime(1,1,1970);
	private boolean onLoan = false;
	private final static double LOAN_FEE = 10;
	private final static int LOAN_PERIOD = 28;
	private final static double LATE_PENALTY = 2;
	private final static DateTime DEFAULT_TIME = new DateTime(1,1,1970);
	private double lateFee = 0.0;
	
	public Book(String holdingId, String title, int numPages)
	{
		super(holdingId, title);
		this.numPages = numPages;
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
			return (diffDayResult * LATE_PENALTY);
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
			String printStr = "\n\nID:\t\t\t" + super.getId() 
				+ "\nTitle:\t\t\t" + super.getTitle()
				+ "\nNumber of Pages:\t" + this.numPages
				+ "\nMax Loan Period:\t" + LOAN_PERIOD
				+ "\nOn Loan:\t\t" + "Yes"
				+ "\nDate of Loan:\t\t" + dateBorrowed.getFormattedDate()
				+ "\nSystem Status:\t\t" + activeStr
				+ "\n";
			
			return printStr;
		}
		else
		{
			String printStr = "\n\nID:\t\t\t" + super.getId() 
				+ "\nTitle:\t\t\t" + super.getTitle()
				+ "\nNumber of Pages:\t" + this.numPages
				+ "\nMax Loan Period:\t" + LOAN_PERIOD
				+ "\nOn Loan:\t\t" + "No" 
				+ "\nSystem Status:\t\t" + activeStr
				+ "\n";
			
			return printStr;
		}
		
	}
	
	public String toString()
	{
		// Book holding format -->
		// id:title:number_of_pages:loan_date:standard_loan_fee:max_loan_period:active
		
		if (this.dateBorrowed.getTime() == DEFAULT_TIME.getTime())
		{
			String bookStr = super.getId() + ":" 
					+ super.getTitle() + ":"  
					+ this.numPages + ":" 
					+ "null" + ":"
					+ LOAN_FEE + ":" 
					+ LOAN_PERIOD + ":" 
					+ getActiveStatusStr();
	
			return bookStr;
		}
		
		else
		{	
			String bookStr = super.getId() + ":" 
					+ super.getTitle() + ":"  
					+ this.numPages + ":" 
					+ this.dateBorrowed.getFormattedDate() + ":"
					+ LOAN_FEE + ":" 
					+ LOAN_PERIOD + ":" 
					+ getActiveStatusStr();
	
			return bookStr;
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
	
	// In this line below is all those getter methods.
	
	public double getLoanFee()
	{
		return LOAN_FEE;
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
		return 0.0;
	}
	
	public int getPages()
	{
		return this.numPages;
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
