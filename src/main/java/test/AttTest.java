package test;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * Generates a little ARFF file with different attribute types.
 *
 * @author FracPete
 */
public class AttTest {
	  
	  
	public static void main(String[] args) throws Exception {
	     FastVector      atts;
	
	     Instances       data;
	 
	     double[]        vals;

	     int             i;
	 
	     // 1. set up attributes
	     atts = new FastVector();
	    
	     // - string
	     atts.addElement(new Attribute("kok", (FastVector) null));
	     // - date
	     atts.addElement(new Attribute("sinif", (FastVector) null));

	     // -- numeric
	    
	 
	    
	 
	     // 2. create Instances object
	     data = new Instances("MyRelation", atts, 0);
	 
	     // 3. fill with data
	     // first instance
	     vals = new double[data.numAttributes()];
	     
	     // - string
	     vals[0] = data.attribute(0).addStringValue("This is a string!");
	     // - date
	     vals[1] = data.attribute(1).addStringValue("?");
	     // - relational
	     
	     // add
	     data.add(new Instance(1.0, vals));
	 
	     // second instance
	     vals = new double[data.numAttributes()];  // important: needs NEW array!
	   
	     // - string
	     vals[0] = data.attribute(0).addStringValue("And another one!");
	     // - date
	     vals[1] = data.attribute(1).addStringValue("2000-12-01");
	    
	     // add
	     data.add(new Instance(1.0, vals));
	     System.out.println(data);
  }
}