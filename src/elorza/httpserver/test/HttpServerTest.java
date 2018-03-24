package elorza.httpserver.test;

import elorza.httpserver.utils.ServerController;

public class HttpServerTest {
	public static void main(String[] args){
		ServerController sv = new ServerController();
		sv.port = 8080;
		sv.mode = 1;
		sv.Save();
		sv.Run();
	}
}
