package server.logic.tables;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import server.logic.model.User;
import utilities.Trace;

public class LibrarianTable {
	private Logger logger = Trace.getInstance().getLogger("opreation_file"); 
	List<User> librarianList = new ArrayList<User>(); 
	private static class LibrarianListHolder {
		private static final LibrarianTable INSTANCE = new LibrarianTable();
	}
	private LibrarianTable() {
		//set up the default list with some instances
		String[] passwordList=new String[]{"Librarian","Password"};
		String[] usernameList=new String[]{"Librarian@carleton.ca", "Alexei.Tchekansky@carleton.ca"};
		for(int i=0;i<usernameList.length;i++){
			User deuser=new User(i,usernameList[i],passwordList[i]);
			librarianList.add(deuser);
		}
		logger.info(String.format("Operation:Initialize LibrarianTable; LibrarianTable: %s", librarianList));
	};
	public static final LibrarianTable getInstance() {
		return LibrarianListHolder.INSTANCE;
	}
	public Object createLibrarian(String userName, String userPassword) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<librarianList.size();i++){
			String email=(librarianList.get(i)).getUsername();
			if(email.equalsIgnoreCase(userName)){
				flag=flag+1;
			}else{
				flag=flag+0;
			}
		}
		if(flag==0){
			User newLibrarian=new User(librarianList.size(),userName,userPassword);
			result=librarianList.add(newLibrarian);
			logger.info(String.format("Operation:Create New Librarian; Librarian Info:[%s,%s];State:Success", userName,userPassword));
		}else{
			result=false;
			logger.info(String.format("Operation:Create New Librarian;Librarian Info:[%s,%s];State:Fail;Reason:The librarian already exists.", userName,userPassword));
		}
		return result;
	}
	public boolean lookup(int j) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<librarianList.size();i++){
			int userid=(librarianList.get(i)).getUserid();
			if(userid==j){
				flag=flag+1;
			}else{
				flag=flag+0;
			}
		}
		if(flag==0){
			result=false;
		}
		return result;
	}
	public List<User> getLLibrarianTable() {
		return librarianList;
	}
	public Object delete(int i) {
		//Since the userid in "User Creation" is automatically assigned to the user,upon its creation.
		//Each user has a unique userid.Even it is deleted,its userid can not be assigned to other user.
		//To maintain the correctness of the data,here instead delete index from the List.
		//I choose to remove the user's information instead the whole index.Keep its userid as reference.
		String result="";
		int flag=0;
		int index=0;
		for(int j=0;j<librarianList.size();j++){
			if(librarianList.get(j).getUserid()==i){
				index=j;
				flag=flag+1;
			}else{
				flag=flag+0;
			}
		}

		if(flag==0){
			result="The Librarian Does Not Exist";
			logger.info(String.format("Operation:Delete Librarian;Librarian Info:[%s,%s];State:Fail;Reason:The Librarian Does Not Exist.", "N/A","N/A"));
		}else{
			String userName=librarianList.get(index).getUsername();
			String password=librarianList.get(index).getPassword();
	
				librarianList.get(index).setUserid(i);
				librarianList.get(index).setPassword("N/A");
				librarianList.get(index).setUsername("N/A");
				result="success";
				logger.info(String.format("Operation:Delete Librarian;Librarian Info:[%s,%s];State:Success", userName,password));
		}

		return result;

	}
	public int lookup(String string) {
		int userid=-1;
		for(int i=0;i<librarianList.size();i++){
			if(librarianList.get(i).getUsername().equalsIgnoreCase(string)){
				userid=i;
			}
		}
		return userid;
	}
	public int checkUser(String userName, String userPassword) {
		int result=0;
		int flag=0;
		int index=0;
		for(int i=0;i<librarianList.size();i++){
			if(librarianList.get(i).getUsername().equalsIgnoreCase(userName)){
				flag=flag+1;
				index=i;
			}else{
				flag=flag+0;
			}
		}
		boolean password=librarianList.get(index).getPassword().equalsIgnoreCase(userPassword);
		if(flag!=0 && password){
			result=0;
		}else if(flag==0){
			result=2;
		}else if(password==false){
			result=1;
		}
		return result;
	}
}
