package org.commonjava.cripto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * @author Jean
 *
 */
@SuppressWarnings("serial")
public class CriptoUtilGUI extends JFrame{

	private JPasswordField chave  = new JPasswordField();
	private JTextArea text1   = new JTextArea ();
	private JTextArea text2   = new JTextArea ();
	private JTextArea text3   = new JTextArea ();
	private JTextArea text4   = new JTextArea ();
	
	
	public CriptoUtilGUI() {
		super("Criptografia");
		SwingUtil.configureSwingOSLookAndFeel();
		setSize(500, 500);
		setContentPane(new JPanel());
		getContentPane().setLayout(new BorderLayout());
		
		JPanel toolBar = new JPanel();
		toolBar.setLayout(new GridLayout(1,3));
		toolBar.add(chave);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JScrollPane jScrollPane1 = new JScrollPane(text1);
		JScrollPane jScrollPane2 = new JScrollPane(text3);
		JScrollPane jScrollPane3 = new JScrollPane(text2);
		JScrollPane jScrollPane4 = new JScrollPane(text4);
		
		JPanel texts = new JPanel();
		texts.setLayout(new GridLayout(2,2));
		text1.setBorder(new LineBorder(Color.black));
		text2.setBorder(new LineBorder(Color.black));
		text3.setBorder(new LineBorder(Color.black));
		text4.setBorder(new LineBorder(Color.black));
		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
		text1.setFont(font);
		text2.setFont(font);
		text3.setFont(font);
		text4.setFont(font);
		text2.setLineWrap(true);
		text3.setLineWrap(true);
		texts.add(jScrollPane1);
		texts.add(jScrollPane2);
		texts.add(jScrollPane3);
		texts.add(jScrollPane4);
		getContentPane().add(texts);
		
		
		chave.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			public void changedUpdate(DocumentEvent e) {
				text2.setText(CriptoUtil.cript(text1.getText(), new String(chave.getPassword())));
				text4.setText(CriptoUtil.decript(text3.getText(), new String(chave.getPassword())));
			}
		});
		
		text1.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			public void changedUpdate(DocumentEvent e) {
				text2.setText(CriptoUtil.cript(text1.getText(), new String(chave.getPassword())));
			}
		});
		
		text3.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			public void changedUpdate(DocumentEvent e) {
				text4.setText(CriptoUtil.decript(text3.getText(), new String(chave.getPassword())));
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CriptoUtilGUI();
			}
		});
	}
}
