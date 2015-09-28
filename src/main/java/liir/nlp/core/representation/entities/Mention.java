package liir.nlp.core.representation.entities;

import liir.nlp.core.representation.Sentence;
import liir.nlp.core.representation.Text;
import liir.nlp.core.representation.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quynhdo on 01/09/15.
 */
public class Mention {
    String startId;
    String endId;
    String headId;
    String senId;
    String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {

        return id;
    }

    public Mention(String startId, String endId, String headId, String senId) {
        this.startId = startId;
        this.endId = endId;
        this.headId = headId;
        this.senId = senId;

    }

    public Mention(String headId) {
        this.headId = headId;
    }

    public Mention(String startId, String endId) {

        this.startId = startId;
        this.endId = endId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public void setHeadId(String headId) {
        this.headId = headId;
    }

    public void setSenId(String senId) {
        this.senId = senId;
    }

    public String getSenId() {
        return senId;
    }

    public String getStartId() {

        return startId;
    }

    public String getEndId() {
        return endId;
    }

    public String getHeadId() {
        return headId;
    }
    public String toXMLString(){
        StringBuilder sb= new StringBuilder();
        sb.append("<mention id=\"" + id + "\" beginWId=\"" + startId + "\"  endWId=\""+ endId + "\" headWId=\"" + headId + "\" senId=\""+ senId + "\"/>\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mention mention = (Mention) o;

        if (!startId.equals(mention.startId)) return false;
        if (!endId.equals(mention.endId)) return false;
    //    if (headId != null ? !headId.equals(mention.headId) : mention.headId != null) return false;
        return senId.equals(mention.senId);

    }

    @Override
    public int hashCode() {
        int result = startId.hashCode();
        result = 31 * result + endId.hashCode();
        result = 31 * result + (headId != null ? headId.hashCode() : 0);
        result = 31 * result + senId.hashCode();
        return result;
    }

    public List<Word> asWordList(Text text){
        Sentence s = text.getSentence(senId);
        List<Word> arr = new ArrayList<>();
        int startID = s.indexOf(s.getWord(startId));
        int endID = s.indexOf(s.getWord(endId));


        for (int i = startID; i<= endID; i++)
            arr.add(s.get(i));

        return arr;

    }

    public List<String> asStringList(Text text){
        Sentence s = text.getSentence(senId);
        List<String> arr = new ArrayList<>();
        int startID = s.indexOf(s.getWord(startId));
        int endID = s.indexOf(s.getWord(endId));


        for (int i = startID; i<= endID; i++)
            arr.add(s.get(i).getStr());

        return arr;

    }

    public List<String> asStringLemmaList(Text text){
        Sentence s = text.getSentence(senId);
        List<String> arr = new ArrayList<>();
        int startID = s.indexOf(s.getWord(startId));
        int endID = s.indexOf(s.getWord(endId));


        for (int i = startID; i<= endID; i++)
            arr.add(s.get(i).getLemma());

        return arr;

    }

    public List<String> asStringWordList(Text text){
        Sentence s = text.getSentence(senId);
        List<String> arr = new ArrayList<>();
        int startID = s.indexOf(s.getWord(startId));
        int endID = s.indexOf(s.getWord(endId));


        for (int i = startID; i<= endID; i++)
            arr.add(s.get(i).getStr());

        return arr;

    }

    public Word getHeadAsWord(Text text){
        Sentence s = text.getSentence(senId);
        return s.getWord(headId);

    }


    // this func is for PG MUSE
    public List<Mention> getDetailedMention(Text text){
        Sentence s = text.getSentence(senId);
        List<Mention> arr = new ArrayList<>();
        Word h = s.getWord(headId);
      //  if (h.getPos().startsWith("N")){  // if mention is a noun phrase
            int position = s.indexOf(h);
            if (position <s.size()-2)
             if (s.get(position+1).getStr().equals("-LRB-")){
              //   List<Word> temp = new ArrayList<>();
                 int startID = position+2;
                 int endID = -1;
                 for (int i = position+2; i<s.size(); i++) {
                     if (s.get(i).getStr().equals("-RRB-")) {
                         endID = i;
                         break;
                     }

                 }

                 for (Mention m : text.getMentions()){
                     Sentence ss = text.getSentence(m.senId);
                     int startId= ss.indexOf(ss.getWord(m.startId));
                     int endId= ss.indexOf(ss.getWord(m.endId));
                     if (startId >=startID && endId <=endID && m.senId.equals(senId))
                         arr.add(m);
                 }
                 Mention mm = new Mention(s.get(startID).getId(), s.get(endID).getId(),s.get(startID).getId(),s.getId());

                 String tt = s.getText().addMentionWithIndexing(mm);
                 if (!arr.contains(tt))
                     arr.add(mm);

             }
   //     }

         // if mention is a noun phrase
            position = s.indexOf(s.getWord(endId));
            if (position <s.size()-2)
                if (s.get(position+1).getStr().equals(":")){
                    //   List<Word> temp = new ArrayList<>();
                    int startID = position+2;
                    int endID = -1;
                    for (int i = position+2; i<s.size(); i++) {
                        if (s.get(i).getStr().equals("etc.")) {
                            endID = i;
                            break;
                        }

                    }

                    for (Mention m : text.getMentions()){
                        Sentence ss = text.getSentence(m.senId);
                        int startId= ss.indexOf(ss.getWord(m.startId));
                        int endId= ss.indexOf(ss.getWord(m.endId));
                        if (startId >=startID && endId <=endID && m.senId.equals(senId))
                            arr.add(m);
                    }
                    Mention mm = new Mention(s.get(startID).getId(), s.get(endID).getId(),s.get(startID).getId(),s.getId());

                    String tt = s.getText().addMentionWithIndexing(mm);
                    if (!arr.contains(tt))
                        arr.add(mm);

                }


        return arr;
    }

    public String toString(Text txt){
        StringBuilder sb=new StringBuilder();
        List<Word> words = asWordList(txt);
        words.forEach(w -> sb.append(w.getStr().toLowerCase() + " "));
        return sb.toString().trim();
    }
}
