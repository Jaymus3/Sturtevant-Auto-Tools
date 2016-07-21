package org.sturtevantauto.io;

import java.io.File;

public class CarDefinitions {
	static String make;
	static String model;
	static File picloc = new File("/Users/sturtevantauto/Pictures/Car_Pictures/SORT_ME/");
	static File[] list = picloc.listFiles();
	static String stock;
	static File stockfile;
	static String[] imagenames = new String[50];
	
	/**
	 * Sets the picture location to the given path
	 * @param folderpath
	 */
	public static void setPictureLocation(String folderpath)
	{
		picloc = new File(folderpath);
	}
	/**
	 * Sets the car model to the given string
	 * @param models
	 */
	public static void setModel(String models)
	{
		model = models;
	}
	/**
	 * Sets the car make to the given string
	 * @param makes
	 */
	public static void setMake(String makes)
	{
		make = makes;
	}
	/**
	 * Set's the stock number placeholder file to the given file
	 * @param stock
	 */
	public static void setStockFile(File stock)
	{
		stockfile = stock;
	}
	/**
	 * Sets the list of pictures in a file array to a new file array
	 * @param thelist
	 */
	public static void setList(File[] thelist)
	{
		list = thelist;
	}
	/**
	 * Set's the image path of a specific image to the given string.  i is the array position given
	 * @param imagename
	 * @param i
	 */
	public static void setImageNames(String imagename, int i)
	{
		imagenames[i] = imagename;
	}

	/**
	 * Trims the stock file name down to only contain the stock number.
	 * @param skipped This determines whether or not the trimmer is trimming the stock number of previously skipped cars or not.
	 */
	public static void TrimStock(boolean skipped)
	{
		if(skipped)
		{
		stock = stock.replace(".JPG", "");
        stock = stock.replace(".jpg", "");
        stock = stock.replace("__", "");
		}
		else
		{
		stock = stock.replace(".JPG", "");
        stock = stock.replace(".jpg", "");
        stock = stock.substring(9);
		}
	}
	/**
	 * Returns the image paths array
	 * @return imagepaths
	 */
	public static String[] getImageNames()
	{
		return imagenames;
	}
	/**
	 * Returns the make of the active car
	 * @return make
	 */
	public static String getMake()
	{
		return make;
	}
	/**
	 * Sets the stock number of the active car to the given string
	 * @param string
	 */
	public static void setStock(String string)
	{
		stock = string;
	}
	/**
	 * Returns the stock number of the active car
	 * @return stock
	 */
	public static String getStock()
	{
		return stock;
	}
	/**
	 * Returns the stock number file for the active car
	 * @return stockfile
	 */
	public static File getStockFile()
	{
		return stockfile;
	}
	/**
	 * Returns the current picture storage location
	 * @return piclocation
	 */
	public static File getPictureLocation()
	{
		return picloc;
	}
	
	/**
	 * When given the parameters listed below, returns a storage path for the images with the proper appendages
	 * @param wheels Whether the storage location is wheels (boolean)
	 * @param make Make of the car
	 * @param model Model of the car
	 * @param stock Stock of the car
	 * @return storage path for an image
	 */
	//TODO: Make these file paths adjustable
	public static File getStorageLocation(boolean wheels, String make, String model, String stock)
	{
		if(wheels)
		{
			File ret = new File("/Users/sturtevantauto/Pictures/Car_Pictures/Wheels/" + make + "/" + model + "/" + stock);
			return ret;
		}
		else
		{
			File ret = new File("/Users/sturtevantauto/Pictures/Car_Pictures/" + make + "/" + model + "/" + stock);
			return ret;
		}
	}
	
	
	/**
	 * Returns the list of files of the active car
	 * @return thelist
	 */
	public static File[] getList()
	{
		return list;
	}
	/**
	 * Returns the model of the active car
	 * @return model
	 */
	public static String getModel() {
		return model;
	}

}
