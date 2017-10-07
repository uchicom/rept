// (c) 2017 uchicom
package com.uchicom.rept;

import java.awt.Rectangle;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.junit.Test;

import com.uchciom.rept.HeaderForm;
import com.uchciom.rept.RootForm;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class PrintTest {
	@Test
	public void test() {
		PrinterJob job = PrinterJob.getPrinterJob();
		RootForm rootForm = new RootForm();
		rootForm.setHeaderForm(new HeaderForm(new Rectangle(0, 0, 1000, 200)));

		boolean doPrint = job.printDialog();
		if (doPrint) {
			try {
				job.print();

			} catch (PrinterException pe) {
				pe.printStackTrace();
			}
		}
	}

}
