import java.net.*;
import java.sql.*;
import java.io.*;

public class ServerSQL {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/courses";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Server started");

            while (true) {
                Socket socket = null;
                BufferedReader in = null;
                PrintWriter out = null;
                try {
                    socket = serverSocket.accept();
                    System.out.println("Client connected");
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

                    String request = in.readLine();
                    System.out.println("Received request: " + request);

                    String[] parts = request.split(":");
                    String functionName = parts[0];
                    String[] params = new String[parts.length - 1];
                    System.arraycopy(parts, 1, params, 0, params.length);

                    switch (functionName) {
                        case "insert":
                            String name = params[0];
                            String course = params[1];
                            String fee = params[2];
                            boolean insertResult = insert(name, course, fee);
                            if (insertResult) {
                                out.println("Record inserted successfully");
                            } else {
                                out.println("Failed");
                            }
                            break;

                        case "edit":
                            int id = Integer.parseInt(params[0]);
                            name = params[1];
                            course = params[2];
                            fee = params[3];
                            boolean editResult = edit(id, name, course, fee);
                            if (editResult) {
                                out.println("Record updated successfully");
                            } else {
                                out.println("Failed");
                            }
                            break;

                        case "delete":
                            id = Integer.parseInt(params[0]);
                            boolean deleteResult = delete(id);
                            if (deleteResult) {
                                out.println("Record deleted successfully");
                            } else {
                                out.println("Failed");
                            }
                            break;

                        case "read":
                            String readResult = read();
                            out.println(readResult);
                            break;

                        case "exit":
                            System.out.println("Closing connection");
                            socket.close();
                            in.close();
                            out.close();
                            return;

                        default:
                            System.out.println("Invalid request: " + request);
                            out.println("Invalid request");
                            break;
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    out.println("Failed");
                } finally {
                    if (socket != null) {
                        socket.close();
                    }
                }
            }
        }
    }

    public static boolean insert(String name, String course, String fee) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO records (name, course, fee) VALUES (?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, course);
        stmt.setString(3, fee);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return true;
    }

    public static boolean edit(int id, String name, String course, String fee) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        PreparedStatement stmt = conn.prepareStatement("UPDATE records SET name=?, course=?, fee=? WHERE id=?");
        stmt.setString(1, name);
        stmt.setString(2, course);
        stmt.setString(3, fee);
        stmt.setInt(4, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return true;
    }

    public static boolean delete(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM records WHERE id=?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return true;
    }

    public static String read() throws SQLException {
        StringBuilder sb = new StringBuilder();
        // connect to database
        Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement stmt = conn.createStatement();
        // execute query
        ResultSet rs = stmt.executeQuery("SELECT * FROM records");      
        // retrieve results
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String course = rs.getString("course");
            String fee = rs.getString("fee");

            sb.append(String.format("%d,%s,%s,%S||", id, name, course, fee));
        }

        // close resources
        rs.close();
        stmt.close();
        conn.close();

        return sb.toString();
    }

}