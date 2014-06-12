package com.bryantravissmith.notetaker.data;
import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteItem {
	private String key;
	private String text;
	public final String KEY = "key";
	public final String TEXT = "text";
	
	@SuppressLint("SimpleDateFormat")
	public static NoteItem getNew(){
		Locale locale = new Locale("en_US");
		Locale.setDefault(locale);
		
		String pattern = "yyyy-MM-dd  HH:mm:ss Z";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String key = formatter.format(new Date());
		
		NoteItem newNote = new NoteItem();
		newNote.setKey(key);
		newNote.setText("");
		return newNote;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString(){
		return this.getText();
	}
	
}
