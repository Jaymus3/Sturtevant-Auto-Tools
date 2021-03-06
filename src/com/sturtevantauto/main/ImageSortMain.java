/**
 * 
 * @author Aevum Kairos
 */
package com.sturtevantauto.main;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.UIManager;
import com.sturtevantauto.gui.ImageSorter;

/**
 *
 * @author Aevum Kairos
 */
public class ImageSortMain {
    public static void main(String[] args) throws IOException, AWTException, ClassNotFoundException, SQLException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Image Sorter");
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    ImageSorter imagesort = new ImageSorter();
                    imagesort.frmImageSorter.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}