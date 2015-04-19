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
		Dictionary dictionary = new Dictionary("dictionary.txt");
		Word2PhoneMapper mapper = new Word2PhoneMapper();
		while(true){
			Socket socket = serverSocket.accept();
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			DataInputStream dataIn = new DataInputStream(input);
			DataOutputStream dataOut = new DataOutputStream(output);
			Helper helper = new Helper();
			(new Recieve(dataIn,helper)).start();
			(new Helper(dictionary,mapper));
			(new Send(dataOut,helper)).start();


		}
	}


	static class Send extends Thread{
		DataOutputStream dataOut;
		int userNum;
		public Send(DataOutputStream dataOut, int userNum){
			this.dataOut = dataOut;
			this.userNum = userNum;
		}
		public void run(){
			String output;
			while(true){
				try{
					synchronized(System.out){
						System.out.println(userNum + "<<" + );
					}
				}catch(Exception e){
					break;
				}
			}
		}
	}

	static class Recieve extends Thread{
		DataInputStream dataIn;
		int userNum;
		public Recieve(DataInputStream dataIn,int userNum){
			this.dataIn = dataIn;
			this.userNum = userNum;
		}
		public void run(){
			while(true){
				try{
					synchronized(System.out){
						System.out.println(userNum + ">>" + dataIn.readUTF());
					}
				}catch(Exception e){
					break;
				}
			}
		}
	}
}