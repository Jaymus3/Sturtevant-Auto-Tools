package com.sturtevantauto.io;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sturtevantauto.gui.ImageSorter;

public class AccountHandler {
    public static void login(JTextField userfield, JPasswordField passwordfield, JLabel error, JFrame frame) {
        try {
            String user = null;
            String pass = null;
            Car car = new Car();
            Connection connection = car.getConnection();
            Statement statement = connection.createStatement();
            ResultSet use = statement.executeQuery("USE car_parts");
            String username = userfield.getText();
            char[] password = passwordfield.getPassword();
            ResultSet rs = statement.executeQuery("SELECT * FROM Account_Index where Username='" + username + "' and Password='" + new String(password) + "'");
            while (rs.next()) {
                user = rs.getString("Username");
                pass = rs.getString("Password");
            }
            if (username.equals(user) && new String(password).equals(pass)) {
                frame.dispose();
                ImageSorter window = new ImageSorter();
                window.frmImageSorter.setVisible(true);
            } else {
                error.setVisible(true);
            }
            use.close();
            rs.close();
            statement.close();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
