package test;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import morphology.ParseWords;

import org.antlr.v4.runtime.Token;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;
import zemberek.tokenizer.ZemberekLexer;
import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.parser.MorphParse;
import zemberek.tokenizer.SentenceBoundaryDetector;
import zemberek.tokenizer.SimpleSentenceBoundaryDetector;

import java.io.IOException;
import java.util.List;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class BagOfWords {


	public static void main(String[] args) throws Exception {

		//köklerine ayrılacak kullanıcı verisini oku
		BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(
						"D:\\eclipse_workspace\\datasets\\gramer.txt"),
				"ISO-8859-9"));
		StringBuilder sb= new StringBuilder();
		String line=null;
		
			FastVector atts;
			double[] vals;
			Instances dataTest;

			atts = new FastVector();
			atts.addElement(new Attribute("kok", (FastVector) null));
			atts.addElement(new Attribute("sinif", (FastVector) null));
			dataTest = new Instances("MyRelation", atts, 0);
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"D:\\eclipse_workspace\\datasets\\sonuc.txt"));

			while((line=input.readLine())!=null) // seçtiğim kelimeleri
														// köklerine ayır.
			{
				
				vals = new double[dataTest.numAttributes()];
				vals[0] = dataTest.attribute(0).addStringValue(line);//kökü attribute olarak tut
				vals[1] = dataTest.attribute(1).addStringValue("askerlik"); //classını belirledik,yok
				dataTest.add(new Instance(1.0, vals));//ikisini içeren bir instance oluştur
				
				
			}
			writer.write(dataTest.toString());
			writer.newLine();
			writer.flush();
			writer.close();

			System.out.println(dataTest);// arff formatli kök test setimiz oluştu
			// set class attribute
						dataTest.setClassIndex(dataTest.numAttributes() - 1);
						
			
		}
	}