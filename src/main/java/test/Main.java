package test;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import morphology.ParseWords;

import org.antlr.v4.runtime.Token;

import zemberek.tokenizer.ZemberekLexer;
import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.parser.MorphParse;
import zemberek.tokenizer.SentenceBoundaryDetector;
import zemberek.tokenizer.SimpleSentenceBoundaryDetector;

import java.io.IOException;
import java.util.List;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.trees.J48;

public class Main {
	
	
	
	TurkishMorphParser parser;

    public Main(TurkishMorphParser parser) {
        this.parser = parser;
    }
	
	 public void parse(String word) {
	        System.out.println("Word = " + word);
	        List<MorphParse> parses = parser.parse(word);
	        for (MorphParse parse : parses) {
	            System.out.println(parse.formatLong());
	        }
	 }
	 public static java.util.List<String> simpleTokenization(String input) {
		 	List<String> token = new ArrayList<String>();
	        System.out.println("Simple tokenization returns a list of token strings.");
	        ZemberekLexer lexer = new ZemberekLexer();
	        System.out.println("Input = " + input);
	        return lexer.tokenStrings(input);
	    }
	 
	 
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
// kullanıcıdan girdi nasıl alınıyor??
		 //arffden okuma çalışıyor
	/*	 BufferedReader reader = new BufferedReader(
	             new FileReader("C:\\Users\\Melis\\Documents\\kendi ders notlarım\\3.yıl\\1.dönem\\Data_mining\\proje\\UCIdataset_arff\\anneal.arff"));
	Instances data = new Instances(reader);
	reader.close();
	// setting class attribute
	data.setClassIndex(data.numAttributes() - 1);
 	System.out.println("Paragraf:"+ data);
*/
	/////**//
		 //csvden okumalıydı çalışmadı
 	/*DataSource source = new DataSource("C:\\Users\\Melis\\Desktop\\text_mining\\Reviews.csv");

 	 Instances data = source.getDataSet();
 	 // setting class attribute if the data format does not provide this information
 	 // For example, the XRFF format saves the class attribute information as well
 	 if (data.classIndex() == -1)
 	   data.setClassIndex(data.numAttributes() - 1);
 	System.out.println("Paragraf:"+ data);*/
 	 ///////////***////////////
 	//dosyadan okuma

//		 BufferedReader datafile = readDataFile("C:\\Users\\Melis\\Desktop\\text_mining\\reviews.txt");
//
//
//	Instances data = new Instances(datafile);
//	data.setClassIndex(data.numAttributes() - 1);
//	System.out.println("Paragraf:"+ data);
//	String[] options = new String[1];
//	 options[0] = "-U";            // unpruned tree
//	 J48 tree = new J48();         // new instance of tree
//	 tree.setOptions(options);     // set the options
//	 tree.buildClassifier(data);   // build classifier
//	 System.out.println(tree);
		 
		 
/*test etme çalışmadı
		BufferedReader data_file = readDataFile("C:\\Users\\Melis\\Desktop\\text_mining\\test.txt");
			 Instances test = new Instances(data_file);   // from somewhere
				System.out.println("Paragraf:"+ test);
			 // train classifier
			 Classifier cls = new J48();
			 System.out.println(cls);
			 cls.buildClassifier(data);
			 // evaluate classifier and print some statistics
			 Evaluation eval = new Evaluation(data);
			 eval.evaluateModel(cls, test);
			 System.out.println(eval.toSummaryString("\nResults\n======\n", false));
			 */
	
	///////
	 
//		 Instances unlabeled = new Instances(
//                 new BufferedReader(
//                   new FileReader("C:\\Users\\Melis\\Desktop\\text_mining\\test.txt")));
//
//// set class attribute
//unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
//
//// create copy
//Instances labeled = new Instances(unlabeled);
//
//// label instances
//for (int i = 0; i < unlabeled.numInstances(); i++) {
//double clsLabel = tree.classifyInstance(unlabeled.instance(i));
//labeled.instance(i).setClassValue(clsLabel);
//System.out.println(clsLabel + " -> " + unlabeled.classAttribute().value((int) clsLabel));
//}
//
//
//// save labeled data
//BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Melis\\Desktop\\text_mining\\sonuc.txt"));
//writer.write(labeled.toString());
//writer.newLine();
//writer.flush();
//writer.close();

		  
		 
	 //benim kodum önemli silme sakın!
Bag<String> bag = new Bag<String>();

BufferedReader input = new BufferedReader(new InputStreamReader(
        new FileInputStream("D:\\eclipse_workspace\\datasets\\deneme.txt"),  "ISO-8859-9"));
//readDataFile("C:\\Users\\Melis\\Desktop\\text_mining\\deneme.txt");
		 //	String input="Bugün hava çok güzel. Eve hiç gidesim yok. gidesim gidesim çok hava";
		 	//System.out.println("Paragraf:"+ input);
		 	SentenceBoundaryDetector detector = new SimpleSentenceBoundaryDetector();
		 	//System.out.println(input.readLine());
		 	List<String>parag=new ArrayList<String>();
		 	String line;
		 	for (int i = 0;(line= input.readLine())!=null; i++) {
		 		parag.add( line);
			}
		 	List<String>paragraph=detector.getSentences(parag.toString());
		
		 	//System.out.println(paragraph.size());
			TurkishMorphParser parser = null;
	        System.out.println();
	        List<String> tokened = new ArrayList();
	        List<String> cumleler = new ArrayList();
	        
		 	System.out.println("Paragraf içindeki cümleler:");
		 	for(String sentence:paragraph){
		 		System.out.println(sentence);
		 		cumleler.add(sentence); // bulduğum cümleleri listeye attım.
		 		
		 	}
		 	
		 for(String kelime: cumleler){ // cümlelerin içinden kelimeleri seçicem
				tokened = simpleTokenization(kelime);
	 	    System.out.println(tokened.toString());
	 	    
	 	   try {
				parser = TurkishMorphParser.createWithDefaults();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	   
		 	BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\eclipse_workspace\\datasets\\deneme.txt"));
	
			for(int i=0; i<tokened.size(); i++) // seçtiğim kelimeleri köklerine ayır.
			{
		        String test = new ParseWords(parser).parseString(tokened.get(i).toString());
			 	
		        bag.add(test);
		        			}

	
		 
		 	
//parsewordsu dene!!!
			       System.out.println("size of bag = " + bag.size());
			        for (String s : bag) {
			        	writer.write(s);
					 	writer.newLine();
						writer.flush();

			        	System.out.println(s);
			        }
			        writer.close();
	    }
	 }
}
