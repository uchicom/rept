// (c) 2017 uchicom
package com.uchicom.rept;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class PageForm extends FooterForm {
	private static Font fnt = new Font("Helvetica", Font.PLAIN, 10);
	private int max;
	/**
	 * @param rectangle
	 */
	public PageForm(Rectangle rectangle, int max) {
		super(rectangle);
		this.max = max;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		super.print(graphics, pageFormat, pageIndex);
		graphics.setColor(Color.BLACK);
		graphics.setFont(fnt);
		System.out.println(((Graphics2D)graphics).getRenderingHint(	RenderingHints.KEY_COLOR_RENDERING));
		System.out.println("Color.RGB" + ColorSpace.TYPE_RGB);
		System.out.println("Color.GRAY" + ColorSpace.TYPE_GRAY);
		FontMetrics fm = graphics.getFontMetrics(fnt);
		String pageString = pageIndex + "/" + max;
		int length = fm.stringWidth(pageString);
		graphics.drawString(pageString, rectangle.x + (rectangle.width - length) /2, rectangle.y + rectangle.height / 2);
	
		System.out.println("c");
		return 0;
	}
}
