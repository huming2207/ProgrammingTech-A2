package library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import member.Member;
import member.PremiumMember;
import member.StandardMember;
import holding.Holding;
import holding.Book;
import holding.Video;
import lms.model.util.DateTime;

public class FileHandler 
{
	public FileHandler()
	{
		
	}
	
	public ArrayList<Member> readMemberList(ArrayList<Holding> holdingList, String memberFilePath, String backupFilePath)
	{
		Scanner memberFileReader = null;
		ArrayList<Member> memberList = new ArrayList<Member>();
		StringTokenizer strSplitter = null;
		String[] memberStr = new String[3];
		Member member = null;
		Holding holding = null;
		
		try
		{
			memberFileReader = new Scanner(new File(memberFilePath));
			
		}
		catch (FileNotFoundException error)
		{
			// Do nothing but try on the backup file instead.
			try
			{
				memberFileReader = new Scanner(new File(backupFilePath));
			}
			catch (FileNotFoundException anotherError)
			{
				System.out.println("Error: List file not found in path: \n" + memberFilePath + "\n" + backupFilePath);
				
				// TODO: Make it easier to debug, but the teacher may not allow to print the trace record.
				error.printStackTrace();
				System.out.println("\n******************************\n******************************");
				anotherError.printStackTrace();
				
				
				System.exit(1);
			}
		}
		
		while (memberFileReader.hasNextLine())
		{
			strSplitter = new StringTokenizer(memberFileReader.nextLine(), ":");
			
			// The tokens' amount cannot be less than 3, otherwise it might be corrupted or incorrect.
			if (strSplitter.countTokens() < 3)
			{
				System.out.println("Error: List file maybe corrupted!");
			}
			else
			{
				memberStr[0] = strSplitter.nextToken(); // Member ID
				memberStr[1] = strSplitter.nextToken(); // Member Name
				memberStr[2] = strSplitter.nextToken(); // Member remaining credit
				
				
				if (memberStr[0].startsWith("s"))
				{
					// Premium member found
					member = new PremiumMember(memberStr[0], memberStr[1]);
				}
				else if (memberStr[0].startsWith("p"))
				{
					// Premium member found
					member = new StandardMember(memberStr[0], memberStr[1]);
				}
				else
				{
					System.out.println("Error: Invalid member information found!");
				}
				
				
				// The rest of them are some unique settings for each itself.
				// After that, save it to the list.
				member.setCredit(Double.parseDouble(memberStr[2]));
				
				
				/*
				 * In my situation,
				 * Since the format is:
				 * 		id:name:credit:book1:book2:........:book(N-1):bookN
				 * 
				 * So, if it has more tokens (elements) then it must be holding items. Add it to this member.
				 * 
				 * */
				while (strSplitter.hasMoreTokens())
				{
					String bookId = strSplitter.nextToken();
					for (int i = 0; i < holdingList.size(); i++)
					{
						if (holdingList.get(i).getId().matches(bookId))
						{
							holding = holdingList.get(i);
							member.borrowHolding(holding);
						}
					}
				}
				
				
				// Add it to the list
				memberList.add(member);
				
			}
		}
		
		// Free up the memory
		memberFileReader.close();
		return memberList;
	}
	
	public ArrayList<Holding> readHoldingList(String holdingFilePath, String backupFilePath)
	{
		Scanner holdingFileReader = null;
		Holding holding = null;
		StringTokenizer strSplitter = null;
		String[] holdingStr = new String[7]; // Each holding information has 7 elements
		ArrayList<Holding> holdingList = new ArrayList<Holding>();
		
		try
		{
			holdingFileReader = new Scanner(new File(holdingFilePath));
		}
		catch (FileNotFoundException error)
		{
			// Do nothing but try on the backup file instead.
			try
			{
				holdingFileReader = new Scanner(new File(backupFilePath));
			}
			catch (FileNotFoundException anotherError)
			{
				System.out.println("Error: List file not found in path: \n" + holdingFilePath + "\n" + backupFilePath);
				
				// TODO: Make it easier to debug, but the teacher may not allow to print the trace record.
				error.printStackTrace();
				System.out.println("\n******************************\n******************************");
				anotherError.printStackTrace();
				
				System.exit(1);
			}
		}
		
		while (holdingFileReader.hasNextLine())
		{
			strSplitter = new StringTokenizer(holdingFileReader.nextLine(), ":");
			
			// The tokens' amount cannot be less than 7, otherwise it might be corrupted or incorrect.
			if (strSplitter.countTokens() < 7)
			{
				System.out.println("Error: List file maybe corrupted!");
			}
			else
			{
				holdingStr[0] = strSplitter.nextToken();	// Holding ID
				holdingStr[1] = strSplitter.nextToken();	// Holding Title (Name)
				holdingStr[2] = strSplitter.nextToken();	// Page/Length
				holdingStr[3] = strSplitter.nextToken();	// Loan date
				holdingStr[4] = strSplitter.nextToken(); 	// Loan fee
				holdingStr[5] = strSplitter.nextToken(); 	// Maximum loan period
				holdingStr[6] = strSplitter.nextToken(); 	// Active Status
				
				
				if (holdingStr[0].startsWith("b"))
				{
					holding = new Book(holdingStr[0], holdingStr[1], Integer.parseInt(holdingStr[2]));
				}
				else if (holdingStr[0].startsWith("v"))
				{
					holding = new Video(holdingStr[0], holdingStr[1], 
										Double.parseDouble(holdingStr[4]), Double.parseDouble(holdingStr[2]));
				}
				
				if (holdingStr[3].contains("null"))
				{
					System.out.println("Info: Holding " + holding.getId() + "has an empty borrow date.");
				}
				else
				{
					// dateStr is for getting the split result, has 3 string elements in total
					String[] dateStr = holdingStr[3].split("/");
					
					holding.setDateBorrowed(new DateTime(
							Integer.parseInt(dateStr[0]),		// Day
							Integer.parseInt(dateStr[0]),		// Month
							Integer.parseInt(dateStr[0])));		// Year
				}
				
				if (holdingStr[6].matches("deactive"))
				{
					holding.deactivate();
				}
				
				holdingList.add(holding);
			}
			
		}
		
		holdingFileReader.close();
		return holdingList;
	}
	
