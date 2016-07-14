package org.sturtevantauto.gui;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;

import org.sturtevantauto.io.CarDefinitions;
import org.sturtevantauto.io.ImageInterface;
import org.sturtevantauto.io.Logger;
import org.sturtevantauto.io.MakeModelInterface;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JMenuBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenu;
import javax.swing.JDesktopPane;
import javax.swing.JScrollBar;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import java.awt.Canvas;
import javax.swing.JPasswordField;

public class MainGUI {

	public JFrame frmImageSorter;
	private JTextField modelField;
	private JTextField makeField;
	private JTextField stockField;
	int increment = 100;

	/**
	 * Create the application.
	 */
	public MainGUI() {
		CarDefinitions.setMake(null);
        ImageInterface.findFile(CarDefinitions.getPictureLocation());
    if(CarDefinitions.getStock() == null)
    {
    	System.err.println("No cars found to sort!");
    }
    else
    	CarDefinitions.TrimStock();
    
    
    if(ImageInterface.countPictures(CarDefinitions.getPictureLocation()) != 0)
    	increment = 100 / ImageInterface.countPictures(CarDefinitions.getPictureLocation());
    
    
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmImageSorter = new JFrame();
		frmImageSorter.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		frmImageSorter.setResizable(false);
		frmImageSorter.setTitle("Image Sorter");
		frmImageSorter.setBounds(100, 100, 1000, 600);
		frmImageSorter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmImageSorter.getContentPane().setLayout(null);
		final JButton btnSort = new JButton("Sort");
		btnSort.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		btnSort.setEnabled(false);
		final JTextPane outputWindow = new JTextPane();
		outputWindow.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		outputWindow.setToolTipText("Displays output such as error or success messages");
		outputWindow.setEditable(false);
		outputWindow.setBounds(15, 141, 295, 172);
		frmImageSorter.getContentPane().add(outputWindow);
		if(CarDefinitions.getStock() == null)
			outputWindow.setText("No cars were found in the sorting directory.  Perhaps you didn't add the stock number to the end of any of the files, or forgot to import the pictures?");
		
		makeField = new JTextField();
		makeField.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		makeField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(modelField.getText() != null)
				{
					try {
						CarDefinitions.setMake(makeField.getText());
						MakeModelInterface.WriteMakeModelIndex(CarDefinitions.getModel(), CarDefinitions.getMake());
						outputWindow.requestFocus();
						outputWindow.setText("Make/Model association set!  Click the sort button to sort this car.");
						btnSort.setEnabled(true);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else
				outputWindow.setText("Please enter the model first, and I'll look up the make for you.");
			}
		});
		final JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(6, 536, 859, 27);
		progressBar.setVisible(false);
		frmImageSorter.getContentPane().add(progressBar);
		
		
		
		JLabel lblStock = new JLabel("Stock #:");
		lblStock.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblStock.setBounds(1, 11, 51, 16);
		frmImageSorter.getContentPane().add(lblStock);
		
		stockField = new JTextField();
		stockField.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		stockField.setToolTipText("Stock # of the current scanned vehicle");
		stockField.setEditable(false);
		stockField.setBounds(55, 5, 255, 28);
		frmImageSorter.getContentPane().add(stockField);
		stockField.setColumns(10);
		
