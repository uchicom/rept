// (c) 2017 uchicom
package com.uchicom.rept;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class TitleForm extends HeaderForm {
	private static Font fnt = new Font("Helvetica", Font.PLAIN, 24);
	protected String title;
	protected Date date;

	/**
	 * @param rectangle
	 */
	public TitleForm(Rectangle rectangle, String title, Date date) {
		super(rectangle);
		this.title = title;
		this.date = date;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		super.print(graphics, pageFormat, pageIndex);
		graphics.setColor(Color.BLUE);
		graphics.setFont(fnt);
		FontMetrics fm = graphics.getFontMetrics(fnt);
		int length = fm.stringWidth(title);
		graphics.drawString(title, rectangle.x + (rectangle.width - length) /2, rectangle.y + rectangle.height / 2);
		//右側に日付を表示
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String formated = format.format(date);
		int dateLength = fm.stringWidth(formated);
		graphics.drawString(formated, rectangle.x + rectangle.width - dateLength - 50 , rectangle.y + fm.getHeight() * 2);

		return 0;
	}

}
