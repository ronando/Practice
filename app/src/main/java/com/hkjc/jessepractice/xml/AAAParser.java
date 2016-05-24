package com.hkjc.jessepractice.xml;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jesse on 5/11/15.
 */
public class AAAParser {
    XmlPullParser xmlParser;
    private AAA data;
    private List<BBB> bbbs = new ArrayList<BBB>();

    public AAAParser() throws XmlPullParserException {
        xmlParser = XmlPullParserFactory.newInstance().newPullParser();
    }


    public Object exeuteParser(final InputStream is) throws Exception {
        xmlParser.setInput(is, "utf-8");
        BBB bbb = null;
        String name;
        int eventType = xmlParser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            switch(eventType){
                case XmlPullParser.START_TAG:
                    name = xmlParser.getName();
                    if("AAA".equals(name)){
                        data = parseA(xmlParser);
                    }else if("BBB".equals(name)){
                        bbb = parseB(xmlParser);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = xmlParser.getName();
                    if("BBB".equals(name)){
                        bbbs.add(bbb);
                    }
                    break;
            }
            eventType = xmlParser.next();
        }
        return data;
    }


    public AAA getData() {
        return data;
    }

    private AAA parseA(XmlPullParser xmlParser){

        AAA result = new AAA();
        result.setId(Integer.valueOf(xmlParser.getAttributeValue(null, "id")));
        result.setName(xmlParser.getAttributeValue(null, "name"));
        result.setBb(bbbs);
        return result;
    }

    private BBB parseB(XmlPullParser xmlParser) throws Exception {
        BBB result = new BBB();
        result.setId(Integer.valueOf(xmlParser.getAttributeValue(null,"id")));
        result.setName(xmlParser.getAttributeValue(null, "name"));
        xmlParser.next();
        result.setContent(xmlParser.getText());
        return result;
    }






}
