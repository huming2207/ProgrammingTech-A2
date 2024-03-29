package main;

import java.util.Scanner;
import lms.model.facade.*;			

// Nothing needs to explain in this Main method. It's just some boring user keyboard input stuff.


public class Main 
{
	// It's a bit weird below, but it seems that so far I have to do like this?
	private static final String mainMenuStr = 
			"\n\n\n*************** LIBRARY MANAGEMENT - MENU ****************\n" + 
			"\n" + 
			"1.  Add Holding\n" + 
			"2.  Remove Holding\n" + 
			"3.  Add Member\n" + 
			"4.  Remove Member\n" + 
			"5.  Borrow Holding\n" + 
			"6.  Return Holding\n" + 
			"7.  Print All Holdings\n" + 
			"8.  Print All Members\n" + 
			"9.  Print Specific Holding\n" + 
			"10. Print Specific Member\n" + 
			"11. Active\n" + 
			"12. Deactive\n" + 
			"13. Reset Member's Credit\n" + 
			"14. Save to File\n" + 
			"15. Load from File\n" + 
			"0.  Exit\n" + 
			"\n" + 
			"**********************************************************\n" +
			"Enter selection: ";
	
	private static LMSFacade facadeController = new LMSFacade();
	
	public static void main(String[] args) 
	{
		facadeController.initialiseEngine();
		mainMenu();
	}
	
	private static void mainMenu()
	{
		Scanner userInputScanner = new Scanner(System.in);
		int selection = 0;
		System.out.print(mainMenuStr);
		
		if(userInputScanner.hasNextInt())
		{
			selection = userInputScanner.nextInt();
			userInputScanner.nextLine();
			System.out.println("Your choice: " + selection);
			selectionSwitch(selection);
		}
		else
		{
			System.out.println("Error: It seems that you've put something wrong? Try again!");
			mainMenu();
		}
		
		
		// Dispose it after user inputs.
		userInputScanner.close();
		
	}
	
	private static void selectionSwitch(int selection)
	{
		// Main switch case for menu selection
		switch(selection)
		{
			case 1:
			{
				addHolding();
				break;
			}
			case 2:
			{
				removeHolding();
				break;
			}
			case 3:
			{
				addMember();
				break;
			}
			case 4:
			{
				removeMember();
				break;
			}
			case 5:
			{
				borrowHolding();
				break;
			}
			case 6:
			{
				returnHolding();
				break;
			}
			case 7:
			{
				printAllHoldings();
				break;
			}
			case 8:
			{
				printAllMembers();
				break;
			}
			case 9:
			{
				printSpecificHolding();
				break;
			}
			case 10:
			{
				printSpecificMember();
				break;
			}
			case 11:
			{
				active();
				break;
			}
			case 12:
			{
				deactive();
				break;
			}
			case 13:
			{
				resetCredit();
				break;
			}
			case 14:
			{
				saveToFile();
				break;
			}
			case 15:
			{
				loadFromFile();
				break;
			}
			case 0:
			{
				System.out.println("Gracefully exiting this program. See you mate.");
				facadeController.saveFile();
				System.exit(0);
			}
			
		}
	}
	
	private static void addHolding()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("Is this a book, or a video? \nEnter \"1\" for book, \"2\" for video: ");
		
