package bw.ac.banking.controller;
import bw.ac.banking.service.AccountService;import bw.ac.banking.model.Account;import bw.ac.banking.dao.TransactionDAO;import java.math.*;import java.sql.*;import java.util.*;import java.time.YearMonth;
public final class AccountController {private final AccountService service;public AccountController(AccountService service){this.service=service;}
 public List<Account> list(long customerId)throws SQLException{return service.accounts(customerId);}
 public long open(long customerId,String type,String amount)throws SQLException{return service.open(customerId,type,parse(amount));}
 public void deposit(long customerId,long accountId,String amount)throws SQLException{service.deposit(customerId,accountId,parse(amount));}
 public void withdraw(long customerId,long accountId,String amount)throws SQLException{service.withdraw(customerId,accountId,parse(amount));}
 public List<TransactionDAO.Entry> history(long customerId,long accountId)throws SQLException{return service.history(customerId,accountId);}
 public int payInterest(String period)throws SQLException{return service.payMonthlyInterest(YearMonth.parse(period));}
 private static BigDecimal parse(String amount){try{return new BigDecimal(amount);}catch(Exception e){throw new IllegalArgumentException("Enter a valid amount");}}
}
