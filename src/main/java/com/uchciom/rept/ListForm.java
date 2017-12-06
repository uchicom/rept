// (c) 2017 uchicom
package com.uchciom.rept;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class ListForm<T> extends ContentForm {

	protected List<T> list = new ArrayList<>();

	
	/**
	 * @param rectangle
	 */
	public ListForm(Rectangle rectangle, List<T> list) {
		super(rectangle);
		this.list = list;
	}

}
