package bw.ac.banking.model;
import java.math.*;
public final class SavingsAccount extends Account implements InterestBearing {
 public SavingsAccount(long id,long customerId,BigDecimal balance){super(id,customerId,balance);}
 @Override public void withdraw(BigDecimal amount){throw new IllegalArgumentException("Savings withdrawals are prohibited");}
 @Override public BigDecimal monthlyInterest(){return balance().multiply(new BigDecimal("0.0005")).setScale(2,RoundingMode.HALF_UP);}
 @Override public String type(){return "SAVINGS";}
}
