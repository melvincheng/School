import java.io.*;
import java.net.*;

class kenServer {
    ServerSocket main;
    Helper helper;

    static class Accumulator extends Thread {
        DataInputStream dataIn;
        int index;
        Helper helper;

        public Accumulator(int index, DataInputStream in, Helper helper) {
            this.dataIn = in;
            this.index = index;
            this.helper = helper;
        }
        public void run() {
            while(true) {
                try {
                    double number = dataIn.readDouble();
                    helper.add(number);
                    System.out.println(
                            this.index 
                            + " << " 
                            + number
                            + " / "
                            + this.helper.total());
                } catch(Exception e) {
                    break;
                }
            }
        }
    }

    static class Reply extends Thread {
        DataOutputStream dataOut;
        Helper helper;

        public Reply(DataOutputStream out, Helper helper) {
            this.dataOut = out;
            this.helper = helper;
        }
        public void run() {
            while(true) {
                try {
                    double total = helper.total();
                    dataOut.writeDouble(total);
                    Thread.sleep(1000);
                } catch(Exception e) {
                    break;
                }
            }
        }
    }

    public kenServer(int port) throws Exception {
        this.main = new ServerSocket(port);
        this.helper = new Helper();
    }

    public void serve() throws Exception {
        int index = 1;
        while(true) {
            Socket socket = this.main.accept();
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);

            // handle the connection
            System.out.println("Handling connection to client");
            (new Accumulator(index, dataIn, this.helper)).start();
            (new Reply(dataOut, this.helper)).start();
            index += 1;
        }
    }

    public static void main(String[] args) throws Exception {
        kenServer s = new kenServer(3000);
        System.out.println("Serving....");
        s.serve();
    }
}
