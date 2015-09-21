package liir.nlp.interfaces.preprocessing;

import liir.nlp.io.XMLReader;
import liir.nlp.representation.Sentence;
import liir.nlp.representation.Text;
import liir.nlp.representation.Word;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

/**
 * Created by quynhdo on 17/09/15.
 */
public class Tokenizer {
    String name="Tokenizer";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Text processXMLToText(String xmlstring) throws IOException, SAXException {
        Text txt = XMLReader.readCorpus(xmlstring).get(0);
        return processToText(txt);

    }

    public Text processToText(Text txt){

        for (Sentence s : txt){
            String[] words= process(s.getStr());
            s.clear();
            for (int i=0;i<words.length;i++)
            {
                Word w = new Word();
                w.setStr(words[i]);
                s.add(w);
            }
        }
        return txt;
    }

    public Text processToText(String txt){

        String[] tokens = process(txt);
        Text text = new Text();
        Sentence s = new Sentence();
        for (int i =0 ; i<tokens.length; i++)
        {
            Word w = new Word();
            w.setStr(tokens[i]);
            s.add(w);
        }
        text.add(s);

        return text;
    }

    /***
     * process a sentence to tokens
     * @param str - sentence
     * @return tokens
     */
    public String[] process(String str){
        throw new UnsupportedOperationException("the function need to be implemented!");

    }

}
