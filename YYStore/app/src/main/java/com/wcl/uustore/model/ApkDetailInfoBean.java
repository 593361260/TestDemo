package com.wcl.uustore.model;

import android.content.pm.ProviderInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.Arrays;

public class ApkDetailInfoBean implements Serializable{

	private int id;
	private String appName;
	private String slogan;
	private String sloganColor;
	private String isOffical;
	private String hasAdvart;
	private String isSafe;
	private String tagName;
	private String typeName;
	private String aboutApp;
	private String aboutImgUrl;
	private String aboutAppDetail;
	private String iconUrl;
	private String color;
	private String apkUrl;
	private String packageName;
	private int versionCode;
	private String versionName;
	private String versionTime;
	private String downloaded;
	private String sourceId;
	private int size;
	
	private String type;
	private String versionInfo;
	private String remark;
	private int minSdkVersion;
	private String permission;
	private Drawable appIcon = null;// 图标
	private long lastInstal;// 最后一次安装时间
	private ProviderInfo[] provider;// 供应商
	private String installPath;// 安装路径
	boolean isNeccesary = false;
	private String time;
	private Bitmap iconBitmap;
	private int states;//0:更新，1：忽略
	private String adremark;//app广告语
	private String apkmd5;
	private String fileCode;
	private String signature;
	//private String lastUpdateTime;
	private long lastModifiedTime; //本地应用更新日期
	
	private String developer;//开发商
	private String language;//语言
	//private String serLastUpdateTime;//服务最新应用更新日期
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getDeveloper() {
		return developer;
	}
//	public String getSerLastUpdateTime() {
//		return serLastUpdateTime;
//	}
//	public void setSerLastUpdateTime(String serLastUpdateTime) {
//		this.serLastUpdateTime = serLastUpdateTime;
//	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
//	public String getLastUpdateTime() {
//		return lastUpdateTime;
//	}
//	public void setLastUpdateTime(String lastUpdateTime) {
//		this.lastUpdateTime = lastUpdateTime;
//	}
	public long getLastModifiedTime() {
		return lastModifiedTime;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public void setLastModifiedTime(long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getAboutApp() {
		return aboutApp;
	}
	public void setAboutApp(String aboutApp) {
		this.aboutApp = aboutApp;
	}
	public String getAboutImgUrl() {
		return aboutImgUrl;
	}
	public void setAboutImgUrl(String aboutImgUrl) {
		this.aboutImgUrl = aboutImgUrl;
	}
	public String getAboutAppDetail() {
		return aboutAppDetail;
	}
	public void setAboutAppDetail(String aboutAppDetail) {
		this.aboutAppDetail = aboutAppDetail;
	}
	public String getAdremark() {
		return adremark;
	}
	public void setAdremark(String adremark) {
		this.adremark = adremark;
	}
	public String getApkmd5() {
		return apkmd5;
	}
	public void setApkmd5(String apkmd5) {
		this.apkmd5 = apkmd5;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public int getStates() {
		return states;
	}
	public void setStates(int states) {
		this.states = states;
	}
	public Bitmap getIconBitmap() {
		return iconBitmap;
	}
	public void setIconBitmap(Bitmap iconBitmap) {
		this.iconBitmap = iconBitmap;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public boolean isNeccesary() {
		return isNeccesary;
	}
	public void setNeccesary(boolean isNeccesary) {
		this.isNeccesary = isNeccesary;
	}
	
	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	
	public long getLastInstal() {
		return lastInstal;
	}

	public void setLastInstal(long lastInstal) {
		this.lastInstal = lastInstal;
	}

	public ProviderInfo[] getProvider() {
		return provider;
	}

	public void setProvider(ProviderInfo[] provider) {
		this.provider = provider;
	}

	public String getInstallPath() {
		return installPath;
	}

	public void setInstallPath(String installPath) {
		this.installPath = installPath;
	}

	public int getMinSdkVersion(){
		return minSdkVersion;
	}
	
	public void setMinSdkVersion(int minSdkVersion){
		this.minSdkVersion = minSdkVersion;
	}
	
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public String getSloganColor() {
		return sloganColor;
	}
	public void setSloganColor(String sloganColor) {
		this.sloganColor = sloganColor;
	}
	public String getIsOffical() {
		return isOffical;
	}
	public void setIsOffical(String isOffical) {
		this.isOffical = isOffical;
	}
	public String getHasAdvart() {
		return hasAdvart;
	}
	public void setHasAdvart(String hasAdvart) {
		this.hasAdvart = hasAdvart;
	}
	public String getIsSafe() {
		return isSafe;
	}
	public void setIsSafe(String isSafe) {
		this.isSafe = isSafe;
	}
 
//	public String getIconUrl() {
//		return iconUrl;
//	}
//	public void setIconUrl(String iconUrl) {
//		this.iconUrl = iconUrl;
//	}
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}
	public String getDownloaded() {
		return downloaded;
	}
	public void setDownloaded(String downloaded) {
		this.downloaded = downloaded;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersionInfo() {
		return versionInfo;
	}
	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Override
	public String toString() {
		return "ApkDetailInfoBean{" +
				"id=" + id +
				", appName='" + appName + '\'' +
				", slogan='" + slogan + '\'' +
				", sloganColor='" + sloganColor + '\'' +
				", isOffical='" + isOffical + '\'' +
				", hasAdvart='" + hasAdvart + '\'' +
				", isSafe='" + isSafe + '\'' +
				", tagName='" + tagName + '\'' +
				", typeName='" + typeName + '\'' +
				", aboutApp='" + aboutApp + '\'' +
				", aboutImgUrl='" + aboutImgUrl + '\'' +
				", aboutAppDetail='" + aboutAppDetail + '\'' +
				", iconUrl='" + iconUrl + '\'' +
				", color='" + color + '\'' +
				", apkUrl='" + apkUrl + '\'' +
				", packageName='" + packageName + '\'' +
				", versionCode=" + versionCode +
				", versionName='" + versionName + '\'' +
				", versionTime='" + versionTime + '\'' +
				", downloaded='" + downloaded + '\'' +
				", sourceId='" + sourceId + '\'' +
				", size=" + size +
				", type='" + type + '\'' +
				", versionInfo='" + versionInfo + '\'' +
				", remark='" + remark + '\'' +
				", minSdkVersion=" + minSdkVersion +
				", permission='" + permission + '\'' +
				", appIcon=" + appIcon +
				", lastInstal=" + lastInstal +
				", provider=" + Arrays.toString(provider) +
				", installPath='" + installPath + '\'' +
				", isNeccesary=" + isNeccesary +
				", time='" + time + '\'' +
				", iconBitmap=" + iconBitmap +
				", states=" + states +
				", adremark='" + adremark + '\'' +
				", apkmd5='" + apkmd5 + '\'' +
				", fileCode='" + fileCode + '\'' +
				", signature='" + signature + '\'' +
				", lastModifiedTime=" + lastModifiedTime +
				", developer='" + developer + '\'' +
				", language='" + language + '\'' +
				'}';
	}
}
