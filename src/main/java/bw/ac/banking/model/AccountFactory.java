package bw.ac.banking.model;
import java.math.BigDecimal;
public final class AccountFactory {
 private AccountFactory(){}
 public static Account fromDatabase(long id,long customerId,String type,BigDecimal balance){return switch(type){case "SAVINGS"->new SavingsAccount(id,customerId,balance);case "INVESTMENT"->new InvestmentAccount(id,customerId,balance);case "CHEQUE"->new ChequeAccount(id,customerId,balance);default->throw new IllegalArgumentException("Unknown account type");};}
 public static Account forOpening(long customerId,String type,BigDecimal initial,Customer customer){Account.positive(initial);if("INVESTMENT".equals(type)&&initial.compareTo(new BigDecimal("500"))<0)throw new IllegalArgumentException("Investment opening requires at least P500");if("CHEQUE".equals(type)&&(!customer.employed()||customer.employer()==null||customer.employer().isBlank()||customer.employerAddress()==null||customer.employerAddress().isBlank()))throw new IllegalArgumentException("Cheque requires employment and employer details");return fromDatabase(0,customerId,type,initial);}
}
