package ch.makery.adress.util;


import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import com.fasterxml.jackson.core.type.TypeReference;


public class Json {
	private static ObjectMapper objmap = new ObjectMapper().registerModules(new JavaTimeModule());
	
	public static <T> List<T> fromJSON(File _file, TypeReference<List<T>> type) {

		if(_file.exists())
		{
			List<T> data = null;
		try {
			data = objmap.readValue(_file, type);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
		}
		else {
			return null;
		}
	}
	
	
	public static void jsonWriteList(List _list, File file) {
		
		if(!file.exists()) {
			try {
			file.createNewFile();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			/*ObjectMapper objectmap = new ObjectMapper();
			objectmap.enable(SerializationFeature.INDENT_OUTPUT);
			objectmap.writeValue(file, _list);*/
			
			objmap.enable(SerializationFeature.INDENT_OUTPUT);
			objmap.writeValue(file, _list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
	
	
	
	
	
	
	
}


