
package bw.ac.banking.view;

import bw.ac.banking.controller.AccountController;
import bw.ac.banking.model.Account;
import bw.ac.banking.model.Customer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.YearMonth;

public final class AccountView {

 private static final String NAVY = "#10233F";
 private static final String BLUE = "#2563EB";
 private static final String GREEN = "#059669";
 private static final String RED = "#DC2626";
 private static final String BG = "#F1F5F9";

 public Scene create(
         Customer customer,
         AccountController controller,
         Runnable logout) {

  Label title = new Label("OOAD BANK");
  title.setStyle(
          "-fx-text-fill: white;" +
                  "-fx-font-size: 25px;" +
                  "-fx-font-weight: bold;"
  );

  Label subtitle = new Label("Customer Banking Dashboard");
  subtitle.setStyle(
          "-fx-text-fill: #CBD5E1;" +
                  "-fx-font-size: 13px;"
  );

  VBox branding = new VBox(5, title, subtitle);

  Button exit = new Button("Logout");
  exit.setStyle(
          "-fx-background-color: #334155;" +
                  "-fx-text-fill: white;" +
                  "-fx-background-radius: 8;" +
                  "-fx-padding: 10 20;"
  );
  exit.setOnAction(e -> logout.run());

  BorderPane header = new BorderPane();
  header.setLeft(branding);
  header.setRight(exit);
  BorderPane.setAlignment(exit, Pos.CENTER_RIGHT);
  header.setPadding(new Insets(22));
  header.setStyle("-fx-background-color: " + NAVY + ";");

  Label welcome = new Label(
          "Welcome back, " + customer.fullName() + "!"
  );
  welcome.setStyle(
          "-fx-font-size: 23px;" +
                  "-fx-font-weight: bold;" +
                  "-fx-text-fill: " + NAVY + ";"
  );

  Label welcomeSub = new Label(
          "Manage your accounts and transactions securely."
  );
  welcomeSub.setStyle(
          "-fx-text-fill: #64748B;" +
                  "-fx-font-size: 13px;"
  );

  ComboBox<Account> selected = new ComboBox<>();
  selected.setMaxWidth(Double.MAX_VALUE);

  selected.setCellFactory(v -> new ListCell<>() {
   @Override
   protected void updateItem(Account a, boolean empty) {
    super.updateItem(a, empty);
    setText(empty || a == null
            ? ""
            : "#" + a.id() + "  |  " + a.type()
            + "  |  P" + a.balance());
   }
  });

  selected.setButtonCell(new ListCell<>() {
   @Override
   protected void updateItem(Account a, boolean empty) {
    super.updateItem(a, empty);
    setText(empty || a == null
            ? "Select your account"
            : "#" + a.id() + "  |  " + a.type()
            + "  |  P" + a.balance());
   }
  });

  Label balanceCaption = new Label("AVAILABLE BALANCE");
  balanceCaption.setStyle(
          "-fx-text-fill: #BFDBFE;" +
                  "-fx-font-size: 12px;"
  );

  Label balance = new Label("P 0.00");
  balance.setStyle(
          "-fx-text-fill: white;" +
                  "-fx-font-size: 36px;" +
                  "-fx-font-weight: bold;"
  );

  Label accountType = new Label("No account selected");
  accountType.setStyle(
          "-fx-text-fill: #DBEAFE;" +
                  "-fx-font-size: 13px;"
  );

  VBox balanceCard = new VBox(
          10, balanceCaption, balance, accountType
  );
  balanceCard.setPadding(new Insets(25));
  balanceCard.setMaxWidth(Double.MAX_VALUE);
  balanceCard.setStyle(
          "-fx-background-color: " + NAVY + ";" +
                  "-fx-background-radius: 14;"
  );

  Label status = new Label("Ready to bank.");
  status.setWrapText(true);
  status.setStyle(
          "-fx-text-fill: " + GREEN + ";" +
                  "-fx-font-weight: bold;"
  );

  TextField amount = new TextField();
  amount.setPromptText("Enter amount in BWP");
  amount.setPrefHeight(42);

  ComboBox<String> type = new ComboBox<>();
  type.getItems().addAll(
          "SAVINGS", "INVESTMENT", "CHEQUE"
  );
  type.setValue("SAVINGS");
  type.setMaxWidth(Double.MAX_VALUE);
  type.setPrefHeight(42);

  TextArea history = new TextArea();
  history.setEditable(false);
  history.setWrapText(true);
  history.setPrefRowCount(8);
  history.setPromptText(
          "Your transaction history will appear here."
  );

  Runnable updateBalance = () -> {
   Account a = selected.getValue();

   if (a == null) {
    balance.setText("P 0.00");
    accountType.setText("No account selected");
   } else {
    balance.setText("P " + a.balance());
    accountType.setText(
            a.type() + " ACCOUNT  |  #" + a.id()
    );
   }
  };

  selected.setOnAction(e -> updateBalance.run());

  Runnable refresh = () -> {
   try {
    long previous = selected.getValue() == null
            ? -1
            : selected.getValue().id();

    selected.getItems().setAll(
            controller.list(customer.id())
    );

    selected.getItems().stream()
            .filter(a -> a.id() == previous)
            .findFirst()
            .ifPresent(selected::setValue);

    if (selected.getValue() == null
            && !selected.getItems().isEmpty()) {
     selected.setValue(
             selected.getItems().get(0)
     );
    }

    updateBalance.run();

   } catch (Exception ex) {
    showError(status, ex);
   }
  };

  Button open = new Button("Open New Account");
  styleButton(open, BLUE);

  open.setOnAction(e -> {
   try {
    controller.open(
            customer.id(),
            type.getValue(),
            amount.getText()
    );

    showSuccess(
            status, "Account opened successfully!"
    );
    refresh.run();

   } catch (Exception ex) {
    showError(status, ex);
   }
  });

  Button deposit = new Button("Deposit");
  styleButton(deposit, GREEN);

  deposit.setOnAction(e -> {
   try {
    controller.deposit(
            customer.id(),
            require(selected),
            amount.getText()
    );

    showSuccess(status, "Deposit successful!");
    refresh.run();

   } catch (Exception ex) {
    showError(status, ex);
   }
  });

  Button withdraw = new Button("Withdraw");
  styleButton(withdraw, RED);

  withdraw.setOnAction(e -> {
   try {
    controller.withdraw(
            customer.id(),
            require(selected),
            amount.getText()
    );

    showSuccess(status, "Withdrawal successful!");
    refresh.run();

   } catch (Exception ex) {
    showError(status, ex);
   }
  });

  Button showHistory = new Button("History");
  styleButton(showHistory, "#475569");

  showHistory.setOnAction(e -> {
   try {
    StringBuilder b = new StringBuilder();

    for (var x : controller.history(
            customer.id(),
            require(selected))) {

     b.append(x.time())
             .append("  |  ")
             .append(x.kind())
             .append("  |  P")
             .append(x.amount())
             .append("\n");
    }

    history.setText(b.toString());

    showSuccess(
            status, "Transaction history loaded."
    );

   } catch (Exception ex) {
    showError(status, ex);
   }
  });

  Button interest = new Button(
          "Post Monthly Interest (Demo)"
  );
  styleButton(interest, "#7C3AED");

  interest.setOnAction(e -> {
   try {
    int n = controller.payInterest(
            YearMonth.now().toString()
    );

    showSuccess(
            status,
            "Interest posted to " + n
                    + " account(s) for this month."
    );

    refresh.run();

   } catch (Exception ex) {
    showError(status, ex);
   }
  });

  Label accountsHeading = heading("MY ACCOUNTS");

  VBox accountsCard = new VBox(
          14,
          accountsHeading,
          new Label("Select Account"),
          selected,
          balanceCard
  );
  styleCard(accountsCard);

  Label actionsHeading = heading("QUICK TRANSACTIONS");

  HBox actionButtons = new HBox(
          10, deposit, withdraw, showHistory
  );
  actionButtons.setAlignment(Pos.CENTER_LEFT);

  VBox transactionsCard = new VBox(
          14,
          actionsHeading,
          new Label("Transaction Amount (BWP)"),
          amount,
          actionButtons,
          interest
  );
  styleCard(transactionsCard);

  Label openHeading = heading("OPEN A NEW ACCOUNT");

  VBox openCard = new VBox(
          14,
          openHeading,
          new Label("Account Type"),
          type,
          open
  );
  styleCard(openCard);

  VBox historyCard = new VBox(
          12,
          heading("TRANSACTION HISTORY"),
          history
  );
  styleCard(historyCard);

  VBox content = new VBox(
          20,
          welcome,
          welcomeSub,
          accountsCard,
          transactionsCard,
          openCard,
          historyCard,
          status
  );

  content.setPadding(new Insets(25));
  content.setMaxWidth(800);

  VBox page = new VBox(header, content);
  page.setStyle("-fx-background-color: " + BG + ";");

  ScrollPane scroll = new ScrollPane(page);
  scroll.setFitToWidth(true);
  scroll.setStyle("-fx-background: " + BG + ";");

  refresh.run();

  return new Scene(scroll, 850, 750);
 }

