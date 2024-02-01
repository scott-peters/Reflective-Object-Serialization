import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ObjectCreator {

    public static Object sendObject(){
        return createObject(createMenu());

    }

    private static int createMenu() {
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("Welcome: choose from the following options");
            System.out.println("  1: Create Object1: ");
            System.out.println("  2: Create Object2: ");
            System.out.println("  3: Create Object3: ");
            System.out.println("  4: Create Object4: ");
            System.out.println("  5: Create Object5: ");
            System.out.println("  6: Exit");
            int choice = Integer.parseInt(input.nextLine());
            switch(choice){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return choice;
                default:
                    System.out.println("Enter a value from 1-6");
            }
            input.close();
        }
    }



    private static Object createObject(int choice){
        Object obj = null;
        switch(choice){
            case 1:
                obj = createObject1();
                break;
            case 2:
                obj = createObject2();
                break;
            case 3:
                obj = createObject3();
                break;
            case 4:
                obj = createObject4();
                break;
            case 5:
                obj = createObject5();
                break;
            case 6:
                System.out.println("Exiting program");
                System.exit(0);
                break;
        }
        return obj;
    }

    private static int getInt()
    {
        Scanner input = new Scanner(System.in);
        int i = 0;
        while(true)
        {
            System.out.print("Int value: ");
            String value = input.nextLine();
            if (value.equals(""))
            {
                //This gives back default value
                break;
            }
            try
            {
                i = Integer.parseInt(value);
                break;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid int.");
            }
        }
        return i;
    }

    private static float getFloat()
    {
        Scanner input = new Scanner(System.in);
        float f = 0;
        while(true)
        {
            System.out.print("Float value: ");
            String value = input.nextLine();
            if (value.equals(""))
            {
                //This gives back default value
                break;
            }
            try
            {
                f = Float.parseFloat(value);
                break;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid float.");
            }
        }
        return f;
    }

    private static double getDouble()
    {
        Scanner input = new Scanner(System.in);
        double d = 0;
        while(true)
        {
            System.out.print("Double value: ");
            String value = input.nextLine();
            if (value.equals(""))
            {
                //This gives back default value
                break;
            }
            try
            {
                d = Double.parseDouble(value);
                break;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid double.");
            }
        }
        return d;
    }

    private static boolean getBoolean()
    {
        Scanner input = new Scanner(System.in);
        boolean bool = false;
        while(true)
        {
            System.out.print("Boolean value (true/false): ");
            String value = input.nextLine();
            if (value.equals(""))
            {
                //This gives back default value
                break;
            }
            else if (value.equals("true") || value.equals("false"))
            {
                bool = Boolean.parseBoolean(value);
                break;
            }
            System.out.println("Invalid boolean.");
        }
        return bool;
    }

    private static Object createObject1() {
        System.out.println("Type an int i, double d, boolean bool, and float b to create object1 (default values are 0");
        Object1 obj1 = new Object1();

        obj1.i = getInt();
        obj1.d = getDouble();
        obj1.f = getFloat();
        obj1.bool = getBoolean();
        return obj1;
    }

    private static Object createObject2() {
        System.out.println("This object contains references to other objects (object 1)");
        Object2 obj2 = new Object2();
        obj2.a_new = (Object1) createObject1();
        Object1 obj1 = new Object1();
        obj1.a_1 = obj2.a_new;
        return obj1;
    }

    private static Object createObject3() {

        Object3 obj3 = new Object3();

        System.out.print("How big do you want the array to be: ");
        // begin scanning for user input
        Scanner input = new Scanner(System.in);
        int value = Integer.parseInt(input.nextLine());
        int [] array = new int[value];

        System.out.println("Type each value you want in the array (as an int): \n");
        for(int i = 0; i < value; i++){
            System.out.print("["+ i +"]: ");
            array[i] = Integer.parseInt(input.nextLine());
        }
        //closing input scanner
        input.close();

        obj3 = new Object3(array);
        return obj3;
    }


    private static Object createObject4() {
        //Object4 obj4 = new Object4();

        System.out.print("How big do you want the array to be: ");
        // begin scanning for user input
        Scanner input = new Scanner(System.in);
        int value = Integer.parseInt(input.nextLine());
        Object[] array = new Object[value];

        for(int i = 0; i < value; i++){
            System.out.println("If you want no object type no, Type yes if you want a new object");
            String line = input.nextLine();

            if(line.equals("yes")){
                System.out.println("Creating a new object");
                array[i] = (Object1) createObject1();
            }
            else if(line.equals("no")){
                System.out.println("Inserting a null object");
                array[i] = null;
            }
            else {
                System.out.println("You have entered an invalid input. Defaulting to null");
            }
        }
        input.close();
        Object4 obj4 = new Object4(array);
        return obj4;
    }

    private static Object createObject5() {
        //Object5 obj5 = new Object5();

        System.out.print("How big do you want the array to be: ");
        // begin scanning for user input
        Scanner input = new Scanner(System.in);
        int value = Integer.parseInt(input.nextLine());
        ArrayList<Object> array = new ArrayList<>(value);

        // Is this right???? I just basically copied code from object4....
        for(int i = 0; i < value; i++){
            System.out.println("If you want no object type no, Type yes if you want a new object");
            String line = input.nextLine();

            if(line.equals("yes")){
                System.out.println("Creating a new object at index ["+i+"]");
                array.add((Object1) createObject1());
            }
            else if(line.equals("no")){
                array.add(null);
            }
            else {
                System.out.println("You have entered an invalid input. Defaulting to null");
            }
        }
        Object5 obj5 = new Object5(array);
        return obj5;
    }

    // Took this from here https://stackoverflow.com/questions/4105795/pretty-print-json-in-java
    // Got the idea from a bunch of people talking in discord about pretty print

    public static String prettyPrintString(Object obj) throws Exception{
        JsonObject json_object = Serializer.serializeObject(obj);
        StringWriter string_writer = new StringWriter();
        Map<String, Object> settings_map = new HashMap<>();
        settings_map.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory write_factory = Json.createWriterFactory(settings_map);
        JsonWriter json_writer = write_factory.createWriter(string_writer);
        json_writer.writeObject(json_object);
        json_writer.close();
        String prettyPrint = string_writer.toString();
        return prettyPrint;
    }

}
