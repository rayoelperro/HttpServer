package elorza.httpserver.server;

import java.io.File;
import java.io.IOException;

import elorza.httpserver.utils.Utils;

public class HttpServerNavigate extends HttpBaseServer {

	private boolean allowInspect;
	
	public HttpServerNavigate(int port, boolean allowInspect) {
		super(port);
		this.allowInspect = allowInspect;
	}

	@Override
	protected HttpData loadContent(String fileName) throws IOException {
		fileName = fileName.replace("|||", " ");
		File file = new File(fileName);
    	if(file.exists())
    		if(file.isDirectory() && allowInspect)
    			return new HttpData(Utils.getFiles(fileName, false),".html");
    		else
    			if(Utils.isImage(fileName))
    				return new HttpData(Utils.readFile(fileName),fileName);
    			else
    				return new HttpData(Utils.readFile(fileName),fileName);
    	else
    		return new HttpData(Utils.Error404(fileName),".html");
    }
	
}
