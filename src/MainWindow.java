package stupIDE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.text.Utilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;


public class MainWindow implements ActionListener{
	
	public JFrame window; 
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int Swidth = (int) (screenSize.getWidth());
    private int Sheight = (int) (screenSize.getHeight());
    
    private JTabbedPane fileTab;
	private JTabbedPane editorTab;
	
	private JPanel filePane;
	private JFXPanel view;
	
	private JTree fileTree;
	private JLabel statusLabel;
	private JLabel statusLength;
	private JLabel statusWord;
	private JPanel statusPanel;
	private ViewerPane viewerPane = new ViewerPane();
	
	public ArrayList<String> data = new ArrayList<String>();
	
	ImageIcon filecss = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("filecss.png")).getImage().getScaledInstance( Swidth/55, Swidth/55, java.awt.Image.SCALE_SMOOTH));
	ImageIcon filehtml = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("filehtml.png")).getImage().getScaledInstance( Swidth/55, Swidth/55, java.awt.Image.SCALE_SMOOTH));
	ImageIcon fileimage = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("fileimage.png")).getImage().getScaledInstance( Swidth/55, Swidth/55, java.awt.Image.SCALE_SMOOTH));
	ImageIcon filejs = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("filejs.png")).getImage().getScaledInstance( Swidth/55, Swidth/55, java.awt.Image.SCALE_SMOOTH));
	ImageIcon filephp = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("filephp.png")).getImage().getScaledInstance( Swidth/55, Swidth/55, java.awt.Image.SCALE_SMOOTH));
	ImageIcon filetext = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("filetext.png")).getImage().getScaledInstance( Swidth/55, Swidth/55, java.awt.Image.SCALE_SMOOTH));
	ImageIcon newfile = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("newFile.png")).getImage().getScaledInstance( Swidth/55, Swidth/55, java.awt.Image.SCALE_SMOOTH));
	ImageIcon newproject = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("newProject.png")).getImage().getScaledInstance( Swidth/55, Swidth/55, java.awt.Image.SCALE_SMOOTH));
	ImageIcon runIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("run.png")).getImage().getScaledInstance( Swidth/55, Swidth/55, java.awt.Image.SCALE_SMOOTH));
	ImageIcon saveIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("save.png")).getImage().getScaledInstance( Swidth/55, Swidth/55, java.awt.Image.SCALE_SMOOTH));
    
    public MainWindow()
    {
    	window = new JFrame("StupIDE");
    	
		data.add(""); //0 --> Path of the project
		data.add(""); //1 --> current File
		data.add(""); //2 --> Folder name
		
		ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("StupIDE_logo.png"));
        window.setIconImage(img.getImage());
    	
    	
    	JMenu File = new JMenu("File");
    		JMenu news = new JMenu("New");
    			news.setIcon(newfile);
    			JMenuItem file = new JMenuItem("File");
    			JMenuItem folder = new JMenuItem("Folder");
    			news.add(file);
    			news.add(folder);
    		JMenuItem open = new JMenuItem("Open");
    		JMenuItem save = new JMenuItem("Save");
    			save.setIcon(saveIcon);
    		JMenuItem closeall = new JMenuItem("Close All");
    		JMenuItem exit = new JMenuItem("Exit");
    		
    		file.addActionListener(this);
    		folder.addActionListener(this);
    		open.addActionListener(this);
    		save.addActionListener(this);
    		closeall.addActionListener(this);
    		exit.addActionListener(this);
    		
    		File.add(news);
    		File.add(open);
    		File.add(save);
    		File.add(closeall);
    		File.add(exit);
    	
    	JMenu Edit = new JMenu("Edit");
			JMenuItem cut = new JMenuItem("Cut");
			JMenuItem copy = new JMenuItem("Copy");
			JMenuItem paste = new JMenuItem("Paste");
			JMenuItem print = new JMenuItem("Print");
			
			cut.addActionListener(this);
			copy.addActionListener(this);
			paste.addActionListener(this);
			print.addActionListener(this);
			
			Edit.add(cut);
			Edit.add(copy);
			Edit.add(paste);
			Edit.add(print);
    	
    	JMenu Run = new JMenu("Run");
			JMenuItem intern = new JMenuItem("Viewer");
				intern.setIcon(runIcon);
			JMenuItem browser = new JMenuItem("Browser");
				browser.setIcon(runIcon);
			
			intern.addActionListener(this);
			browser.addActionListener(this);
			
			Run.add(intern);
			Run.add(browser);
    	
    	JMenu Help = new JMenu("Help");
			JMenuItem about = new JMenuItem("About");
			JMenuItem website = new JMenuItem("Developer website");
			
			about.addActionListener(this);
			website.addActionListener(this);
			
			Help.add(about);
			Help.add(website);
    	
    	Menu bar = new Menu();
    	bar.addItem(File);
    	bar.addItem(Edit);
    	bar.addItem(Run);
    	bar.addItem(Help);
    	
    	//--------------------------------------JTabbedPane
    	//File
    	fileTab = new JTabbedPane();
		fileTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		fileTab.updateUI();
		fileTab.setBackground(new Color(32,32,32));
		fileTab.setForeground(Color.WHITE);
		
		filePane = new JPanel();
		filePane.setBackground(Color.DARK_GRAY);
		filePane.setForeground(Color.WHITE);
		
		filePane.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource().getClass().getName().equals("javax.swing.JPanel"))
				{
					if(!data.get(0).equals(""))
					{
						((JTree) (filePane.getComponent(0))).clearSelection();
						data.set(1, "");
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    });
		
		JScrollPane fileScroll = new JScrollPane(filePane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		fileTab.addTab("FileSystem",fileScroll);
    	
		//Editor
    	editorTab = new JTabbedPane();
		editorTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		editorTab.updateUI();
		editorTab.setBackground(new Color(32,32,32));
		editorTab.setForeground(Color.WHITE);
		
		editorTab.addMouseListener(new MouseAdapter() {
			
			public void mouseReleased(MouseEvent e)
			{
				if(e.isPopupTrigger())
				{
					if(editorTab.getSelectedIndex() != -1)
					{
						JPopupMenu pop = new JPopupMenu();
						
						JMenuItem close = new JMenuItem("Close");
						close.setBackground(new Color(32,32,32));
						close.setForeground(Color.WHITE);
						
						close.addActionListener(new ActionListener() {
	
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								editorTab.remove(editorTab.getSelectedIndex());
							}
						});
						
						pop.add(close);
						pop.show(e.getComponent(), e.getX(),e.getY());
					}
				}
			}
		});
		
		editorTab.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) 
	        {
	        	String titre = editorTab.getTitleAt(editorTab.getSelectedIndex());
	        	
	        	if(titre.lastIndexOf(".html") != -1 || titre.lastIndexOf(".htm") != -1)
	        	{
	        		statusLabel.setText("Hyper Text Markup Language file");
	        	}
	        	else if(titre.lastIndexOf(".php") != -1)
	        	{
	        		statusLabel.setText("PHP Hypertext Preprocessor file");
	        	}
	        	else if(titre.lastIndexOf(".css") != -1)
	        	{
	        		statusLabel.setText("Cascade Style Sheet file");
	        	}
	        	else if(titre.lastIndexOf(".js") != -1)
	        	{
	        		statusLabel.setText("JavaScript file");
	        	}
	        	else if(titre.lastIndexOf(".txt") != -1)
	        	{
	        		statusLabel.setText("Normal Text file");
	        	}
	        	else if(titre.lastIndexOf(".py") != -1 || titre.lastIndexOf(".pyc") != -1 || titre.lastIndexOf(".pyd") != -1 || titre.lastIndexOf(".pyo") != -1 || titre.lastIndexOf(".pyw") != -1 || titre.lastIndexOf(".pyz") != -1)
	        	{
	        		statusLabel.setText("Python file");
	        	}
	        	else if(titre.lastIndexOf(".java") != -1)
	        	{
	        		statusLabel.setText("Java file");
	        	}
	        	else if(titre.lastIndexOf(".rb") != -1 || titre.lastIndexOf(".rbw") != -1)
	        	{
	        		statusLabel.setText("Ruby file");
	        	}
	        	else if(titre.lastIndexOf(".cs") != -1 || titre.lastIndexOf(".csx") != -1)
	        	{
	        		statusLabel.setText("C Sharp file");
	        	}
	        	else if(titre.lastIndexOf(".cc") != -1 || titre.lastIndexOf(".cpp") != -1 || titre.lastIndexOf(".cxx") != -1 || titre.lastIndexOf(".c") != -1 || titre.lastIndexOf(".c++") != -1 || titre.lastIndexOf(".h") != -1 || titre.lastIndexOf(".hpp") != -1 || titre.lastIndexOf(".hh") != -1 || titre.lastIndexOf(".hxx") != -1 || titre.lastIndexOf(".h++") != -1)
	        	{
	        		statusLabel.setText("C++ file");
	        	}
	        	else if(titre.lastIndexOf(".sql") != -1)
	        	{
	        		statusLabel.setText("Structured Query Language file");
	        	}
	        }
	    });
		
		//View
		view = new JFXPanel();
		
		//------------------------------spl JSplitPane
        JSplitPane spl = new JSplitPane(SwingConstants.VERTICAL, editorTab, view);
        
        spl.setDividerLocation((int) (2*Swidth/5));
         
        BasicSplitPaneDivider divider = (BasicSplitPaneDivider) spl.getComponent(2);
        Border border = new LineBorder(Color.BLACK, 5);
        UIManager.put("BasicSplitPaneDivider.border", border);
        divider.setBorder(border);
        
        
      //------------------------------spl1 JSplitPane
        JSplitPane spl1 = new JSplitPane(SwingConstants.VERTICAL, fileTab, spl);
	    
	    spl1.setDividerLocation((int) (Swidth/5));
         
        BasicSplitPaneDivider divider1 = (BasicSplitPaneDivider) spl1.getComponent(2);
        Border border1 = new LineBorder(Color.RED.darker(), 5);
        UIManager.put("BasicSplitPaneDivider.border", border1);
        divider1.setBorder(border1);
        
		//---------------------------------------------JToolBar
        JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		tools.setRollover(true);
		tools.setBackground(new Color(31,37,49));
		tools.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		//SAVE AND RUN BUTTON
		JButton saveButton = new JButton();
		saveButton.setIcon(saveIcon);
		saveButton.setBackground(Color.BLACK);
		saveButton.setForeground(Color.WHITE);
		saveButton.setFocusPainted(false);
		saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		saveButton.setFont(new Font("Times New Roman", Font.BOLD, 24));
				
		saveButton.setToolTipText("Save project");
				
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) 
			{
				//Save the project
				SaveFile();
			}
		});
				
		JButton runButton = new JButton();
		runButton.setIcon(runIcon);
		runButton.setBackground(Color.BLACK);
		runButton.setForeground(Color.WHITE);
		runButton.setFocusPainted(false);
		runButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		runButton.setFont(new Font("Times New Roman", Font.BOLD, 24));
		runButton.setToolTipText("Run project");
				
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) 
			{
				//Run the project
				    	 
			}
		});
        
		tools.add(saveButton, BorderLayout.CENTER);
		tools.add(runButton, BorderLayout.CENTER);
		
		//------------------------------------------statusPanel
		statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setBackground(Color.GRAY);			
		statusPanel.setPreferredSize(new Dimension(window.getWidth(), 26));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		
		statusLabel = new JLabel("StupIDE version 2.0");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusLabel.setForeground(Color.WHITE);
		
		statusLength = new JLabel("Length : 0");
		statusLength.setHorizontalAlignment(SwingConstants.LEFT);
		statusLength.setForeground(Color.WHITE);
		
		statusWord = new JLabel("Words : 0");
		statusWord.setHorizontalAlignment(SwingConstants.LEFT);
		statusWord.setForeground(Color.WHITE);
		
		statusPanel.add(statusLabel);
		statusPanel.add(new JSeparator(SwingConstants.VERTICAL));
		statusPanel.add(statusLength);
		statusPanel.add(new JSeparator(SwingConstants.VERTICAL));
		statusPanel.add(statusWord);
		
		//-------------------------------------------------------------------
    	
		window.add(tools, BorderLayout.NORTH);
		window.add(statusPanel, BorderLayout.SOUTH);
    	window.setJMenuBar(bar);
    	window.add(spl1);
    	window.getContentPane().setBackground(Color.WHITE);
    	window.setSize(new Dimension(Swidth, Sheight));
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
		window.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(window, 
		            "Are you sure you want to close StupIDE?", "Close Window?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
		            Thread.currentThread().interrupt();
		        }
		    }
		});
    }
    
    public LookAndFeel lookAndFeel(JTree mytree, String type)
	{
		LookAndFeel previousLF = UIManager.getLookAndFeel();

		try {
			
			if(type.equals("Windows"))
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				SwingUtilities.updateComponentTreeUI(mytree);
			}
			else if(type.equals("Nimbus"))
			{
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
				{
					if ("Nimbus".equals(info.getName())) 
					{
	                    UIManager.setLookAndFeel(info.getClassName());
	                    SwingUtilities.updateComponentTreeUI(mytree);
	                    break;
	                }
	            }
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return previousLF;
	}
    
    public void loadFile()
	{
		
		if(fileTree != null)
		{
			DefaultTreeModel model = (DefaultTreeModel) fileTree.getModel();
			DefaultMutableTreeNode oldRoot = (DefaultMutableTreeNode) fileTree.getModel().getRoot();
			oldRoot.removeAllChildren();
			model.reload();
			model.setRoot(null);
		}
		
		//Check all the files in the project folder
		File[] list = listOfFiles(new File(data.get(0)));
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("res");
		root.setUserObject("res");
		
		DefaultTreeCellRenderer filder = new DefaultTreeCellRenderer() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public Color getBackgroundNonSelectionColor()
			{
				return (null);
			}
			
			@Override
			public Color getBackgroundSelectionColor()
			{
				return (null);
			}
			
			@Override
			public Color getBackground()
			{
				return (null);
			}
			
			@Override
			public Color getTextNonSelectionColor()
			{
				return (Color.WHITE);
			}
			
			@Override
			public Color getTextSelectionColor()
			{
				return (Color.RED);
			}
			
			@Override
			public Color getBorderSelectionColor()
			{
				return (null);
			}
			
			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
			{
				final Component rc = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

				if(((DefaultMutableTreeNode) value).toString() != "res")
				{
					String toolTip = value.toString();
					setToolTipText(toolTip);
					
					if(((DefaultMutableTreeNode) value).toString().endsWith(".html") == true)
					{
						setIcon(filehtml);
					}
					else if(((DefaultMutableTreeNode) value).toString().endsWith(".css") == true)
					{
						setIcon(filecss);
					}
					else if(((DefaultMutableTreeNode) value).toString().endsWith(".php") == true)
					{
						setIcon(filephp);
					}
					else if(((DefaultMutableTreeNode) value).toString().endsWith(".js") == true)
					{
						setIcon(filejs);
					}
					else if(((DefaultMutableTreeNode) value).toString().endsWith(".txt") == true)
					{
						setIcon(filetext);
					}
					else if(((DefaultMutableTreeNode) value).toString().endsWith(".htm") == true)
					{
						setIcon(filehtml);
					}
				}
				else
				{
					setToolTipText(null);
				}
				
				return rc;
			}
			
		};
		
		/*filder.setClosedIcon(folderIcon);
		filder.setOpenIcon(folderIcon);
		filder.setLeafIcon(fileIcon);*/
		
		filder.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		for(int i = 0; i < list.length; i++)
		{
			if (list[i].isFile()) 
			{
				DefaultMutableTreeNode fich = new DefaultMutableTreeNode(list[i].getName());
				fich.setAllowsChildren(false);
				root.add(fich);
			} 
			else if (list[i].isDirectory()) 
			{
				File[] subList = listOfFiles(new File(list[i].getAbsolutePath()));
				
				DefaultMutableTreeNode dire = new DefaultMutableTreeNode(list[i].getName(), true);
				
				for(int j = 0; j < subList.length; j++)
				{
					DefaultMutableTreeNode subFile = new DefaultMutableTreeNode(subList[j].getName(),false);
					dire.add(subFile);
				}
				
				
				root.add(dire);
			}
		}
		
		DefaultTreeModel rootFirm = new DefaultTreeModel(root);
		rootFirm.setAsksAllowsChildren(true);
		
		fileTree = new JTree(rootFirm);
		fileTree.setShowsRootHandles(true);
		fileTree.setCellRenderer(filder);
		fileTree.setOpaque(false);
		
		fileTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getNewLeadSelectionPath() != null)
				{
					Object obj = e.getNewLeadSelectionPath().getLastPathComponent();
					
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
					
					if(node.getParent() != null)
					{
						
						String link3 = "";
						TreePath chem = fileTree.getSelectionPaths()[fileTree.getSelectionPaths().length-1];
						
						for(int s = 1; s < chem.getPath().length; s++)
						{
							if(s < chem.getPath().length -1)
							{
								link3 += chem.getPath()[s].toString()+"\\";
							}
							else
							{
								link3 += chem.getPath()[s].toString();
							}
						}
						
						data.set(1, data.get(0)+"\\"+link3);
					}
				}
			}
		});
		
		for(int i = 0; i < fileTree.getRowCount(); i++)
		{
			fileTree.expandRow(i);
		}
		
		fileTree.putClientProperty("JTree.lineStyle", "Angled");
		fileTree.setRowHeight(25);
		
		final FilePopup filepopup = new FilePopup(fileTree, this);
		
		fileTree.addMouseListener(new MouseAdapter() {
			
			public void mouseReleased(MouseEvent e)
			{
				if(e.isPopupTrigger())
				{
					String[] noterror = {".html",".htm",".php", ".css",".js",".txt",".py",".pyc",".pyd",".pyo",".pyw",".pyz",".java",".rb",".rbw",".cs",".csx",".cc",".cpp",".cxx",".c",".c++",".h",".hpp",".hh",".hxx",".h++", ".sql"}; 
					String path = fileTree.getLastSelectedPathComponent().toString();
					boolean value = false;
					
					if(path != null)
					{
						for(String chemin : noterror)
						{
							if(path.lastIndexOf(chemin) != -1)
							{
								value = true;
								break;
							}
						}
						
						filepopup.addItem(value);
						filepopup.show(e.getComponent(), e.getX(),e.getY());
					}
				}
			}
		});
		
		filePane.removeAll();
		filePane.add(fileTree, BorderLayout.WEST);//, BorderLayout.CENTER);
		filePane.revalidate();
		
		try 
		{
			UIManager.setLookAndFeel(lookAndFeel(fileTree, "Windows"));
		} 
		catch (UnsupportedLookAndFeelException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    
    public static void setTabs( final JTextPane textPane, int charactersPerTab)
    {
        FontMetrics fm = textPane.getFontMetrics( textPane.getFont() );
//          int charWidth = fm.charWidth( 'w' );
        int charWidth = fm.charWidth( ' ' );
        int tabWidth = charWidth * charactersPerTab;
//      int tabWidth = 100;

        TabStop[] tabs = new TabStop[5];

        for (int j = 0; j < tabs.length; j++)
        {
            int tab = j + 1;
            tabs[j] = new TabStop( tab * tabWidth );
        }

        TabSet tabSet = new TabSet(tabs);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setTabSet(attributes, tabSet);
        int length = textPane.getDocument().getLength();
        textPane.getStyledDocument().setParagraphAttributes(0, length, attributes, false);
    }
    
    public String getWordAtCaret(JTextPane tc) 
    {
        try 
        {
           int caretPosition = tc.getCaretPosition();
           
           int start = Utilities.getWordStart(tc, caretPosition);
           int end = Utilities.getWordEnd(tc, caretPosition);
           
           //System.out.println("Start: "+start+", End: "+end);
           
           return tc.getText(start, end - start);
        } 
        catch (BadLocationException e) 
        {
            System.err.println(e);
        }

        return null;
    }
    
    public void setFile(File fichier)
	{
    	EditorListener listen = new EditorListener();
    	
    	//Set the file type to the EditorListener
    	if(fichier.getAbsolutePath().lastIndexOf(".html") != -1 || fichier.getAbsolutePath().lastIndexOf(".htm") != -1)
    	{
    		listen.setFileType("HTML");
    	}
    	else if(fichier.getAbsolutePath().lastIndexOf(".php") != -1)
    	{
    		listen.setFileType("PHP");
    	}
    	else if(fichier.getAbsolutePath().lastIndexOf(".css") != -1)
    	{
    		listen.setFileType("CSS");
    	}
    	else if(fichier.getAbsolutePath().lastIndexOf(".js") != -1)
    	{
    		listen.setFileType("JavaScript");
    	}
    	else if(fichier.getAbsolutePath().lastIndexOf(".txt") != -1)
    	{
    		listen.setFileType("Normal Text");
    	}
    	else if(fichier.getAbsolutePath().lastIndexOf(".py") != -1 || fichier.getAbsolutePath().lastIndexOf(".pyc") != -1 || fichier.getAbsolutePath().lastIndexOf(".pyd") != -1 || fichier.getAbsolutePath().lastIndexOf(".pyo") != -1 || fichier.getAbsolutePath().lastIndexOf(".pyw") != -1 || fichier.getAbsolutePath().lastIndexOf(".pyz") != -1)
    	{
    		listen.setFileType("Python");
    	}
    	else if(fichier.getAbsolutePath().lastIndexOf(".java") != -1)
    	{
    		listen.setFileType("Java");
    	}
    	else if(fichier.getAbsolutePath().lastIndexOf(".rb") != -1 || fichier.getAbsolutePath().lastIndexOf(".rbw") != -1)
    	{
    		listen.setFileType("Ruby");
    	}
    	else if(fichier.getAbsolutePath().lastIndexOf(".cs") != -1 || fichier.getAbsolutePath().lastIndexOf(".csx") != -1)
    	{
    		listen.setFileType("C#");
    	}
    	else if(fichier.getAbsolutePath().lastIndexOf(".cc") != -1 || fichier.getAbsolutePath().lastIndexOf(".cpp") != -1 || fichier.getAbsolutePath().lastIndexOf(".cxx") != -1 || fichier.getAbsolutePath().lastIndexOf(".c") != -1 || fichier.getAbsolutePath().lastIndexOf(".c++") != -1 || fichier.getAbsolutePath().lastIndexOf(".h") != -1 || fichier.getAbsolutePath().lastIndexOf(".hpp") != -1 || fichier.getAbsolutePath().lastIndexOf(".hh") != -1 || fichier.getAbsolutePath().lastIndexOf(".hxx") != -1 || fichier.getAbsolutePath().lastIndexOf(".h++") != -1)
    	{
    		listen.setFileType("C++");
    	}
    	else if(fichier.getAbsolutePath().lastIndexOf(".sql") != -1)
    	{
    		listen.setFileType("SQL");
    	}
    	//---------------------------------------
    	
    	JTextPane editor = new JTextPane();
    	
    	listen.setContainer(editor);
    	editor.setStyledDocument(listen.getDoc());
    	
		editor.setBackground(Color.BLACK);
		editor.setForeground(Color.WHITE);
		editor.setFont(new Font("Times New Roman", Font.BOLD, 16));
		editor.setCaretColor(Color.WHITE);
		
		JScrollPane editorScroll = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		setTabs(editor, 8);
		
		editor.setText(readFile(fichier));
		
		editor.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e)
			{	
				editorTab.setTitleAt(editorTab.getSelectedIndex(), "*"+fichier.getName());
					
				//int position = editor.getCaretPosition();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0))
				{
					//CTRL + S to save project
					SaveFile();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		editor.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				// TODO Auto-generated method stub
				int longueur = editor.getText().length();
				int wordCount = editor.getText().split("\\s").length;
				
				statusLength.setText("Length : "+longueur);
				statusWord.setText("Words : "+wordCount);
			}
			
		});
		
        TextLineNumber tln = new TextLineNumber(editor);
        tln.setBorderGap(1);
        tln.setBackground(Color.DARK_GRAY);
        tln.setForeground(Color.WHITE);
        editorScroll.setRowHeaderView(tln);
        
        editorTab.addTab(fichier.getName(),editorScroll);
        
        editorTab.setSelectedIndex(editorTab.getTabCount()-1);
	}
    
    public File[] listOfFiles(final File folder)
	{
		ArrayList<File> names = new ArrayList<File>();
		
		for(final File fileEntry: folder.listFiles())
		{
			names.add(fileEntry);
		}
		
		return Arrays.copyOf(names.toArray(), names.toArray().length, File[].class);
	}
    
    public String readFile(File fich)
	{
		try 
		{
			String data = "";
			File myObj = fich;
		    
			if(myObj.exists())
			{
			    Scanner myReader = new Scanner(myObj);
			      
			    while (myReader.hasNextLine()) 
			    {
			    	data += myReader.nextLine()+"\n";
			    }
			    myReader.close();
			}
		    return data;
		} 
		catch (FileNotFoundException e) 
		{
		      System.out.println("Cannot read the file.");
		      e.printStackTrace();
		      return null;
		}
	}
    
	public void SaveFile() {
		
		 // Save the current data in current file 
		if(!data.get(0).equals(""))
		{
			String newTitle = editorTab.getTitleAt(editorTab.getSelectedIndex());
			
			if(newTitle.charAt(0) == '*')
			{
				newTitle = newTitle.substring(1);
				editorTab.setTitleAt(editorTab.getSelectedIndex(), newTitle);
			}
			
				File fi_save = new File(data.get(0)+"\\"+editorTab.getTitleAt(editorTab.getSelectedIndex()));
				
				JViewport viw = ((JScrollPane) editorTab.getComponentAt(editorTab.getSelectedIndex())).getViewport();
				
				String temporary[] = ((JTextPane) viw.getView()).getText().split("\n");
				
				try 
				{ 
					// Create a file writer 
					FileWriter wr = new FileWriter(fi_save, false); 
	
					// Create buffered writer to write 
					BufferedWriter w = new BufferedWriter(wr); 
					
					//write
					
					for(int ir = 0; ir<temporary.length;ir++)
					{         
						w.write(temporary[ir]);
						w.newLine();
					}
	
					w.flush(); 
					w.close(); 	
					
					JOptionPane.showMessageDialog(window, "Files saved!");
				} 
				catch (Exception evtsave) 
				{ 
					JOptionPane.showMessageDialog(window, evtsave.getMessage()); 
				}
		}
	}
    

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String s = e.getActionCommand();
		
		if(s.equals("File"))
		{
			if(!data.get(0).equals(""))
			{
				DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
				String fullPath = "";
				boolean error = true;
				
				if(!node1.isLeaf() && !node1.isRoot())
				{
					//A no-root folder
					fullPath = data.get(1)+"\\";
					data.set(1, fullPath);
					error = false;
				}
				else if(node1.isRoot())
				{
					//The root folder
					fullPath = data.get(0)+"\\";
					data.set(1, fullPath);
					error = false;
				}
				
				if(!error)
				{
					JDialog dialog = new JDialog(window,"Create new File");
					
					JTextField newFichier = new JTextField();
					newFichier.setBackground(Color.BLACK);
					newFichier.setForeground(Color.WHITE);
					newFichier.setCaretColor(Color.WHITE);
					newFichier.setFont(new Font("Times New Roman", Font.BOLD, 14));
					
					JButton creer = new JButton("Create");
					creer.setFocusable(false);
					
					creer.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {
							
							String newPath = newFichier.getText();
							
							if(newPath.lastIndexOf(".") == -1)
							{
								newPath += ".txt";
							}
							
							File nouveauFichier = new File(data.get(1)+""+newPath);
							
							if(!nouveauFichier.exists())
							{
								try
								{
									nouveauFichier.createNewFile();
									dialog.dispose();
									
									JOptionPane.showMessageDialog(window, "New file created!");
									loadFile();
								} 
								catch (IOException e1) 
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					});
					
					dialog.setLayout(new GridLayout(2,1));
					dialog.add(newFichier);
					dialog.add(creer);
					dialog.setSize(200, 100);
					dialog.setVisible(true);
				}
			}
		}
		else if(s.equals("Folder"))
		{
			if(!data.get(0).equals(""))
			{
				DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
				String fullPath = "";
				boolean error = true;
				
				if(!node1.isLeaf() && !node1.isRoot())
				{
					//A no-root folder
					fullPath = data.get(1)+"\\";
					data.set(1, fullPath);
					error = false;
				}
				else if(node1.isRoot())
				{
					//The root folder
					fullPath = data.get(0)+"\\";
					data.set(1, fullPath);
					error = false;
				}
				
				if(!error)
				{
					JDialog dialog = new JDialog(window,"Create new Folder");
					
					JTextField newFichier = new JTextField();
					newFichier.setBackground(Color.BLACK);
					newFichier.setForeground(Color.WHITE);
					newFichier.setCaretColor(Color.WHITE);
					newFichier.setFont(new Font("Times New Roman", Font.BOLD, 14));
					
					JButton creer = new JButton("Create");
					creer.setFocusable(false);
					
					creer.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {
							
							String newPath = newFichier.getText()+"\\";
							
							File nouveauFolder = new File(data.get(1)+""+newPath);
							
							if(!nouveauFolder.exists())
							{
								nouveauFolder.mkdir();
								dialog.dispose();
								
								JOptionPane.showMessageDialog(window, "New folder created!");
								loadFile();
							}
						}
					});
					
					dialog.setLayout(new GridLayout(2,1));
					dialog.add(newFichier);
					dialog.add(creer);
					dialog.setSize(200, 100);
					dialog.setVisible(true);
				}
			}
		}
		else if(s.equals("Open"))
		{
			Chooser project = new Chooser(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath(), window, "dir");
			
			if(project.getDirectoryPath() != null)
			{
				data.set(0, project.getDirectoryPath());
				data.set(2, project.getDirectoryName());
				
				loadFile();
			}
		}
		else if(s.equals("Save"))
		{
			if(editorTab.getTabCount() > 0)
			{
				SaveFile();
			}
		}
		else if(s.equals("Close All"))
		{
			if(editorTab.getTabCount() > 0)
			{
				editorTab.removeAll();
			}
		}
		else if(s.equals("Exit"))
		{
			System.exit(0);
		}
		else if(s.equals("Cut"))
		{
			if(editorTab.getTabCount() > 0)
			{
				JViewport selectview = ((JScrollPane) editorTab.getComponentAt(editorTab.getSelectedIndex())).getViewport();
	
				if(((JTextPane) selectview.getView()).getSelectedText() != null)
				{
					((JTextPane) selectview.getView()).cut();
					
					if(!editorTab.getTitleAt(editorTab.getSelectedIndex()).startsWith("*"))
					{
						editorTab.setTitleAt(editorTab.getSelectedIndex(), "*"+editorTab.getTitleAt(editorTab.getSelectedIndex()));
					}
				}
			}
		}
		else if(s.equals("Copy"))
		{
			if(editorTab.getTabCount() > 0)
			{
				JViewport selectview = ((JScrollPane) editorTab.getComponentAt(editorTab.getSelectedIndex())).getViewport();
	
				if(((JTextPane) selectview.getView()).getSelectedText() != null)
				{
					((JTextPane) selectview.getView()).copy();
				}
			}
		}
		else if(s.equals("Paste"))
		{
			if(editorTab.getTabCount() > 0)
			{
				JViewport selectview = ((JScrollPane) editorTab.getComponentAt(editorTab.getSelectedIndex())).getViewport();
	
				((JTextPane) selectview.getView()).paste();
				
				if(!editorTab.getTitleAt(editorTab.getSelectedIndex()).startsWith("*"))
				{
					editorTab.setTitleAt(editorTab.getSelectedIndex(), "*"+editorTab.getTitleAt(editorTab.getSelectedIndex()));
				}
			}
		}
		else if(s.equals("Print"))
		{
			if(editorTab.getTabCount() > 0)
			{
				JViewport selectview = ((JScrollPane) editorTab.getComponentAt(editorTab.getSelectedIndex())).getViewport();
	
				try 
				{
					((JTextPane) selectview.getView()).print();
				} 
				catch (PrinterException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else if(s.equals("Viewer"))
		{
			if(!data.get(0).equals("") && (data.get(1).endsWith(".html") || data.get(1).endsWith(".htm")))
			{
				Platform.runLater(new Runnable() {
	                @Override
	                public void run() 
	                {
	                   view.setScene(viewerPane.begin(data.get(1), view.getWidth(), view.getHeight())); 
	                }
	            });
			}
		}
		else if(s.equals("Browser"))
		{
			if(!data.get(0).equals("") && (data.get(1).endsWith(".html") || data.get(1).endsWith(".htm")))
			{
				try
				{	
					File link = new File(data.get(1));
					Desktop.getDesktop().browse(link.toURI());
				} 
				catch (IOException e4) 
				{
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
			}
		}
		else if(s.equals("About"))
		{
			
		}
		else if(s.equals("Developer website"))
		{
			try
			{	
				URL link = new URL("https://liwenstudios.fun/");
				Desktop.getDesktop().browse(link.toURI());
			} 
			catch (IOException e4) 
			{
				// TODO Auto-generated catch block
				e4.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
