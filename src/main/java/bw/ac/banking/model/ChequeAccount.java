package bw.ac.banking.model;
import java.math.*;
public final class ChequeAccount extends Account {
 public ChequeAccount(long id,long customerId,BigDecimal balance){super(id,customerId,balance);}
 @Override public String type(){return "CHEQUE";}
}
