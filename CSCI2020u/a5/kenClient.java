import java.io.*;
import java.net.*;

class kenClient {
    Socket socket;

    static class Sender extends Thread {
        DataOutputStream dataOut;
        Helper helper;
        public Sender(DataOutputStream dataOut) {
            this.dataOut = dataOut;
            this.helper = new Helper();
        }
        public void run() {
            while(true) {
                double number = helper.number();
                try {
                    dataOut.writeDouble(number);
                    dataOut.flush();
                    synchronized(System.out) {
                      System.out.println(">>" + number);
                    }
                    Thread.sleep(100);
                } catch(Exception e) {
                    break;
                }
            }
        }
    }

    static class Receiver extends Thread {
        DataInputStream dataIn;
        public Receiver(DataInputStream dataIn) {
            this.dataIn = dataIn;
        }
        public void run() {
            while(true) {
                try {
                    double number = dataIn.readDouble();
                    synchronized(System.out) {
                        System.out.println("<<<<<<<<<< " + number);
                        for(int i=0; i < 60; i++)
                            System.out.print("=");
                        System.out.println();
                    }
                } catch(Exception e) {
                    break;
                }
            }
        }
    }

    public kenClient(String hostname, int port) throws Exception {
        this.socket = new Socket(hostname, port);
    }

    public void connect() throws Exception {
        InputStream in = this.socket.getInputStream();
        OutputStream out = this.socket.getOutputStream();
        DataInputStream dataIn = new DataInputStream(in);
        DataOutputStream dataOut = new DataOutputStream(out);

        // Handle the connection
        System.out.println("Handling connection to server");
        Thread sender = new Sender(dataOut);
        Thread receiver = new Receiver(dataIn);

        sender.start();
        receiver.start();

        sender.join();
        receiver.join();
    }

    public static void main(String[] args) throws Exception {
        kenClient c = new kenClient("localhost", 3000);
        c.connect();
    }
}
