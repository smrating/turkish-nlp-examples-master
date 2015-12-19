package test;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import morphology.ParseWords;

import org.antlr.v4.runtime.Token;

import weka.core.Instance;
import zemberek.tokenizer.ZemberekLexer;
import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.parser.MorphParse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ZemberekKok {
	
	TurkishMorphParser parser;

    public ZemberekKok(TurkishMorphParser parser) {
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
		
	 public static void main(String[] args) {
		 	

			
		 	TurkishMorphParser parser = null;
	        System.out.println();
	        List<String> tokened = new ArrayList();
	        
	        
	        try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\eclipse_workspace\\datasets\\deneme.txt"),"ISO-8859-9"))) {
	        	StringBuilder sb= new StringBuilder();
	        	String line=input.readLine();
				while((line!=null)) // seçtiğim kelimeleri
					// köklerine ayır.
				{
					sb.append(line);
					sb.append(System.lineSeparator());
					line=input.readLine();
				}
				String text=sb.toString();
		        tokened = simpleTokenization(text);
		        System.out.println(tokened.toString());
		        
				try {
					parser = TurkishMorphParser.createWithDefaults();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						"D:\\eclipse_workspace\\datasets\\sonuc.txt"));
				for(int i=0; i<tokened.size(); i++)
				{
					String kok = new ParseWords(parser).parseString(tokened.get(i).toString());		
					System.out.println(kok);
				
					writer.write(kok.toString());
					writer.newLine();
					
				}
				
			
				writer.flush();
				writer.close();

			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        
        
	        
	        

	    }
}
