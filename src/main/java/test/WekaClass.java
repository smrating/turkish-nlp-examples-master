package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import morphology.ParseWords;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.parser.MorphParse;
import zemberek.tokenizer.SentenceBoundaryDetector;
import zemberek.tokenizer.SimpleSentenceBoundaryDetector;
import zemberek.tokenizer.ZemberekLexer;

public class WekaClass {
	
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}

	 public static void main(String[] args) throws Exception {
	 BufferedReader datafile = readDataFile("D:\\eclipse_workspace\\datasets\\reviews.txt");

	// StringToWordVector m_Filter = new StringToWordVector();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		System.out.println("Paragraf:"+ data);
		
	    StringToWordVector filter = new StringToWordVector();
	    filter.setInputFormat(data);
	    Instances dataFiltered = Filter.useFilter(data, filter);
	    System.out.println("\n\nFiltered data:\n\n" + dataFiltered);
	    
	    
		String[] options = new String[1];
		 options[0] = "-U";            // unpruned tree
		 J48 tree = new J48();         // new instance of tree
		 tree.setOptions(options);     // set the options
		 tree.buildClassifier(dataFiltered);   // build classifier
		 System.out.println(tree);
//		// evaluate classifier and print some statistics
//		 Evaluation eval = new Evaluation(train);
//		 eval.evaluateModel(tree, test);
//		 System.out.println(eval.toSummaryString("\nResults\n======\n", false));

		 
		 BufferedReader testfile = readDataFile("D:\\eclipse_workspace\\datasets\\test.txt");
				Instances unlabeled = new Instances(testfile);
				unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
				System.out.println("Paragraf:"+ unlabeled);
				
			    StringToWordVector filtertest = new StringToWordVector();
			    filtertest.setInputFormat(unlabeled);
			    Instances testFiltered = Filter.useFilter(unlabeled, filtertest);
			    System.out.println("\n\nFiltered data:\n\n" + testFiltered);
//		 Instances unlabeled = new Instances(
//                 new BufferedReader(
//                   new FileReader("D:\\eclipse_workspace\\datasets\\test.txt")));
//		 
//		// set class attribute
//		 unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
//		    StringToWordVector filter2 = new StringToWordVector();
//		    filter2.setInputFormat(unlabeled);
//		    Instances dataFiltered2 = Filter.useFilter(unlabeled, filter2);
		 // create copy
		 Instances labeled = new Instances(testFiltered);

		 // label instances
		 for (int i = 0; i < testFiltered.numInstances(); i++) {
		 double clsLabel = tree.classifyInstance(testFiltered.instance(i));
		 labeled.instance(i).setClassValue(labeled.classAttribute().value((int) clsLabel));// burda sorun var evet hayır olarak değil sayısal değerlerini yazıyor.
		 System.out.println(clsLabel + " -> " + labeled.classAttribute().value((int) clsLabel));
		 }
		 
		 // save labeled data
		 BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\eclipse_workspace\\datasets\\sonuc.txt"));
		 writer.write(labeled.toString());
		 writer.newLine();
		 writer.flush();
		 writer.close();
}
}
