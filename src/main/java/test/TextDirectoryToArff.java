package test;

/*
 *    TextDirectoryToArff.java
 *    Copyright (C) 2002 Richard Kirkby
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
 
import java.io.*;

import weka.core.*;
 
/**
 * Builds an arff dataset from the documents in a given directory.
 * Assumes that the file names for the documents end with ".txt".
 *
 * Usage:<p/>
 *
 * TextDirectoryToArff <directory path> <p/>
 *
 * @author Richard Kirkby (rkirkby at cs.waikato.ac.nz)
 * @version 1.0
 */
public class TextDirectoryToArff {
 
  public Instances createDataset(String directoryPath) throws Exception {
 
    FastVector atts = new FastVector(2);
    atts.addElement(new Attribute("filename", (FastVector) null));//string degil nominal olmalı! ilk değer class olmalı
    //The StringToWordVector filter moves the class attributes, which is usually the last one, to the front. 
    //The last attribute will be most likely a word-count that the filter produced. 
    //Define the index of the class attribute to use.
    atts.addElement(new Attribute("contents", (FastVector) null));
    Instances data = new Instances("text_files_in_" + directoryPath, atts, 0);
 
    File dir = new File(directoryPath);
    String[] files = dir.list();
    for (int i = 0; i < files.length; i++) {
      if (files[i].endsWith(".txt")) {
    try {
      double[] newInst = new double[2];
      newInst[0] = (double)data.attribute(0).addStringValue(files[i]);
      File txt = new File(directoryPath + File.separator + files[i]);
      InputStreamReader is;
      is = new InputStreamReader(new FileInputStream(txt),"ISO-8859-9");
      StringBuffer txtStr = new StringBuffer();
      int c;
      while ((c = is.read()) != -1) {
        txtStr.append((char)c);
      }
      newInst[1] = (double)data.attribute(1).addStringValue(txtStr.toString());
      data.add(new Instance(1.0, newInst));
    } catch (Exception e) {
      //System.err.println("failed to convert file: " + directoryPath + File.separator + files[i]);
    }
      }
    }
    return data;
  }
 
  public static void main(String[] args) {
 
    
      TextDirectoryToArff tdta = new TextDirectoryToArff();
      try {
    Instances dataset = tdta.createDataset("D:\\eclipse_workspace\\tasarım");
    System.out.println(dataset);
	// BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\eclipse_workspace\\datasets\\sonuc.txt"));
	 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				"D:\\eclipse_workspace\\datasets\\sonuc.txt"),"ISO-8859-9"));
	 writer.write(dataset.toString());
	 writer.newLine();
	 writer.flush();
	 writer.close();
      } catch (Exception e) {
    System.err.println(e.getMessage());
    e.printStackTrace();
      }
  
  }
}