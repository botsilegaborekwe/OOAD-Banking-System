package bw.ac.banking.controller;
import bw.ac.banking.model.Customer;import bw.ac.banking.service.AuthService;import java.sql.SQLException;
public final class LoginController {private final AuthService service;public LoginController(AuthService service){this.service=service;}
 public Customer login(String username,String password)throws SQLException{return service.login(username,password.toCharArray());}
 public long register(String username,String password,String first,String last,String address,boolean employed,String employer,String employerAddress)throws SQLException{return service.register(username,password.toCharArray(),first,last,address,employed,employer,employerAddress);}
}
