package liir.nlp.core.representation.io;

import liir.nlp.core.representation.*;
import liir.nlp.core.representation.entities.Mention;
import liir.nlp.core.representation.entities.MentionCluster;
import liir.nlp.core.representation.srl.Predicate;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.joox.JOOX.$;

/**
 * Created by quynhdo on 31/08/15.
 */
public class XMLReader {
    public static String toXMLString(List<Text> corpus){
        StringBuilder sb= new StringBuilder();
        sb.append("<musecorpus>");

        return sb.toString();
    }
    public static List<Text> readCorpus(String xmlContent) throws IOException, SAXException {
        xmlContent = "<musecorpus>" + xmlContent + "</musecorpus>";
        StringReader sr = new StringReader( xmlContent );

        List<Text> arr= new ArrayList<>();
        $(sr)
                .find("text")
                .each(ctx -> {
                    Text text = new Text();
                    //read sentences
                    $(ctx).find("s").each(stx -> {
                        Sentence se = new Sentence();
                        if ($(stx).attr("id") != null)
                            se.setId($(stx).attr("id"));


                        $(stx).find("str").each(str -> {

                            se.setStr($(str).text());
                        });

                        if ($(stx).attr("str") != null)
                            se.setStr($(stx).attr("str"));

                        $(stx).find("w").each(
                                wtx -> {
                                    Word w = new Word($(wtx).attr("id"), $(wtx).attr("str"), $(wtx).attr("lemma"), $(wtx).attr("pos"), $(wtx).attr("head"), $(wtx).attr("deprel"), se);

                                    //check parsebit
                                    if ($(wtx).attr("parseBit") != null) {
                                        w.addFeature("parseBit", $(wtx).attr("parseBit"));
                                    }

                                    se.add(w);

                                }


                        );

                        text.add(se);

                    });


                    //read mentions

                    //read coref results
                    HashMap<String, String> mIdMapping = new HashMap<String, String>();

                    $(ctx).find("mention").each(mtx -> {

                        String senId = $(mtx).attr("senId");

                        Sentence s = text.getSentence(senId);

                        Mention m = new Mention($(mtx).attr("beginWId"), $(mtx).attr("endWId"), $(mtx).attr("headWId"), s.getId());

                        String newId = text.addMentionWithIndexing(m);
                        mIdMapping.put($(mtx).attr("id"), newId);

                    });

                    $(ctx).find("chain").each(ch -> {

                        MentionCluster mc = new MentionCluster();

                        $(ch).find("mId").each(mm -> {
                          mc.addMention($(mm).text());


                        });
                        text.addCorefCluster(mc);


                    });



                    //read srl results

                    $(ctx).find("frame").each(stx -> {

                        String senId = $(stx).attr("senId");

                        Sentence s = text.getSentence(senId);
                        $(stx).find("pred").each(pred -> {
                            String pId = $(pred).attr("wId");
                            Word wp = s.getWord(pId);
                            Predicate p = new Predicate(wp);
                            s.set(s.indexOf(wp), p);


                            $(stx).find("argument").each(arg -> {
                                String argId1 = $(arg).attr("beginWId");
                                String argId2 = $(arg).attr("endWId");
                                String argId3 = $(arg).attr("headWId");
                                Mention m = new Mention(argId1, argId2, argId3, s.getId());

                                String newId = text.addMentionWithIndexing(m);



                                p.addArgument(newId, $(arg).attr("label"));
                            });

                            p.setSense($(stx).attr("sense"));

                        });


                    });


                    $(ctx).find("paragraph").each(ptx -> {

                        Paragraph p = new Paragraph(  Integer.parseInt($(ptx).attr("beginSenId")), Integer.parseInt($(ptx).attr("endSenId")) );
                        text.addParagraph(p);

                    });

                    $(ctx).find("discourseRel").each(ptx -> {

                        DiscourseRelation dr = new DiscourseRelation(Integer.parseInt($(ptx).attr("firstId")), Integer.parseInt($(ptx).attr("secondId")),$(ptx).attr("label"));
                                text.addDiscourseRel(dr);

                    });


                    arr.add(text);

                });
    return  arr;

    }


}
