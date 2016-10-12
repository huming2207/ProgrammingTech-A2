package member;

import java.util.ArrayList;
import holding.Book;
import holding.Video;
import holding.Holding;
import lms.model.util.DateTime;

public class StandardMember extends Member
{
	private String memberID;
	private String memberName;
	private static double credit;
	private final static double MAX_CREDIT = 30;				// Max credit is $30
	private boolean activateStatus;
	private double lateFee = 0.0;
	
	// Don't use the array any more, it's too young too simple, sometimes naive.
	// Use the C#-like ArrayList<T> instead!
	private ArrayList<Book> memberBook = new ArrayList<Book>();
	private ArrayList<Video> memberVideo = new ArrayList<Video>();
	
	public StandardMember(String standardMemberId, String standardMemberName)
	{
		super(standardMemberId, standardMemberName, credit);
		this.memberID = standardMemberId;
		this.memberName = standardMemberName;
	}
	
	public boolean borrowHolding(Holding holding) 
	{
		// Get current time in Unix time stamp.
		DateTime time = new DateTime();
		
		// Judge whether the member borrowed book or video
		if(holding.getId().charAt(0) == 'b')
		{
			Book book = (Book)holding;
			if(book.borrowHolding(time))
			{
				// Add video to the book list
				memberBook.add(book);
				return true;
			}
			else
			{
				System.out.print("Error: Book failed to borrow!");
				return false;
			}
			
		}
		else if(holding.getId().charAt(0) == 'v')
		{
			Video video = (Video)holding;
			if (video.borrowHolding(time))
			{
				// Add video to the video list
				memberVideo.add(video);
				return true;
			}
			else
			{
				System.out.print("Error: Video failed to borrow!");
				return false;
			}
		}
		else
		{
			System.out.println("Error: Something went wrong (might be a invalid item??)");
			return false;
		}
		
	}
	
	
	public boolean returnHolding(Holding holding, DateTime returnDate) 
	{
		if(holding.getId().charAt(0) == 'b')
		{
			Book book = (Book)holding;
			if(book.returnHolding(returnDate))
			{
				if(book.getLatePenalty() > 0)
				{
					lateFee += book.getLatePenalty();
				}
				
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(holding.getId().charAt(0) == 'v')
		{
			Video video = (Video)holding;
			if(video.returnHolding(returnDate))
			{
				if(video.getLatePenalty() > 0)
				{
					lateFee += video.getLatePenalty();
				}
				
				return true;
			}
			else
			{
				return false;
			}
			
		}
		else
		{
			return false;
		}
		
	
		
	}

	
	
	public boolean deactivate() 
	{
		if(this.activateStatus)
		{
			this.activateStatus = false;
			return true;
		}
		else
		{
			return false;
		}
	}

	
	public boolean activate() 
	{
		if(!this.activateStatus)
		{
			this.activateStatus = true;
			return true;
		}
		else
		{
			return false;
		}
	}

	
	public String toString() 
	{
		String str = this.memberID + ":" +  this.memberName + ":" + StandardMember.credit;
		return str;
	}

	
	public String print() 
	{
		String memberStr = "\n\nID:\t\t\t" +  this.memberID
						+ "\nTitle\t\t\t" + this.memberName
						+ "\nRemaining Credit:\t" + StandardMember.credit + "\n";
		
		String itemId = new String();
		String itemStr = new String();
		
		// Add the item ID only if it exists more than 1.
		if (memberBook.size() >= 1)
		{
			for (int i = 0; i < memberBook.size(); i++)
			{
				itemId += memberBook.get(i).getId() + ":";
			}
		}
		if (memberVideo.size() >= 1)
		{
			for (int i = 0; i < memberVideo.size(); i++)
			{
				itemId += memberVideo.get(i).getId() + ":";
			}
		}
		
		
		// Remove the last colon ":" symbol if exists (obviously it must exists if this member has an item!)
		if (itemId.endsWith(":"))
		{
			itemId = itemId.substring(0, itemId.length() - 1);
		}
		
		if(memberBook.size() == 0 && memberVideo.size() == 0)
		{
			itemStr =  "";
		}
		else
		{
			itemStr = "Current holdings on loan:\n\n" + itemId;
		}
		
		// Finally, return two parts of the strings.
		return memberStr + itemStr;
	}

	
	public void setCredit(double credit) 
	{
		StandardMember.credit = credit;
	}

	
	public void addCredit(double creditAddValue) 
	{
		if ((StandardMember.credit +  creditAddValue) >= MAX_CREDIT)
		{
			System.out.println("Error: Credit already reset to maxium value!");
		}
		else
		{
			StandardMember.credit += creditAddValue;
		}
	}

	
	public void substractCredit(double creditSubstractValue) 
	{
		StandardMember.credit -= creditSubstractValue;
	}

	
	public double getCredit() 
	{
		return StandardMember.credit;
	}
	
	
	public boolean resetCredit() 
	{
		if (StandardMember.credit >= MAX_CREDIT)
		{
			System.out.println("Error: Credit already reset to maxium value!");
			return false;
		}
		else
		{
			StandardMember.credit = MAX_CREDIT;
			return true;
		}
	}

	
	public String getId() 
	{
		return this.memberID;
	}

	
	public String getName() 
	{
		return this.memberName;
	}
	
	
	public double getLatePenalty() 
	{
		return this.lateFee;
	}
	
	
	public String getHoldingStr()
	{
		String itemId = new String();
		
		// Add the item ID only if it exists more than 1.
		if (memberBook.size() >= 1)
		{
			for (int i = 0; i < memberBook.size(); i++)
			{
				itemId += memberBook.get(i).getId() + ":";
			}
		}
		if (memberVideo.size() >= 1)
		{
			for (int i = 0; i < memberVideo.size(); i++)
			{
				itemId += memberVideo.get(i).getId() + ":";
			}
		}
		
		
		// Remove the last colon ":" symbol if exists (obviously it must exists if this member has an item!)
		if (itemId.endsWith(":"))
		{
			itemId = itemId.substring(0, itemId.length() - 1);
		}
		
		return itemId;
	}
	
	
	public boolean hasHolding()
	{
		if (memberBook.isEmpty())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
