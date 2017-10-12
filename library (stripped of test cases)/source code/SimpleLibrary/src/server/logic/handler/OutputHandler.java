package server.logic.handler;

import java.util.Date;

import server.logic.handler.model.Output;
import server.logic.model.Loan;
import server.logic.model.Title;
import server.logic.model.User;
import server.logic.tables.FeeTable;
import server.logic.tables.ItemTable;
import server.logic.tables.LibrarianTable;
import server.logic.tables.LoanTable;
import server.logic.tables.TitleTable;
import server.logic.tables.UserTable;
import utilities.Config;

public class OutputHandler {
	public static final int WAITING = 0;
	public static final int FINISHWAITING=1;
	public static final int CLERK = 2;
	public static final int USER = 3;
	public static final int CREATEUSER=4;
	public static final int CREATETITLE=5;
	public static final int CREATEITEM=6;
	public static final int DELETEUSER=7;
	public static final int DELETETITLE=8;
	public static final int DELETEITEM=9;
	public static final int BORROW=10;
	public static final int RENEW=11;
	public static final int RETURN=12;
	public static final int PAYFINE=13;
	public static final int CLERKLOGIN=14;
	public static final int USERLOGIN=15;
	public static final int TITLE_EXISTS = 16;
	public static final int TITLE_DOESNT_EXIST = 17;
	public static final int USER_EXISTS = 18;
	public static final int USER_DOESNT_EXIST = 19;
	public static final int ITEM_EXISTS = 20;
	public static final int ITEM_DOESNT_EXIST = 21;

	public Output createUser(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean email=strArray[0].contains("@");
		Object result="";
		if(strArray.length!=2 || email!=true){
			output.setOutput("Your input should in this format:'username,password'");
			output.setState(CREATEUSER);
		}else{
			result=UserTable.getInstance().createuser(strArray[0], strArray[1]);
			if(result.equals(true)){
				output.setOutput("Success!");
			}else{
				output.setOutput("The User Already Exists!");
			}
			output.setState(CLERK);
		}
		return output;
	}

	public Output createTitle(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean number=isInteger(strArray[0]);
		Object result="";
		if(strArray.length!=2 || number!=true){
			output.setOutput("Your input should in this format:'ISBN,title',ISBN should be a 13-digit number");
			output.setState(CREATETITLE);
		}else{
			result=TitleTable.getInstance().createtitle(strArray[0], strArray[1]);
			if(result.equals(true)){
				output.setOutput("Success!");
			}else{
				output.setOutput("The Title Already Exists!");
			}
			output.setState(CLERK);
		}
		return output;
	}

	public Output createItem(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean number=isInteger(strArray[0]);
		Object result="";
		if(strArray.length!=1 || number!=true){
			output.setOutput("Your input should in this format:'ISBN',ISBN should be a 13-digit number");
			output.setState(CREATEITEM);
		}else{
			result=ItemTable.getInstance().createitem(strArray[0]);
			if(result.equals(true)){
				output.setOutput("Success!");
			}else{
				output.setOutput("The Title Does Not Exists!");
			}
			output.setState(CLERK);
		}
		return output;
	}

	public Output deleteUser(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		int userid=UserTable.getInstance().lookup(strArray[0]);
		boolean email=strArray[0].contains("@");
		Object result="";
		if(strArray.length!=1 || email!=true){
			output.setOutput("Your input should in this format:'useremail'");
			output.setState(DELETEUSER);
		}else if(userid==-1){
			output.setOutput("The User Does Not Exist!");
			output.setState(DELETEUSER);
		}else{
			result=UserTable.getInstance().delete(userid);
			if(result.equals("success")){
				output.setOutput("Success!");
			}else{
				output.setOutput(result+"!");
			}
			output.setState(CLERK);
		}
		return output;
	}

