package stupIDE;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.*;

public class EditorListener {
	
    private int findLastNonWordChar(String text, int index) 
	{
        while (--index >= 0) 
		{
            if (String.valueOf(text.charAt(index)).matches("\\W"))
			{
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar (String text, int index) 
	{
        while (index < text.length()) 
		{
            if (String.valueOf(text.charAt(index)).matches("\\W")) 
			{
                break;
            }
            index++;
        }
        return index;
    }
    
    
    private void updating(int offset, String str, DefaultStyledDocument document) throws BadLocationException
    {
    	content = container.getText();
    	
    	if(str.length() < 2)
		{
			document.setCharacterAttributes(0, content.length(), attrBlack, false);
		}
    	
    	String text = document.getText(0, document.getLength());
		int before = findLastNonWordChar(text, offset);
		if (before < 0) before = 0;
		int after = findFirstNonWordChar(text, offset + str.length());
		int wordL = before;
		int wordR = before;
		
		//-----------------------------
		
		if(fileType.equals("JavaScript"))
		{
			//Customize keywords
			while (wordR <= after) 
			{
				if(wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W"))
				{	
					if (text.substring(wordL, wordR).matches("(\\W)*(await|break|case|catch|class|const|continue|debugger|default|delete|do|else|enum|export|extends|false|finally|for|function|if|implements|import|in|instanceof|interface|let|new|null|package|private|protected|public|return|super|switch|self|static|this|throw|try|true|typeof|var|void|while|with|yield)"))
					{
						document.setCharacterAttributes(wordL, wordR - wordL, attr, false);
					}
					else if(text.substring(wordL, wordR).matches("(\\W)*(document|console|window|eval|toString|undefined|valueOf|Number|parseInt|Date|isNaN|NaN|alert|setInterval|clearInterval|innerHeight|innerWidth|screenX|screenY|prompt|confirm|onkeydown|onkeypress|onkeyup|onmouseover|onclick|onerror|onfocus|onload|onmouseup|onmousedown|onsubmit)"))
					{
						document.setCharacterAttributes(wordL, wordR - wordL, attrW, false);
					}
					else
					{
						document.setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
					}
					
					wordL = wordR;
				}
				
				wordR++;
			}
			
			//Customize String 			
			//strings double quotes		
			Pattern doublequote = Pattern.compile("\"");
			Matcher mat = doublequote.matcher(text);
			int beg = 0; int end = 0;
			
			ArrayList<Integer> debut = new ArrayList<Integer>();
			ArrayList<Integer> fin = new ArrayList<Integer>();
			
			while(mat.find())
			{
				beg = mat.start();
				mat.find();
				end = mat.start();
				
				debut.add(beg);
				fin.add(end);
				
				document.setCharacterAttributes(beg, end - beg + 1, attrS, true);
			}
			
			//strings single quote
			Pattern pl2 = Pattern.compile("\'");
			mat = pl2.matcher(text);
			
			while(mat.find())
			{
				beg = mat.start();
				mat.find();
				end = mat.start();
				
				boolean ins = false;
				
				for(int i = 0; i < debut.size(); i++)
				{
					if(beg > debut.get(i) && beg < fin.get(i))
					{
						ins = true;
						break;
					}
				}
				
				if(!ins)
				{
					document.setCharacterAttributes(beg, end - beg + 1, attrS, true);
				}
			}
			
			//Customize single-line and multiple-lines comments
			
			Pattern singleLinecommentsPattern = Pattern.compile("\\/\\/.*");
			Matcher matcher = singleLinecommentsPattern.matcher(text);
	
			while (matcher.find()) 
			{
			    document.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), attrC, true);
			}
	
			Pattern multipleLinecommentsPattern = Pattern.compile("\\/\\*.*?\\*\\/", Pattern.DOTALL);
			matcher = multipleLinecommentsPattern.matcher(text);
	
			while (matcher.find()) 
			{
			    document.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), attrC, true);
			}
		}
		else if(fileType.equals("HTML"))
		{
			//Balises
			Pattern balise = Pattern.compile("<.*?>");
			Matcher mat = balise.matcher(text);
			
			while (mat.find()) 
			{
			    document.setCharacterAttributes(mat.start(), mat.end() - mat.start(), attrT, true);
			}
			
			//strings double quotes		
			Pattern doublequote = Pattern.compile("\"");
			mat = doublequote.matcher(text);
			int beg = 0; int end = 0;
			
			ArrayList<Integer> debut = new ArrayList<Integer>();
			ArrayList<Integer> fin = new ArrayList<Integer>();
			
			while(mat.find())
			{
				beg = mat.start();
				mat.find();
				end = mat.start();
				
				debut.add(beg);
				fin.add(end);
				
				document.setCharacterAttributes(beg, end - beg + 1, attrS, true);
			}
			
			//strings single quote
			Pattern pl2 = Pattern.compile("\'");
			mat = pl2.matcher(text);
			int beg1 = 0; int end1 = 0;
			
			while(mat.find())
			{
				beg1 = mat.start();
				mat.find();
				end1 = mat.start();
				
				boolean ins = false;
				
				for(int i = 0; i < debut.size(); i++)
				{
					if(beg1 > debut.get(i) && beg1 < fin.get(i))
					{
						ins = true;
						break;
					}
				}
				
				if(!ins)
				{
					document.setCharacterAttributes(beg1, end1 - beg1 + 1, attrS, true);
				}
			}
			
			//Comments
			Pattern comm = Pattern.compile("<\\!--.*?-->");
			mat = comm.matcher(text);
			
			while(mat.find())
			{
				document.setCharacterAttributes(mat.start(),  mat.end() - mat.start(), attrC, true);
			}
		}
		else if(fileType.equals("CSS"))
		{
			//comment
			Pattern balise = Pattern.compile("\\/\\*.*?\\*\\/", Pattern.DOTALL);
			Matcher mat = balise.matcher(text);
			
			while (mat.find()) 
			{
			    document.setCharacterAttributes(mat.start(), mat.end() - mat.start(), attrC, true);
			}
			
			//style
			Pattern style = Pattern.compile("{.*}");
			mat = style.matcher(text);
			
			while (mat.find()) 
			{
			    document.setCharacterAttributes(mat.start(), mat.end() - mat.start(), attr, true);
			}
			
			//string
			Pattern string = Pattern.compile("\"");
			mat = string.matcher(text);
			
			while(mat.find())
			{
				document.setCharacterAttributes(mat.start(), mat.end() - mat.start(), attrS, true);
			}
		}
	}
    
    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(246,40,23));
    final AttributeSet attrW = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(21,105,199));
    final AttributeSet attrC = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(94,125,126));
    final AttributeSet attrS = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,174,66));
    final AttributeSet attrT = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(226,56,236));
    AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.WHITE);
    
    private String fileType = "";
    private String content = "";
    private JTextPane container;
	
    private DefaultStyledDocument doc = new DefaultStyledDocument() 
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void insertString (int offset, String str, AttributeSet a) throws BadLocationException 
		{
			super.insertString(offset, str, a);
			
			updating(offset,str, this);
		}

        public void remove (int offs, int len) throws BadLocationException 
		{
        	super.remove(offs, len);
            
            content = container.getText();
            String text = getText(0, getLength());
            
            //Customize String 
			
			Pattern pl = Pattern.compile("\"");
			Matcher match2 = pl.matcher(text);
			int beg = 0; int end = 0;
			
			while(match2.find())
			{
				beg = match2.start();
				match2.find();
				end = match2.start();
				
				setCharacterAttributes(beg, end - beg + 1, attrS, true);
			}
            
            //-----Customize single-line and multiple-lines comments
			
			Pattern singleLinecommentsPattern = Pattern.compile("\\/\\/.*");
			Matcher matcher = singleLinecommentsPattern.matcher(text);

			while (matcher.find()) 
			{
			    setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), attrC, true);
			}

			Pattern multipleLinecommentsPattern = Pattern.compile("\\/\\*.*?\\*\\/", Pattern.DOTALL);
			matcher = multipleLinecommentsPattern.matcher(text);

			while (matcher.find()) 
			{
			    setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), attrC, true);
			}
        }
    };
        
    EditorListener()
	{
    	
    }
    
    public DefaultStyledDocument getDoc()
	{
    	return doc;
    }
    
    public void setFileType(String type)
    {
    	this.fileType = type;
    }
    
    public void setContainer(JTextPane re)
    {
    	container = re;
    }
    
    EditorListener(JTextPane txt) 
	{	       
    	attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, txt.getForeground());   	
    }
}

