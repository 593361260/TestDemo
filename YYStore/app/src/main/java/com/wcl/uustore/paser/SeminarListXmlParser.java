package com.wcl.uustore.paser;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.wcl.uustore.model.SeminarInfoBean;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SeminarListXmlParser {
	static final String TAG = "XMLPaser";
	private static final String ns = null;
	private Context context;

	public SeminarListXmlParser(Context context) {
		this.context = context;
	}

	public List<SeminarInfoBean> parse(String xmlStr) {
//		if(xmlStr != null){
//			xmlStr = xmlStr.replace("&", "&amp;");
//		}
		List<SeminarInfoBean> seminarList = new ArrayList<SeminarInfoBean>();
		SeminarInfoBean seminarBean = null;
		InputStream stream = null;
		// get xml parser
		XmlPullParser xmlParse = Xml.newPullParser();
		try {
			// get file stream and set encoding
			stream = new ByteArrayInputStream(xmlStr.getBytes()); // context.getAssets().open("seminar_list_info.xml");//
			xmlParse.setInput(stream, "utf-8");
			// get event type
			int evnType = xmlParse.getEventType();
			// continue to end document
			while (evnType != XmlPullParser.END_DOCUMENT) {
				switch (evnType) {
				case XmlPullParser.START_TAG:
					String tag = xmlParse.getName();

					if (tag.equalsIgnoreCase("seminar")) {
						seminarBean = new SeminarInfoBean();
					} else if (seminarBean != null) {
						if (tag.equalsIgnoreCase("id")) {
							seminarBean.setId(Integer.parseInt(xmlParse.nextText()));
						} else if (tag.equalsIgnoreCase("sname")) {
							seminarBean.setName(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("remark")) {
							seminarBean.setSlogan(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("iconurl")) {
							seminarBean.setIconUrl(xmlParse.nextText());
						}else if (tag.equalsIgnoreCase("scount")) {
							seminarBean.setScount(Integer.parseInt(xmlParse
									.nextText()));
						} else if (tag.equalsIgnoreCase("namecolor")) {
							seminarBean.setNameColor(xmlParse.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if (xmlParse.getName().equalsIgnoreCase("seminar")
							&& seminarBean != null) {
						seminarList.add(seminarBean);
						seminarBean = null;
					}
					break;
				default:
					break;
				}
				evnType = xmlParse.next();
			}

		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		return seminarList;
	}
}
