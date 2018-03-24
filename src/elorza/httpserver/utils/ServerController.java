package elorza.httpserver.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import elorza.httpserver.server.*;

public class ServerController {
	
	public static final int HANDLER = 0;
	public static final int NAVIGATE = 1;
	
	private Map<String,File> handlers;
	
	private HttpBaseServer base;
	
	public int mode;
	
	public int port;
	
	public ServerController(){
		handlers = new HashMap<String, File>();
		base = null;
	}

	public void Save(){
		try {
			if(mode == HANDLER)
				base = new HttpServerHandler(port,handlers,false);			
			else if(mode == NAVIGATE)
				base = new HttpServerNavigate(port,true);
		} catch (IOException e) {
		}
	}
	
	public void AddHandler(String name, String file){
		handlers.put(name, new File(file));
	}
	
	public void Clear(){
		handlers.clear();
	}

	public void Run(){
		base.start();
	}
	
	public void Stop(){
		base.run();
	}
	
}
