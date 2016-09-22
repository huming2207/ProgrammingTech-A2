package member;

import java.util.ArrayList;
import java.util.List;
import holding.Book;
import holding.Holding;
import holding.Video;
import lms.model.util.DateTime;

public class PremiumMember extends Member
{
	private String memberID;
	private String memberName;
	private static double credit;
	private final double MAX_CREDIT = 45;
	private boolean activateStatus;
	private List<Book> memberBook = new ArrayList<Book>();
	private List<Video> memberVideo = new ArrayList<Video>();
	private double lateFee = 0.0;
	
	public PremiumMember(String premimumMemberId, String premiumMemberName)
	{
		super(premimumMemberId, premiumMemberName, credit);
		this.memberID = premimumMemberId;
		this.memberName = premiumMemberName;
	}

	@Override
	public boolean borrowHolding(Holding holding) 
	{
		// Get current time in Unix time stamp.
		DateTime time = new DateTime();
				
		// Judge whether the member borrowed a book or a video first.
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
			if(holding.returnHolding(returnDate))
			{
				System.out.println("Info: Book returned successfully!!");
				
				if(book.getLatePenalty() > 0)
				{
					lateFee += book.getLatePenalty();
				}
				
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
				
				if(video.getLatePenalty() > 0)
				{
					lateFee += video.getLatePenalty();
				}
				
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
		String str = this.memberID + ":" +  this.memberName + ":" + credit;
		return str;
	}

	@Override
	public String print() 
	{
		String memberStr = "ID:\t\t\t" +  this.memberID
						+ "\nTitle\t\t\t" + this.memberName
						+ "\nRemaining Credit:\t" + credit + "\n";
		
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

	@Override
	public void setCredit(double credit) 
	{
		PremiumMember.credit = credit;
	}

	@Override
	public void addCredit(double creditAddValue) 
	{
		if ((PremiumMember.credit +  creditAddValue) >= this.MAX_CREDIT)
		{
			System.out.println("Error: Credit already reset to maxium value!");
		}
		else
		{
			PremiumMember.credit += creditAddValue;
		}
	}

	@Override
	public void substractCredit(double creditSubstractValue) 
	{
		PremiumMember.credit -= creditSubstractValue;
	}

	@Override
	public double getCredit() 
	{
		return PremiumMember.credit;
	}
	
	@Override
	public boolean resetCredit() 
	{
		if (PremiumMember.credit >= this.MAX_CREDIT)
		{
			System.out.println("Error: Credit already reset to maxium value!");
			return false;
		}
		else
		{
			PremiumMember.credit = this.MAX_CREDIT;
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

	@Override
	public double getLatePenalty() 
	{
		return this.lateFee;
	}
}
