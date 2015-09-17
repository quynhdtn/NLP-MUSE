package liir.nlp.representation;

/**
 * Created by quynhdo on 06/09/15.
 */
public class Paragraph {


    int startSentence;
    int endSentence;

    public Paragraph(int startSentence, int endSentence) {
        this.startSentence = startSentence;
        this.endSentence = endSentence;
    }

    public int getStartSentence() {
        return startSentence;
    }

    public int getEndSentence() {
        return endSentence;
    }

    public void setEndSentence(int endSentence) {
        this.endSentence = endSentence;
    }

    public void setStartSentence(int startSentence) {
        this.startSentence = startSentence;
    }

    public String toXMLString(){
        return "<paragraph beginSenId=\"" + String.valueOf(startSentence) + "\" endSenId=\"" +String.valueOf(endSentence) +"\"/>\n";
    }
}
