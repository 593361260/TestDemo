package com.wcl.uustore.paser;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.wcl.uustore.model.ApkDetailInfoBean;
import com.wcl.uustore.tool.AppTools;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ApkDetailXmlParser {
    static final String TAG = "XMLPaser";
    private Context context;

    public ApkDetailXmlParser(Context context) {
        this.context = context;
    }

    public List<ApkDetailInfoBean> parse(String xmlStr) {
        List<ApkDetailInfoBean> recommendList = null;
        ApkDetailInfoBean bean = null;
        InputStream stream = null;
        // get xml parser
        XmlPullParser xmlParse = Xml.newPullParser();
        try {
            // get file stream and set encoding
            stream = new ByteArrayInputStream(xmlStr.getBytes());//context.getAssets().open("home.xml");//
            xmlParse.setInput(stream, "utf-8");
            // get event type
            int evnType = xmlParse.getEventType();
            // continue to end document
            while (evnType != XmlPullParser.END_DOCUMENT) {
                switch (evnType) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlParse.getName();
                        if (tag.equalsIgnoreCase("applist")) {
                            recommendList = new ArrayList<ApkDetailInfoBean>();
                        } else if (tag.equalsIgnoreCase("app")) {
                            bean = new ApkDetailInfoBean();
                        }
                        if (bean != null) {
                            if (tag.equalsIgnoreCase("id")) {
                                bean.setId(Integer.parseInt(xmlParse.nextText()));
                            } else if (tag.equalsIgnoreCase("about")) {
                                bean.setAboutApp(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("color")) {
                                bean.setColor(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("todayurl")) {
                                bean.setAboutImgUrl(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("description")) {
                                bean.setAboutAppDetail(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("name")) {
                                bean.setAppName(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("slogan")) {
                                bean.setSlogan(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("slogancolor")) {
                                bean.setSloganColor(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("iconurl")) {
                                bean.setIconUrl(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("apkurl")) {
                                bean.setApkUrl(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("versioncode")) {
                                bean.setVersionCode(AppTools.objToInt(xmlParse.nextText()));
                            } else if (tag.equalsIgnoreCase("versionname")) {
                                bean.setVersionName(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("packagename")) {
                                bean.setPackageName(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("downloaded")) {
                                bean.setDownloaded(xmlParse.nextText());
                            } else if (tag.equalsIgnoreCase("size")) {
                                bean.setSize(AppTools.objToInt(xmlParse.nextText()));
                            } else if ("minSdkVersion".equalsIgnoreCase(tag)) {
                                bean.setMinSdkVersion(AppTools.objToInt(xmlParse.nextText()));
                            } else if ("adremark".equalsIgnoreCase(tag)) {
                                bean.setAdremark(xmlParse.nextText());
                            } else if ("apkmd5".equalsIgnoreCase(tag)) {
                                bean.setApkmd5(xmlParse.nextText());
                            } else if ("signature".equalsIgnoreCase(tag)) {
                                bean.setSignature(xmlParse.nextText());
                            } else if ("publishname".equalsIgnoreCase(tag)) {
                                bean.setDeveloper(xmlParse.nextText());
                            } else if ("language".equalsIgnoreCase(tag)) {
                                bean.setLanguage(xmlParse.nextText());
                            } else if ("typename".equalsIgnoreCase(tag)) {
                                bean.setTypeName(xmlParse.nextText());
                            } else if ("filecode".equalsIgnoreCase(tag)) {
                                bean.setFileCode(xmlParse.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlParse.getName().equalsIgnoreCase("app") && bean != null) {
                            recommendList.add(bean);
                            bean = null;
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
        return recommendList;
    }
}
