package elorza.httpserver.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
	public static byte[] readFile(String fileName) throws IOException {
   	 InputStream input = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            input = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            int size;
            while (-1 != (size = input.read(buffer))) {
                output.write(buffer, 0, size);
            }
            output.flush();
            return output.toByteArray();
        } catch(FileNotFoundException e){
        	return Error404(fileName).getBytes();
        } finally {
            if (null != input) {
                input.close();
            }
        }
	}
	
	public static String Error404(String fileName){
		if(fileName.equals("") || fileName.equals(null))
			fileName = "Error 404";
		else
			fileName += " does not exists";
		return "<html><head><title>Error 404</title></head><body><h1>" + fileName + "</h1></body></html>";
    }
	
	public static String getFiles(String folderName, boolean restrict){
		String tr = "<html><head><title>" + folderName.replace("|||", " ") + "</title></head><body>";
		for(File s : new File(folderName.replace("|||", " ")).listFiles())
			if(restrict)
				if(s.isDirectory())
					tr += "<h3>" + s.getName() + "</h3><br />";
				else
					tr += "<h4>" + s.getName() + "</h4><br />";
			else
				if(s.isDirectory())
					tr += "<a href=\"/" + s.getAbsolutePath().replace(" ", "|||") + "\"><h3>" + s.getName() + "</h3></a><br />";
				else
					tr += "<a href=\"/" + s.getAbsolutePath().replace(" ", "|||") + "\"><h4>" + s.getName() + "</h4></a><br />";
		tr += "</body></html>";
		return tr;
    }
	
	public static boolean isImage(String fileName){
		if(fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif"))
			return true;
		return false;
    }
	
}