		if(userInputScanner.hasNextInt())
		{
			int userSelection = userInputScanner.nextInt();
			
			// Before continue, use this to "absorb" the damn useless newline symbol (i.e. "\n")
			// Otherwise all the things below won't work.
			userInputScanner.nextLine();
			
			if (userSelection == 1) 
			{
				System.out.print("\n\nEnter book item ID: ");
				String itemId = userInputScanner.nextLine();
				
				if (itemId.length() < 6)
				{
					System.out.print("Error: add failed, wrong format!");
					mainMenu();
				}
				
				System.out.print("\nEnter book item title: ");
				String itemTitle = userInputScanner.nextLine();
				System.out.print("\nEnter book pages: ");
				int itemPages = userInputScanner.nextInt();
				
				System.out.println("Summary:\n\tBook ID: " + itemId + "\n\tBook title: " + itemTitle + "\n\tBook pages: " + itemPages);
				
				if (!facadeController.addBook(itemId, itemTitle, itemPages))
				{
					System.out.println("Error: Book failed to add.");
					mainMenu();
				}
				else
				{
					System.out.println("Info: Book added successfully.");
				}
				
			}
			else if (userSelection == 2) 
			{
				System.out.print("\n\nEnter video item ID: ");
				String itemId = userInputScanner.nextLine();
				
				if (itemId.length() < 6)
				{
					System.out.print("Error: add failed, wrong format!");
					mainMenu();
				}
				
				System.out.print("\nEnter video item title: ");
				String itemTitle = userInputScanner.nextLine();
				System.out.print("\nEnter video loan fee: ");
				double itemFee = userInputScanner.nextDouble();
				System.out.print("\nEnter video length (time): ");
				double itemLength = userInputScanner.nextDouble();
				
				
				System.out.println("Summary:\n\tVideo ID: " + itemId 
									+ "\n\tVideo title: " + itemTitle 
									+ "\n\tVideo loan fee: " + itemFee 
									+ "\n\tVideo length: " + itemLength);
				
				if (!facadeController.addVideo(itemId, itemTitle, itemFee, itemLength))
				{
					System.out.println("Error: Video failed to add");
					mainMenu();
				}
				else
				{
					System.out.println("Info: Book added successfully.");
					mainMenu();
				}
				
			}
			else
			{
				System.out.println("Error: You've input something invalid!");
				mainMenu();
			}
		}
		else
		{
			System.out.println("Error: You've input something invalid!");
			mainMenu();
		}
		
