package bw.ac.banking.model;
import java.math.*;
public abstract class Account {
    private final long id, customerId; private BigDecimal balance;
    protected Account(long id,long customerId,BigDecimal balance){this.id=id;this.customerId=customerId;this.balance=money(balance);if(this.balance.signum()<0)throw new IllegalArgumentException("Negative balance");}
    public static BigDecimal money(BigDecimal amount){if(amount==null)throw new IllegalArgumentException("Amount required");return amount.setScale(2,RoundingMode.HALF_UP);}
    protected static void positive(BigDecimal amount){if(amount==null||amount.signum()<=0)throw new IllegalArgumentException("Amount must be positive");}
    public void deposit(BigDecimal amount){positive(amount);balance=balance.add(money(amount));}
    public void withdraw(BigDecimal amount){positive(amount);amount=money(amount);if(amount.compareTo(balance)>0)throw new IllegalArgumentException("Insufficient funds");balance=balance.subtract(amount);}
    public void creditInterest(BigDecimal amount){positive(amount);balance=balance.add(money(amount));}
    public long id(){return id;}public long customerId(){return customerId;}public BigDecimal balance(){return balance;}
    public abstract String type();
}
