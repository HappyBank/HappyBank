package com.happybank.model;

public class SoftwareInfo {
	private int theme;
	public String edition = "V1.0";
	private int soupStart;
	private int soupEnd;
	
	public int getTheme() {
		return theme;
	}
	public void setTheme(int theme) {
		this.theme = theme;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public int getSoupStart() {
		return soupStart;
	}
	public void setSoupStart(int soupStart) {
		this.soupStart = soupStart;
	}
	public int getSoupEnd() {
		return soupEnd;
	}
	public void setSoupEnd(int soupEnd) {
		this.soupEnd = soupEnd;
	}
	
}
