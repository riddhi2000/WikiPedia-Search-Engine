import java.util.*;

public class IndexCreater{
	public static TreeMap<String, TreeMap<String, Fields>> indexMap = new TreeMap<String,TreeMap<String,Fields>>();
	
	public void addToMap(String word,String type,String id){

		Stemmer stemmer_object = new Stemmer();
		stemmer_object.add(word.toCharArray(),word.length());
		try{
        	word=stemmer_object.stem();
        }
        catch(Exception e){

        }

        if(	word != null && id!=null && word.length() > 2){
        	if(indexMap.get(word) == null){
			TreeMap<String,Fields> temp_map=new TreeMap<String,Fields>();
			indexMap.put(word,new TreeMap<String,Fields>());
		}

		if(indexMap.get(word).get(id) == null){
            indexMap.get(word).put(id, new Fields());
		}
		if (type == "Text")
			indexMap.get(word).get(id).text++;
		else if(type == "Infobox")
			indexMap.get(word).get(id).infobox++;
		else if(type == "Category")
			indexMap.get(word).get(id).category++;
		else if(type == "References")
			indexMap.get(word).get(id).references++;
		else if(type == "External Links")
			indexMap.get(word).get(id).extLinks++;
		else if(type == "Title")
			indexMap.get(word).get(id).title++;
        }
		
	}

}