package lms.model.facade;


import lms.model.util.DateTime;

public class LMSFacade 
{

	/* you need to modify the methods below to forward calls to the methods in your library class
	 * and return the correct values to your menu. */
	
	
	public LMSFacade()
	{
		
	}
	
	private void initialiseEngine() 
	{
		
	}
	
	public boolean addBook(String id, String title, int numPages) 
	{
		return false;
	}

	public boolean addVideo(String id, String title, double loanFee, 
			double runningTime) 
	{
		return false;
	}
	
	public boolean removeHolding(String holdingId) 
	{
		return false;
	}
	
	public boolean addMember(String id, String name) 
	{
		return false;		
	}
	
	public boolean removeMember(String memberId)
	{
		return false;
	}
	
	public boolean borrowHolding(String memberId, String holdingId)  
	{
		return false;
	}
	
	public boolean returnHolding(String memberId, String holdingId, DateTime dateReturned) 
	{
		return false;
	}
	
	public String printAllHoldings()
	{
		return "";
	}
	
	public String printAllMembers()
	{
		return "";
	}
	
	public String printSpecificHolding(String holdingId)
	{
		return "";
	}
	
	public String printSpecificMember(String memberId)
	{
		return "";
	}
	
	public boolean resetMembersCredit(String memberId)
	{
		return false;
	}
	
	public double getLateFee(String memberId)
	{
		return 0.0;
	}
	
	public double getMembersBalance(String memberId)
	{
		return 0.0;
	}
	
	public boolean activate(String id)
	{
		return false;
	}

	public boolean deactivate(String id)
	{
		return false;
	}
}