package controllerview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Person;
import model.TelephonelistM;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author: Oliver Steiner
 * @version: 1, 24.02.2021
 */

public class TelephonelistC implements Initializable {

    @FXML
    private TextArea txtName;
    @FXML
    private TextArea txtAddress;
    @FXML
    private TextArea txtPhoneNum;
    @FXML
    private Label errorTxt;
    @FXML
    private Label counterTxt;

    TelephonelistM telM = new TelephonelistM();
    public static TelephonelistC telC;
    int index;

    //create stage
    public static void show(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TelephonelistC.class.getResource("/controllerview/TelephonelistV.fxml"));
            Parent root = fxmlLoader.load();

            stage.setTitle("Telephone-List");
            stage.setScene(new Scene(root));

            telC = fxmlLoader.getController();

            telC.closerStage(stage);
            stage.show();
        } catch (IOException e){
            System.err.println("Something wrong with TelephonelistV.fxml: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    @FXML
    public void addNewPerson(){
        String name = txtName.getText();
        String address = txtAddress.getText();
        String phoneNum = txtPhoneNum.getText();

        Person p = new Person(name, address, phoneNum);
        try{
           if(txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtPhoneNum.getText().isEmpty()){
               errorTxt.setText("is null");
            }
            else{
               if(!phoneNum.matches("^[0-9]*/[0-9]*$")){
                   errorTxt.setText("Check your input the phone-number must contains '+' '/' and only numbers");
               }
               else {
                   telM.personList.add(p);
                   errorTxt.setText("Person was successfully added!");
               }
            }
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
            errorTxt.setText("Something went wrong!");
        }
    }

    @FXML
    public void removePerson(){
        try{
            telM.removePerson(index);
            txtName.clear();
            txtAddress.clear();
            txtPhoneNum.clear();
            errorTxt.setText("Person was successfully deleted");
        }
        catch (Exception ex){
            errorTxt.setText("There are no indexes to delete!");
        }
    }

    @FXML
    public void next(){
        if(telM.personList.size() >= 1){
            if (index < telM.personList.size() - 1) {
                index++;
            }
            else {
                index = 0;
            }
            print(index);
        }
    }

    @FXML
    public  void previous(){
        if(telM.personList.size() >= 1){
            if(index > 0){
                index --;
            }
            else{
                index = telM.personList.size() -1;
            }
            print(index);
        }
    }

    @FXML
    public void changePerson(){
        try {
            Person p = telM.personList.get(index);

            String nameTxt = txtName.getText();
            String addressTxt = txtAddress.getText();
            String phoneNumTxt = txtPhoneNum.getText();

            String name = p.getName();
            String address = p.getAddress();
            String phoneNum = p.getPhoneNr();
            String changedName = txtName.getText();
            String changedAddress = txtAddress.getText();
            String changedPhoneNum = txtPhoneNum.getText();
            if (!name.equals(changedAddress) && !address.equals(changedAddress) && !phoneNum.equals(changedPhoneNum) && nameTxt.isEmpty() && addressTxt.isEmpty() && phoneNumTxt.isEmpty()) {
                if (!changedPhoneNum.matches("^[0-9]*/[0-9]*$")) {
                    p.setName(changedName);
                    p.setAddress(changedAddress);
                    p.setPhoneNum(changedPhoneNum);
                    errorTxt.setText("Peron was successfully changed!");
                }
            }
            else {
                errorTxt.setText("Check your changes!");
            }
        }catch (Exception ex){
            errorTxt.setText("There are no changes!");
        }
    }

    public void closerStage(Stage stage){
        stage.setOnCloseRequest(
                windowEvent -> {
                    telM.saveToFile();
                    stage.close();
                    System.out.println("The Phonebook was successfully saved!");
                });
    }

   public void print(int index){
        txtName.setText(telM.personList.get(index).getName());
        txtAddress.setText(telM.personList.get(index).getAddress());
        txtPhoneNum.setText(telM.personList.get(index).getPhoneNr());

        counterTxt.setText(index + 1 + "/" + telM.personList.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(telM.personList.isEmpty()){
            Person person = new Person("Oliver Steiner", "LindenStr. 7b", "0650/2915151");
            telM.personList.add(person);
            print(0);
        }
        else {
            telM.loadFromFile();
            print(index);
        }
    }
}
