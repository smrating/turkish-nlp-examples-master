package test;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import morphology.ParseWords;

import org.antlr.v4.runtime.Token;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
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

	String text;
	/**
	 * Object that stores the instance.
	 */
	Instances instances;
	/**
	 * Object that stores the classifier.
	 */
	FilteredClassifier classifier;

	public void load(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			text = "";
			while ((line = reader.readLine()) != null) {
				text = text + " " + line;
			}
			System.out
					.println("===== Loaded text data: " + fileName + " =====");
			reader.close();
			System.out.println(text);
		} catch (IOException e) {
			System.out.println("Problem found when reading: " + fileName);
		}
	}

	public void writeTest(String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(instances.toString());
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Problem found when reading: " + fileName);
		}
	}

	public void makeInstance() throws IOException {
		// Create the attributes, class and text
		FastVector fvNominalVal = new FastVector(2);
		fvNominalVal.addElement("negatif");
		fvNominalVal.addElement("pozitif");
		// fvNominalVal.addElement("biyoloji");
		// fvNominalVal.addElement("botanik");
		// fvNominalVal.addElement("din");
		// fvNominalVal.addElement("edebiyat");
		// fvNominalVal.addElement("bilisim");
		// fvNominalVal.addElement("cografya");
		// fvNominalVal.addElement("ekonomi");
		// fvNominalVal.addElement("felsefe");
		// fvNominalVal.addElement("fizik");
		// fvNominalVal.addElement("gramer");
		// fvNominalVal.addElement("hukuk");
		// fvNominalVal.addElement("kimya");
		// fvNominalVal.addElement("matematik");
		// fvNominalVal.addElement("muzik");
		// fvNominalVal.addElement("psikoloji");
		// fvNominalVal.addElement("sosyoloji");
		// fvNominalVal.addElement("spor");
		// fvNominalVal.addElement("tarih");
		// fvNominalVal.addElement("ticaret");
		// fvNominalVal.addElement("tip");
		// fvNominalVal.addElement("zooloji");
		Attribute attribute1 = new Attribute("class", fvNominalVal);
		Attribute attribute2 = new Attribute("text", (FastVector) null);
		// Create list of instances with one element
		FastVector fvWekaAttributes = new FastVector(2);
		fvWekaAttributes.addElement(attribute1);
		fvWekaAttributes.addElement(attribute2);
		instances = new Instances("Test relation", fvWekaAttributes, 1);
		// Set class index
		instances.setClassIndex(0);
		// Create and add the instance
		Instance instance = new Instance(2);
		instance.setValue(attribute2, text);
		// Another way to do it:
		// instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text);
		instances.add(instance);
		System.out
				.println("===== Instance created with reference dataset =====");
		System.out.println(instances);

	}

	public static void main(String[] args) throws Exception {
		BagOfWords classifier;
		classifier = new BagOfWords();
		classifier.load("D:\\eclipse_workspace\\datasets\\sonuc.txt");
		classifier.makeInstance();
		classifier.writeTest("D:\\eclipse_workspace\\datasets\\sonuc2.txt");
		// //köklerine ayrılacak kullanıcı verisini oku
		// BufferedReader input = new BufferedReader(new InputStreamReader(
		// new FileInputStream(
		// "D:\\eclipse_workspace\\datasets\\gramer.txt"),
		// "ISO-8859-9"));
		//
		// String line=null;
		//
		// FastVector atts;
		// double[] vals;
		// Instances dataTest;
		//
		// atts = new FastVector();
		// atts.addElement(new Attribute("class", (FastVector) null));
		// atts.addElement(new Attribute("text", (FastVector) null));
		//
		// dataTest = new Instances("MyRelation", atts, 0);
		// BufferedWriter writer = new BufferedWriter(new FileWriter(
		// "D:\\eclipse_workspace\\datasets\\sonuc.txt"));
		//
		// while((line=input.readLine())!=null) // seçtiğim kelimeleri
		// // köklerine ayır.
		// {
		// // StringBuilder sb= new StringBuilder();
		// // sb.append("'");
		// // sb.append(line);
		// // sb.append("'");
		// vals = new double[dataTest.numAttributes()];
		// vals[0] = dataTest.attribute(0).addStringValue("bilişim");//kökü
		// attribute olarak tut
		// vals[1] =
		// dataTest.attribute(1).addStringValue(line/*sb.toString()*/);
		// //classını belirledik,yok
		// dataTest.add(new Instance(1.0, vals));//ikisini içeren bir instance
		// oluştur
		//
		//
		// }
		// writer.write(dataTest.toString());
		// writer.newLine();
		// writer.flush();
		// writer.close();
		//
		// System.out.println(dataTest);// arff formatli kök test setimiz oluştu
		// // set class attribute
		// dataTest.setClassIndex(0);

	}
}