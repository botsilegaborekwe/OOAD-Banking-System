
package bw.ac.banking.view;

import bw.ac.banking.controller.LoginController;
import bw.ac.banking.model.Customer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.function.Consumer;

public final class LoginView {

 private static final String NAVY = "#10233F";
 private static final String BLUE = "#2563EB";
 private static final String GREEN = "#059669";
 private static final String RED = "#DC2626";
 private static final String BG = "#F1F5F9";

 public Scene create(
         LoginController controller,
         Consumer<Customer> onLogin) {

  // ===== BANK HEADER =====

  Label bankName = new Label("OOAD BANK");
  bankName.setStyle(
          "-fx-font-size: 30px;" +
                  "-fx-font-weight: bold;" +
                  "-fx-text-fill: white;"
  );

  Label slogan = new Label(
          "Secure Banking. Simple Banking."
  );
  slogan.setStyle(
          "-fx-font-size: 14px;" +
                  "-fx-text-fill: #CBD5E1;"
  );

  Label welcome = new Label(
          "Welcome to Your Digital Banking System"
  );
  welcome.setWrapText(true);
  welcome.setStyle(
          "-fx-font-size: 16px;" +
                  "-fx-text-fill: white;"
  );

  VBox header = new VBox(
          10, bankName, slogan, welcome
  );
  header.setAlignment(Pos.CENTER);
  header.setPadding(new Insets(35, 20, 35, 20));
  header.setStyle(
          "-fx-background-color: " + NAVY + ";"
  );

  // ===== LOGIN SECTION =====

  Label loginTitle = new Label("LOGIN TO YOUR ACCOUNT");
  styleHeading(loginTitle);

  Label loginDescription = new Label(
          "Enter your credentials to access your accounts."
  );
  loginDescription.setWrapText(true);
  loginDescription.setStyle(
          "-fx-text-fill: #64748B;" +
                  "-fx-font-size: 13px;"
  );

  TextField user = new TextField();
  user.setPromptText("Enter your username");
  styleField(user);

  PasswordField pass = new PasswordField();
  pass.setPromptText("Enter your password");
  styleField(pass);

  Label status = new Label();
  status.setWrapText(true);
  status.setMaxWidth(Double.MAX_VALUE);

  Button login = new Button("LOGIN");
  styleButton(login, BLUE);
  login.setMaxWidth(Double.MAX_VALUE);

  login.setOnAction(e -> {
   try {
    Customer customer = controller.login(
            user.getText(),
            pass.getText()
    );

    showSuccess(status, "Login successful!");

    onLogin.accept(customer);

   } catch (Exception ex) {
    showError(status, ex);
   }
  });

  VBox loginCard = new VBox(
          14,
          loginTitle,
          loginDescription,
          new Label("Username"),
          user,
          new Label("Password"),
          pass,
          login
  );
  styleCard(loginCard);

  // ===== REGISTRATION SECTION =====

  Label registerTitle = new Label(
          "CREATE A NEW ACCOUNT"
  );
  styleHeading(registerTitle);

  Label registerDescription = new Label(
          "New customer? Register below to start banking."
  );
  registerDescription.setWrapText(true);
  registerDescription.setStyle(
          "-fx-text-fill: #64748B;" +
                  "-fx-font-size: 13px;"
  );

  TextField first = new TextField();
  first.setPromptText("First name");
  styleField(first);

  TextField last = new TextField();
  last.setPromptText("Surname");
  styleField(last);

  TextField address = new TextField();
  address.setPromptText("Residential address");
  styleField(address);

  CheckBox employed = new CheckBox(
          "I am currently employed"
  );
  employed.setStyle(
          "-fx-font-size: 13px;" +
                  "-fx-text-fill: " + NAVY + ";"
  );

  TextField employer = new TextField();
  employer.setPromptText("Employer name");
  styleField(employer);

  TextField employerAddress = new TextField();
  employerAddress.setPromptText("Employer address");
  styleField(employerAddress);

  // Employer details are needed for employed customers.
  employer.setDisable(true);
  employerAddress.setDisable(true);

  employed.selectedProperty().addListener(
          (obs, oldValue, isEmployed) -> {
           employer.setDisable(!isEmployed);
           employerAddress.setDisable(!isEmployed);

           if (!isEmployed) {
            employer.clear();
            employerAddress.clear();
           }
          }
  );

  Button register = new Button(
          "REGISTER CUSTOMER"
  );
  styleButton(register, GREEN);
  register.setMaxWidth(Double.MAX_VALUE);

  register.setOnAction(e -> {
   try {
    controller.register(
            user.getText(),
            pass.getText(),
            first.getText(),
            last.getText(),
            address.getText(),
            employed.isSelected(),
            employer.getText(),
            employerAddress.getText()
    );

    showSuccess(
            status,
            "Registration successful! You can now log in."
    );

   } catch (Exception ex) {
    showError(status, ex);
   }
  });

  VBox registerCard = new VBox(
          14,
          registerTitle,
          registerDescription,
          new Label("First Name"),
          first,
          new Label("Surname"),
          last,
          new Label("Residential Address"),
          address,
          employed,
          new Label("Employer Name (if employed)"),
          employer,
          new Label("Employer Address (if employed)"),
          employerAddress,
          register
  );
  styleCard(registerCard);

  // ===== FOOTER =====

  Label footer = new Label(
          "OOAD Banking System | JavaFX Banking Application"
  );
  footer.setWrapText(true);
  footer.setStyle(
          "-fx-text-fill: #64748B;" +
                  "-fx-font-size: 12px;"
  );

  VBox content = new VBox(
          20,
          loginCard,
          registerCard,
          status,
          footer
  );

  content.setPadding(new Insets(25));
  content.setMaxWidth(650);
  content.setAlignment(Pos.CENTER);

  VBox page = new VBox(header, content);
  page.setAlignment(Pos.TOP_CENTER);
  page.setStyle(
          "-fx-background-color: " + BG + ";"
  );

  ScrollPane scroll = new ScrollPane(page);
  scroll.setFitToWidth(true);
  scroll.setStyle(
          "-fx-background: " + BG + ";"
  );

  return new Scene(scroll, 700, 750);
 }

