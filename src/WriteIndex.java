import java.io.*;
import java.util.*;

public class WriteIndex{

	/**************Writing index to file**********************/
	public void writeIndexToFile(String outputFile){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
			for (Map.Entry<String, TreeMap<String,Fields>> entry : IndexCreater.indexMap.entrySet()) {
				String ans="";
			    String key = entry.getKey();
			    TreeMap<String,Fields> subMap = entry.getValue();
			    ans+=key;
			    ans+="|";
			    for(Map.Entry<String, Fields> entry1 : subMap.entrySet()){
			    	ans=ans + entry1.getKey() + ":";
			    	Fields f= entry1.getValue();
			    	if(f.title > 0)
			    		ans =ans +  "t" + f.title + ",";
			    	if(f.infobox > 0)
			    		ans = ans + "i" + f.infobox + ",";
			    	if(f.category > 0)
			    		ans = ans + "c" + f.category + ",";
			    	if(f.references > 0)
			    		ans = ans + "r" + f.references + ",";
			    	if(f.extLinks > 0)
			    		ans = ans+ "l" + f.extLinks + ";";
			    	if(f.text > 0)
			    		ans = ans + "b" + f.text ;
			    	ans+=";";
			    }
			    ans += "\n";
    			writer.write(ans);	
			}
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}