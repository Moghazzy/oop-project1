package com.example.banksystemproject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUp_Continue_Controller {
    @FXML
    private RadioButton CheckingAccount_Radio;
    @FXML
    private RadioButton SavingAccount_Radio;
    @FXML
    private RadioButton VIP_Radio;
    @FXML
    private RadioButton Normal_Radio;

    @FXML
    private TextField InitializingBalance_TextField;

    @FXML
    private Button Creat_Button;

    @FXML
    private Label Card_Id_label;
    @FXML
    private Label AccountBalance_label;
    @FXML
    private Button Back_Button;
    @FXML
    private Label accno_label;
    @FXML
    private Label newadata;
    @FXML
    private void handleLBack_Button() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Sign Up.fxml"));
        Stage window = (Stage) Back_Button.getScene().getWindow();
        window.setTitle("Sign Up");
        window.setScene(new Scene(root, 600, 400));
    }
    String cardType;
    String accType;
    long accno=Account.generateAccno();
    long accnoo=accno;
public void creatAccount(ActionEvent event){
    if(CheckingAccount_Radio.isSelected()){
        Checkingaccount acc=new Checkingaccount();
        accType="CheckingAccount";
    }else if(SavingAccount_Radio.isSelected()){
        Savingsaccount acc=new Savingsaccount();
        accType="SavingsAccount";
    }
    if(VIP_Radio.isSelected()){
        cardType="VIP";
    }else if(Normal_Radio.isSelected()){
        cardType="Normal";
    }
    DatabaseConnection connectNow=new DatabaseConnection();
    Connection connection=connectNow.getConnection();
    String add ="INSERT INTO accounts(Accountnumber,balance,accounttype,cardtype)"+ "VALUES('"+accnoo+"','"+InitializingBalance_TextField.getText()+"','"+accType+"','"+cardType+"')";


    try{
        Statement statement=connection.createStatement();
        statement.executeUpdate(add);


    }catch (Exception e){
        System.out.println(e);
    }
}
public void display(ActionEvent event){
    DatabaseConnection connectNow=new DatabaseConnection();
    Connection connection=connectNow.getConnection();

    String connectQuery="WITH Base AS (SELECT *, ROW_NUMBER() OVER (ORDER BY Acountnumber) RN FROM accounts)" +
            "SELECT *FROM Base WHERE RN IN ("+accnoo+")";
    try{
        Statement statement=connection.createStatement();
        ResultSet queryOutput=statement.executeQuery(connectQuery);
        while(queryOutput.next()){
            accno_label.setText(queryOutput.getString("Accountnumber"));
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
}
}