	public Output deleteTitle(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean number=isInteger(strArray[0]);
		Object result="";
		if(strArray.length!=1 || number!=true){
			output.setOutput("Your input should in this format:'ISBN',ISBN should be a 13-digit number");
			output.setState(DELETETITLE);
		}else{
			result=TitleTable.getInstance().delete(strArray[0]);
			if(result.equals("success")){
				output.setOutput("Success!");
			}else{
				output.setOutput(result+"!");
			}
			output.setState(CLERK);
		}
		return output;
	}

	public Output deleteItem(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean number=isInteger(strArray[0]);
		Object result="";
		if(strArray.length!=2 || number!=true){
			output.setOutput("Your input should in this format:'ISBN,copynumber',ISBN should be a 13-digit number");
			output.setState(DELETEITEM);
		}else{
			boolean copynumber=isNumber(strArray[1]);
			if(copynumber!=true){
				output.setOutput("Your input should in this format:'ISBN,copynumber',ISBN should be a 13-digit number");
				output.setState(DELETEITEM);
			}else{
				result=ItemTable.getInstance().delete(strArray[0], strArray[1]);
				if(result.equals("success")){
					output.setOutput("Success!");
				}else{
					output.setOutput(result+"!");
				}
				output.setState(CLERK);
			}
		}
		return output;
	}

	public Output borrow(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean email=strArray[0].contains("@");
		int userid=UserTable.getInstance().lookup(strArray[0]);
		Object result="";
		if(strArray.length!=3 || email!=true){
			output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
			output.setState(BORROW);
		}else if(userid==-1){
			output.setOutput("The User Does Not Exist!");
			output.setState(BORROW);
		}else{
			boolean ISBN=isInteger(strArray[1]);
			boolean copynumber=isNumber(strArray[2]);
			if(ISBN!=true || copynumber!=true){
				output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
				output.setState(BORROW);
			}else{
				result=LoanTable.getInstance().createloan(userid, strArray[1], strArray[2], new Date());
				if(result.equals("success")){
					output.setOutput("Success!");
				}else{
					output.setOutput(result+"!");
				}
			}
			output.setState(USER);
		}
		return output;
	}

	public Output renew(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean email=strArray[0].contains("@");
		int userid=UserTable.getInstance().lookup(strArray[0]);
		Object result="";
		if(strArray.length!=3 || email!=true){
			output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
			output.setState(RENEW);
		}else if(userid==-1){
			output.setOutput("The User Does Not Exist!");
			output.setState(RENEW);
		}else{
			boolean ISBN=isInteger(strArray[1]);
			boolean copynumber=isNumber(strArray[2]);
			if(ISBN!=true || copynumber!=true){
				output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
				output.setState(RENEW);
			}else{
				result=LoanTable.getInstance().renewal(userid, strArray[1], strArray[2], new Date());
				if(result.equals("success")){
					output.setOutput("Success!");
				}else{
					output.setOutput(result+"!");
				}
			}
			output.setState(USER);
		}
		return output;
	}

	public Output returnBook(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean email=strArray[0].contains("@");
		int userid=UserTable.getInstance().lookup(strArray[0]);
		Object result="";
		if(strArray.length!=3 || email!=true){
			output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
			output.setState(RETURN);
		}else if(userid==-1){
			output.setOutput("The User Does Not Exist!");
			output.setState(RETURN);
		}else{
			boolean ISBN=isInteger(strArray[1]);
			boolean copynumber=isNumber(strArray[2]);
			if(ISBN!=true || copynumber!=true){
				output.setOutput("Your input should in this format:'useremail,ISBN,copynumber'");
				output.setState(RETURN);
			}else{
				result=LoanTable.getInstance().returnItem(userid, strArray[1], strArray[2], new Date());
				if(result.equals("success")){
					output.setOutput("Success!");
				}else{
					output.setOutput(result+"!");
				}
			}
			output.setState(USER);
		}
		return output;

	}

