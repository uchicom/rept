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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class TitleForm extends HeaderForm {
	private static Font fnt = new Font("Helvetica", Font.PLAIN, 24);
	private String title;
	private Date date;

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
		//管理者でないとdrawString系が使えないので、画像出力とする
		BufferedImage image = new BufferedImage(rectangle.width, rectangle.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, rectangle.width*10, rectangle.height*10);
		g.setColor(Color.BLUE);
		g.setFont(fnt);
		System.out.println(((Graphics2D)graphics).getDeviceConfiguration().getColorModel().getColorSpace());
		FontMetrics fm = g.getFontMetrics(fnt);
		int length = fm.stringWidth(title);
		g.drawString(title, rectangle.x + (rectangle.width - length) /2, rectangle.y + rectangle.height / 2);
		g.drawRect(0, 0, rectangle.width, rectangle.height);
		//右側に日付を表示
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String formated = format.format(date);
		int dateLength = fm.stringWidth(formated);
		g.drawString(formated, rectangle.x + rectangle.width - dateLength - 50 , rectangle.y + fm.getHeight() * 2);
		graphics.drawImage(image, rectangle.x / 10, rectangle.y / 10, rectangle.width / 10, rectangle.height / 10, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);


		System.out.println("b");
		return 0;
	}

}
