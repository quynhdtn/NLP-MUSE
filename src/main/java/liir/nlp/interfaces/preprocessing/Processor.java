package liir.nlp.interfaces.preprocessing;

import liir.nlp.representation.Text;

/**
 * Created by quynhdo on 19/09/15.
 */
public class Processor {

    String name;
    public Processor(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String processToXML(Text txt){

        Text text= processToText(txt);
        return text.toXMLString();

    }

    public Text processToText(Text txt){
        return txt;
    }
}
