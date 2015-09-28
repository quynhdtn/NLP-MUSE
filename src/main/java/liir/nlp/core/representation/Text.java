package liir.nlp.core.representation;

import liir.nlp.core.representation.entities.Mention;
import liir.nlp.core.representation.entities.MentionCluster;
import liir.nlp.core.representation.srl.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by quynhdo on 28/08/15.
 */
public class Text extends ArrayList<Sentence> {
    String id = "";
    List<Mention> mentions = new ArrayList<>();
    List<MentionCluster> mentionClusters = new ArrayList<>();
    List<Paragraph> paragraphs;
    List<DiscourseRelation> discourseRels ;

    public Text(Collection<? extends Sentence> c) {
        super(c);
    }

    public Text() {
        super();

    }

    public String getId() {
        return id;
    }

    public Sentence getSentence(String sId) {

        for (Sentence s : this)
            if (s.getId().equals(sId))
                return s;
        return null;
    }

    public List<MentionCluster> getMentionClusters() {
        return mentionClusters;
    }

    public String toXMLString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<text id=\"" + id + "\"" + ">\n");
        this.forEach(s -> {
            sb.append(s.toXMLString());
            sb.append("\n");
        });


        if (mentions.size() != 0) {
            sb.append("<mentions>\n");
            for (Mention m : mentions)
                sb.append(m.toXMLString());


            sb.append("</mentions>");
        }

        if (mentionClusters.size() != 0) {
            sb.append("<chains>\n");
            for (MentionCluster mc : mentionClusters)
                sb.append(mc.toXMLString());


            sb.append("</chains>");
        }

        List<Predicate> preds = getPredicates();
        if (preds.size() != 0) {
            sb.append("<frames>\n");
            for (Predicate pred : preds)
                sb.append(pred.toPredicateXMLString());


            sb.append("</frames>\n");
        }
        if (paragraphs != null){
            sb.append("<paragraphs>\n");
            for (Paragraph p : paragraphs){
                sb.append(p.toXMLString());
            }
                sb.append("</paragraphs>\n");
        }

        if (discourseRels != null){
            sb.append("<discourseRels>\n");
            for (DiscourseRelation r : discourseRels){
                sb.append(r.toXMLString());
            }
            sb.append("</discourseRels>\n");
        }

        sb.append("</text>\n");
        return sb.toString();
    }

    public void setAutomaticIndexing() {

        for (Sentence s : this) {
            s.setId(String.valueOf(this.indexOf(s)));
            for (Word w : s)
                w.setId(String.valueOf(s.indexOf(w) + 1));
        }

    }

    public String addMention(Mention m) {
        for (Mention oldm : mentions) {
            if (oldm.equals(m))
                return oldm.getId();

        }
        mentions.add(m);
        return m.getId();

    }

    public Mention getMention(String mid) {
        for (Mention m : mentions) {
            if (m.getId().equals(mid))
                return m;

        }
        return null;
    }

    public String addMentionWithIndexing(Mention m) {
        m.setId(String.valueOf(mentions.size()));
        return this.addMention(m);

    }

    public void addCorefCluster(MentionCluster cl) {
        mentionClusters.add(cl);
    }

    public boolean add(Sentence s) {
        if (s.size()==0)
            return false;
        s.setText(this);
        return super.add(s);
    }

    public List<Predicate> getPredicates() {
        List<Predicate> preds = new ArrayList<>();

        for (Sentence s : this) {
            preds.addAll(s.getPredicates());
        }

        return preds;
    }

    public void addParagraph(Paragraph p){
        if (paragraphs == null)
            paragraphs = new ArrayList<>();
        paragraphs.add(p);
    }


    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void addDiscourseRel(DiscourseRelation dr){
        if (discourseRels == null)
            discourseRels = new ArrayList<>();
        discourseRels.add(dr);
    }


    public List<Mention> getMentions() {
        return mentions;
    }

    public List<DiscourseRelation> getDiscourseRels() {
        return discourseRels;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public void setDiscourseRels(List<DiscourseRelation> discourseRels) {
        this.discourseRels = discourseRels;
    }
}
