import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Client{
	Socket commSocket;
	InputStream input;
	OutputStream output;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	Boolean status = false;


	public Client(){

	}

	public void clientSetup(String hostname,int socket) throws Exception{
		try{
			commSocket = new Socket(hostname, socket);
		}catch(Exception e){
			
		}
		input = commSocket.getInputStream();
		output = commSocket.getOutputStream();
		dataIn = new DataInputStream(input);
		dataOut = new DataOutputStream(output);
		status = true;
	}
	public void connect() throws Exception{
		Thread send = new Send(dataOut,dataIn);
		Thread receive = new Receive(dataIn);
		send.start();
		receive.start();
		send.join();
		receive.join();
	}
	public Boolean connected(){
		return status;
	}
	static class Send extends Thread{
		DataOutputStream dataOut;
		DataInputStream dataIn;
		String numMatch = "[\\d]*";
		String wordMatch = "[\\w]*";

		public Send(DataOutputStream dataOut, DataInputStream dataIn){
			this.dataOut = dataOut;
			this.dataIn = dataIn;
		}
		public void run(){
			Scanner scanner = new Scanner(System.in);
			Pattern numPattern = Pattern.compile(numMatch);
			Pattern wordPattern = Pattern.compile(wordMatch);
			Matcher numMatcher;
			Matcher wordMatcher;
			String line;
			Boolean server = true;
			while(true){
				try{
					while(server){
						line = Gui.toServer;
						Gui.toServer = null;
						if(line != null){
							numMatcher = numPattern.matcher(line);
							wordMatcher = wordPattern.matcher(line);
							if(numMatcher.find()||wordMatcher.find()){
								dataOut.writeUTF(line);
								server = dataIn.readBoolean();
							}
							else{
								Gui.fromServer = "Invalid number";
							}
						}
					}
				}catch(Exception e){
					break;
				}
			}
		}
	}
	static class Receive extends Thread{
		DataInputStream dataIn;

		public Receive(DataInputStream dataIn){
			this.dataIn = dataIn;
		}
		public void run(){
			while(true){
				try{
					Gui.fromServer = dataIn.readUTF();
				}catch(Exception e){
					break;
				}
			}
		}
	}
}