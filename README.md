# CSE202 Part B — OOAD Banking System

Java 21 / JavaFX / Maven / SQLite JDBC implementation based on the corrected Part A banking design.

## Features
- Customer registration and login (PBKDF2 password hashing; salted passwords)
- Savings, investment and cheque accounts; multiple accounts per customer
- Savings: deposits only, 0.05% monthly interest
- Investment: minimum P500 opening deposit, deposits/withdrawals, 5% monthly interest
- Cheque: requires employed customer and employer details; deposits/withdrawals
- Transaction history with timestamps, SQLite persistence and rollback on failure
- Manual **demo** button to post monthly interest once per account per month
- JavaFX views separate from controllers, services, model and DAOs

## Run
Install JDK 21 and Maven. From the project directory:

```bash
mvn test
mvn javafx:run
```

The SQLite database `banking.db` is created in the working directory. To use a different path: `mvn javafx:run -Dbank.db=/path/to/bank.db` (JVM system-property propagation may vary; configure the JavaFX plugin or set it in Main if necessary).

**Codespaces:** JavaFX requires a graphical display. In a headless Codespace, use `mvn test` to verify the business logic and JDBC layer. For the GUI, use a local desktop JDK/Maven environment, or configure a remote desktop/X11 display.

## Demonstration steps
1. Register a customer and log in.
2. Open savings (P100), investment (P500) and cheque (only for an employed customer).
3. Deposit P50, inspect balances and transaction history.
4. Attempt a savings withdrawal and show rejection.
5. Post monthly interest; repeat to show duplicate prevention.
6. Restart the app and confirm balances persist.

## Important scope notes
This is a teaching prototype, **not** production banking software. It does not implement bank-staff/admin roles, transfers, audit logs, account closing, account locks, a real scheduler, or production-grade concurrency. The interest button is a demonstrator, not automatic monthly scheduling. The schema includes customers, users, accounts and transactions. Monetary values use BigDecimal in Java; SQLite NUMERIC storage does not guarantee exact decimal storage for every value and should be replaced with integer minor units for production. The design assumptions (e.g., no overdraft, no cheque interest) should be confirmed with the lecturer.

## GitHub commands
```bash
git init
git add .
git commit -m "Implement Part B JavaFX banking system"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/YOUR-REPO.git
git push -u origin main
```

Create the repository on GitHub first. Do not commit database files, passwords or secrets. For the final presentation, the assignment asks for at least **10 sample records**; use the GUI to create demo records and show the database rows. Do not use real customer information.
