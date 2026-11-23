package bw.ac.banking.dao;
import java.sql.*;
public final class Database {
 private final String url;
 public Database(String path){url="jdbc:sqlite:"+path;}
 public Connection connect() throws SQLException {Connection c=DriverManager.getConnection(url);try(Statement s=c.createStatement()){s.execute("PRAGMA foreign_keys=ON");s.execute("PRAGMA busy_timeout=5000");}return c;}
 public void initialize() throws SQLException {try(Connection c=connect();Statement s=c.createStatement()){
 s.executeUpdate("CREATE TABLE IF NOT EXISTS customers(id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT NOT NULL, surname TEXT NOT NULL, address TEXT NOT NULL, employed INTEGER NOT NULL, employer TEXT, employer_address TEXT)");
 s.executeUpdate("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, customer_id INTEGER NOT NULL UNIQUE REFERENCES customers(id), username TEXT NOT NULL UNIQUE, salt TEXT NOT NULL, password_hash TEXT NOT NULL)");
 s.executeUpdate("CREATE TABLE IF NOT EXISTS accounts(id INTEGER PRIMARY KEY AUTOINCREMENT, customer_id INTEGER NOT NULL REFERENCES customers(id), type TEXT NOT NULL CHECK(type IN ('SAVINGS','INVESTMENT','CHEQUE')), balance NUMERIC NOT NULL CHECK(balance>=0))");
 s.executeUpdate("CREATE TABLE IF NOT EXISTS transactions(id INTEGER PRIMARY KEY AUTOINCREMENT, account_id INTEGER NOT NULL REFERENCES accounts(id), kind TEXT NOT NULL, amount NUMERIC NOT NULL CHECK(amount>0), created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, period TEXT, UNIQUE(account_id,kind,period))");
 }}
}
