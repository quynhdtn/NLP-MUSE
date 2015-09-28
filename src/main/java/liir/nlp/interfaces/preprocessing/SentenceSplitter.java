package liir.nlp.interfaces.preprocessing;

import liir.nlp.core.representation.io.XMLReader;
import liir.nlp.core.representation.Sentence;
import liir.nlp.core.representation.Text;
import liir.nlp.core.representation.Word;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by quynhdo on 15/09/15.
 */
public class SentenceSplitter {


    String name="Sentence Splitter";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /***
     * the function to process a text to xml string
     * <text> <s id=""><str>blabla</str></s><s id=""><str>blabla</str></s></text>
     * @param xmltext
     * @return
     */
    public Text processXMLToText(String xmltext){

        try {
            Text xmlt= XMLReader.readCorpus(xmltext).get(0);
            Text txt= processToText(xmlt);
            return txt;



        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;

    }




    /***
     * the function to return a Text object
     * @param text
     * @return
     */
    public Text processToText(String text){

        System.out.println(text);
        String[] sen_strs= process(text);
        System.out.println(Arrays.toString(sen_strs));
        Text txt=new Text();
        for (int i=0;i<sen_strs.length;i++)
        {
            Sentence s = new Sentence();
            s.setStr(sen_strs[i]);
            Word w =new Word();
            s.add(w);
            txt.add(s);
            System.out.println(s.size());
        }


        System.out.println(txt.toXMLString());
        System.out.println(txt.size());
        return txt;

    }


    // when the input is a Text in which all the tokens are considered as one sentence.
    public Text processToText(Text txt){

        if (txt.size() >1){
            System.out.println("the input should contain only one sentence");
            return txt;
        }
        else{
            String[] tokens = new String[txt.get(0).size()];
            for (int i=0; i<txt.get(0).size(); i++)
                tokens[i]= txt.get(0).get(i).getStr();

            return processToText(tokens);
        }

    }



    public Text processToText(String[] tokens){

        List<String[]> sen_strs= process(tokens);
        Text txt=new Text();
        for (int i=0;i<sen_strs.size();i++)
        {
            Sentence s = new Sentence();
            for (int j=0;j<sen_strs.get(i).length;j++)
            {
                Word w =new Word();
                w.setStr(sen_strs.get(i)[j]);
                s.add(w);
            }

            txt.add(s);
        }
        return txt;

    }

    /***
     * process text to sentences, each sentence is a string
     * @param text
     * @return
     */
    public String[] process(String text){

        throw new UnsupportedOperationException("the function need to be implemented!");


    }

    public List<String[]> process(String[] tokens){

        throw new UnsupportedOperationException("the function need to be implemented!");


    }

}
