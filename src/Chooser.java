package stupIDE;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Chooser{
	
	private String path;
	private JFileChooser chooser;
	private String directoryPath;
	private String directoryName;
	private String filePath;
	private String fileName;
	boolean approv = false;
	
	public Chooser()
	{
		return;
	}
	public Chooser(String set, JFrame fe, String type)
	{
		chooser = new JFileChooser(set);
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(chooser);
			
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
		
		if(type.equals("dir"))
		{
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		else
		{
	    	if(type.equals("image"))
	    	{
	    		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "png","jpg","jpeg", "gif");
	    		chooser.setFileFilter(filter);
	    	}
	    	else if(type.equals("sound"))
	    	{
	    		FileNameExtensionFilter filter = new FileNameExtensionFilter("Sounds", "mp3","wma","wav", "aac");
	    		chooser.setFileFilter(filter);
	    	}
	    	else if(type.equals("web"))
	    	{
	    		FileNameExtensionFilter filter = new FileNameExtensionFilter("Web Files", "html","htm","js", "css");
	    		chooser.setFileFilter(filter);
	    	}
		}
		
		int sr = chooser.showOpenDialog(fe);
		
		// If the user selects a file 
		if (sr == JFileChooser.APPROVE_OPTION) 
		{ 
		     // Set the label to the path of the selected directory 
		     path = chooser.getSelectedFile().getAbsolutePath();
		     approv = true;
		     
		     File req = new File(path);
		     
		     if(req.isDirectory())
		     {
		    	 directoryPath = path;
		    	 directoryName = path.substring(path.lastIndexOf("\\")+1);
		     }
		     else
		     {
		    	 directoryPath = chooser.getCurrentDirectory().getPath();
		    	 directoryName = directoryPath.substring(directoryPath.lastIndexOf("\\")+1);
		    	 
		    	 filePath = path;
		    	 fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
		     }		     
		     
		}
		else
		{
			JOptionPane.showMessageDialog(chooser, "There was no selection. The user cancelled the operation.");
			fe.setVisible(true);
		}
		
		Clean();
	}
	
	public String getDirectoryName()
	{
		return directoryName;
	}
	
	public String getDirectoryPath()
	{
		return directoryPath;
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public JFileChooser get()
	{
		return chooser;
	}
	
	private void Clean()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			//SwingUtilities.updateComponentTreeUI(reha);
			
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
	}

}
