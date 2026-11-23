package bw.ac.banking;
import bw.ac.banking.dao.*;import bw.ac.banking.model.*;import bw.ac.banking.service.*;import org.junit.jupiter.api.*;import java.math.*;import java.nio.file.*;import java.time.YearMonth;import static org.junit.jupiter.api.Assertions.*;
public class BankingTest {
 Database db;AuthService auth;AccountService service;long customer;
 @BeforeEach void setup()throws Exception{Path f=Files.createTempFile("banktest",".db");f.toFile().deleteOnExit();db=new Database(f.toString());db.initialize();auth=new AuthService(db);service=new AccountService(db);customer=auth.register("tester","password123".toCharArray(),"Test","Customer","Gaborone",false,"","");}
 @Test void loginAndDepositHistory()throws Exception{assertEquals(customer,auth.login("tester","password123".toCharArray()).id());long a=service.open(customer,"SAVINGS",new BigDecimal("100"));service.deposit(customer,a,new BigDecimal("25"));assertEquals(new BigDecimal("125.00"),service.accounts(customer).get(0).balance());assertEquals(2,service.history(customer,a).size());}
 @Test void savingsCannotWithdraw()throws Exception{long a=service.open(customer,"SAVINGS",new BigDecimal("100"));assertThrows(IllegalArgumentException.class,()->service.withdraw(customer,a,new BigDecimal("10")));}
 @Test void investmentMinimum() {assertThrows(IllegalArgumentException.class,()->service.open(customer,"INVESTMENT",new BigDecimal("499.99")));}
 @Test void chequeRequiresEmployment(){assertThrows(IllegalArgumentException.class,()->service.open(customer,"CHEQUE",new BigDecimal("50")));}
 @Test void interestOnlyOncePerMonth()throws Exception{long a=service.open(customer,"INVESTMENT",new BigDecimal("500"));assertEquals(1,service.payMonthlyInterest(YearMonth.of(2026,11)));assertEquals(0,service.payMonthlyInterest(YearMonth.of(2026,11)));assertEquals(new BigDecimal("525.00"),service.accounts(customer).get(0).balance());}
 @Test void cannotUseOtherCustomersAccount()throws Exception{long a=service.open(customer,"SAVINGS",new BigDecimal("100"));assertThrows(SecurityException.class,()->service.deposit(customer+1,a,new BigDecimal("10")));}
}
