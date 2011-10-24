package org.commonjava;

import javax.swing.UIManager;

public class UIUtils {

	public static void configureSystemLookAndFeel(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
}
