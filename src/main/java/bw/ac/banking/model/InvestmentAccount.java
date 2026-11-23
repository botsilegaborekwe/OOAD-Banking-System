package bw.ac.banking.model;
import java.math.*;
public final class InvestmentAccount extends Account implements InterestBearing {
 public InvestmentAccount(long id,long customerId,BigDecimal balance){super(id,customerId,balance);}
 @Override public BigDecimal monthlyInterest(){return balance().multiply(new BigDecimal("0.05")).setScale(2,RoundingMode.HALF_UP);}
 @Override public String type(){return "INVESTMENT";}
}
