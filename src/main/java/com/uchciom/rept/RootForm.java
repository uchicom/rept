// (c) 2017 uchicom
package com.uchciom.rept;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class RootForm implements Printable {

	protected HeaderForm headerForm;
	protected ContentForm contentForm;
	protected FooterForm footerForm;

	protected MediaSizeName mediaSizeName;
	protected int width;
	protected int height;

	public RootForm(MediaSizeName mediaSizeName, OrientationRequested orientationRequested) {
		this.mediaSizeName = mediaSizeName;
		MediaSize size = MediaSize.getMediaSizeForName(mediaSizeName);
		if (OrientationRequested.LANDSCAPE == orientationRequested) {
			height = (int) (size.getX(MediaSize.INCH) * 72);
			width = (int) (size.getY(MediaSize.INCH) * 72);
		} else {
			width = (int) (size.getX(MediaSize.INCH) * 72);
			height = (int) (size.getY(MediaSize.INCH) * 72);
		}
	}

	public RootForm(MediaSizeName mediaSizeName) {
		this(mediaSizeName, OrientationRequested.LANDSCAPE);

	}
	public MediaSizeName getMediaSizeName() {
		return mediaSizeName;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

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

	/*
	 * (非 Javadoc)
	 * 
	 * @see java.awt.print.Printable#print(java.awt.Graphics,
	 * java.awt.print.PageFormat, int)
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
		if (header == NO_SUCH_PAGE || content == NO_SUCH_PAGE || footer == NO_SUCH_PAGE
				|| header == -1 && content == -1 && footer == -1) {
			return NO_SUCH_PAGE;
		} else {
			return PAGE_EXISTS;
		}
	}
	
	public int getMaxPage() {
		if (contentForm != null) {
			return contentForm.getMaxPage();
		} else {
			return 0;
		}
	}
}
