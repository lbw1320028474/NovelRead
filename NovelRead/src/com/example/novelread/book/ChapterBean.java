package com.example.novelread.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

public class ChapterBean {
	private String chapterName = "";		//章节名
	private int chapterOrder = 0;		//章节位置
	private String content = "";			//总文字
	private String oneLineContent = "";		//无空格无换行的章节内容
	private List<String> sectionList = null;		//段落列表
	private Map<Integer, Integer> sectionIndex = null;		//段落索引
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content.replace("\r", "");
		this.sectionList = analySectionList();
	}

	public List<String> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<String> sectionList) {
		this.sectionList = sectionList;
		for (String str: sectionList){
			content = content + str + "\n";
		}
	}

	private List<String>analySectionList() {
		// TODO Auto-generated method stub
		if(TextUtils.isEmpty(this.content)){
			return null;
		}
		List<String> sectionList = new ArrayList<String>();
		String[] sectionArray = this.content.split("\n");
		if(sectionList != null && sectionArray.length > 0){
			for (String ss: sectionArray){
				sectionList.add(ss);
			}
			if(sectionList != null && sectionList.size() > 0){
				return sectionList;
			}
		}
		return null;
	}

	public int getChapterOrder() {
		return chapterOrder;
	}

	public void setChapterOrder(int chapterOrder) {
		this.chapterOrder = chapterOrder;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public Map<Integer, Integer> getSectionIndex() {
		return sectionIndex;
	}

	public void setSectionIndex(Map<Integer, Integer> sectionIndex) {
		this.sectionIndex = sectionIndex;
	}

	public String getOneLineContent() {
		return oneLineContent;
	}

	public void setOneLineContent(String oneLineContent) {
		this.oneLineContent = oneLineContent;
	}

	@Override
	public String toString() {
		return "ChapterBean [chapterName=" + chapterName + ", chapterOrder="
				+ chapterOrder + ", content=" + content + ", oneLineContent="
				+ oneLineContent + ", sectionIndex=" + sectionIndex + "]";
	}


	
	
}