	public void writeHoldingToFile(String filePath, ArrayList<Holding> holdingList)
	{
		FileWriter fileWriter = null;
		try
		{
			fileWriter = new FileWriter(filePath, false);
		}
		catch (FileNotFoundException existenceError)
		{

		}
		catch (IOException IOError)
		{
			
		}
		
		for (int i = 0; i < holdingList.size(); i++)
		{
			if (holdingList.get(i).getId().startsWith("b"))
			{
				String strToWrite = holdingList.get(i).getId()  + ":" 
								  + holdingList.get(i).getTitle() + ":"
								  + holdingList.get(i).getPages() + ":"
								  + holdingList.get(i).getDateBorrowed().getFormattedDate() + ":"
								  + holdingList.get(i).getLoanFee() + ":"
								  + holdingList.get(i).getLoanPeriod() + ":"
								  + holdingList.get(i).getActiveStatusStr() + "\n";
				try 
				{
					fileWriter.write(strToWrite);
				} 
				
				catch (IOException error) 
				{
					System.out.println("Error: cannot write to file.");
					error.printStackTrace();
				}
				
			}
			else if (holdingList.get(i).getId().startsWith("v"))
			{
				String strToWrite = holdingList.get(i).getId()  + ":" 
						  + holdingList.get(i).getTitle() + ":"
						  + holdingList.get(i).getLength() + ":"
						  + holdingList.get(i).getLoanFee() + ":"
						  + holdingList.get(i).getLoanPeriod() + ":"
						  + holdingList.get(i).getActiveStatusStr() + "\n";
				try 
				{
					fileWriter.write(strToWrite);
				} 
		
				catch (IOException error) 
				{
					System.out.println("Error: cannot write to file. I/O Error.");
					error.printStackTrace();
				}
			}
		}
		
		try 
		{
			/*
			 * I have to flush the cache every time it finishes write to disk.
			 * Otherwise sometimes it will returns me an empty file!
			 * */
			fileWriter.flush();
		} 
		catch (IOException e) 
		{
			System.out.println("Error: File cache error! That so rare! Congratulations!\nCheck your drives first!");
			e.printStackTrace();
		}
		
	}
	
	public void writeMemberToFile(String filePath, ArrayList<Member> memberList)
	{
		FileWriter fileWriter = null;
		try
		{
			fileWriter = new FileWriter(filePath, false);
		}
		catch (FileNotFoundException existenceError)
		{

		}
		catch (IOException IOError)
		{
			
		}
		
		for (int i = 0; i < memberList.size(); i++)
		{
			if (memberList.get(i).getId().startsWith("p") || memberList.get(i).getId().startsWith("s"))
			{
				String strToWrite = memberList.get(i).getId() + ":" 
								  + memberList.get(i).getName() + ":"
								  + memberList.get(i).getCredit();
				
				// If the member has holding, then 
				if (memberList.get(i).hasHolding())
				{
					strToWrite += ":" + memberList.get(i).getHoldingStr() + "\n";
				}
				else
				{
					strToWrite += "\n";
				}
				
				
				try 
				{
					fileWriter.write(strToWrite);
				} 
				
				catch (IOException error) 
				{
					System.out.println("Error: cannot write to file. I/O Error.");
					error.printStackTrace();
				}
				
			}
			else 
			{
				System.out.println("Error: cannot write to file. Array corrupted.");
			}
		}
		
		try 
		{
			/*
			 * I have to flush the cache every time it finishes write to disk.
			 * Otherwise sometimes it will returns me an empty file!
			 * */
			fileWriter.flush();
		} 
		catch (IOException e) 
		{
			System.out.println("Error: File cache error! That so rare! Congratulations!\nCheck your drives first!");
			e.printStackTrace();
		}
		
	}
}
