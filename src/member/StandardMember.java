package member;

import java.util.ArrayList;
import java.util.List;
import holding.Book;
import holding.Video;
import holding.Holding;
import lms.model.util.DateTime;

public class StandardMember extends Member
{
	private String memberID;
	private String memberName;
	private static double credit;
	private final double maxiumCredit = 30;
	private boolean activateStatus;
	private List<Book> memberBook = new ArrayList<Book>();
	private List<Video> memberVideo = new ArrayList<Video>();
	
	public StandardMember(String standardMemberId, String standardMemberName)
	{
		super(standardMemberId, standardMemberName, credit);
		this.memberID = standardMemberId;
		this.memberName = standardMemberName;
	}

	@Override
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
				System.out.println("Info: Book borrowed!");
				System.out.println("Name: " + book.getTitle());
				System.out.println("ID: " + book.getId());
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
				System.out.println("Info: Video borrowed!");
				System.out.println("Name: " + video.getTitle());
				System.out.println("ID: " + video.getId());
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
	
	@Override
	public boolean returnHolding(Holding holding, DateTime returnDate) 
	{
		if(holding.getId().charAt(0) == 'b')
		{
			Book book = (Book)holding;
			if(book.returnHolding(returnDate))
			{
				System.out.println("Info: Book returned successfully!!");
				
				return true;
			}
			else
			{
				System.out.println("Error: Book failed to return!!!");
				return false;
			}
		}
		else if(holding.getId().charAt(0) == 'v')
		{
			Video video = (Video)holding;
			if(video.returnHolding(returnDate))
			{
				System.out.println("Info: Video returned successfully!!");
				return true;
			}
			else
			{
				System.out.println("Error: Video failed to return!!!");
				return false;
			}
		}
		else
		{
			System.out.println("Error: Something went wrong (might be a invalid item??)");
			return false;
		}
		
	}

	
	@Override
	public boolean deactivate() 
	{
		if(this.activateStatus)
		{
			System.out.println("Info: Member deactivated successfully!");
			this.activateStatus = false;
			return true;
		}
		else
		{
			System.out.println("Error: Member already deactivated!");
			return false;
		}
	}

	@Override
	public boolean activate() 
	{
		if(!this.activateStatus)
		{
			System.out.println("Info: Member activated successfully!");
			this.activateStatus = true;
			return true;
		}
		else
		{
			System.out.println("Error: Member already activated!");
			return false;
		}
	}

	@Override
	public String toString() 
	{
		String str = this.memberID + ":" +  this.memberName + ":" + StandardMember.credit;
		return str;
	}

	@Override
	public String print() 
	{
		String memberStr = "ID:\t" +  this.memberID
						+ "\nTitle\t" + this.memberName
						+ "\nRemaining Credit:\t" + StandardMember.credit + "\n";
		
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
		
		String itemStr = "Current holdings on loan:\n\n" + itemId;
		
		// Finally, return two parts of the strings.
		return memberStr + itemStr;
	}

	@Override
	public void setCredit(double credit) 
	{
		StandardMember.credit = credit;
	}

	@Override
	public void addCredit(double creditAddValue) 
	{
		if ((StandardMember.credit +  creditAddValue) >= this.maxiumCredit)
		{
			System.out.println("Error: Credit already reset to maxium value!");
		}
		else
		{
			StandardMember.credit += creditAddValue;
		}
	}

	@Override
	public void substractCredit(double creditSubstractValue) 
	{
		StandardMember.credit -= creditSubstractValue;
	}

	@Override
	public double getCredit() 
	{
		return StandardMember.credit;
	}
	
	@Override
	public boolean resetCredit() 
	{
		if (StandardMember.credit >= this.maxiumCredit)
		{
			System.out.println("Error: Credit already reset to maxium value!");
			return false;
		}
		else
		{
			StandardMember.credit = this.maxiumCredit;
			return true;
		}
	}

	@Override
	public String getId() 
	{
		return this.memberID;
	}

	@Override
	public String getName() 
	{
		return this.memberName;
	}
}