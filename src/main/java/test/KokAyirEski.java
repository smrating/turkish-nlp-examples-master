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

public class KokAyirEski {

	TurkishMorphParser parser;

	public KokAyirEski(TurkishMorphParser parser) {
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
		System.out
				.println("Simple tokenization returns a list of token strings.");
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

		//köklerine ayrılacak kullanıcı verisini oku
		BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(
						"D:\\eclipse_workspace\\datasets\\deneme.txt"),
				"ISO-8859-9"));
	

		SentenceBoundaryDetector detector = new SimpleSentenceBoundaryDetector();
		// System.out.println(input.readLine());
		List<String> parag = new ArrayList<String>();
		String line;
		for (int i = 0; (line = input.readLine()) != null; i++) {
			parag.add(line);
		}
		List<String> paragraph = detector.getSentences(parag.toString());

		// System.out.println(paragraph.size());
		TurkishMorphParser parser = null;
		System.out.println();
		List<String> tokened = new ArrayList();
		List<String> cumleler = new ArrayList();

		System.out.println("Paragraf içindeki cümleler:");
		for (String sentence : paragraph) {
			System.out.println(sentence);
			cumleler.add(sentence); // bulduğum cümleleri listeye attım.
		}

		for (String kelime : cumleler) { // cümlelerin içinden kelimeleri
											// seçicem
			tokened = simpleTokenization(kelime);
			System.out.println(tokened.toString());

			try {
				parser = TurkishMorphParser.createWithDefaults();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FastVector atts;
			double[] vals;
			Instances dataTest;

			atts = new FastVector();
			atts.addElement(new Attribute("kok", (FastVector) null));
			atts.addElement(new Attribute("sinif", (FastVector) null));
			dataTest = new Instances("MyRelation", atts, 0);

			for (int i = 0; i < tokened.size(); i++) // seçtiğim kelimeleri
														// köklerine ayır.
			{
				String test = new ParseWords(parser).parseString(tokened.get(i)
						.toString());

				vals = new double[dataTest.numAttributes()];
				vals[0] = dataTest.attribute(0).addStringValue(test);//kökü attribute olarak tut
				vals[1] = dataTest.attribute(1).addStringValue("un"); //classını belirledik,yok
				dataTest.add(new Instance(1.0, vals));//ikisini içeren bir instance oluştur
			}

			// parsewordsu dene!!! zemberekte köklere ayırma işlemi ile ilgili ayrıntılar için.

			System.out.println(dataTest);// arff formatli kök test setimiz oluştu
			// set class attribute
						dataTest.setClassIndex(dataTest.numAttributes() - 1);
						
			//training seti oku
			Instances dataClass = new Instances(new BufferedReader(
					new InputStreamReader(new FileInputStream(
							"D:\\eclipse_workspace\\datasets\\bagofwords.txt"),
							"ISO-8859-9")));

			dataClass.setClassIndex(dataClass.numAttributes() - 1);//son att. classtır.
			System.out.println("Paragraf:" + dataClass);//training seti yazdır.
			String[] options = new String[1];
			options[0] = "-U"; // unpruned tree
			J48 tree = new J48(); // new instance of tree
			tree.setOptions(options); // set the options
			tree.buildClassifier(dataClass); // build classifier
			System.out.println(tree);

			// create copy
			Instances labeled = new Instances(dataTest);

			// label instances sınıflandır sıkıntı burda biryerde!!! stringtovector lazım!!
			for (int i = 0; i < dataTest.numInstances(); i++) {
				double clsLabel = tree.classifyInstance(dataTest.instance(i));
				labeled.instance(i).setClassValue(clsLabel);
				System.out.println(clsLabel + " -> "
						+ dataTest.classAttribute().value((int) clsLabel));
			}

			// save labeled data sonucu yaz.
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"D:\\eclipse_workspace\\datasets\\sonuc.txt"));
			writer.write(labeled.toString());
			writer.newLine();
			writer.flush();
			writer.close();

			// writer.write(data);
			// writer.newLine();
			// writer.flush();
			// writer.close();
		}
	}
}