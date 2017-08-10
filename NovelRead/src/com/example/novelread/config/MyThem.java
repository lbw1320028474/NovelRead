package com.example.novelread.config;

import android.graphics.Typeface;

import com.example.novelread.R;
import com.example.novelread.util.Gloable;

public class MyThem {
	private int textColor = Gloable.mApplicationContext.getResources().getColor(R.color.nomor_readPageBgColor);
	private int readPageBgColor = Gloable.mApplicationContext.getResources().getColor(R.color.nomor_readPageTextColor);
	private int textSize = 50;
	private float lineSpacForTextSize = 1.3f;		//行间距，相对于字体大小的倍数
	private float textSpace = 3; 		//字体间距
	private float textSpaceForTextSize = 3; 		//字体间距，相对于字体大小的倍数
	private int lineSpace = 70;		//行高
	private float paddingLeft = 50;
	private float paddingRight = 50;
	private float paddingTop = 50;
	private float paddingBottom = 50;
	private Typeface typeFace = null;		//字体

	public MyThem(){

	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public int getReadPageBgColor() {
		return readPageBgColor;
	}

	public void setReadPageBgColor(int readPageBgColor) {
		this.readPageBgColor = readPageBgColor;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public float getLineSpacForTextSize() {
		return lineSpacForTextSize;
	}

	public void setLineSpacForTextSize(float lineSpacForTextSize) {
		this.lineSpacForTextSize = lineSpacForTextSize;
		this.lineSpace = (int) (textSize * lineSpacForTextSize);
	}

	public float getTextSpace() {
		return textSpace;
	}

	public void setTextSpace(float textSpace) {
		this.textSpace = textSpace;
		this.textSpaceForTextSize = textSpace/textSize;
	}

	public float getTextSpaceForTextSize() {
		return textSpaceForTextSize;
	}

	public void setTextSpaceForTextSize(float textSpaceForTextSize) {
		this.textSpaceForTextSize = textSpaceForTextSize;
		this.textSpace = textSize * textSpaceForTextSize;
	}

	public int getLineSpace() {
		return lineSpace;
	}

	public void setLineSpace(int lineSpace) {
		if(textSize > 0){
			this.lineSpacForTextSize = lineSpace/textSize;
		}
		this.lineSpace = lineSpace;
	}

	public Typeface getTypeFace() {
		return typeFace;
	}

	public void setTypeFace(Typeface typeFace) {
		this.typeFace = typeFace;
	}

	public float getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(float paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public float getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(float paddingRight) {
		this.paddingRight = paddingRight;
	}

	public float getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(float paddingTop) {
		this.paddingTop = paddingTop;
	}

	public float getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(float paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	@Override
	public String toString() {
		return "MyThem [textColor=" + textColor + ", readPageBgColor="
				+ readPageBgColor + ", textSize=" + textSize
				+ ", lineSpacForTextSize=" + lineSpacForTextSize
				+ ", textSpace=" + textSpace + ", textSpaceForTextSize="
				+ textSpaceForTextSize + ", lineSpace=" + lineSpace
				+ ", paddingLeft=" + paddingLeft + ", paddingRight="
				+ paddingRight + ", paddingTop=" + paddingTop
				+ ", paddingBottom=" + paddingBottom + ", typeFace=" + typeFace
				+ "]";
	}



}