		modelField = new JTextField();
		modelField.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		modelField.setToolTipText("Enter the model of the vehicle here");
		modelField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CarDefinitions.setModel(modelField.getText());
				MakeModelInterface.foundmake = false;
		        try {
					MakeModelInterface.CheckMakeModelIndex(CarDefinitions.getModel());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		        if(!MakeModelInterface.foundmake)
		        {
		        	outputWindow.requestFocus();
		        	outputWindow.setText("Please enter the make as well.  I can't retrieve it.");
		        	makeField.setText("");
		        	makeField.setEditable(true);
		        }
		        else
		        {
		        outputWindow.requestFocus();
		        outputWindow.setText("Make found!  Click sort to sort this car!");
		        btnSort.setEnabled(true);
		        makeField.setText(CarDefinitions.getMake());
		        makeField.setEditable(false);
		        }
			}
		});
		
		JLabel lblmodel = new JLabel("\u0010Model:");
		lblmodel.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblmodel.setBounds(6, 45, 46, 16);
		frmImageSorter.getContentPane().add(lblmodel);
		
		modelField.setBounds(55, 39, 255, 28);
		frmImageSorter.getContentPane().add(modelField);
		modelField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Make:");
		lblNewLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblNewLabel.setBounds(15, 79, 37, 16);
		frmImageSorter.getContentPane().add(lblNewLabel);
		makeField.setToolTipText("Sometimes the make won't be found, and you'll have to enter it here");
		makeField.setBounds(55, 73, 255, 28);
		makeField.setEditable(false);
		frmImageSorter.getContentPane().add(makeField);
		makeField.setColumns(10);
		if(CarDefinitions.getStock() == null)
		modelField.setEditable(false);
		
		
		
		JLabel lblOutputWindow = new JLabel("Output window:");
		lblOutputWindow.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblOutputWindow.setBounds(15, 113, 117, 16);
		frmImageSorter.getContentPane().add(lblOutputWindow);
		
		
		final JLabel carPicture2 = new JLabel();
		carPicture2.setToolTipText("Double click on this to open the displayed image");
		carPicture2.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		carPicture2.addMouseListener(new MouseAdapter() 
		{
			boolean OneClick;

			@Override
			public void mouseClicked(MouseEvent mouseEvent) 
		{
			if (OneClick) 
			{
				if(CarDefinitions.getImageNames()[0] != null)
					try {
						Desktop.getDesktop().open(new File(CarDefinitions.getImageNames()[0]));
					} catch (IOException e) {
						e.printStackTrace();
					}
				  OneClick = false;
			}
			else 
			{
				  OneClick = true;
				  Timer t = new Timer("clickTimer", false);
				  t.schedule(new TimerTask() 
				  	{
					  @Override
					  public void run()
					  {
						  OneClick = false;
					  }
				  	},500);
			}
		}
		});
		carPicture2.setBounds(664, 6, 330, 253);
		if(CarDefinitions.getImageNames()[0] != null)
		{
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(CarDefinitions.getImageNames()[0]));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(carPicture2.getWidth(), carPicture2.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		carPicture2.setIcon(imageIcon);
		}
		else
		{
			carPicture2.setText("                      No image found!");
		}
		final JLabel carPicture1 = new JLabel();
		carPicture1.setToolTipText("Double click on this to open the displayed image");
		carPicture1.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		carPicture1.addMouseListener(new MouseAdapter() 
	{
		boolean OneClick;

		@Override
		public void mouseClicked(MouseEvent mouseEvent) 
	{
		if (OneClick) 
		{
			  if(CarDefinitions.getImageNames()[1] != null)
				try {
					Desktop.getDesktop().open(new File(CarDefinitions.getImageNames()[1]));
				} catch (IOException e) {
					e.printStackTrace();
				}
			  OneClick = false;
		}
		else 
		{
			  OneClick = true;
			  Timer t = new Timer("clickTimer", false);
			  t.schedule(new TimerTask() 
			  	{
				  @Override
				  public void run()
				  {
					  OneClick = false;
				  }
			  	},500);
		}
	}
	});
		carPicture1.setBounds(322, 6, 330, 253);
		frmImageSorter.getContentPane().add(carPicture1);
		frmImageSorter.getContentPane().add(carPicture2);
		
		if(CarDefinitions.getImageNames()[1] != null)
		{
		BufferedImage img1 = null;
		try {
		    img1 = ImageIO.read(new File(CarDefinitions.getImageNames()[1]));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg1 = img1.getScaledInstance(carPicture1.getWidth(), carPicture1.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon1 = new ImageIcon(dimg1);
		carPicture1.setIcon(imageIcon1);
		}
		else
		{
			carPicture1.setText("                      No image found!");
		}
		final JLabel carPicture3 = new JLabel();
		carPicture3.setToolTipText("Double click on this to open the displayed image");
		carPicture3.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		carPicture3.addMouseListener(new MouseAdapter() 
		{
			boolean OneClick;

			@Override
			public void mouseClicked(MouseEvent mouseEvent) 
		{
			if (OneClick) 
			{
				if(CarDefinitions.getImageNames()[2] != null)
					try {
						Desktop.getDesktop().open(new File(CarDefinitions.getImageNames()[2]));
					} catch (IOException e) {
						e.printStackTrace();
					}
				  OneClick = false;
			}
			else 
			{
				  OneClick = true;
				  Timer t = new Timer("clickTimer", false);
				  t.schedule(new TimerTask() 
				  	{
					  @Override
					  public void run()
					  {
						  OneClick = false;
					  }
				  	},500);
			}
		}
		});
		carPicture3.setBounds(322, 271, 330, 253);
		if(CarDefinitions.getImageNames()[2] != null)
		{
		BufferedImage img2 = null;
		try {
		    img2 = ImageIO.read(new File(CarDefinitions.getImageNames()[2]));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg2 = img2.getScaledInstance(carPicture3.getWidth(), carPicture3.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon2 = new ImageIcon(dimg2);
		//TODO: 2007 Vue
		carPicture3.setIcon(imageIcon2);
		}
		else
		{
			carPicture3.setText("                      No image found!");
		}
		frmImageSorter.getContentPane().add(carPicture3);
		final JLabel carPicture4 = new JLabel();
		carPicture4.setToolTipText("Double click on this to open the displayed image");
		carPicture4.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		carPicture4.addMouseListener(new MouseAdapter() 
		{
			boolean OneClick;

			@Override
			public void mouseClicked(MouseEvent mouseEvent) 
		{
			if (OneClick) 
			{
				if(CarDefinitions.getImageNames()[3] != null)
					try {
						Desktop.getDesktop().open(new File(CarDefinitions.getImageNames()[3]));
					} catch (IOException e) {
						e.printStackTrace();
					}
				  OneClick = false;
			}
			else 
			{
				  OneClick = true;
				  Timer t = new Timer("clickTimer", false);
				  t.schedule(new TimerTask() 
				  	{
					  @Override
					  public void run()
					  {
						  OneClick = false;
					  }
				  	},500);
			}
		}
		});
		carPicture4.setBounds(664, 271, 330, 253);
		if(CarDefinitions.getImageNames()[3] != null)
		{
		BufferedImage img3 = null;
		try {
		    img3 = ImageIO.read(new File(CarDefinitions.getImageNames()[3]));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg3 = img3.getScaledInstance(carPicture4.getWidth(), carPicture4.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon3 = new ImageIcon(dimg3);
		carPicture4.setIcon(imageIcon3);
		}
		else
		{
			carPicture4.setText("                      No image found!");
		}
		frmImageSorter.getContentPane().add(carPicture4);
		
		/**
		 * Handles when the "Sort" button is clicked
		 */
		
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				progressBar.setVisible(true);
				btnSort.setEnabled(false);
				progressBar.setValue(progressBar.getValue() + increment);
				outputWindow.setText("");
	        	String[] options = new String[]{"Delete", "Continue"};
	        	try {
					if(Logger.CheckIfCarIndexed(CarDefinitions.getStock()))
					{
					int choice = JOptionPane.showOptionDialog(null, "Would you like to delete the existing files for this car, or just continue anyways?",
							"Car already sorted!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					if(choice == 0)
					{
						CarDefinitions.getStockFile().delete();
		        		File[] images = new File[5];
		        		int i = 0;
		        		while(i < CarDefinitions.getImageNames().length)
		        		{
		        			if(CarDefinitions.getImageNames()[i] != null)
		        			{
		        			images[i] = new File(CarDefinitions.getImageNames()[i]);
		        			images[i].delete();
		        			}
		        			i++;
		        		}
		        		return;
					}
					if(choice == 1)
					{
					//This is where the continue option is handled
					}
					}
					if(CarDefinitions.getStock() != null)
					{
					ImageInterface.CopyFiles(CarDefinitions.getStock(), CarDefinitions.getModel(), CarDefinitions.getMake());
					CarDefinitions.getStockFile().delete();
					Logger.LogCar(CarDefinitions.getStock());
					outputWindow.setText(CarDefinitions.getMake() + " " + CarDefinitions.getModel() + " successfully sorted!");
					CarDefinitions.setMake(null);
			        ImageInterface.findFile(CarDefinitions.getPictureLocation());
					}
			        
			        if(CarDefinitions.getStock() != null)
			        {
			        if(CarDefinitions.getImageNames()[0] != null)
			        {
			        BufferedImage img = null;
					try {
					    img = ImageIO.read(new File(CarDefinitions.getImageNames()[0]));
					} catch (IOException e) {
					    e.printStackTrace();
					}
					Image dimg = img.getScaledInstance(carPicture2.getWidth(), carPicture2.getHeight(),
					        Image.SCALE_SMOOTH);
					ImageIcon imageIcon = new ImageIcon(dimg);
					carPicture2.setIcon(imageIcon);
			        }
			        else
					{
						carPicture2.setText("                      No image found!");
					}
					
			        if(CarDefinitions.getImageNames()[1] != null)
			        {
					BufferedImage img1 = null;
					try {
					    img1 = ImageIO.read(new File(CarDefinitions.getImageNames()[1]));
					} catch (IOException e) {
					    e.printStackTrace();
					}
					Image dimg1 = img1.getScaledInstance(carPicture1.getWidth(), carPicture1.getHeight(),
					        Image.SCALE_SMOOTH);
					ImageIcon imageIcon1 = new ImageIcon(dimg1);
					carPicture1.setIcon(imageIcon1);
			        }
			        else
					{
						carPicture1.setText("                      No image found!");
					}
					
					if(CarDefinitions.getImageNames()[2] != null)
			        {
					BufferedImage img2 = null;
					try {
					    img2 = ImageIO.read(new File(CarDefinitions.getImageNames()[2]));
					} catch (IOException e) {
					    e.printStackTrace();
					}
					Image dimg2 = img2.getScaledInstance(carPicture3.getWidth(), carPicture3.getHeight(),
					        Image.SCALE_SMOOTH);
					ImageIcon imageIcon2 = new ImageIcon(dimg2);
					carPicture3.setIcon(imageIcon2);
					frmImageSorter.getContentPane().add(carPicture3);
			        }
					else
					{
						carPicture3.setText("                      No image found!");
					}
					
					if(CarDefinitions.getImageNames()[3] != null)
			        {
					BufferedImage img3 = null;
					try {
					    img3 = ImageIO.read(new File(CarDefinitions.getImageNames()[3]));
					} catch (IOException e) {
					    e.printStackTrace();
					}
					Image dimg3 = img3.getScaledInstance(carPicture4.getWidth(), carPicture4.getHeight(),
					        Image.SCALE_SMOOTH);
					ImageIcon imageIcon3 = new ImageIcon(dimg3);
					carPicture4.setIcon(imageIcon3);
			        }
					else
					{
						carPicture4.setText("                      No image found!");
					}
			        }
			        else
			        {
			        	carPicture2.setText("                      No image found!");
			        	carPicture1.setText("                      No image found!");
			        	carPicture3.setText("                      No image found!");
			        	carPicture4.setText("                      No image found!");
			        }
					if(CarDefinitions.getStock() != null)
					{
						CarDefinitions.TrimStock();
						stockField.setText(CarDefinitions.getStock());
					}
						else
						stockField.setText("NO CARS");
					makeField.setText("");
					makeField.setEditable(false);
					modelField.setText("");
					
					
			        
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btnSort.setToolTipText("Clicking this button will sort the current car and proceed");
		btnSort.setBounds(877, 536, 117, 36);
		frmImageSorter.getContentPane().add(btnSort);
		
		JButton skipCarButton = new JButton("Skip this car (copies it to skipped car folder)");
		skipCarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				progressBar.setValue(progressBar.getValue() + increment);
				CarDefinitions.getStockFile().renameTo(new File("/Users/sturtevantauto/Pictures/Car_Pictures/SKIPPED/" + CarDefinitions.getStock() + "__.jpg"));
				for(int i = 0; CarDefinitions.getImageNames().length > i; i++)
				{
					if(CarDefinitions.getImageNames()[i] != null)
					{
					File carfile = new File(CarDefinitions.getImageNames()[i]);
					carfile.renameTo(new File("/Users/sturtevantauto/Pictures/Car_Pictures/SKIPPED/" + CarDefinitions.getStock() + "_" + i + ".jpg"));
					}
				}
				outputWindow.setText(CarDefinitions.getMake() + " " + CarDefinitions.getModel() + " successfully moved out for later sorting!");
				CarDefinitions.setMake(null);
		        ImageInterface.findFile(CarDefinitions.getPictureLocation());
		        if(CarDefinitions.getStock() != null)
				{
					CarDefinitions.TrimStock();
					stockField.setText(CarDefinitions.getStock());
				}
					else
					stockField.setText("NO CARS");
				makeField.setText("");
				makeField.setEditable(false);
				modelField.setText("");
			}
		});
		skipCarButton.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		skipCarButton.setBounds(15, 323, 295, 36);
		frmImageSorter.getContentPane().add(skipCarButton);
		
		JLabel label = new JLabel("");
		label.setBounds(436, 547, 61, 16);
		frmImageSorter.getContentPane().add(label);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
		frmImageSorter.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
		menuBar.add(mnFile);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
		mnFile.add(mntmAbout);
		
		JMenuItem mntmAaaaaaa = new JMenuItem("AAAAAAA");
		mntmAaaaaaa.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
		mnFile.add(mntmAaaaaaa);
		
		JMenuItem mntmTset = new JMenuItem("TSET");
		mntmTset.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
		mnFile.add(mntmTset);
		
		JCheckBoxMenuItem chckbxmntmCheck = new JCheckBoxMenuItem("Check!");
		mnFile.add(chckbxmntmCheck);
		if(CarDefinitions.getStock() != null)
		stockField.setText(CarDefinitions.getStock());
		else
		{
		carPicture1.setText("                      No image found!");
		carPicture1.setIcon(null);
		carPicture2.setText("                      No image found!");
		carPicture2.setIcon(null);
		carPicture3.setText("                      No image found!");
		carPicture3.setIcon(null);
		carPicture4.setText("                      No image found!");
		carPicture4.setIcon(null);
		stockField.setText("NO CARS");
		}
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