 // ===== DESIGN HELPERS =====

 private static void styleHeading(Label label) {
  label.setStyle(
          "-fx-font-size: 19px;" +
                  "-fx-font-weight: bold;" +
                  "-fx-text-fill: " + NAVY + ";"
  );
 }

 private static void styleCard(VBox card) {
  card.setPadding(new Insets(25));
  card.setMaxWidth(Double.MAX_VALUE);
  card.setStyle(
          "-fx-background-color: white;" +
                  "-fx-background-radius: 12;" +
                  "-fx-border-color: #E2E8F0;" +
                  "-fx-border-radius: 12;"
  );
 }

 private static void styleField(TextField field) {
  field.setPrefHeight(43);
  field.setMaxWidth(Double.MAX_VALUE);
  field.setStyle(
          "-fx-background-color: #F8FAFC;" +
                  "-fx-border-color: #CBD5E1;" +
                  "-fx-border-radius: 7;" +
                  "-fx-background-radius: 7;" +
                  "-fx-padding: 10;" +
                  "-fx-font-size: 13px;"
  );
 }

 private static void styleButton(
         Button button,
         String color) {

  button.setPrefHeight(44);
  button.setStyle(
          "-fx-background-color: " + color + ";" +
                  "-fx-text-fill: white;" +
                  "-fx-font-weight: bold;" +
                  "-fx-background-radius: 8;" +
                  "-fx-padding: 10 20;" +
                  "-fx-cursor: hand;"
  );
 }

 private static void showSuccess(
         Label status,
         String message) {

  status.setText(message);
  status.setStyle(
          "-fx-text-fill: " + GREEN + ";" +
                  "-fx-font-weight: bold;"
  );
 }

 private static void showError(
         Label status,
         Exception ex) {

  status.setText(
          ex.getMessage() == null
                  ? "An unexpected error occurred."
                  : ex.getMessage()
  );

  status.setStyle(
          "-fx-text-fill: " + RED + ";" +
                  "-fx-font-weight: bold;"
  );
 }
}

