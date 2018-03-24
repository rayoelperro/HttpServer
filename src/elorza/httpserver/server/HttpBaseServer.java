package elorza.httpserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import elorza.httpserver.utils.Utils;

public abstract class HttpBaseServer implements Runnable {
	
	protected final int port;
	protected boolean serverOn;

    private ServerSocket ssocket;
	
	public HttpBaseServer(int port){
		this.port = port;
	}
	
	public void start() {
        serverOn = true;
        new Thread(this).start();
    }
    
    public void stop() {
        try {
            serverOn = false;
            if (null != ssocket) {
                ssocket.close();
                ssocket = null;
            }
        } catch (IOException e) {
        	
        }
    }
    
    @Override
    public void run() {
        try {
            ssocket = new ServerSocket(port);
            while (serverOn) {
                Socket socket = ssocket.accept();
                handle(socket);
                socket.close();
            }
        } catch (SocketException e) {
        } catch (IOException e) {
        	
        }
    }
    
    public int getPort() {
        return port;
    }
    
    private void handle(Socket socket) throws IOException {
        BufferedReader reader = null;
        PrintStream output = null;
        try {
            String route = null;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("GET /")) {
                    int start = line.indexOf('/') + 1;
                    int end = line.indexOf(' ', start);
                    route = line.substring(start, end);
                    break;
                }
            }
            HttpData params = loadContent(route);
            output = new PrintStream(socket.getOutputStream());
            if (null == route) {
                writeServerError(output);
                return;
            }
            byte[] bytes = params.content;
            if (null == bytes) {
                writeServerError(output);
                return;
            }
            output.println("HTTP/1.0 200 OK");
            output.println("Content-Type: " + detectMimeType(params.mime));
            output.println("Content-Length: " + bytes.length);
            output.println();
            output.write(bytes);
            output.flush();
        } finally {
            if (null != output) {
                output.close();
            }
            if (null != reader) {
                reader.close();
            }
        }
    }
    
    private void writeServerError(PrintStream output) {
        output.println("HTTP/1.0 500 Internal Server Error");
        output.flush();
    }
    
    protected HttpData loadContent(String fileName) throws IOException {
    	return new HttpData(Utils.readFile(fileName).toString(),fileName);
    }
    
	protected String detectMimeType(String fileName) {
		fileName = fileName.toLowerCase();
    	if (fileName == null || fileName.isEmpty()) {
            return null;
        } else if (fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".js")) {
        	return "text/javascript";
        } else if (fileName.endsWith(".css")) {
            return "text/css";
        } else if (fileName.endsWith(".txt")) {
            return "text/plain";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else {
            return "application/octet-stream";
        }
    }
}
