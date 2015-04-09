import java.io.*;
import java.net.*;

public class Server{
	static public void main(String[] args) throws Exception{
		int port = Integer.parseInt(args[0]);
		ServerSocket serversocket = new ServerSocket(port);
		Socket commsocket = serversocket.accept();
		InputStream input = commsocket.getInputStream();
		OutputStream output = commsocket.getOutputStream();
		DataInputStream dataIn = new DataInputStream(input);
		DataOutputStream dataOut = new DataOutputStream(output);
		Map<String,String[]> storage = new TreeMap<String,String[]>();
		String in = "";
		while(true){
			in = dataIn.readUTF();
			if(in.compareToIgnoreCase("fetch")){
				dataOut.writeUTF(in);
			}
		}

		static class fetch{
			public fetch(){
				dataOut.writeUTF("Server");
			}
		}

	}
}