package stupIDE;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
//import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class FilePopup extends JPopupMenu {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem delete;
	private JMenuItem rename;
	private JMenuItem edit;

	public FilePopup(JTree tree, MainWindow ui)
	{
		delete = new JMenuItem("Delete");
		delete.setBackground(new Color(32,32,32));
		delete.setForeground(Color.WHITE);
		
		rename = new JMenuItem("Rename");
		rename.setBackground(new Color(32,32,32));
		rename.setForeground(Color.WHITE);
		
		edit = new JMenuItem("Edit");
		edit.setBackground(new Color(32,32,32));
		edit.setForeground(Color.WHITE);
		
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				
				TreePath[] paths = tree.getSelectionPaths();
				
				if(paths != null)
				{
					for(TreePath path : paths)
					{
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
						
						if(node.getParent() != null)
						{
							String folder = node.getParent().toString(); 
							//String file = node.toString();
							
							if(folder.equals("images") || folder.equals("sounds"))
							{
								model.removeNodeFromParent(node);
								/*new FileManage().deleteFile(folder, file, ui);
								
								if(!ui.console.isEnabled())
					        	{
					        		ui.console.setForeground(Color.WHITE);
					        		ui.console.setEnabled(true);
					        	}
								ui.console.setText("Asset "+file+" deleted.");*/
							}
						}
					}
				}
			}
			
		});
		
		rename.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				JDialog diag = new JDialog(ui.window,"Rename");
				
				JTextField newName = new JTextField(tree.getLastSelectedPathComponent().toString().substring(0, tree.getLastSelectedPathComponent().toString().lastIndexOf(".")));
				newName.setBackground(Color.BLACK);
				newName.setForeground(Color.WHITE);
				newName.setCaretColor(Color.WHITE);
				newName.setFont(new Font("Times New Roman", Font.BOLD, 14));
				
				JButton renommer = new JButton("Rename");
				renommer.setFocusable(false);
				
				renommer.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						TreePath[] paths = tree.getSelectionPaths();
						String link1 = "";
						String namae = "";
						
						for(int s = 1; s < paths[paths.length-1].getPath().length; s++)
						{
							if(s < paths[paths.length-1].getPath().length -1)
							{
								link1 += paths[paths.length-1].getPath()[s].toString()+"//";
							}
							else
							{
								link1 += paths[paths.length-1].getPath()[s].toString();
								namae = paths[paths.length-1].getPath()[s].toString().substring(paths[paths.length-1].getPath()[s].toString().lastIndexOf("."));
							}
						}
						
						String fullPath = ui.data.get(0)+"\\"+link1;
						
						Path source = Paths.get(fullPath);
						
						try
						{
							// rename a file in the same directory
						    Files.move(source, source.resolveSibling(newName.getText()+""+namae));
						    
						    diag.dispose();
						    
						    ui.loadFile();
						} 
						catch (IOException r)
						{
						   r.printStackTrace();
						}
					}
				});
				
				diag.setLayout(new GridLayout(2,1));
				diag.add(newName);
				diag.add(renommer);
				diag.setSize(200, 100);
				diag.setVisible(true);
			}
		});
		
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//DefaultTreeModel model2 = (DefaultTreeModel) tree.getModel();
				
				TreePath[] paths = tree.getSelectionPaths();
				
				if(paths != null)
				{
					for(TreePath path : paths)
					{
						//DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) path.getLastPathComponent();
						
						String link = "";
						
						for(int s = 1; s < path.getPath().length; s++)
						{
							if(s < path.getPath().length -1)
							{
								link += path.getPath()[s].toString()+"//";
							}
							else
							{
								link += path.getPath()[s].toString();
							}
						}
						
						ui.setFile(new File(ui.data.get(0)+"//"+link));
					}
				}
			}
		});
		
		/*add(edit);
		add(rename);
		add(delete);*/
		//add(new JSeparator());
		//add(change);
	}
	
	public void addItem(boolean value)
	{
		if(value)
		{
			removeAll();
			add(edit);
			add(rename);
			add(delete);
		}
		else
		{
			removeAll();
			add(rename);
			add(delete);
		}
	}
}

