// (c) 2017 uchicom
package com.uchciom.rept;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class RootForm implements Printable {

	protected HeaderForm headerForm;
	protected ContentForm contentForm;
	protected FooterForm footerForm;
	public HeaderForm getHeaderForm() {
		return headerForm;
	}
	public void setHeaderForm(HeaderForm headerForm) {
		this.headerForm = headerForm;
	}
	public ContentForm getContentForm() {
		return contentForm;
	}
	public void setContentForm(ContentForm contentForm) {
		this.contentForm = contentForm;
	}
	public FooterForm getFooterForm() {
		return footerForm;
	}
	public void setFooterForm(FooterForm footerForm) {
		this.footerForm = footerForm;
	}
	int count;
	/* (非 Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (headerForm != null) {
			headerForm.print(graphics, pageFormat, pageIndex);
		}
		if (contentForm != null) {
			contentForm.print(graphics, pageFormat, pageIndex);
		}
		if (footerForm != null) {
			footerForm.print(graphics, pageFormat, pageIndex);
		}
		if (count == 0) {
			count++;
			return Printable.PAGE_EXISTS;
		} else {
			return Printable.NO_SUCH_PAGE;
		}
	}
}
