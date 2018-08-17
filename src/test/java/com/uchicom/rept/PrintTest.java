// (c) 2017 uchicom
package com.uchicom.rept;

import java.awt.Rectangle;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Date;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PageRanges;

import org.junit.Test;

import com.uchicom.rept.ContentForm;
import com.uchicom.rept.FooterForm;
import com.uchicom.rept.HeaderForm;
import com.uchicom.rept.PageForm;
import com.uchicom.rept.RootForm;
import com.uchicom.rept.TitleForm;

/**
 * 
 * 210x297mm
 * 72dpi 595x842px
 * set.add(new NumberOfDocuments(10));//javax.print.attribute.standardにすべてある
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class PrintTest {
	/**
	 * A4縦 72dpi ヘッダ、コンテンツ、フッタ表示
	 * ダイアログ無し
	 */
	@Test
	public void testBackground() {
		PrinterJob job = PrinterJob.getPrinterJob();
		RootForm rootForm = new RootForm(MediaSizeName.ISO_A4);
		rootForm.setHeaderForm(new HeaderForm(new Rectangle(0, 0, 595, 100)));
		rootForm.setContentForm(new ContentForm(new Rectangle(0, 110, 595, 600)));
		rootForm.setFooterForm(new FooterForm(new Rectangle(0, 720, 595, 122)));
		job.setPrintable(rootForm);
		HashPrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
		set.add(MediaSizeName.ISO_A4);
		set.add(OrientationRequested.PORTRAIT);
		try {
			job.print(set);
		} catch (PrinterException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * A4縦 72dpi ヘッダ、コンテンツ、フッタ表示
	 * ダイアログ無し
	 */
	@Test
	public void testDialog() {
		PrinterJob job = PrinterJob.getPrinterJob();
		RootForm rootForm = new RootForm(MediaSizeName.ISO_A4);
		rootForm.setHeaderForm(new HeaderForm(new Rectangle(0, 0, 595, 100)));
		rootForm.setContentForm(new ContentForm(new Rectangle(0, 110, 595, 600)));
		rootForm.setFooterForm(new FooterForm(new Rectangle(0, 720, 595, 122)));
		job.setPrintable(rootForm);
		HashPrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
		set.add(MediaSizeName.ISO_A4);
		//set.add(new NumberOfDocuments(10));//javax.print.attribute.standardにすべてある
		set.add(OrientationRequested.PORTRAIT);
		boolean doPrint = job.printDialog(set);
		if (doPrint) {
			try {
				job.print(set);
			} catch (PrinterException pe) {
				pe.printStackTrace();
			}
		}
	}
	/**
	 * A4縦 72dpi ヘッダ、コンテンツ、フッタ表示
	 * ダイアログ無し
	 */
	@Test
	public void testLandscape() {
		//タイトルを出力
		//リストで文字を渡して表にする
		//ページ番号を出力
		//
		PrinterJob job = PrinterJob.getPrinterJob();
//		job.getPrintService().createPrintJob().getAttributes().add(new PrinterResolution(300,300,PrinterResolution.DPI));
		RootForm rootForm = new RootForm(MediaSizeName.ISO_A4);
		rootForm.setHeaderForm(new TitleForm(new Rectangle(0, 0, 595, 100), "あいう", new Date()));
		rootForm.setContentForm(new ContentForm(new Rectangle(0, 110, 595, 600)));
		rootForm.setFooterForm(new PageForm(new Rectangle(0, 720, 595, 122), 10));
		job.setPrintable(rootForm);
//		Book book = new Book();
//		book.append(rootForm,  job.defaultPage(), 5);
//		job.setPageable(book);
		HashPrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
		set.add(MediaSizeName.ISO_A4);
		set.add(OrientationRequested.LANDSCAPE);
		set.add(Chromaticity.MONOCHROME);
		set.add(javax.print.attribute.standard.PrintQuality.HIGH);
//		set.add(javax.print.attribute.standard.Compression.NONE);
		set.add(new PageRanges(1, 3));
		set.add(new MediaPrintableArea(0,0, 210, 297, MediaPrintableArea.MM));
		boolean doPrint = job.printDialog(set);
		
		if (doPrint) {
			try {
//				set.add(new PrinterResolution(600, 600, PrinterResolution.DPI));
//				set.add(PrintQuality.HIGH);
				job.print(set);

			} catch (PrinterException pe) {
				pe.printStackTrace();
			}
		}
	}

}