 private static Label heading(String text) {
  Label label = new Label(text);
  label.setStyle(
          "-fx-font-size: 15px;" +
                  "-fx-font-weight: bold;" +
                  "-fx-text-fill: " + NAVY + ";"
  );
  return label;
 }

 private static void styleCard(VBox card) {
  card.setPadding(new Insets(22));
  card.setMaxWidth(Double.MAX_VALUE);
  card.setStyle(
          "-fx-background-color: white;" +
                  "-fx-background-radius: 12;" +
                  "-fx-border-color: #E2E8F0;" +
                  "-fx-border-radius: 12;"
  );
 }

 private static void styleButton(
         Button button, String color) {

  button.setPrefHeight(40);
  button.setStyle(
          "-fx-background-color: " + color + ";" +
                  "-fx-text-fill: white;" +
                  "-fx-font-weight: bold;" +
                  "-fx-background-radius: 8;" +
                  "-fx-padding: 10 18;" +
                  "-fx-cursor: hand;"
  );
 }

 private static void showSuccess(
         Label status, String message) {

  status.setText(message);
  status.setStyle(
          "-fx-text-fill: " + GREEN + ";" +
                  "-fx-font-weight: bold;"
  );
 }

 private static void showError(
         Label status, Exception ex) {

  status.setText(ex.getMessage());
  status.setStyle(
          "-fx-text-fill: " + RED + ";" +
                  "-fx-font-weight: bold;"
  );
 }

 private long require(ComboBox<Account> box) {
  if (box.getValue() == null) {
   throw new IllegalArgumentException(
           "Select an account"
   );
  }

  return box.getValue().id();
 }
}
