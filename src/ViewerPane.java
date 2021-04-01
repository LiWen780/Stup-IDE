package stupIDE;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

public class ViewerPane{

    private WebView webView;
	
    public ViewerPane()
    {
    	
    } 
	
	public Scene begin(String file_path, double width, double height)
	{	
		webView = new WebView();
        webView.getEngine().setJavaScriptEnabled(true);
        webView.setMinSize(width, height);
           
        webView.getEngine().load(new File(file_path).toURI().toString());
     

        StackPane root = new StackPane(webView);
        Scene scene = new Scene(root, width, height);
        
        return scene;	
	}
	
	public void changeUrl(String file_path)
	{
		webView.getEngine().load(file_path);
		webView.getEngine().reload();
	}

}

