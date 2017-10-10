package server.logic.handler;

import server.logic.handler.model.Output;
import server.logic.handler.model.ServerOutput;

public class InputHandler {
	// TODO move all of these to a common STATE class.
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
	// Librarian states
	public static final int LIBRARIAN = 16;
	public static final int LIBRARIANLOGIN = 17;
	// Librarian action states - Note: each corresponds to a like titled UC
	public static final int ADD_ITEM = 18;
	public static final int ADD_USER = 19;
	public static final int ADD_TITLE = 20;
	public static final int BORROW_LOANCOPY = 21;
	public static final int COLLECT_FINE = 22;
	public static final int REMOVE_ITEM = 23;
	public static final int REMOVE_TITLE = 24;
	public static final int REMOVE_USER = 25;
	public static final int RENEW_LOAN = 26;
	public static final int RETURN_LOANCOPY = 27;
	public static final int MONITOR_SYSTEM = 28;

	OutputHandler outputHandler=new OutputHandler();


	public ServerOutput processInput(String input, int state) {
		String screenOutput = "";
		Output functionOutput = new Output("",0);
		ServerOutput serverOutput = new ServerOutput(screenOutput,functionOutput.getState());
		if (state == WAITING) {
			screenOutput = "Who Are you?Clerk or User?";
			state = FINISHWAITING;
			serverOutput.setOutput(screenOutput);
			serverOutput.setState(state);
		}else if (state == FINISHWAITING) {
			if (input.equalsIgnoreCase("clerk")) {
				screenOutput="Please Input The Password:";
				state=CLERKLOGIN;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("user")) {
				screenOutput="Please Input Username and Password:'username,password'";
				state=USERLOGIN;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else if (input.equalsIgnoreCase("librarian")) {
				functionOutput = outputHandler.librarianLogin(input);
				screenOutput="Please enter the Username and Password: 'username,password'";
				state=LIBRARIANLOGIN;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else {
				screenOutput = "Who Are you?Clerk or User?";
				state = FINISHWAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}else if(state==CLERKLOGIN){
			functionOutput=outputHandler.clerkLogin(input);
			screenOutput=functionOutput.getOutput();
			state=functionOutput.getState();
			serverOutput.setOutput(screenOutput);
			serverOutput.setState(state);
		}else if(state==USERLOGIN){
			functionOutput=outputHandler.userLogin(input);
			screenOutput=functionOutput.getOutput();
			state=functionOutput.getState();
			serverOutput.setOutput(screenOutput);
			serverOutput.setState(state);
		} else if (state==LIBRARIANLOGIN) {
			functionOutput=outputHandler.userLogin(input);
			screenOutput=functionOutput.getOutput();
			state=functionOutput.getState();
			serverOutput.setOutput(screenOutput);
			serverOutput.setState(state);
		} else if (state==CLERK){
			if (input.equalsIgnoreCase("create user")) {
				screenOutput = "Please Input User Info:'username,password'";
				state=CREATEUSER;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("create title")) {
				screenOutput = "Please Input Title Info:'ISBN,title'";
				state=CREATETITLE;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("create item")) {
				screenOutput = "Please Input Item Info:'ISBN'";
				state=CREATEITEM;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("delete user")) {
				screenOutput = "Please Input User Info:'useremail'";
				state=DELETEUSER;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("delete title")) {
				screenOutput = "Please Input Title Info:'ISBN'";
				state=DELETETITLE;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("delete item")) {
				screenOutput = "Please Input Item Info:'ISBN,copynumber'";
				state=DELETEITEM;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Create User/Title/Item,Delete User/Title/Item.";
				state = CLERK;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				screenOutput = "Please select from the menu.Menu:Create User/Title/Item,Delete User/Title/Item.";
				state = CLERK;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}else if (state==USER){
			if (input.equalsIgnoreCase("borrow")) {
				screenOutput = "Please Input User Info:'useremail,ISBN,copynumber'";
				state=BORROW;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("renew")) {
				screenOutput = "Please Input Title Info:'useremail,ISBN,copynumber'";
				state=RENEW;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("return")) {
				screenOutput = "Please Input Item Info:'useremail,ISBN,copynumber'";
				state=RETURN;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("pay fine")) {
				screenOutput = "Please Input User Info:'useremail'";
				state=PAYFINE;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Borrow,Renew,Return,Pay Fine.";
				state = USER;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				screenOutput = "Please select from the menu.Menu:Borrow,Renew,Return,Pay Fine.";
				state = USER;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		} else if (state==LIBRARIAN){
			if (input.equalsIgnoreCase("add item")) {
				screenOutput = "Please enter the ISBN of the book to be entered:";
				state=ADD_ITEM;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("add user")) {
				screenOutput = "Please enter the user ID information:'username,password'";
				state=ADD_USER;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("add title")) {
				screenOutput = "Please enter the ISBN of the title:";
				state=ADD_TITLE;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("borrow loancopy")) {
				screenOutput = "Please enter the user ID and item ID:'user ID,item ID'";
				state=BORROW_LOANCOPY;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("collect fine")) {
				screenOutput = "Please enter the user ID and item ID:'user ID,item ID'";
				state=COLLECT_FINE;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if (input.equalsIgnoreCase("remove item")) {
				screenOutput = "Please enter the item ID:";
				state=REMOVE_ITEM;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("remove title")){
				screenOutput = "Please enter the ISBN:";
				state = REMOVE_TITLE;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else if(input.equalsIgnoreCase("remove user")) {
				screenOutput = "Please enter the user ID:";
				state = REMOVE_USER;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else if(input.equalsIgnoreCase("renew loan")) {
				screenOutput = "Please enter the user ID and item ID:'user ID,item ID'";
				state = RENEW_LOAN;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else if(input.equalsIgnoreCase("return loancopy")) {
				screenOutput = "Please enter the user ID and item ID:'user ID,item ID'";
				state = RETURN_LOANCOPY;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else if(input.equalsIgnoreCase("monitor system")) {
				functionOutput=outputHandler.systemStatus();
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What would you like to do? Please select one of the following:'add item', 'add user',"
						+ " 'add title', 'borrow loancopy', 'collect fine', 'remove item', 'remove title', 'remove user', "
						+ "'remove user', 'renew loan', 'return loancopy', 'monitor system'.";
				state = LIBRARIAN;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				screenOutput = "What would you like to do? Please select one of the following:'add item', 'add user',"
						+ " 'add title', 'borrow loancopy', 'collect fine', 'remove item', 'remove title', 'remove user', "
						+ "'remove user', 'renew loan', 'return loancopy', 'monitor system'.";
				state = LIBRARIAN;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}			
		}else if(state==CREATEUSER){
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Create User/Title/Item,Delete User/Title/Item.";
				state = CLERK;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.createUser(input);
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}else if(state==CREATETITLE){
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Create User/Title/Item,Delete User/Title/Item.";
				state = CLERK;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.createTitle(input);
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		} else if (state == ADD_ITEM) {
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What would you like to do? Please select one of the following:'add item', 'add user',"
						+ " 'add title', 'borrow loancopy', 'collect fine', 'remove item', 'remove title', 'remove user', "
						+ "'remove user', 'renew loan', 'return loancopy', 'monitor system'.";
				state = LIBRARIAN;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.findTitle(input);
				if (functionOutput.getState() == OutputHandler.TITLE_EXISTS) {	
					state = LIBRARIAN;
				} else if (functionOutput.getState() == OutputHandler.TITLE_DOESNT_EXIST) {
					input = "get title information";
					state = ADD_TITLE;
				}
				screenOutput=functionOutput.getOutput();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		} else if (state == ADD_USER) {
			if (input.equalsIgnoreCase("log out")) {
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else if (input.equalsIgnoreCase("main menu")) {
				screenOutput = "What would you like to do? Please select one of the following:'add item', 'add user',"
						+ " 'add title', 'borrow loancopy', 'collect fine', 'remove item', 'remove title', 'remove user', "
						+ "'remove user', 'renew loan', 'return loancopy', 'monitor system'.";
				state = LIBRARIAN;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else {
				functionOutput=outputHandler.createUser(input);
				screenOutput=functionOutput.getOutput();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(LIBRARIAN);
			}
		} else if(state==ADD_TITLE){
			if (input.equalsIgnoreCase("log out")) {
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else if (input.equalsIgnoreCase("main menu")) {
				screenOutput = "What would you like to do? Please select one of the following:'add item', 'add user',"
						+ " 'add title', 'borrow loancopy', 'collect fine', 'remove item', 'remove title', 'remove user', "
						+ "'remove user', 'renew loan', 'return loancopy', 'monitor system'.";
				state = LIBRARIAN;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else if (input.equalsIgnoreCase("get title information")) { 
				screenOutput = "Please enter the title information:'ISBN,title',ISBN should be a 13-digit number";
				state = ADD_TITLE;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			} else {
				functionOutput=outputHandler.createTitle(input);
				screenOutput=functionOutput.getOutput();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(LIBRARIAN);
			}
		} else if(state==CREATEITEM){
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Create User/Title/Item,Delete User/Title/Item.";
				state = CLERK;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.createItem(input);
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}else if(state==DELETEUSER){
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Create User/Title/Item,Delete User/Title/Item.";
				state = CLERK;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.deleteUser(input);
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}else if(state==DELETETITLE){
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Create User/Title/Item,Delete User/Title/Item.";
				state = CLERK;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.deleteTitle(input);
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}else if(state==DELETEITEM){
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Create User/Title/Item,Delete User/Title/Item.";
				state = CLERK;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.deleteItem(input);
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}else if(state==BORROW){
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Borrow,Renew,Return,Pay Fine.";
				state = USER;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.borrow(input);
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}else if(state==RENEW){
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Borrow,Renew,Return,Pay Fine.";
				state = USER;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.renew(input);
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}else if(state==RETURN){
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Borrow,Renew,Return,Pay Fine.";
				state = USER;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.returnBook(input);
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}else if(state==PAYFINE){
			if(input.equalsIgnoreCase("log out")){
				screenOutput = "Successfully Log Out!";
				state = WAITING;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else if(input.equalsIgnoreCase("main menu")){
				screenOutput = "What can I do for you?Menu:Borrow,Renew,Return,Pay Fine.";
				state = USER;
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}else{
				functionOutput=outputHandler.payFine(input);
				screenOutput=functionOutput.getOutput();
				state=functionOutput.getState();
				serverOutput.setOutput(screenOutput);
				serverOutput.setState(state);
			}
		}
		return serverOutput;
	}


}
