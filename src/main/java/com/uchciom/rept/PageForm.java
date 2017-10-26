// (c) 2017 uchicom
package com.uchciom.rept;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
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
		//管理者でないとdrawString系が使えないので、画像出力とする
		BufferedImage image = new BufferedImage(rectangle.width, rectangle.height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.setFont(fnt);
		System.out.println(((Graphics2D)graphics).getDeviceConfiguration().getColorModel().getColorSpace());
		FontMetrics fm = g.getFontMetrics(fnt);
		String pageString = pageIndex + "/" + max;
		int length = fm.stringWidth(pageString);
		g.drawString(pageString, (rectangle.width - length) /2, rectangle.height / 2);
	
		graphics.drawImage(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);


		System.out.println("c");
		return 0;
	}
}
