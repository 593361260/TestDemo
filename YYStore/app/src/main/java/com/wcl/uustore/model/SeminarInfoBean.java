package com.wcl.uustore.model;

public class SeminarInfoBean {
	private int id;
	private String name;
	private String slogan;
	// private String imageUrl;
	private String nameColor;
	private String iconUrl;

	public String getIconUrl() {
		return iconUrl;
	}

	
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	private int scount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getNameColor() {
		return nameColor;
	}

	public void setNameColor(String nameColor) {
		this.nameColor = nameColor;
	}

	public int getScount() {
		return scount;
	}

	public void setScount(int scount) {
		this.scount = scount;
	}
	// public String getImageUrl() {
	// return imageUrl;
	// }
	// public void setImageUrl(String imageUrl) {
	// this.imageUrl = imageUrl;
	// }

}
