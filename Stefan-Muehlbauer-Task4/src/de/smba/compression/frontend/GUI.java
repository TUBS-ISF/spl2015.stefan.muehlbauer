package de.smba.compression.frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import de.smba.compression.analysis.Analyser;
import de.smba.compression.coding.CodingStore;
import de.smba.compression.coding.Compressor;
import de.smba.compression.coding.Decompressor;
import de.smba.compression.coding.HuffmanCodingFactory;
import de.smba.compression.coding.ICodingFactory;
import de.smba.compression.coding.ICodingStore;
import de.smba.compression.coding.ICompressor;
import de.smba.compression.file.FileHandler;
import de.smba.compression.file.IFileHandler;
import de.smba.compression.frontend.documentation.GUIDocumenter;
import de.smba.compression.frontend.documentation.IGUIDocumenter;

/**
 * This class defines the GUI frontend variant of the compression tool.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 *
 */
public class GUI extends JFrame implements IFrontend, ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	/*
	 * TODO to be implemented
	 */
	private IGUIDocumenter guiDocumenter;
	
	private IFileHandler fileHandler;
	private ICodingFactory codingFactory;
	private ICompressor compressor;
	private Action openAction = new OpenAction();
	private TextArea textArea;
	private TextArea textArea_1;

	/**
	 * Create the frame.
	 */
	public GUI(IGUIDocumenter guiDocumenter, ICodingFactory factory, ICompressor compressor, IFileHandler fileHandler) {

		this.guiDocumenter = guiDocumenter;
		this.codingFactory = factory;
		this.compressor = compressor;
		this.fileHandler = fileHandler;

		setTitle("CoCo Compression Console UI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mnFile.add(getOpenAction());
		// JMenuItem mntmOpenopen = new JMenuItem("Open uncompressed file...");
		// mnFile.add(mntmOpenopen);

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

		btnCompre.setActionCommand("compress");
		btnCompre.addActionListener(this);

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
		
		private static final long serialVersionUID = 1L;

		public ExitAction() {
			super("Exit");
		}

		public void actionPerformed(ActionEvent ev) {
			System.exit(0);
		}
	}

	protected Action getOpenAction() {
		return openAction;
	}

	// TODO test
	class OpenAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;

		public OpenAction() {
			super("Open", new ImageIcon("icons/open.gif"));
		}

		//TODO test
		public void actionPerformed(ActionEvent ev) {
			JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(GUI.this) != JFileChooser.APPROVE_OPTION)
				return;
			File file = chooser.getSelectedFile();
			if (file == null)
				return;
			String decompressed = fileHandler.loadCompressedFile(file.getAbsolutePath());
			textArea.setText(decompressed);
		}
	}

	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI(new GUIDocumenter(), 
							new HuffmanCodingFactory(new Analyser(), new FileHandler(new Decompressor())),
							new Compressor(),
							new FileHandler(new Decompressor()));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// TODO compress & save
	class SaveAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public SaveAction() {
			super("Save", new ImageIcon("icons/save.gif"));
		}

		public void actionPerformed(ActionEvent ev) {
			JFileChooser chooser = new JFileChooser();
			if (chooser.showSaveDialog(GUI.this) != JFileChooser.APPROVE_OPTION)
				return;
			File file = chooser.getSelectedFile();
			if (file == null)
				return;

			
			String toCompress = textArea.getText();
			
			final ICodingFactory cFac = GUI.this.codingFactory;
			Map<String, String> coding = cFac.buildCodingFromText(toCompress);
			ICodingStore store = new CodingStore();
			store.addCoding("current", coding);
			Map<String, String> anticoding = store.getAnticoding("current");
			
			
			String compressed = GUI.this.compressor.compress(coding, toCompress);
			
			
			
			try {
				GUI.this.fileHandler.storeCompressedFile(file.getAbsolutePath(), compressed, anticoding);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	//TODO test
	public void actionPerformed(ActionEvent e) {
		if ("compress".equals(e.getActionCommand())) {

			String toCompress = textArea.getText();
			
			final ICodingFactory cFac = GUI.this.codingFactory;
			Map<String, String> coding = cFac.buildCodingFromText(toCompress);
						
			String compressed = GUI.this.compressor.compress(coding, toCompress);
			
			textArea_1.setText(compressed);
			
			//TODO ratio berechnen, ausgeben

		}
	}
}
