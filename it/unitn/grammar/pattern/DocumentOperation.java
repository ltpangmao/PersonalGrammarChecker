package it.unitn.grammar.pattern;

import java.io.*;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DocumentOperation {

	
	
	    public static void main(String[] args)
	    {
	        File file = null;
	        WordExtractor extractor = null;
	        try
	        {
	        	String result = "";
	            file = new File("/Users/tongli/Desktop/REposter/poster.doc");
	            
	            if (file.getAbsolutePath().endsWith("doc")){
	            	result = readDocFile(file.getAbsolutePath());
	            } else if (file.getAbsolutePath().endsWith("docx")){
	            	result = readDocxFile(file.getAbsolutePath());
	            } else{
	            	System.out.println("Do not support this type of file!");
	            }
                System.out.println(result);
	        }
	        catch (Exception exep)
	        {
	            exep.printStackTrace();
	        }
	    }
	    
	    
	    public static String readDocFile(String fileName) {
	    	String result="";

	    	try {
				File file = new File(fileName);
				FileInputStream fis = new FileInputStream(file.getAbsolutePath());
	 
				HWPFDocument doc = new HWPFDocument(fis);
	 
				WordExtractor we = new WordExtractor(doc);
	 
				String[] paragraphs = we.getParagraphText();
				
				System.out.println("Total no of paragraph "+paragraphs.length);
				for (String para : paragraphs) {
					result+=para.toString();
				}
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
	 
		}
	 
		public static String readDocxFile(String fileName) {
			String result="";
			
			try {
				File file = new File(fileName);
				FileInputStream fis = new FileInputStream(file.getAbsolutePath());
	 
				XWPFDocument document = new XWPFDocument(fis);
	 
				List<XWPFParagraph> paragraphs = document.getParagraphs();
				
				System.out.println("Total no of paragraph "+paragraphs.size());
				for (XWPFParagraph para : paragraphs) {
//					System.out.println(para.getText());
					result += para.getText();
				}
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
		}
	 
	
}
