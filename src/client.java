// took a lot of this from professor hudson,
// ~jwhudson/CPSC501F22/code/Reflection4GeneralPurpose
// Also took code from one of the TA's, it was uploaded to d2l

import javax.json.JsonObject;
import java.io.*;
import java.net.Socket;



public class client {


    public static void main(String[] args) throws Exception {
            Socket s = new Socket("localhost", 6666);
            System.out.println("Beginning to create objects to be serialized");
            //Sending object to server
            System.out.println("Sending object to server");
            Object obj = ObjectCreator.sendObject();
            System.out.println("Serializing object");
            FileWriter fileWrite = new FileWriter("Serialized.json");
            fileWrite.write(ObjectCreator.prettyPrintString(obj));
            fileWrite.close();
            JsonObject json_object = Serializer.serializeObject(obj);
            OutputStream outputStream = s.getOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(((Object) json_object).toString());
            os.flush();
            s.close();

    }


}


