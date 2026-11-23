package bw.ac.banking;
import bw.ac.banking.dao.Database;import bw.ac.banking.service.*;import bw.ac.banking.controller.*;import bw.ac.banking.view.*;import javafx.application.Application;import javafx.stage.Stage;
public final class Main extends Application {
 private Stage stage;private LoginController login;private AccountController accounts;
 @Override public void start(Stage primary)throws Exception{stage=primary;Database db=new Database(System.getProperty("bank.db","banking.db"));db.initialize();login=new LoginController(new AuthService(db));accounts=new AccountController(new AccountService(db));showLogin();stage.setTitle("OOAD Banking System");stage.show();}
 private void showLogin(){stage.setScene(new LoginView().create(login,c->stage.setScene(new AccountView().create(c,accounts,this::showLogin))));}
 public static void main(String[] args){launch(args);}
}
