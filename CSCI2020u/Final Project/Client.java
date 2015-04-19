import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Client{
	Socket commSocket;
	InputStream input;
	OutputStrema output;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	String match = "[\\d]*"
	
	static public void main(String[] args) throws Exception{
		Client c = new Client()
	}

	public Client(){
		try{
			commsocket = new Socket();
		}catch(Exception e){
		}
		input = commsocket.getInputStream();
		output = commsocket.getOutputStream();
		dataIn = new DataInputStream(input);
		dataOut = new DataOutputStream(output);
	}
	public void connect() throws Exception{
		Thread 
	}

	static class Send extends Thread{
		DataOutputStream dataOut;

		public Send(DataOutputStream dataOut){
			this.dataOut = dataOut;
		}
		public void run(){
			Scanner scanner = new Scanner(System.in);
			Pattern pattern = Pattern.compile(match);
			Matcher matcher;
			String line;
			while(true){
				try{
					line = scanner.nextLine();
					matcher = pattern.matcher(line);
					if(matcher.find()==true){
						dataOut.writeUTF(line);
					}
					else{
						System.out.println("Invalid number");
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
					System.out.println(dataIn.readUTF());
				}catch(Exception e){
					break;
				}
			}
		}
	}
}