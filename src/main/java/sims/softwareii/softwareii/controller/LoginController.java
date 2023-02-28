package sims.softwareii.softwareii.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {
    static ResourceBundle rb = ResourceBundle.getBundle("bundle/lang", Locale.getDefault());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void main(String[] args) {
        System.out.println(rb.getString("username"));
        System.out.println(rb.getString("error"));
    }
}
