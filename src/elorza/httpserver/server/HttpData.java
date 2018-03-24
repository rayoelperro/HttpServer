package elorza.httpserver.server;

public class HttpData {
	
	public byte[] content;
	public String mime;
	
	public HttpData(byte[] content, String mime){
		this.content = content;
		this.mime = mime;
	}
	
	public HttpData(String content, String mime){
		this.content = content.getBytes();
		this.mime = mime;
	}
	
}
