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
	String login;

	static public void main(String[] args) throws Exception{
		Client c = new Client(args[0],Integer.parseInt(args[1]),args[2]);
		c.connect();
	}

	public Client(String hostname, int socket, String name) throws Exception{
		commsocket = new Socket(hostname,socket);
		input = commsocket.getInputStream();
		output = commsocket.getOutputStream();
		dataIn = new DataInputStream(input);
		dataOut = new DataOutputStream(output);
		login = name;
	}

	public void connect() throws Exception{
		Thread interact = new Interact(this.dataOut,this.dataIn,login);
		interact.start();
		interact.join();
	}

	static class Interact extends Thread{
		DataOutputStream dataOut;
		DataInputStream dataIn;
		String login;
		String match = "(?<command>[A-Za-z]+)[\\s]?+(?<other>(.+))?[\\s]?+(?<message>(.+))?";

		public Interact(DataOutputStream dataOut, DataInputStream dataIn, String login){
			this.dataOut = dataOut;
			this.dataIn = dataIn;
			this.login = login;
			System.out.println("Ready");
		}
		public void run(){
			Scanner scanner = new Scanner(System.in);
			Pattern pattern = Pattern.compile(match);
			Matcher matcher;
			String line, in;
			try{
				dataOut.writeUTF("login " + login);
			}catch(Exception e){
				System.err.println("Login error");
			}
			while(true){
				try{

					line = scanner.nextLine();
					matcher = pattern.matcher(line);
					if(matcher.find()==true){
						if(matcher.group("command").compareToIgnoreCase("fetch")==0){
							dataOut.writeUTF("fetch "+login);
							in = dataIn.readUTF();
							if(in == ""){
								System.out.println("No new messages");
							}
							else{
								System.out.println(in);
							}
						}else if(matcher.group("command").compareToIgnoreCase("login")==0){
							dataOut.writeUTF(line);
						}
						else if(matcher.group("command").compareToIgnoreCase("send")==0){
							dataOut.writeUTF(line);
						}
					}else{
						System.out.println("Invalid command");
					}
				}catch(Exception e){
					break;
				} 
			}
		}
	}
}
