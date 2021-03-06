package server.logic.tables;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import server.logic.model.Loan;
import utilities.Config;
import utilities.Trace;

public class LoanTable {
	private Logger logger = Trace.getInstance().getLogger("opreation_file");
	List<Loan> loanList=new ArrayList<Loan>();
    private static class LoanListHolder {
        private static final LoanTable INSTANCE = new LoanTable();
    }
    private LoanTable(){
    	//set up the default list with some instances
    	Loan loan=new Loan(0,"9781442668584","1",new Date(),"0");
    	loanList.add(loan);
    	logger.info(String.format("Operation:Initialize LoanTable;LoanTable: %s", loanList));
    };
    public static final LoanTable getInstance() {
        return LoanListHolder.INSTANCE;
    }
	public Object createloan(int userId, String bookIsbn, String copyNumber, Date date) {
		String result="";
		boolean user=UserTable.getInstance().lookup(userId);
		boolean isbn=TitleTable.getInstance().lookup(bookIsbn);
		boolean copynumber=ItemTable.getInstance().lookup(bookIsbn,copyNumber);
		boolean oloan=LoanTable.getInstance().lookup(userId,bookIsbn,copyNumber);
		boolean limit=LoanTable.getInstance().checkLimit(userId);
		boolean fee=FeeTable.getInstance().lookup(userId);
		if(user==false){
			result="User Invalid";
			logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Invalid User.", userId,bookIsbn,copyNumber,dateformat(date)));
		}else if(isbn==false){
			result="ISBN Invalid";
			logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Invalid ISBN.", userId,bookIsbn,copyNumber,dateformat(date)));
		}else if(copynumber==false){
			result="Copynumber Invalid";
			logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Invalid Copynumber.", userId,bookIsbn,copyNumber,dateformat(date)));
		}else{
			if(oloan){
				if(limit && fee){
				Loan loan=new Loan(userId,bookIsbn,copyNumber,date,"0");
				loanList.add(loan);
				result="success";
				logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Success", userId,bookIsbn,copyNumber,dateformat(date)));
				}else if(limit==false){
					result="The Maximun Number of Items is Reached";
					logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:The Maximun Number of Items is Reached.", userId,bookIsbn,copyNumber,dateformat(date)));
				}else if(fee==false){
					result="Outstanding Fee Exists";
					logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Outstanding Fee Exists.", userId,bookIsbn,copyNumber,dateformat(date)));
				}
			}else{
				result="The Item is Not Available";
				logger.info(String.format("Operation:Borrow Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:The Item is Not Available.", userId,bookIsbn,copyNumber,dateformat(date)));
			}
		}
    	return result;
	}
	
	
	public boolean lookup(String isbn, String copyNumber) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			String ISBN=(loanList.get(i)).getIsbn();
			String copynumber=(loanList.get(i)).getCopynumber();
			if(ISBN.equalsIgnoreCase(isbn) && copynumber.equalsIgnoreCase(copyNumber)){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			result=false;
		}
		return result;
	}
	
	public boolean lookup(int user, String isbn, String copyNumber) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			String ISBN=(loanList.get(i)).getIsbn();
			String copynumber=(loanList.get(i)).getCopynumber();
			int userId=(loanList.get(i)).getUserid();
			if(ISBN.equalsIgnoreCase(isbn) && copynumber.equalsIgnoreCase(copyNumber) && userId == user){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			result=false;
		}
		return result;
	}
	
	public Loan findLoan(int user, String isbn, String copyNumber) {
		Loan loan = null;
		
		for (int i=0;i<loanList.size();i++) {
			String ISBN=(loanList.get(i)).getIsbn();
			String copynumber=(loanList.get(i)).getCopynumber();
			int userId=(loanList.get(i)).getUserid();
			if (ISBN.equalsIgnoreCase(isbn) && copynumber.equalsIgnoreCase(copyNumber) && userId == user) {
				loan = loanList.get(i);
			}
		}
		
		return loan;
	}
    
	public boolean checkLimit(int j) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			int userid=(loanList.get(i)).getUserid();
			if(userid==j){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag>=Config.MAX_BORROWED_ITEMS){
			result=false;
		}
		return result;
	}

	public Object renewal(int j, String string, String string2, Date date) {
		String result="";
		int flag=0;
		int index=0;
		boolean limit=LoanTable.getInstance().checkLimit(j);
		boolean fee=FeeTable.getInstance().lookup(j);
		for(int i=0;i<loanList.size();i++){
			String ISBN=(loanList.get(i)).getIsbn();
			String copynumber=(loanList.get(i)).getCopynumber();
			int userid=(loanList.get(i)).getUserid();
			if((userid==j) && ISBN.equalsIgnoreCase(string) && copynumber.equalsIgnoreCase(string2)){
				flag=flag+1;
				index=i;
			}else{
				flag=flag+0;	
			}
		}
		if(limit && fee){
			if(flag!=0){
				if(loanList.get(index).getRenewstate().equalsIgnoreCase("0")){
					loanList.get(index).setUserid(j);
					loanList.get(index).setIsbn(string);
					loanList.get(index).setCopynumber(string2);
					loanList.get(index).setDate(new Date());
					loanList.get(index).setRenewstate("1");
					result="success";
					logger.info(String.format("Operation:Renew Item;Loan Info:[%d,%s,%s,%s];State:Success", j,string,string2,dateformat(date)));
				}else{
					result="Renewed Item More Than Once for the Same Loan";
					logger.info(String.format("Operation:Renew Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Renewed Item More Than Once for the Same Loan.", j,string,string2,dateformat(date)));
					}
			}else{
				result="The loan does not exist";
				logger.info(String.format("Operation:Renew Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:The loan does not exist.", j,string,string2,dateformat(date)));
			}
			
		}else if(limit==false){
			result="The Maximun Number of Items is Reached";
			logger.info(String.format("Operation:Renew Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:The Maximun Number of Items is Reached.", j,string,string2,dateformat(date)));
		}else if(fee==false){
			result="Outstanding Fee Exists";
			logger.info(String.format("Operation:Renew Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:Outstanding Fee Exists.", j,string,string2,dateformat(date)));
		}
		return result;
	}
    
	private String dateformat(Date date){
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String datestr=format1.format(date);
		return datestr;
	}
	public Object returnItem(int j, String string, String string2, Date date) {
		String result="";
		int flag=0;
		int index=0;
		for(int i=0;i<loanList.size();i++){
			String ISBN=(loanList.get(i)).getIsbn();
			String copynumber=(loanList.get(i)).getCopynumber();
			int userid=(loanList.get(i)).getUserid();
			if((userid==j) && ISBN.equalsIgnoreCase(string) && copynumber.equalsIgnoreCase(string2)){
				flag=flag+1;
				index=i;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			long time = date.getTime()-loanList.get(index).getDate().getTime();
			loanList.remove(index);
			logger.info(String.format("Operation:Return Item;Loan Info:[%d,%s,%s,%s];State:Success", j,string,string2,dateformat(date)));
			if(time>Config.OVERDUE*Config.STIMULATED_DAY){
				FeeTable.getInstance().applyfee(j,time);
			}
			result="success";
		}else{
			result="The Loan Does Not Exist";
			logger.info(String.format("Operation:Return Item;Loan Info:[%d,%s,%s,%s];State:Fail;Reason:The Loan Does Not Exist.", j,string,string2,dateformat(date)));
		}
		
		return result;
	}
	public List<Loan> getLoanTable() {
		return loanList;
	}
	public boolean looklimit(int j) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			int userid=(loanList.get(i)).getUserid();
			if(userid==j){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			result=false;
		}
		return result;
	}
	public boolean checkUser(int j) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			int userid=(loanList.get(i)).getUserid();
			if(userid==j){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			result=false;
		}
		return result;
	}
	public boolean checkLoan(String isbn, String copyNumber) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			String ISBN=(loanList.get(i)).getIsbn();
			String copynumber=(loanList.get(i)).getCopynumber();
			if(ISBN.equalsIgnoreCase(isbn) && copynumber.equalsIgnoreCase(copyNumber)){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			result=false;
		}
		return result;
	}
	public boolean checkLoan(String string) {
		boolean result=true;
		int flag=0;
		for(int i=0;i<loanList.size();i++){
			String ISBN=(loanList.get(i)).getIsbn();
			if(ISBN.equalsIgnoreCase(string)){
				flag=flag+1;
			}else{
				flag=flag+0;	
			}
		}
		if(flag!=0){
			result=false;
		}
		return result;
	}
}
