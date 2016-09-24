package lms.model.facade;

import library.*;
import lms.model.util.DateTime;

public class LMSFacade 
{	
	
	/*
	 * According to the Email from teacher,
	 * I'm allowed to change/add something in this "Facade" class.
	 * 
	 * */
	public LMSFacade()
	{
		
	}
	
	private Library library = new Library();
	public DateTime currentTime = new DateTime();
	
	public void initialiseEngine() 
	{
		library.initialiseEngine();
	}
	
	public boolean addBook(String id, String title, int numPages) 
	{
		return library.addBook(id, title, numPages);
	}

	public boolean addVideo(String id, String title, double loanFee, double runningTime) 
	{
		return library.addVideo(id, title, loanFee, runningTime);
	}
	
	public boolean removeHolding(String holdingId) 
	{
		return library.removeHolding(holdingId);
	}
	
	public boolean addMember(String id, String name) 
	{
		return library.addMember(id, name);
	}
	
	public boolean removeMember(String memberId)
	{
		return library.removeMember(memberId);
	}
	
	public boolean borrowHolding(String memberId, String holdingId)  
	{
		return library.borrowHolding(memberId, holdingId);
	}
	
	public boolean returnHolding(String memberId, String holdingId, DateTime dateReturned) 
	{
		return library.returnHolding(memberId, holdingId, dateReturned);
	}
	
	public String printAllHoldings()
	{
		return library.printAllHoldings();
	}
	
	public String printAllMembers()
	{
		return library.printAllMembers();
	}
	
	public String printSpecificHolding(String holdingId)
	{
		return library.printSpecificHolding(holdingId);
	}
	
	public String printSpecificMember(String memberId)
	{
		return library.printSpecificMember(memberId);
	}
	
	public boolean resetMembersCredit(String memberId)
	{
		return library.resetMembersCredit(memberId);
	}
	
	public double getLateFee(String memberId)
	{
		return library.getLateFee(memberId);
	}
	
	public double getMembersBalance(String memberId)
	{
		return library.getMembersBalance(memberId);
	}
	
	public boolean activate(String id)
	{
		return library.activate(id);
	}

	public boolean deactivate(String id)
	{
		return library.deactivate(id);
	}
	
	// Added these methods below, for Part C, File Handling stuff.
	
	public void saveFile()
	{
		library.saveFile();
	}
	
	public void saveFile(String filePath)
	{
		library.saveFile(filePath);
	}
	
	public void readFile()
	{
		library.readFile();
	}
	
	public void readFile(String filePath)
	{
		library.readFile(filePath);
	}

}