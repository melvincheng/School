import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Server{
	ServerSocket serverSocket;
	public Server(int port) throws Exception{
		serverSocket = new ServerSocket(port);
	}
	static public void main(String[] args) throws Exception{
		int port = Integer.parseInt(args[0]);
		Server server = new Server(port);
		server.serverRun();
	}

	public void serverRun() throws Exception{
		System.out.println("Server is running");
		while(true){
			Socket socket = serverSocket.accept();
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			DataInputStream dataIn = new DataInputStream(input);
			DataOutputStream dataOut = new DataOutputStream(output);
			(new Recieve(dataIn)).start();

		}
	}
	static class Fetch extends Thread{
		DataOutputStream dataOut;
		public Fetch(DataOutputStream dataOut){
			this.dataOut = dataOut;
		}
		public void run(){
			try{
				dataOut.writeUTF("Server");
			}catch(Exception e){
				System.err.println("Fetch error");
			}
		}
	}

	static class Recieve extends Thread{
		DataInputStream dataIn;
		Map<String,ArrayList<String>> messages = new HashMap<String,ArrayList<String>>();
		String match = "(?<command>[A-Za-z]+)[\\s]?+(?<other>(.+))?[\\s]?+(?<message>(.+))?";
		public Recieve(DataInputStream dataIn){
			System.out.println("A user has connected");
			this.dataIn = dataIn;
		}
		public void run(){
			String in;
			ArrayList<String> tempMess;
			Scanner scanner = new Scanner(System.in);
			Pattern pattern = Pattern.compile(match);
			Matcher matcher;
			while(true){
				try{
					in = dataIn.readUTF();
					matcher = pattern.matcher(in);
					if(matcher.group("command").compareToIgnoreCase("login")==0){
						synchronized(this.messages){
						if(!messages.containsKey(matcher.group("other"))){
								messages.put(matcher.group("other"),(new ArrayList<String>()));
							}
						}
					}else if(matcher.group("command").compareToIgnoreCase("send")==0){
						synchronized(this.messages){
							if(messages.containsKey(matcher.group("message"))){
								tempMess = messages.get(matcher.group("other"));
								tempMess.add(matcher.group("other"));
								messages.put(matcher.group("other"),tempMess);
							}
						}
					}else if(matcher.group("command").compareToIgnoreCase("fetch")==0){
						synchronized(this.messages){
							if(message.containsKey(matcher.group("other"))){
								tempMess = message.get(matcher.group("other"));
								for(String temp:tempMess){
									dataOut.writeUTF(temp);
								}
							}
					}
				}catch(Exception e){
					break;
				}
			}
		}
	}
}