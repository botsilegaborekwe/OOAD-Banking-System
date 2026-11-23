package bw.ac.banking.service;
import bw.ac.banking.dao.*;import bw.ac.banking.model.*;import java.sql.*;import java.math.*;import java.time.YearMonth;import java.util.*;
public final class AccountService {
 private final Database db;private final AccountDAO accounts=new AccountDAO();private final CustomerDAO customers=new CustomerDAO();private final TransactionDAO transactions=new TransactionDAO();
 public AccountService(Database db){this.db=db;}
 public long open(long customerId,String type,BigDecimal initial)throws SQLException{try(Connection c=db.connect()){c.setAutoCommit(false);try{Customer owner=customers.get(c,customerId);Account a=AccountFactory.forOpening(customerId,type,initial,owner);long id=accounts.create(c,a);transactions.add(c,id,"OPENING",a.balance(),null);c.commit();return id;}catch(Exception e){c.rollback();throw e;}}}
 public List<Account> accounts(long customerId)throws SQLException{try(Connection c=db.connect()){return accounts.forCustomer(c,customerId);}}
 public List<TransactionDAO.Entry> history(long customerId,long accountId)throws SQLException{try(Connection c=db.connect()){Account a=accounts.get(c,accountId);requireOwner(a,customerId);return transactions.history(c,accountId);}}
 private void requireOwner(Account a,long id){if(a.customerId()!=id)throw new SecurityException("Account does not belong to this customer");}
 public void deposit(long customerId,long accountId,BigDecimal amount)throws SQLException{change(customerId,accountId,amount,false);}
 public void withdraw(long customerId,long accountId,BigDecimal amount)throws SQLException{change(customerId,accountId,amount,true);}
 private void change(long customerId,long accountId,BigDecimal amount,boolean withdrawal)throws SQLException{try(Connection c=db.connect()){c.setAutoCommit(false);try{Account a=accounts.get(c,accountId);requireOwner(a,customerId);if(withdrawal)a.withdraw(amount);else a.deposit(amount);accounts.update(c,a);transactions.add(c,accountId,withdrawal?"WITHDRAWAL":"DEPOSIT",Account.money(amount),null);c.commit();}catch(Exception e){c.rollback();throw e;}}}
 public int payMonthlyInterest(YearMonth period)throws SQLException{int count=0;try(Connection c=db.connect()){c.setAutoCommit(false);try{for(Account a:accounts.interestAccounts(c)){if(transactions.alreadyPosted(c,a.id(),period.toString()))continue;BigDecimal interest=((InterestBearing)a).monthlyInterest();if(interest.signum()==0)continue;a.creditInterest(interest);accounts.update(c,a);transactions.add(c,a.id(),"INTEREST",interest,period.toString());count++;}c.commit();return count;}catch(Exception e){c.rollback();throw e;}}}
}
