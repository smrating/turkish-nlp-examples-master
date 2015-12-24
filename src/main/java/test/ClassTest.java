package test;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class ClassTest {

	//ZemberekKok kokler;
	
	MyFilteredLearner learner;
	
	
	MyFilteredClassifier classifier;
	
	public void learnerTest() {

		learner = new MyFilteredLearner();
		learner.loadDataset("D:\\eclipse_workspace\\datasets\\trainingset.arff");
		// Evaluation must be done before training
		// More info in: http://weka.wikispaces.com/Use+WEKA+in+your+Java+code
		learner.evaluate();
		learner.learn();
		learner.saveModel("D:\\eclipse_workspace\\datasets\\test.arff");
		
	}
	
	public void classifierTest() {

		classifier = new MyFilteredClassifier();
		classifier.load("D:\\eclipse_workspace\\datasets\\kök_sonuç.txt");
		classifier.loadModel("D:\\eclipse_workspace\\datasets\\test.arff");
		classifier.makeInstance();
		classifier.classify();
		
	}
	
	public void KokTest() {
	//	kokler=new ZemberekKok(parser);

//kokler.KokAyirTest();
		
		
	}
	public static void main (String[] args) {
		
	
		ClassTest testEt=new ClassTest();
		testEt.KokTest();
		testEt.learnerTest();
		testEt.classifierTest();
	
		
			
		}
	

}
