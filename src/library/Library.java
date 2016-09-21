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
			String str = holdingItem.getId();
			
			// Delete only if the holding ID matches.
			if (holdingItem.getId().matches(holdingId))
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
			if (memberItem.getId().matches(memberId))
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
			if (holdingItem.getId().matches(holdingId))
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
			if (holdingItem.getId().matches(holdingId))
			{
				System.out.println("Info: Holding item found.");
				Holding selectedHolding = holdingItem;
				holdingStatus = true;
				
				// Inner-bound loop, for querying members
				for (int j = 0; j < memberList.size(); j++)
				{
					Member memberItem = memberList.get(j);
					if (memberItem.getId().matches(memberId))
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
		String str = new String();
		for (int i = 0; i < holdingList.size(); i++)
		{
			str += holdingList.get(i).print();
		}
		
		return str;
	}
	
	public String printAllMembers()
	{
		String str = new String();
		for (int i = 0; i < memberList.size(); i++)
		{
			str += memberList.get(i).print();
		}
		
		return str;
	}
	
	public String printSpecificHolding(String holdingId)
	{
		String str = new String();
		for (int i = 0; i < holdingList.size(); i++)
		{
			if (holdingList.get(i).getId().matches(holdingId))
			{
				str = holdingList.get(i).toString();
			}
		}
		
		return str;
	}
	
	public String printSpecificMember(String memberId)
	{
		String str = new String();
		for (int i = 0; i < holdingList.size(); i++)
		{
			if (memberList.get(i).getId().matches(memberId))
			{
				str = memberList.get(i).print();
			}
		}
		
		return str;
	}
	
	public boolean resetMembersCredit(String memberId)
	{
		boolean resetStatus = false;
		for (int i = 0; i < memberList.size(); i++)
		{
			if (memberList.get(i).getId().matches(memberId))
			{
				memberList.get(i).resetCredit();
			}
		}
		
		return resetStatus;
	}
	
	public double getLateFee(String memberId)
	{
		double lateFee = 0.0;
		for (int i = 0; i < memberList.size(); i++)
		{
			if (memberList.get(i).getId().matches(memberId))
			{
				memberList.get(i).getLatePenalty();
			}
		}
		
		return lateFee;
	}
	
	public double getMembersBalance(String memberId)
	{
		double result = 0.0;
		for (int i = 0; i < memberList.size(); i++)
		{
			if (memberList.get(i).getId().matches(memberId))
			{
				result = memberList.get(i).getCredit();
			}
		}
		
		return result;
	}
	
	public boolean activate(String id)
	{
		if (id.startsWith("v") || id.startsWith("b"))
		{
			boolean result = false;
			for (int i = 0; i < holdingList.size(); i++)
			{
				if (holdingList.get(i).getId().matches(id))
				{
					result = holdingList.get(i).activate();
				}
			}
			
			return result;
			
		}
		else if(id.startsWith("s") || id.startsWith("p"))
		{
			boolean result = false;
			for (int i = 0; i < memberList.size(); i++)
			{
				if (memberList.get(i).getId().matches(id))
				{
					result = memberList.get(i).activate();
				}
			}
			
			return result;
		}
		else
		{
			System.out.println("Error: Something went wrong, probably it's an invalid item or ID (" + id + ").");
			return false;
		}
		
		
	}

	public boolean deactivate(String id)
	{
		if (id.startsWith("v") || id.startsWith("b"))
		{
			boolean result = false;
			for (int i = 0; i < holdingList.size(); i++)
			{
				if (holdingList.get(i).getId().matches(id))
				{
					result = holdingList.get(i).deactivate();
				}
			}
			
			return result;
			
		}
		else if(id.startsWith("s") || id.startsWith("p"))
		{
			boolean result = false;
			for (int i = 0; i < memberList.size(); i++)
			{
				if (memberList.get(i).getId().matches(id))
				{
					result = memberList.get(i).deactivate();
				}
			}
			
			return result;
		}
		else
		{
			System.out.println("Error: Something went wrong, probably it's an invalid item or ID (" + id + ").");
			return false;
		}
	}
}