	public Output payFine(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean email=strArray[0].contains("@");
		int userid=UserTable.getInstance().lookup(strArray[0]);
		Object result="";
		if(strArray.length!=1 || email!=true){
			output.setOutput("Your input should in this format:'useremail'");
			output.setState(PAYFINE);
		}else if(userid==-1){
			output.setOutput("The User Does Not Exist!");
			output.setState(PAYFINE);
		}else{
			result=FeeTable.getInstance().payfine(userid);
			if(result.equals("success")){
				output.setOutput("Success!");
			}else{
				output.setOutput(result+"!");
			}
			output.setState(USER);
		}

		return output;
	}

	public Output clerkLogin(String input) {
		Output output=new Output("",0);
		if(input.equalsIgnoreCase(Config.CLERK_PASSWORD)){
			output.setOutput("What can I do for you?Menu:Create User/Title/Item,Delete User/Title/Item.");
			output.setState(CLERK);
		}else{
			output.setOutput("Wrong Password!Please Input The Password:");
			output.setState(CLERKLOGIN);
		}
		return output;
	}

	public Output librarianLogin(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean email=strArray[0].contains("@");
		int result=0;
		if(strArray.length!=2 || email!=true){
			output.setOutput("Your input should in this format:'username,password'");
			output.setState(InputHandler.LIBRARIANLOGIN);
		}else{
			result=LibrarianTable.getInstance().checkUser(strArray[0], strArray[1]);
			if(result==0){
				output.setOutput("Please select one of the following options: 'add item', 'add user', ");
				output.setState(InputHandler.LIBRARIAN);
			}else if(result==1){
				output.setOutput("Wrong Password!Please Input Username and Password:'username,password'");
				output.setState(InputHandler.LIBRARIANLOGIN);
			}else{
				output.setOutput("The User Does Not Exist!Please The Username and Password:'username,password'");
				output.setState(InputHandler.LIBRARIANLOGIN);
			}
		}
		return output;
	}

