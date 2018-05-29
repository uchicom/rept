// (c) 2017 uchicom
package com.uchciom.rept;

import java.awt.Rectangle;
import java.awt.print.Book;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Date;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PrinterJob job = PrinterJob.getPrinterJob();
		RootForm rootForm = new RootForm(MediaSizeName.ISO_A4);
		rootForm.setHeaderForm(new TitleForm(new Rectangle(0, 0, 1000, 200), "あいう", new Date()));
//		rootForm.setHeaderForm(new HeaderForm(new Rectangle(0, 0, 1000, 200)));
		rootForm.setContentForm(new ContentForm(new Rectangle(0, 210, 500, 500)));
//		job.setPrintable(rootForm);
		Book book = new Book();
		book.append(rootForm,  job.defaultPage(), 5);
		job.setPageable(book);
		HashPrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
		set.add(MediaSizeName.ISO_A4);
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

}
