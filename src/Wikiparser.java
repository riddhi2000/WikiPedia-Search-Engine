import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;
import java.util.Map.Entry;


class WikiHandler extends DefaultHandler{
	public static HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	
	TextProcessing tp=new TextProcessing();

	boolean text = false,id=false,title=false;
	StringBuilder currentText = new StringBuilder();

	/******************Start Element*****************/
	public void startElement(String uri,String localName, String qName, 
	Attributes attributes)throws SAXException {
		if (qName.equalsIgnoreCase("text")) {
			text = true;
		}
		else if (qName.equalsIgnoreCase("id")) {
			id = true;
		}
		else if (qName.equalsIgnoreCase("title")) {
			title = true;
		}
    }

    /*********************No of char**************************/
    public void characters(char[] ch, int start, int length){
		if (text){
   			currentText.append(ch, start, length);
		}
   		else if (id){
   			currentText.append(ch, start, length);
   		} 
   		else if (title){
   			currentText.append(ch, start, length);			
   		}
	}

	/****************end Element************************/
	public void endElement(String uri, String localName, String qName) throws SAXException {
      	String str=currentText.toString();
        

		if (text){
   			text=false;
   			tp.processText(str);
		}
   		else if (title){
   			title = false;
   			tp.processTitle(str);
   		}
   		else if (id){
   			id = false;
   			tp.processId(str);
   		}
		currentText.setLength(0);
		text=false;id=false;title=false;
	}
}


/*******************************Main File******************************************/

public class Wikiparser{

	public static void main(String argv[]){
		try{
			String inputFile=argv[0];
			String outputFile=argv[1];

			long startTime = System.currentTimeMillis();
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
        	SAXParser saxParser = factory.newSAXParser();

        	WikiHandler wikihandler = new WikiHandler();
        	saxParser.parse(inputFile, wikihandler);

        	
        	WriteIndex wi=new WriteIndex();
        	wi.writeIndexToFile(outputFile);

        	/************calculate time****************/
        	long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime);
		    
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}