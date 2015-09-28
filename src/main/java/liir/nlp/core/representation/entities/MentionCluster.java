package liir.nlp.core.representation.entities;

import liir.nlp.core.representation.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by quynhdo on 02/09/15.
 */
public class MentionCluster {

    ArrayList<String> mentionIds;

    public MentionCluster(ArrayList<String> mentionIds) {
        this.mentionIds = mentionIds;
    }

    public MentionCluster(){
        this.mentionIds = new ArrayList<>();
    }

    public void addMention(String mid){
        if (!mentionIds.contains(mid)) {
            mentionIds.add(mid);
            Collections.sort(mentionIds);
        }

    }
    public String toXMLString(){
        StringBuilder sb=new StringBuilder();

        if (mentionIds.size()!=0){
            sb.append("<chain>\n");
            for (String mid : mentionIds)
                sb.append("\t<mId>" + mid + "</mId>\n");
            sb.append("</chain>\n");
        }

        return  sb.toString();
    }

    public List<Mention> inSameChainWith(String id, Text txt){
        List<Mention> arr = new ArrayList<>();
        if (mentionIds.contains(id)) {
            for (String idx : mentionIds) {
                if (!id.equals(idx)) {
                    arr.add(txt.getMention(idx));

                }
            }
        }
            return arr;
    }
}
