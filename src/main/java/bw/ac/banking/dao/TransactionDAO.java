package bw.ac.banking.dao;
import java.sql.*;import java.math.*;import java.util.*;
public final class TransactionDAO {
 public record Entry(long id,long accountId,String kind,BigDecimal amount,String time){}
 public void add(Connection c,long accountId,String kind,BigDecimal amount,String period)throws SQLException{try(PreparedStatement p=c.prepareStatement("INSERT INTO transactions(account_id,kind,amount,period) VALUES(?,?,?,?)")){p.setLong(1,accountId);p.setString(2,kind);p.setBigDecimal(3,amount);p.setString(4,period);p.executeUpdate();}}
 public boolean alreadyPosted(Connection c,long accountId,String period)throws SQLException{try(PreparedStatement p=c.prepareStatement("SELECT 1 FROM transactions WHERE account_id=? AND kind='INTEREST' AND period=?")){p.setLong(1,accountId);p.setString(2,period);try(ResultSet r=p.executeQuery()){return r.next();}}}
 public List<Entry> history(Connection c,long accountId)throws SQLException{List<Entry> out=new ArrayList<>();try(PreparedStatement p=c.prepareStatement("SELECT * FROM transactions WHERE account_id=? ORDER BY id DESC")){p.setLong(1,accountId);try(ResultSet r=p.executeQuery()){while(r.next())out.add(new Entry(r.getLong("id"),r.getLong("account_id"),r.getString("kind"),new BigDecimal(r.getString("amount")),r.getString("created_at")));}}return out;}
}
