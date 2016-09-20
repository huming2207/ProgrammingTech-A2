package library;

import java.util.ArrayList;
import java.util.List;
import holding.*;
import member.*;
import lms.model.util.DateTime;

public class Library 
{
	
	private List<Holding> holdingList = new ArrayList<Holding>();
	private List<Member> memberList = new ArrayList<Member>();
	
	public Library()
	{
		
	}
	
	public void initialiseEngine() 
	{
		
	}
	
	public boolean addBook(String id, String title, int numPages) 
	{
		Book bookItem = new Book(id, title, numPages);
		if (holdingList.contains(bookItem))
		{
			System.out.println("Error: Book item already exist in the list!");
			return false;
		}
		else
		{
			System.out.println("Info: Book item added!");
			holdingList.add(bookItem);
			return true;
		}
	}

	public boolean addVideo(String id, String title, double loanFee, double runningTime) 
	{
		Video videoItem = new Video(id, title, loanFee, runningTime);
		if (holdingList.contains(videoItem))
		{
			System.out.println("Error: Video item already exist in the list!");
			return false;
		}
		else
		{
			System.out.println("Info: Video item added!");
			holdingList.add(videoItem);
			return true;
		}
	}
	
	public boolean removeHolding(String holdingId) 
	{
		boolean removeStatus = false;
		
		// Loop it, in order to find out the item which needs to be deleted.
		// It's quite a stupid way but it seems this is the best way to do this so far.
		// In C#, maybe I can do it in LINQ!
		for(int i = 0; i < holdingList.size(); i++)
		{
			Holding holdingItem = holdingList.get(i);
			
			// Delete only if the holding ID matches.
			if (holdingItem.getId() == holdingId)
			{
				holdingList.remove(i);
				removeStatus = true;
			}
		}
		return removeStatus;	
	}
	
	public boolean addMember(String id, String name) 
	{
		if (id.startsWith("p"))
		{
			Member memberItem = new PremiumMember(id, name);
			memberList.add(memberItem);
			System.out.println("Info: Premium Member added successfully!");
			return true;
		}
		else if (id.startsWith("s"))
		{
			Member memberItem = new StandardMember(id, name);
			memberList.add(memberItem);
			System.out.println("Info: Standard Member added successfully!");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean removeMember(String memberId)
	{
		boolean removeStatus = false;
		
		// Loop it, in order to find out the item which needs to be deleted.
		// It's quite a stupid way but it seems this is the best way to do this so far.
		// In C#, maybe I can do it in LINQ!
		for(int i = 0; i < memberList.size(); i++)
		{
			Member memberItem = memberList.get(i);
			
			// Delete only if the holding ID matches.
			if (memberItem.getId() == memberId)
			{
				memberList.remove(i);
				removeStatus = true;
			}
		}
		return removeStatus;
	}
	
	
	public boolean borrowHolding(String memberId, String holdingId)  
	{
		boolean memberStatus = false;
		boolean holdingStatus = false;
		
		// Outer-bound loop, for querying holding
		for (int i = 0; i < holdingList.size(); i++)
		{
			Holding holdingItem = holdingList.get(i);
			if (holdingItem.getId() == holdingId)
			{
				System.out.println("Info: Holding item found.");
				Holding selectedHolding = holdingItem;
				holdingStatus = true;
				
				// Inner-bound loop, for querying members
				for (int j = 0; j < memberList.size(); j++)
				{
					Member memberItem = memberList.get(j);
					if (memberItem.getId() == memberId)
					{
						memberItem.borrowHolding(selectedHolding);
						memberStatus = true;
					}
					else
					{
						System.out.println("Error: member not found!");
						memberStatus = false;
					}
				}
			}
			else
			{
				System.out.println("Error: Holding not found!");
				holdingStatus = false;
			}
		}
		
		return (memberStatus && holdingStatus);
		
	}
	
	public boolean returnHolding(String memberId, String holdingId, DateTime dateReturned) 
	{
		boolean memberStatus = false;
		boolean holdingStatus = false;
		
		// Outer-bound loop, for querying holding
		for (int i = 0; i < holdingList.size(); i++)
		{
			Holding holdingItem = holdingList.get(i);
			if (holdingItem.getId() == holdingId)
			{
				System.out.println("Info: Holding item found.");
				Holding selectedHolding = holdingItem;
				holdingStatus = true;
				
				// Inner-bound loop, for querying members
				for (int j = 0; j < memberList.size(); j++)
				{
					Member memberItem = memberList.get(j);
					if (memberItem.getId() == memberId)
					{
						DateTime returnTime = new DateTime();
						memberItem.returnHolding(selectedHolding, returnTime);
						memberStatus = true;
					}
					else
					{
						System.out.println("Error: member not found!");
						memberStatus = false;
					}
				}
			}
			else
			{
				System.out.println("Error: Holding not found!");
				holdingStatus = false;
			}
		}
		
		return (memberStatus && holdingStatus);
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