	public Output userLogin(String input) {
		Output output=new Output("",0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean email=strArray[0].contains("@");
		int result=0;
		if(strArray.length!=2 || email!=true){
			output.setOutput("Your input should in this format:'username,password'");
			output.setState(USERLOGIN);
		}else{
			result=UserTable.getInstance().checkUser(strArray[0], strArray[1]);
			if(result==0){
				output.setOutput("What can I do for you?Menu:Borrow,Renew,Return,Pay Fine.");
				output.setState(USER);
			}else if(result==1){
				output.setOutput("Wrong Password!Please Input Username and Password:'username,password'");
				output.setState(USERLOGIN);
			}else{
				output.setOutput("The User Does Not Exist!Please The Username and Password:'username,password'");
				output.setState(USERLOGIN);
			}
		}
		return output;
	}
	
	public Output systemStatus() {
		Output systemStatus = new Output("",0);
		
		// Build the user list
		String output = "------\nUsers:\n------\n";
		for (User user : UserTable.getInstance().getUserTable()) {
			output += "[Name][User ID]==[" + user.getUsername() + "][" + user.getUserid() + "]\n";
		}
		// Build the title list
		output += "\n-------\nTitles:\n-------\n";
		for (Title title : TitleTable.getInstance().getTitleTable()) {
			output += "[Book title][ISBN]==[" + title.getBooktitle() + "][" + title.getISBN() + "]\n";
		}
		systemStatus.setOutput(output);
		systemStatus.setState(InputHandler.LIBRARIANLOGIN);
		
		return systemStatus;
	}
	
	public Output findTitle(String isbn) {
		Output output = new Output("", 0);
		Object result = "";
		boolean isInteger = isInteger(isbn);
		if (isbn.length() != 13 || !isInteger) {
			output.setOutput("The ISBN must be 13 digits long.");
		} else {
			result = TitleTable.getInstance().lookup(isbn);
			if ((boolean)result) {
				output.setOutput("Title already exists.");
				output.setState(TITLE_EXISTS);
			} else {
				output.setOutput("Title does not exist.");
				output.setState(TITLE_DOESNT_EXIST);
			}
		}
		return output;
	}
	
	public Output findUser(String userId) {
		Output output = new Output("", 0);
		Object result = "";
		boolean isInteger = isNumber(userId);
		if (!isInteger) {
			output.setOutput("The user ID must be a number.");
		} else {
			result = UserTable.getInstance().lookup(Integer.valueOf(userId));
			if ((boolean)result) {
				output.setOutput("User already exists.");
				output.setState(USER_EXISTS);
			} else {
				output.setOutput("User does not exist.");
				output.setState(USER_DOESNT_EXIST);
			}
		}
		return output;
	}
	
	public Output findItem(String itemId, String copyNumber) {
		Output output = new Output("", 0);
		Object result = "";
		boolean isInteger = isNumber(itemId);
		if (!isInteger) {
			output.setOutput("The item ID must be a number.");
		} else {
			result = ItemTable.getInstance().lookup(ItemTable.getInstance().lookupISBN(Integer.parseInt(itemId)), copyNumber);
			if ((boolean)result) {
				output.setOutput("Item already exists.");
				output.setState(ITEM_EXISTS);
			} else {
				output.setOutput("Item does not exist.");
				output.setState(ITEM_DOESNT_EXIST);
			}
		}
		return output;
	}
	
	public Output checkReservation(String input) {
		Output output = new Output("", 0);
		String[] strArray = null;
		strArray = input.split(",");
		boolean isISBN = OutputHandler.isInteger(strArray[0]);
		if (strArray.length!=2 || !isISBN) {
			output.setOutput("Your input should be in this format:'ISBN,copyID'");
			output.setState(InputHandler.CHECK_RESERVATION);
		} else {
			boolean onLoan = LoanTable.getInstance().checkLoan(strArray[0], strArray[1]);
			output.setOutput(!onLoan ? "The title is on loan." : "The title is free to loan.");
			output.setState(InputHandler.LIBRARIAN);
		}
		
		return output;
	}
	
	public Output findLoan(String input) {
		Output output = new Output("", 0);
		Loan result;
		String[] strArray = null;
		strArray = input.split(",");
		if (strArray.length != 2) {
			output.setOutput("Your input should be in this format:'userID,itemID'");
			output.setState(InputHandler.FIND_LOAN);
		} else {
			boolean isValidUserId = isNumber(strArray[0]);
			boolean isValidItemId = isNumber(strArray[1]);
			if (!isValidUserId) {
				output.setOutput("The user ID must be a number.");
				output.setState(InputHandler.FIND_LOAN);
			} else if (!isValidItemId) {
				output.setOutput("The item ID must be a number.");
				output.setState(InputHandler.FIND_LOAN);
			} else {
				String itemISBN = ItemTable.getInstance().lookupISBN(Integer.valueOf(strArray[1]));
				String copyNumber = ItemTable.getInstance().lookupCopyNumber(Integer.valueOf(strArray[1]));
				result = LoanTable.getInstance().findLoan(Integer.valueOf(strArray[0]), itemISBN, copyNumber);
				if (result == null) {
					output.setOutput("Loan does not exist!");
				} else {
					output.setOutput(result.toString());
				}
				output.setState(InputHandler.LIBRARIAN);
			}
		} 
		return output;
	}

	public static boolean isInteger(String value) {
		char[] ch = value.toCharArray();
		boolean isNumber=true;
		if(value.length()==13){
			for (int i = 0; i < ch.length; i++) {
				isNumber = Character.isDigit(ch[i]);
			}
		}else{
			isNumber=false;
		}
		return isNumber;
	}

	public boolean isNumber(String value) {
		char[] ch = value.toCharArray();
		boolean isNumber=true;
		for (int i = 0; i < ch.length; i++) {
			isNumber = Character.isDigit(ch[i]);
		}
		return isNumber;
	}
}
