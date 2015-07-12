package de.smba.compression.frontend; 

import java.awt.BorderLayout; 
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
import javax.swing.JPanel; 
import javax.swing.UIManager; 
import javax.swing.border.EmptyBorder; 

import de.smba.compression.frontend.documentation.GUIDocumenter; 
import de.smba.compression.frontend.benchmarking.GUIBenchmarker; 
import de.smba.compression.coding.CodingFactoryMediator; 
import de.smba.compression.coding.CodingStore; 
import de.smba.compression.coding.ICodingFactory; 
import de.smba.compression.coding.ICodingStore; 
import de.smba.compression.coding.ICompressor; 
import de.smba.compression.file.IFileHandler; 
import de.smba.compression.file.FileHandler; 
import de.smba.compression.coding.Compressor; 
import de.smba.compression.frontend.benchmarking.AbstractGUIBenchmarker; 
import de.smba.compression.frontend.documentation.IGUIDocumenter; 

/**
 * This class defines the GUI frontend variant of the compression tool.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 *
 */
public  class  GUI  extends JFrame  implements IFrontend, ActionListener {
	

	private static final long serialVersionUID = 1L;

	
	private JPanel contentPane;

	
	
	private IGUIDocumenter guiDocumenter;

	
	private AbstractGUIBenchmarker guiBenchmarker;

	
	
	private IFileHandler fileHandler;

	
	private ICodingFactory codingFactory;

	
	private ICompressor compressor;

	
	private Action openAction = new OpenAction();

	
	private Action saveAction = new SaveAction();

	
	private TextArea textArea;

	
	private TextArea textArea_1;

	

	/**
	 * Create the frame.
	 */
	public GUI() {

		this.guiDocumenter = new GUIDocumenter();
		this.guiBenchmarker = new GUIBenchmarker();
		
		CodingFactoryMediator med = new CodingFactoryMediator();
		this.codingFactory = med.getCodingFactory();
		
		this.compressor = new Compressor();
		this.fileHandler = new FileHandler();

		setTitle("CoCo Compression Console UI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mnFile.add(getOpenAction());
		mnFile.add(getSaveAction());
		mnFile.add(new ExitAction());

		JMenu mnAbout = new JMenu("About");
		mnAbout.add(new AboutAction());
		menuBar.add(mnAbout);

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

	

	public  class  ExitAction  extends AbstractAction {
		
		
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

	

	protected Action getSaveAction() {
		return saveAction;
	}

	
	
	 
	
	class  OpenAction  extends AbstractAction {
		
		
		private static final long serialVersionUID = 1L;

		

		public OpenAction() {
			super("Open compressed ...", new ImageIcon("icons/open.gif"));
		}

		

		public void actionPerformed(ActionEvent ev) {

			JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(GUI.this) != JFileChooser.APPROVE_OPTION)
				return;
			File file = chooser.getSelectedFile();
			if (file == null)
				return;
			String decompressed = fileHandler.loadCompressedFile(file.getAbsolutePath());
			textArea.setText(decompressed);
			textArea_1.setText("");
		}


	}

	

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
	}

	

	 

	class  AboutAction  extends AbstractAction {
		

		private static final long serialVersionUID = 1L;

		

		public AboutAction() {
			super("About");
		}

		
		
		//TODO delegate this method to an aspect
		public void actionPerformed(ActionEvent arg0) {
			GUI.this.guiDocumenter.documentAbout();
		}


	}

	
	

	 
	

	class  SaveAction  extends AbstractAction {
		

		private static final long serialVersionUID = 1L;

		

		public SaveAction() {
			super("Save compressed...", new ImageIcon("icons/save.gif"));
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

	
	
	//TODO same
	public void actionPerformed(ActionEvent e) {
		if ("compress".equals(e.getActionCommand())) {

			String toCompress = textArea.getText();
			
			final ICodingFactory cFac = GUI.this.codingFactory;
			Map<String, String> coding = cFac.buildCodingFromText(toCompress);
						
			String compressed = GUI.this.compressor.compress(coding, toCompress);
			
			textArea_1.setText(compressed);
			
			this.guiBenchmarker.compressBenchmarkNotification(toCompress, compressed);

		}
	}


}
