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
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.parser.MorphParse;
import zemberek.tokenizer.SentenceBoundaryDetector;
import zemberek.tokenizer.SimpleSentenceBoundaryDetector;
import zemberek.tokenizer.ZemberekLexer;

public class Model {
	
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
	 BufferedReader testfile = readDataFile("D:\\eclipse_workspace\\datasets\\test.txt");
	 BufferedReader unlabel = readDataFile("D:\\eclipse_workspace\\datasets\\test2.txt");
	 	Instances unlabeled = new Instances(unlabel);
	 	unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
	 	 // create copy
	 	 Instances labeled = new Instances(unlabeled);
	 	 
	 	 
	 	Instances test = new Instances(testfile);
	 	 test.setClassIndex(test.numAttributes() - 1);
		Instances data = new Instances(datafile);
		 data.setClassIndex(data.numAttributes() - 1);
		 StringToWordVector stw = new StringToWordVector();

		data.setClassIndex(data.numAttributes() - 1);
		System.out.println("Okunan training verisi\n\n"+ data);
		
		 J48 tree = new J48();         // new instance of tree
		 tree.setUnpruned(true);
		 FilteredClassifier fc = new FilteredClassifier();
		 fc.setFilter(stw);
		 fc.setClassifier(tree);
		 fc.buildClassifier(data);   // build classifier
		 
		 Evaluation eval = new Evaluation(data);
		 eval.evaluateModel(fc, test);
		 System.out.println(eval.toSummaryString("\nResults\n======\n", false));

		 for (int i = 0; i < test.numInstances(); i++) {
			   double pred = fc.classifyInstance(test.instance(i));
			   System.out.print("ID: " + test.instance(i).value(0));
			   System.out.print(", actual: " + test.classAttribute().value((int) test.instance(i).classValue()));
			   System.out.println(", predicted: " + test.classAttribute().value((int) pred));
			 }
	   //burası modeli geliştiriyor.
	//alttaki ise test ediyor
		 // label instances
		 for (int i = 0; i < unlabeled.numInstances(); i++) {
		   double clsLabel = fc.classifyInstance(unlabeled.instance(i));
		   labeled.instance(i).setClassValue(clsLabel);
		   System.out.println(clsLabel + " -> "
					+ labeled.classAttribute().value((int) clsLabel));
		 }
		 // save labeled data
		 BufferedWriter writer = new BufferedWriter(
		                           new FileWriter("D:\\eclipse_workspace\\datasets\\sonuc.txt"));
		 writer.write(labeled.toString());
		 writer.newLine();
		 writer.flush();
		 writer.close();
}
}
