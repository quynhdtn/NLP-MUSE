package liir.nlp.core.representation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by quynhdo on 28/08/15.
 */
public class Word {
    String id="";
    String str="";
    String lemma="";
    String pos="";
    String head="";
    String deprel="";
    Sentence sentence;

    HashMap<String,String> otherFeatures;

    public Word(){}
    public Word(String id, String str, String lemma, String pos, String head, String deprel, Sentence sentence) {
        if (id!=null)
            this.id = id;
        if (str!=null)
            this.str = str;
        if (lemma!=null)
            this.lemma = lemma;
        if (pos!=null)
            this.pos = pos;
        if (head!=null)
            this.head = head;
        if (deprel !=null)
            this.deprel = deprel;
        if (sentence !=null)
            this.sentence = sentence;
    }

    public Word(Word w){
        this.id = w.getId();
        this.str = w.getStr();
        this.lemma = w.getLemma();
        this.pos = w.getPos();
        this.head = w.getHead();
        this.deprel = w.getDeprel();
        this.sentence = w.getSentence();
        this.otherFeatures = w.otherFeatures;
    }

    public int getPositionInSentence(){
        return sentence.indexOf(this) + 1;
    }
    public String toXMLString(){

        String s=  "<w id=\"" + id + "\" str=\"" + str  + "\" lemma=\"" + lemma  + "\" pos=\"" + pos  + "\" head=\"" + head +  "\" deprel=\"" + deprel +    "\"";

        if (otherFeatures!=null){
            for (String k : otherFeatures.keySet()){
                s = s + " " + k + "=\"" + otherFeatures.get(k) + "\"";
            }
        }


        s = s + "/>" ;
        return s;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setDeprel(String deprel) {
        this.deprel = deprel;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public String getId() {
        return id;
    }

    public String getStr() {
        return str;
    }

    public String getLemma() {
        return lemma;
    }

    public String getPos() {
        return pos;
    }

    public String getHead() {
        return head;
    }

    public String getDeprel() {
        return deprel;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public void addFeature (String name, String value){
        if (otherFeatures == null)
            otherFeatures = new HashMap<>();
        otherFeatures.put(name, value);
    }

    public String getFeature (String name){

        if (otherFeatures == null)
            return  "";
        if (otherFeatures.containsKey(name))


        return otherFeatures.get(name);
        else
            return "";
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Word w = new Word();
        Method m= Word.class.getMethod("show", null);
        int i = Integer.parseInt(m.invoke(w).toString());
        System.out.println(i);

    }

}
