import java.net.*;
import java.io.*;

public class ServerConnectionTest {
    public static void main(String[] args) throws Exception {
        try {
            // create socket connection to server
            Socket socket = new Socket("localhost", 1234);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            // read user input
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter a message to send to the server: ");
            String message = reader.readLine();

            // send message to server
            out.write(message.getBytes("UTF-8"));

            // read response from server
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            String response = new String(buffer, 0, bytesRead, "UTF-8");
            System.out.println("Server response: " + response);

            // close socket and streams
            socket.close();
            out.close();
            in.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
