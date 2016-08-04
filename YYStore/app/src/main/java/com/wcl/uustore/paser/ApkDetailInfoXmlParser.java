package com.wcl.uustore.paser;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.wcl.uustore.model.ApkDetailInfoBean;
import com.wcl.uustore.model.OtherApkLinkBean;
import com.wcl.uustore.model.RemarkImageBean;
import com.wcl.uustore.tool.AppTools;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApkDetailInfoXmlParser {
	static final String TAG = "XMLPaser";
	private static final String ns = null;
	private Context context;

	public ApkDetailInfoXmlParser(Context context) {
		this.context = context;
	}

	public HashMap<String, Object> parse(String xmlStr) {
		// if(xmlStr != null){
		// ,
		// }
		HashMap<String, Object> map = null;

		ArrayList<RemarkImageBean> rmkImageList = null;
		List<OtherApkLinkBean> otherLinkBeanList = new ArrayList<OtherApkLinkBean>();

		ApkDetailInfoBean detailInfoBean = null;
		OtherApkLinkBean otLinkBean = null;
		String[] tmpRmkImage = new String[3];
		InputStream stream = null;
		// get xml parser
		XmlPullParser xmlParse = Xml.newPullParser();
		try {
			// get file stream and set encoding
			stream = new ByteArrayInputStream(xmlStr.getBytes());// context.getAssets().open("soft_detail_info.xml");//
			xmlParse.setInput(stream, "utf-8");
			// get event type
			int evnType = xmlParse.getEventType();
			// continue to end document
			while (evnType != XmlPullParser.END_DOCUMENT) {
				switch (evnType) {
				case XmlPullParser.START_TAG:
					String tag = xmlParse.getName();
					if (tag.equalsIgnoreCase("app")) {
						detailInfoBean = new ApkDetailInfoBean();
					} else if (tag.equalsIgnoreCase("remarkimages")) {
						rmkImageList = new ArrayList<RemarkImageBean>();
					} else if (tag.equalsIgnoreCase("otherapk")) {
						otLinkBean = new OtherApkLinkBean();
					} else if (detailInfoBean != null) {
						// 软件信息解析
						// parse after tag
						if (tag.equalsIgnoreCase("id")) {
							detailInfoBean.setId(Integer.parseInt(xmlParse.nextText()));
						} else if (tag.equalsIgnoreCase("appname")) {
							detailInfoBean.setAppName(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("slogan")) {
							detailInfoBean.setSlogan(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("slogancolor")) {
							detailInfoBean.setSloganColor(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("isoffical")) {
							detailInfoBean.setIsOffical(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("hasadvart")) {
							detailInfoBean.setHasAdvart(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("issafe")) {
							detailInfoBean.setIsSafe(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("iconurl")) {
							detailInfoBean.setIconUrl(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("apkurl")) {
							detailInfoBean.setApkUrl(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("versioncode")) {
							detailInfoBean.setVersionCode(AppTools.objToInt(xmlParse.nextText()));
						} else if (tag.equalsIgnoreCase("versionname")) {
							detailInfoBean.setVersionName(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("versiontime")) {
							detailInfoBean.setVersionTime(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("downloaded")) {
							detailInfoBean.setDownloaded(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("sourceid")) {
							detailInfoBean.setSourceId(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("size")) {
							detailInfoBean.setSize(AppTools.objToInt(xmlParse.nextText()));
						} else if (tag.equalsIgnoreCase("type")) {
							detailInfoBean.setType(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("versioninfo")) {
							detailInfoBean.setVersionInfo(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("remark")) {
							detailInfoBean.setRemark(xmlParse.nextText());
						} else if ("packagename".equalsIgnoreCase(tag)) {
							detailInfoBean.setPackageName(xmlParse.nextText());
						} else if ("minSdkVersion".equalsIgnoreCase(tag)) {
							detailInfoBean.setMinSdkVersion(AppTools.objToInt(xmlParse.nextText()));
						} else if ("permission".equalsIgnoreCase(tag)) {
							detailInfoBean.setPermission(xmlParse.nextText());
						} else if ("adremark".equalsIgnoreCase(tag)) {
							detailInfoBean.setAdremark(xmlParse.nextText());
						} else if ("apkmd5".equalsIgnoreCase(tag)) {
							detailInfoBean.setApkmd5(xmlParse.nextText());
						} else if ("signature".equalsIgnoreCase(tag)) {
							detailInfoBean.setSignature(xmlParse.nextText());
						} else if ("publishname".equalsIgnoreCase(tag)) {
							detailInfoBean.setDeveloper(xmlParse.nextText());
						} else if ("language".equalsIgnoreCase(tag)) {
							detailInfoBean.setLanguage(xmlParse.nextText());
						}else if("fileCode".equalsIgnoreCase(tag)){
							detailInfoBean.setFileCode(xmlParse.nextText());
						}

					} else if (rmkImageList != null) {
						if (tag.equalsIgnoreCase("imageurl_l")) {
							tmpRmkImage[0] = xmlParse.nextText();
						} else if (tag.equalsIgnoreCase("imageurl_m")) {
							tmpRmkImage[1] = xmlParse.nextText();
						} else if (tag.equalsIgnoreCase("imageurl_h")) {
							tmpRmkImage[2] = xmlParse.nextText();
						}

					} else if (otLinkBean != null) {
						// 其他浏览的apk链接信息解析
						if (tag.equalsIgnoreCase("apkid")) {
							otLinkBean.setApkId((Integer.parseInt(xmlParse.nextText())));
						} else if (tag.equalsIgnoreCase("apkname")) {
							otLinkBean.setApkName(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("imageurl")) {
							otLinkBean.setImageUrl(xmlParse.nextText());
						} else if (tag.equalsIgnoreCase("iconurl")) {
							otLinkBean.setIconUrl(xmlParse.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if (xmlParse.getName().equalsIgnoreCase("app") && detailInfoBean != null) {
						map = new HashMap<String, Object>();
						map.put("app", detailInfoBean);
						detailInfoBean = null;
					} else if (xmlParse.getName().equalsIgnoreCase("remarkimages") && rmkImageList != null) {
						for (int j = 0; j < tmpRmkImage.length; j++) {
							String imageUrl = tmpRmkImage[j];
							if (imageUrl != null) {
								String[] imageUrls = imageUrl.split(",");
								ArrayList<RemarkImageBean> rmkImageList1 = new ArrayList<RemarkImageBean>();
								for (int i = 0; i < imageUrls.length; i++) {
									RemarkImageBean rkImageBean = new RemarkImageBean();
									rkImageBean.setImageUrl(imageUrls[i]);
									rmkImageList1.add(rkImageBean);
								}

								switch (j) {
								case 0:
									// 低分辨率
									map.put("remarkimages_l", rmkImageList1);
									break;
								case 1:
									// 中分辨率
									map.put("remarkimages_m", rmkImageList1);
									break;
								case 2:
									map.put("remarkimages_h", rmkImageList1);
									// 高分辨率
									break;
								}

							}
						}

						rmkImageList = null;
					} else if (xmlParse.getName().equalsIgnoreCase("otherapk") && otLinkBean != null) {
						otherLinkBeanList.add(otLinkBean);
						otLinkBean = null;
					}
					break;
				default:
					break;
				}
				evnType = xmlParse.next();
			}

			/*
			 * if (map == null) { map = new HashMap<String, Object>();
			 * 
			 * } map.put("otherapks", otherLinkBeanList);
			 */
			if (map != null) {
				map.put("otherapks", otherLinkBeanList);
			}

			// map.put("remarkimages", rmkImageList);

			// rntList.add(map);

		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
		return map;
	}
}
