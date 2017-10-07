// (c) 2017 uchicom
package com.uchciom.rept;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class ContentForm extends RectangleForm implements Printable {

	/**
	 * @param rectangle
	 */
	public ContentForm(Rectangle rectangle) {
		super(rectangle);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see java.awt.print.Printable#print(java.awt.Graphics,
	 * java.awt.print.PageFormat, int)
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
