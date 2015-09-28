package liir.nlp.core.representation.srl;

import liir.nlp.core.representation.Word;
import liir.nlp.core.representation.entities.Mention;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by quynhdo on 01/09/15.
 */
public class Predicate extends Word {

    String sense;
    HashMap<String,String> argmap; //<Argument ID (Mention or Word), Label>

    public Predicate(Word w) {

        super(w);
        argmap = new HashMap<>();


    }

    public void addArgument(String argId, String label){
        argmap.put(argId,label);
    }

    public String getLabel (String argId){
        return argmap.get(argId);
    }

    public void setSense(String sense) {
        this.sense = sense;
    }

    public String getSense() {

        return sense;
    }

    public List<String> getArgument(String label){
        List<String> arr = new ArrayList<>();
        for (String k : argmap.keySet()){
            if (argmap.get(k).toUpperCase().equals(label)){
                arr.add(k);

            }
        }

        return arr;
    }

    public List<Mention> getArgumentAsMention(String label){
        List<Mention> arr = new ArrayList<>();
        for (String k : argmap.keySet()){
            if (argmap.get(k).toUpperCase().equals(label)){

                Mention arg = this.getSentence().getText().getMention(k);
                arr.add(arg);

            }
        }

        return arr;
    }

    public List<Mention> getAllArgumentAsMentions(){
        List<Mention> arr =new ArrayList<>();
        for (String argId : argmap.keySet()){
            Mention arg = this.getSentence().getText().getMention(argId);
            arr.add(arg);
        }

        return arr;
    }

    public String toPredicateXMLString(){
        StringBuffer sb=new StringBuffer();
        sb.append("<frame senId=\"" +this.getSentence().getId()+ "\" sense=\"" + sense + "\">\n");
        sb.append("\t<pred wId=\""+ getId() +"\" />\n");

        for (String argId : argmap.keySet())
        {

            Mention arg = this.getSentence().getText().getMention(argId);
            if (arg == null) continue;

            sb.append("\t<argument beginWId=\"" + arg.getStartId() +
                    "\"  endWId=\"" +arg.getEndId() +
                    "" +
                    "\" headWId=\""+ arg.getHeadId() +"\" mId=\"" + arg.getId() +"\" label=\"" + argmap.get(argId) + "\" />\n");
        }

        sb.append("</frame>\n");

        return sb.toString();

    }


}
