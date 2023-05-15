import java.io.*;
import java.net.*;

public class ServerTest {
    public static void main(String[] args) {
        try {
            // create server socket
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started on port 1234");

            // wait for client to connect
            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket);

            // create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            // start chat
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (true) {
                // read client message
                message = in.readLine();
                if (message == null) {
                    // client disconnected
                    break;
                }
                System.out.println("Client: " + message);

                // send response
                System.out.print("Server: ");
                message = reader.readLine();
                out.println(message);
            }

            // close streams and socket
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
