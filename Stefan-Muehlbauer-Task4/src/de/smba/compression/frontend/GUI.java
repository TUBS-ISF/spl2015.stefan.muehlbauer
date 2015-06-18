package de.smba.compression.frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import de.smba.compression.frontend.documentation.GUIDocumenter;
import de.smba.compression.frontend.documentation.IGUIDocumenter;

public class GUI extends JFrame implements IFrontend {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private IGUIDocumenter guiDocumenter;
	private Action openAction = new OpenAction();
	private TextArea textArea;
	private TextArea textArea_1;
	/**
	 * Create the frame.
	 */
	public GUI(IGUIDocumenter guiDocumenter) {

		this.guiDocumenter = guiDocumenter;

		setTitle("CoCo Compression Console UI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mnFile.add(getOpenAction());
		//JMenuItem mntmOpenopen = new JMenuItem("Open uncompressed file...");
		//mnFile.add(mntmOpenopen);

		JMenuItem mntmNewMenuItem = new JMenuItem("Save compressed file");
		mnFile.add(mntmNewMenuItem);

		mnFile.add(new ExitAction());

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnAbout.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		JPanel leftPanel = new JPanel();
		contentPane.add(leftPanel);
		leftPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblComp = new JLabel("Uncompressed");
		leftPanel.add(lblComp, BorderLayout.NORTH);

		textArea = new TextArea();
		textArea.setFont(UIManager.getFont("TextPane.font"));
		textArea.setColumns(1);
		textArea.setRows(10);
		textArea.setBackground(UIManager.getColor("TextArea.background"));
		leftPanel.add(textArea, BorderLayout.CENTER);

		JButton btnCompre = new JButton("Compress");
		leftPanel.add(btnCompre, BorderLayout.SOUTH);

		JPanel rightPanel = new JPanel();
		contentPane.add(rightPanel);
		rightPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Compressed");
		rightPanel.add(lblNewLabel, BorderLayout.NORTH);

		textArea_1 = new TextArea();
		textArea_1.setEditable(false);
		textArea_1.setRows(1);
		textArea_1.setColumns(1);
		textArea_1.setBackground(UIManager
				.getColor("TextArea.inactiveForeground"));
		rightPanel.add(textArea_1, BorderLayout.CENTER);
	}

	public class ExitAction extends AbstractAction {
		public ExitAction() {
			super("Exit");
		}

		public void actionPerformed(ActionEvent ev) {
			System.exit(0);
		}
	}
	
	protected Action getOpenAction() { return openAction; }

	// An action that opens an existing file
	class OpenAction extends AbstractAction {
		public OpenAction() {
			super("Open", new ImageIcon("icons/open.gif"));
		}

		// TODO kram
		public void actionPerformed(ActionEvent ev) {
			JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(GUI.this) != JFileChooser.APPROVE_OPTION)
				return;
			File file = chooser.getSelectedFile();
			if (file == null)
				return;

			FileReader reader = null;
			try {
				reader = new FileReader(file);
				BufferedReader br = new BufferedReader(reader);
				String bener;
				StringBuffer s = new StringBuffer(); 
				while((bener = br.readLine()) != null) { 
					s.append(bener);
				} 
				GUI.this.textArea.setText(s.toString() + "\n");//read(reader, null);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(GUI.this,
						"File Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException x) {
					}
				}
			}
		}
	}

	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI(new GUIDocumenter());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
