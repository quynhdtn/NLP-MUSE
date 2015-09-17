package liir.nlp.representation;

/**
 * Created by quynhdo on 07/09/15.
 */
public class DiscourseRelation {

    int first;
    int second;
    String label;

    public DiscourseRelation(int first, int second, String label) {
        this.first = first;
        this.second = second;
        this.label = label;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public String getLabel() {
        return label;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String toXMLString(){
        return "<discourseRel firstId=\"" + String.valueOf(first) + "\" secondId=\"" +String.valueOf(second) +"\" label=\""+ label +"\"/>\n";
    }
}
