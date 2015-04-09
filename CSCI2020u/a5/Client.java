import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Client{
	Socket commsocket;
	InputStream input;
	OutputStream output;
	DataInputStream dataIn;
	DataOutputStream dataOut;

	static public void main(String[] args) throws Exception{
		Client c = new Client(args[0],Integer.parseInt(args[1]));
		c.connect();
	}

	public Client(String hostname, int socket) throws Exception{
		commsocket = new Socket(hostname,socket);
		input = commsocket.getInputStream();
		output = commsocket.getOutputStream();
		dataIn = new DataInputStream(input);
		dataOut = new DataOutputStream(output);
	}

	public void connect() throws Exception{
		Thread interact = new Interact(this.dataOut);
		interact.start();
		iteract.join();
	}

	static class Interact extends Thread{
		DataOutputStream dataOut;
		String match = "(?<command>[A-Za-z]+)[\\s]+(?<other>(.+))";

		public Interact(DataOutputStream dataOut){
			this.dataOut = dataOut;
			Scanner scanner = new Scanner(System.in);
			String line;
			System.out.println("Ready");
			Pattern pattern = Pattern.compile(match);
			Matcher matcher;
		}
		public void run(){
			while(true){
				try{
					System.out.println("Command");
					line = scanner.nextLine();
					matcher = pattern.matcher(line);
					System.out.println(line);
					if(matcher.find()==true){
						System.out.println(matcher.group(0));
						if(matcher.group("command").compareToIgnoreCase("fetch")==0){
							String in = dataIn.readUTF();
							if(in == ""){
								System.out.println("No new messages");
							}
							else{
								System.out.println(in);
							};
						}else if(matcher.group("command").compareToIgnoreCase("send")==0){
							dataOut.writeUTF(matcher.group("other"));
							dataOut.flush();
						}
					}else{
						System.out.println("Invalid command");
					}
				} catch(Exception e){
					break;
				}
			}
		}
	}
}