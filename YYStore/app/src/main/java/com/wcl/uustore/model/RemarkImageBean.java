package com.wcl.uustore.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RemarkImageBean implements Parcelable {
	private String imageUrl;
	public static final Creator<RemarkImageBean> CREATOR = new Creator<RemarkImageBean>() {
		@Override
		public RemarkImageBean[] newArray(int size) {
			return null;
		}

		@Override
		public RemarkImageBean createFromParcel(Parcel source) {
			RemarkImageBean result = new RemarkImageBean();
			result.imageUrl = source.readString();
			return result;
		}
	};

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(imageUrl);
	}
}
