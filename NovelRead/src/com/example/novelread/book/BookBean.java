package com.example.novelread.book;

import java.util.ArrayList;
import java.util.List;



public class BookBean {
	private String contentStr = "";
	private String bookName = "";
	private String bookAuthor = "";
	private String bookDesc = "";
	private String bookConvreImageUrl = "";
	private int wordCount = 0;
	private int chapterCount = 0;
	private List<ChapterBean> chapterList = new ArrayList<ChapterBean>();
	public String getContentStr() {
		return contentStr;
	}
	public void setContentStr(String contentStr) {
		this.contentStr = contentStr;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public String getBookConvreImageUrl() {
		return bookConvreImageUrl;
	}
	public void setBookConvreImageUrl(String bookConvreImageUrl) {
		this.bookConvreImageUrl = bookConvreImageUrl;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public int getChapterCount() {
		return chapterCount;
	}
	public void setChapterCount(int chapterCount) {
		this.chapterCount = chapterCount;
	}
	public List<ChapterBean> getChapterList() {
		return chapterList;
	}
	public void setChapterList(List<ChapterBean> chapterList) {
		this.chapterList = chapterList;
	}
	@Override
	public String toString() {
		return "BookBean [contentStr=" + contentStr + ", bookName=" + bookName
				+ ", bookAuthor=" + bookAuthor + ", bookConvreImageUrl="
				+ bookConvreImageUrl + ", wordCount=" + wordCount
				+ ", chapterCount=" + chapterCount + ", chapterList="
				+ chapterList + "]";
	}
}
