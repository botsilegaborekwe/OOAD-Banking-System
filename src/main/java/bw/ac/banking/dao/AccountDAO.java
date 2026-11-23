package bw.ac.banking.dao;
import bw.ac.banking.model.*;import java.sql.*;import java.math.*;import java.util.*;
public final class AccountDAO {
 public long create(Connection c,Account a)throws SQLException{try(PreparedStatement p=c.prepareStatement("INSERT INTO accounts(customer_id,type,balance) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS)){p.setLong(1,a.customerId());p.setString(2,a.type());p.setBigDecimal(3,a.balance());p.executeUpdate();try(ResultSet r=p.getGeneratedKeys()){r.next();return r.getLong(1);}}}
 public Account get(Connection c,long id)throws SQLException{try(PreparedStatement p=c.prepareStatement("SELECT * FROM accounts WHERE id=?")){p.setLong(1,id);try(ResultSet r=p.executeQuery()){if(!r.next())throw new IllegalArgumentException("Account not found");return map(r);}}}
 public List<Account> forCustomer(Connection c,long id)throws SQLException{List<Account> out=new ArrayList<>();try(PreparedStatement p=c.prepareStatement("SELECT * FROM accounts WHERE customer_id=? ORDER BY id")){p.setLong(1,id);try(ResultSet r=p.executeQuery()){while(r.next())out.add(map(r));}}return out;}
 public List<Account> interestAccounts(Connection c)throws SQLException{List<Account> out=new ArrayList<>();try(Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT * FROM accounts WHERE type IN ('SAVINGS','INVESTMENT')")){while(r.next())out.add(map(r));}return out;}
 public void update(Connection c,Account a)throws SQLException{try(PreparedStatement p=c.prepareStatement("UPDATE accounts SET balance=? WHERE id=?")){p.setBigDecimal(1,a.balance());p.setLong(2,a.id());if(p.executeUpdate()!=1)throw new SQLException("Account update failed");}}
 private Account map(ResultSet r)throws SQLException{return AccountFactory.fromDatabase(r.getLong("id"),r.getLong("customer_id"),r.getString("type"),new BigDecimal(r.getString("balance")));}
}
