package tokenization;

import zemberek.tokenizer.SentenceBoundaryDetector;
import zemberek.tokenizer.SimpleSentenceBoundaryDetector;

import java.util.List;

public class SentenceBoundaryDetection {
// cümleleri ayırıyor.
    public static void simpleSentenceBoundaryDetector() {
        String input = "Prof. Dr. Veli Davul açıklama yaptı. Kimse %6.5 lik enflasyon oranını beğenmemiş!" +
                " Oysa maçta ikinci olmuştuk... Değil mi?";
        System.out.println("Paragraph = " + input); // paragrafı olduğu gibi yaz.
        SentenceBoundaryDetector detector = new SimpleSentenceBoundaryDetector();// bu classa ait nesne
        List<String> sentences = detector.getSentences(input); // bu fonksiyon nerde tanımlandı??
        System.out.println("Sentences:");
        for (String sentence : sentences) { //paragrafın içindeki sentenceları yaz.
            System.out.println(sentence);
        }
    }

    public static void main(String[] args) {
        simpleSentenceBoundaryDetector();
    }
}
