package elorza.httpserver.server;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import elorza.httpserver.utils.Utils;

public class HttpServerHandler extends HttpBaseServer {

	private Map<String, File> Handlers;
	private boolean allowFolders;
	
	public HttpServerHandler(int port, File root, boolean allowFolders) throws IOException {
		super(port);
		Handlers = new HashMap<String, File>();
		this.allowFolders = allowFolders;
		if(!root.exists())
			throw new IOException("File does not exists");
		else if(root.isDirectory() && !allowFolders)
			throw new IOException("You setted a directory instead of a file");
		Handlers.put("", root);
	}
	
	public HttpServerHandler(int port, Map<String, File> handlers, boolean allowFolders) throws IOException {
		super(port);
		Handlers = new HashMap<String, File>();
		this.allowFolders = allowFolders;
		Handlers = handlers;
	}
	
	public void setHandler(String hname, File hcontent) throws IOException {
		if(!hcontent.exists())
			throw new IOException("File does not exists");
		else if(hcontent.isDirectory() && !allowFolders)
			throw new IOException("You setted a directory instead of a file");
		Handlers.put(hname, hcontent);
	}
	
	public void removeHandlers(){
		Handlers.clear();
	}
	
	@Override
	protected HttpData loadContent(String fileName) throws IOException {
    	File file = new File(fileName);
    	if(Handlers.containsKey(fileName))
    		if(file.isDirectory())
    			return new HttpData(Utils.getFiles(Handlers.get(fileName).getAbsolutePath(),true),".html");
    		else
    			return new HttpData(Utils.readFile(Handlers.get(fileName).getAbsolutePath()),Handlers.get(fileName).getAbsolutePath());
    	else
    		return new HttpData(Utils.Error404(fileName),".html");
    }

}
