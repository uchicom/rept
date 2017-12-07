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
	/* (非 Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		int header = -1;
		if (headerForm != null) {
			header = headerForm.print(graphics, pageFormat, pageIndex);
		}
		int content = -1;
		if (contentForm != null) {
			content = contentForm.print(graphics, pageFormat, pageIndex);
		}
		int footer = -1;
		if (footerForm != null) {
			footer = footerForm.print(graphics, pageFormat, pageIndex);
		}
		if (header == NO_SUCH_PAGE ||
				content == NO_SUCH_PAGE ||
				footer == NO_SUCH_PAGE ||
				header == -1 && content == -1 && footer == -1) {
			return NO_SUCH_PAGE;
		} else {
			return PAGE_EXISTS;
		}
	}
}
