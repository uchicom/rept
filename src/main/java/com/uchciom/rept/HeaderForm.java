// (c) 2017 uchicom
package com.uchciom.rept;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class HeaderForm extends RectangleForm implements Printable {

	/**
	 * @param rectangle
	 */
	public HeaderForm(Rectangle rectangle) {
		super(rectangle);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see java.awt.print.Printable#print(java.awt.Graphics,
	 * java.awt.print.PageFormat, int)
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		graphics.setColor(Color.RED);
		graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		return 0;
	}

}
