import java.io.*;
import java.net.*;

public class Test {

    public static void main(String[] args) {
        String str = "2,hh,jj,JJ||3,Mahdi,Math,77||4,fd,cc,TB||5,jrl,bdbd,BD||";

        // Split the string into an array of lines
        String[] lines = str.split("\\|\\|");
        for (String line : lines) {
            // Split the line into separate pieces of data
            String[] data = line.split(",");
            // Extract the values for each field
            System.out.println(line);
            System.out.println(data[0]);
            System.out.println(data[1]);
            System.out.println(data[2]);
            System.out.println(data[3]);
        }
    }
}
