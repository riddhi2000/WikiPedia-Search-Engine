import java.util.*;
import java.io.*;
import java.util.Map.Entry;

public class TextProcessing{
	
	public static HashMap<String, Integer> stopWordmap=new HashMap<String, Integer>();
	IndexCreater ic=new IndexCreater();
	public String curId;

	public TextProcessing(){
		loadStopWord("stopwords.txt");
	}
	
	/***************************Case Folding***********************************/
	String caseFold(String text){
		return text.toLowerCase();
	}

	String [] tokenize(String text){
		return text.split("\\s+");
	}

	/************************Load Stop word from txt File*************************/
	void loadStopWord(String filename){
		try{
			Scanner scanner = new Scanner(new FileReader("stopwords.txt"));
			while (scanner.hasNextLine()) {
	            String word = scanner.nextLine();
	            stopWordmap.put(word, 1);
	        }
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
	}
/************************ Stop word Processing*************************/
	String[] removeStopWord(String[] words){
		for (int i = 0; i < words.length; i++){
		    
		    if (stopWordmap.containsKey(words[i])){
		        words[i] = null;
		        break;
		    }
		}
		return words;
	}

/***************Process Title*************************************/
	
	void processTitle(String title){
		int len=title.length();
		String curText="";

		for(int i=0;i<len;i++){
			char ch=title.charAt(i);
			if((ch>=65 &&ch<=90) || (ch>=97 && ch<=122) ){
				curText += Character.toLowerCase(ch);
			}
			else{
				curText=curText.trim();

				if(curText.length() > 2 && !(stopWordmap.containsKey(curText))){
					ic.addToMap(curText,"Title",curId);
				}
				curText="";
			}
		}

	}

/**********************processId********************************/
	void processId(String id){
		curId=id;
	}


/*******************Process Text**********************************/

	void processText(String text){
		int len=text.length();
		String curText="";
		boolean endFlag=false;

		for (int i=0;i<len;i++){
			char ch=text.charAt(i);
			/******************Checking For Infobox*******************/
			if(ch == '{'){
				boolean flag=false;
				if(i+9<len && "{Infobox".contains(text.substring(i+1, i+9) )) {
					i=i+9;
					
					int braceCount=2;
					for(;i < len;i++){
						ch=text.charAt(i);
						if(ch=='<'){
                            while(i<len && ch!='>'){
                                i++;
                                ch=text.charAt(i);
                            }
                            i++;
						}
						if(ch == '{')
							braceCount++;
						else if(ch == '}')
							braceCount--;

						if(ch=='['&&text.charAt(i+1)=='['){
                            flag=true;
                            i+=1;
                        }
                        else if(ch==']'&&(i+1)<len &&text.charAt(i+1)==']'){
                            flag=false;
						}

						if(braceCount == 0)
							break;
						if(flag){
							if((ch>=65 &&ch<=90) || (ch>=97 && ch<=122) ){
								curText+= Character.toLowerCase(ch);
								if(i == len-1)
									endFlag=true;
							}
							else{
								endFlag=true;
							}

							if(endFlag){
								if(curText.length() >2 && !(stopWordmap.containsKey(curText))){
									curText=curText.trim();
									ic.addToMap(curText,"Infobox",curId);
						  		}
								curText="";
								endFlag=false;
							}
						}
					}
				}
				else if((i+6)<len && "{cite".contains(text.substring(i+1, i+6).toLowerCase()) ||
					 ((i+4<len) && "{gr".contains(text.substring(i+1, i+4).toLowerCase()) ) ||
					 ((i+7)<len) && "{coord".contains(text.substring(i+1, i+7).toLowerCase()) ){
                	flag=true;
                    i+=6;
                    int count=2;
                    while(flag && i<len){
                        ch=text.charAt(i);
                        if(ch=='{'){
                            count++;
                        }
                        else if(ch=='}'){
                            count--;
                        }
                        if(count==0){
                            flag=false;
                        }
                        i++;
                    }
				}
			}
			else if(ch == '['){
				if(i+11<len && "[Category:".contains(text.substring(i+1, i+11)) ){

					i=i+11;
					curText="";
					for(;i < len;i++){
						ch=text.charAt(i);
						if(ch == ']' && (i+1<len && text.charAt(i+1)==']')){
							break;
						}
						if((ch>=65 &&ch<=90) || (ch>=97 && ch<=122) ){
							curText+= Character.toLowerCase(ch);
						}
						else
							endFlag=true;
						if(i == len-1)
							endFlag=true;
						if(endFlag){
							if( curText.length() > 2 &&  !(stopWordmap.containsKey(curText)) ){
								curText=curText.trim();
								ic.addToMap(curText,"Category",curId);
							}
							curText="";
							endFlag=false;
						}
					}
				}
				else if(((i+8)<len && "[image:".contains(text.substring(i+1, i+8).toLowerCase()))
					|| (i+7)<len && "[file:".contains(text.substring(i+1, i+7).toLowerCase()) ){

	                boolean flag=true;
	                i+=8;
	                int count=2;
	                while(flag && i<len){
	                    ch=text.charAt(i);
	                    if(ch=='['){
	                        count++;
	                    }
	                    else if(ch==']'){
	                        count--;
	                    }
	                    if(count==0){
	                        flag=false;
	                    }
	                    i++;
	                }
				}
			}
			else if(ch == '='){
				if(i+18<len && "=References==".contains(text.substring(i+1, i+14)) ){
					i=i+14;
				}
			}
			else if(ch == '='){
				if(i+18<len && "=External links==".contains(text.substring(i+1, i+18)) ){
					i=i+18;
				}
			}
			else if(ch=='<'){
                if((i+4)<len && "!--".contains(text.substring(i+1, i+4))){
                    i+=4;
                    int e=text.indexOf("-->",i);
                    if(e+2<len&&e>0){
                        i=e+2;
                    }
                }
                else if((i+8)<len && "gallery".contains(text.substring(i+1, i+8).toLowerCase())){
                    i+=8;
                    int end=text.indexOf("</gallery>",i);
                    if(end+10<len&&end>0){
                        i=end+10;
                    }
				}
			}
			else{
				if(ch=='#'){
                    while(i<len && ch!=' '){
                        ch=text.charAt(i);
                        i++;
                    }
                }
				else{
					if((ch>=65 &&ch<=90) || (ch>=97 && ch<=122) ){
						curText+= Character.toLowerCase(ch);
						if(i == len-1)
							endFlag=true;
					}
					else{
						endFlag=true;
					}
					if(endFlag){
						if(curText.length() >2 && !(stopWordmap.containsKey(curText))){
							curText=curText.trim();
							ic.addToMap(curText,"Text",curId);
				        }
						curText="";
						endFlag=false;
					}
				}
			}
		}
	}

}