		mainMenu();
		userInputScanner.close();
	}
	
	private static void removeHolding()
	{
		Scanner userInputScanner = new Scanner(System.in);
		System.out.print("Enter holding ID: ");
		String userInput = userInputScanner.nextLine();
		
		if (userInput.length() < 6)
		{
			System.out.print("Error: add failed, wrong format!");
			mainMenu();
		}
			
		if (facadeController.removeHolding(userInput))
		{
			System.out.print("Info: Holding removed successfully.");
		}
		
		mainMenu();
		userInputScanner.close();
		
	}
	
	private static void addMember()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter member ID: ");
		String memberId = userInputScanner.nextLine();

		if (memberId.length() < 6)
		{
			System.out.print("Error: add failed, wrong format!");
			mainMenu();
		}
		
		System.out.print("\nEnter member's name: ");
		String memberName = userInputScanner.nextLine();
		
		
		if (facadeController.addMember(memberId, memberName))
		{
			System.out.println("Info: Member added successfully!");
		}
		else
		{
			System.out.println("Error: Member failed to add, name: " + memberName + "   ID: " + memberId);
		}
		
		mainMenu();
		userInputScanner.close();
	}
	
	private static void removeMember()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter member ID: ");
		String memberId = userInputScanner.nextLine();
	
		if (memberId.length() < 6)
		{
			System.out.print("Error: removal failed, wrong format!");
			mainMenu();
		}
		
		if (facadeController.removeMember(memberId))
		{
			System.out.println("Info: Member remove successfully!");
		}
		else
		{
			System.out.println("Error: Member failed to remove, " + "ID: " + memberId);
		}
		
		mainMenu();
		userInputScanner.close();
	}
	
	private static void borrowHolding()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter member ID: ");
		String memberId = userInputScanner.nextLine();
		
		if (memberId.length() < 6)
		{
			System.out.print("Error: failed with wrong format!");
			mainMenu();
		}
		
		System.out.print("\n\nEnter holding ID: ");
		String holdingId = userInputScanner.nextLine();
		
		if (holdingId.length() < 6)
		{
			System.out.print("Error: failed with wrong format!");
			mainMenu();
		}
		
		
		if (facadeController.borrowHolding(memberId, holdingId))
		{
			System.out.println("Info: Holding borrowed successfully!");
		}
		else
		{
			System.out.println("Error: Member failed to borrow, " + "Member ID: " + memberId + " Holding ID: " + holdingId);
		}
		
		mainMenu();
		userInputScanner.close();
	}
	
	private static void returnHolding()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter member ID: ");
		String memberId = userInputScanner.nextLine();
		
		if (memberId.length() < 6)
		{
			System.out.print("Error: failed with wrong format!");
			mainMenu();
		}
		
		System.out.print("\n\nEnter holding ID: ");
		String holdingId = userInputScanner.nextLine();

		if (holdingId.length() < 6)
		{
			System.out.print("Error: failed with wrong format!");
			mainMenu();
		}
		
		if (facadeController.returnHolding(memberId, holdingId, facadeController.currentTime))
		{
			System.out.println("Info: Holding returned successfully!");
		}
		else
		{
			System.out.println("Error: Member failed to borrow, " + "Member ID: " + memberId + " Holding ID: " + holdingId);
		}
		
		mainMenu();
		userInputScanner.close();
	}

	private static void printAllHoldings()
	{
		System.out.println(facadeController.printAllHoldings());
		mainMenu();
	}
	
	private static void printAllMembers()
	{
		System.out.println(facadeController.printAllMembers());
		mainMenu();
	}
	
	private static void printSpecificHolding()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter holding ID: ");
		String holdingId = userInputScanner.nextLine();
		
		if (holdingId.length() < 6)
		{
			System.out.print("Error: failed with wrong format!");
			mainMenu();
		}
		
		System.out.println(facadeController.printSpecificHolding(holdingId));
		
		mainMenu();
		userInputScanner.close();
	}
	
	private static void printSpecificMember()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter member ID: ");
		String memberId = userInputScanner.nextLine();
		
		if (memberId.length() < 6)
		{
			System.out.print("Error: failed with wrong format!");
			mainMenu();
		}
		
		System.out.println(facadeController.printSpecificHolding(memberId));
		
		mainMenu();
		userInputScanner.close();
	}
	
	private static void active()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter ID: ");
		String idStr = userInputScanner.nextLine();
		
		if (idStr.length() < 6)
		{
			System.out.print("Error: failed with wrong format!");
			mainMenu();
		}
		
		if(facadeController.activate(idStr))
		{
			System.out.println("Info: Member/Holding activated!");
		}
		else
		{
			System.out.println("Error: Something went wrong, member/holding cannot be activated!");
		}
		
		mainMenu();
		userInputScanner.close();
	}
	
	private static void deactive()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter ID: ");
		String idStr = userInputScanner.nextLine();
		
		if (idStr.length() < 6)
		{
			System.out.print("Error: failed with wrong format!");
			mainMenu();
		}
		
		if(facadeController.deactivate(idStr))
		{
			System.out.println("Info: Member/Holding deactivated!");
		}
		else
		{
			System.out.println("Error: Something went wrong, member/holding cannot be deactivated!");
		}
		
		mainMenu();
		userInputScanner.close();
	}
	
	private static void resetCredit()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter ID: ");
		String idStr = userInputScanner.nextLine();
		
		if (idStr.length() < 6)
		{
			System.out.print("Error: failed with wrong format!");
			mainMenu();
		}
		
		if(facadeController.resetMembersCredit(idStr))
		{
			System.out.println("Info: Member/Holding has been reset!");
		}
		else
		{
			System.out.println("Error: Something went wrong, member/holding cannot be reset!");
		}
		
		mainMenu();
		userInputScanner.close();
	}
	
	private static void saveToFile()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter \"A\" to save to another path (folder), \nFor default, enter something else:");
		String idStr = userInputScanner.nextLine();
		
		if (idStr.matches("A"))
		{
			System.out.print("\n\nEnter path:");
			String pathStr = userInputScanner.nextLine();
			facadeController.saveFile(pathStr);
			System.out.println("\nInfo: File saved.");
		}
		else
		{
			facadeController.saveFile();
			System.out.println("\nInfo: File saved.");
		}
		
		System.out.print("\n\nEnter \"E\" to exit, \nIf you want to stay, enter something else:");
		String exitStr = userInputScanner.nextLine();
		
		if (exitStr.matches("E"))
		{
			System.out.println("Info: See you mate~!");
			System.exit(0);
		}
		else
		{
			mainMenu();
			userInputScanner.close();
		}
		
	}
	
	private static void loadFromFile()
	{
		Scanner userInputScanner = new Scanner(System.in);
		
		System.out.print("\n\nEnter \"A\" to load file another path (folder), \nFor default, enter something else:");
		String selectionStr = userInputScanner.nextLine();
		
		if (selectionStr.matches("A") || selectionStr.matches("a"))
		{
			System.out.print("\n\nEnter path:");
			String pathStr = userInputScanner.nextLine();
			facadeController.readFile(pathStr);
			System.out.println("\nInfo: File loaded.");
		}
		else
		{
			facadeController.readFile();
			System.out.println("\nInfo: File loaded.");
		}
		
		System.out.print("\n\nEnter \"E\" to exit, \nIf you want to stay, enter something else:");
		String exitStr = userInputScanner.nextLine();
		
		if (exitStr.matches("E") || exitStr.matches("e"))
		{
			System.out.println("Info: See you mate~!");
			System.exit(0);
		}
		else
		{
			mainMenu();
			userInputScanner.close();
		}
	}
	

	

}
