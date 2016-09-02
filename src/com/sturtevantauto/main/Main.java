package com.sturtevantauto.main;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.sturtevantauto.gui.LoginWindow;
import com.sturtevantauto.gui.MainGUI;
import com.sturtevantauto.io.CarDefinitions;
import com.sturtevantauto.io.ImageInterface;
import com.sturtevantauto.io.Logger;
import com.sturtevantauto.io.MakeModelInterface;

@SuppressWarnings("unused")
public class Main {
    /**
     * Runs at the start of the program and sets properties as well as
     * initializing the GUI.
     * 
     * @param args
     * @throws IOException
     * @throws AWTException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void main(String[] args) throws IOException, AWTException, ClassNotFoundException, SQLException {
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Image Sorter");
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    LoginWindow window = new LoginWindow();
                    window.frmLoginWindow.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}