package com.ereader.client.entities;

import java.io.Serializable;

public class BookExtra implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/*	 "author": "神笔马良",
     "author_info": "从前，有个孩子名字叫马良。",
     "contents": "这里是目录",
     "edition": 1,
     "language": "中文",
     "press": "清华大学出版社",
     "pubdate": "2015年6月26日15:47:53",
     "summary": "从前，有个孩子名字叫马良。父亲母亲早就死了，靠他自己打柴、割草过日子。他从小喜欢学画，可是，他连一支笔也没有啊！ 一天，他走过一个学馆门口，看见学馆里的教师，拿着一支笔，正在画画。”",
     "wordcount": 1000*/
	private String author;
	private String author_info;
	private String contents;
	private String edition;
	private String language;
	private String press;
	private String pubdate;
	private String summary;
	private String wordcount;
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the author_info
	 */
	public String getAuthor_info() {
		return author_info;
	}
	/**
	 * @param author_info the author_info to set
	 */
	public void setAuthor_info(String author_info) {
		this.author_info = author_info;
	}
	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return the edition
	 */
	public String getEdition() {
		return edition;
	}
	/**
	 * @param edition the edition to set
	 */
	public void setEdition(String edition) {
		this.edition = edition;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the press
	 */
	public String getPress() {
		return press;
	}
	/**
	 * @param press the press to set
	 */
	public void setPress(String press) {
		this.press = press;
	}
	/**
	 * @return the pubdate
	 */
	public String getPubdate() {
		return pubdate;
	}
	/**
	 * @param pubdate the pubdate to set
	 */
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the wordcount
	 */
	public String getWordcount() {
		return wordcount;
	}
	/**
	 * @param wordcount the wordcount to set
	 */
	public void setWordcount(String wordcount) {
		this.wordcount = wordcount;
	}
}
