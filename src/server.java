// took a lot of this from professor hudson,
// ~jwhudson/CPSC501F22/code/Reflection4GeneralPurpose
// Also took code from one of the TA's, it was uploaded to d2l. That is where the comments are from too

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;

import java.net.Socket;

import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class server {


    public static void main(String[] args) throws Exception {
        // 1. Creating a server socket object
        ServerSocket ss = new ServerSocket(6666); // listening for new client connection requests via port 6666

        // 2. Creating a separate socket object to accept connection
        Socket s = ss.accept();

        // 3. Creating data input/output streams dependent on software functionality requirements
        // Gets input and output streams from the socket that accepts client connections (input from the client, output to the client)
        InputStream is = s.getInputStream();
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(is);

        System.out.println("Received object. Attempting to deserialize the object.");
        System.out.println("--------------------------------");
        Visualizer vis = new Visualizer();

        String json_string = (String) ois.readObject();
        JsonReader json_reader = Json.createReader(new StringReader(json_string));
        JsonObject json_object = json_reader.readObject();

        System.out.println("Printing off the inspection of the deserialized object.");
        System.out.println("--------------------------------");

        Object obj = Deserializer.deserializeObject(json_object);
        vis.inspect(obj, true);


        // Closing data input and output streams
        dout.close();
        is.close();

        // Closing socket connections
        s.close();
        ss.close();
    }
}