import java.io.*;
import java.net.*;

public class ClientTest {

    public static void main(String[] args) {
        try {
            // create socket connection to server
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connected to server");

            // create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            // start chat
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (true) {
                // read user input
                System.out.print("Client: ");
                message = reader.readLine();

                // send message to server
                out.println(message);

                // read response from server
                message = in.readLine();
                if (message == null) {
                    // server disconnected
                    break;
                }
                System.out.println("Server: " + message);
            }

            // close streams and socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
