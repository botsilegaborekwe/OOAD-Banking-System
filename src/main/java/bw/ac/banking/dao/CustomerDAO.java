package bw.ac.banking.dao;
import bw.ac.banking.model.Customer;import java.sql.*;
public final class CustomerDAO {
 public long create(Connection c,Customer x)throws SQLException {try(PreparedStatement p=c.prepareStatement("INSERT INTO customers(first_name,surname,address,employed,employer,employer_address) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){p.setString(1,x.firstName());p.setString(2,x.surname());p.setString(3,x.address());p.setBoolean(4,x.employed());p.setString(5,x.employer());p.setString(6,x.employerAddress());p.executeUpdate();try(ResultSet r=p.getGeneratedKeys()){r.next();return r.getLong(1);}}}
 public Customer get(Connection c,long id)throws SQLException {try(PreparedStatement p=c.prepareStatement("SELECT * FROM customers WHERE id=?")){p.setLong(1,id);try(ResultSet r=p.executeQuery()){if(!r.next())throw new IllegalArgumentException("Customer not found");return new Customer(r.getLong("id"),r.getString("first_name"),r.getString("surname"),r.getString("address"),r.getBoolean("employed"),r.getString("employer"),r.getString("employer_address"));}}}
}
