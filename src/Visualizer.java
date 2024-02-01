import java.lang.*;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashSet;

/**
 * CPSC 501
 * Inspector starter class
 *
 * @author Jonathan Hudson
 * @author Scott Peters
 */
public class Visualizer {

    //Needed to add a hashset I believe to track everything?
    private HashSet<Object> inspectedObjects = new HashSet<Object>();
    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    // Found most of these on https://www.logicbig.com/how-to/code-snippets.html
    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        String indent = "\t";
        System.out.println(indent + "Class");
        String name = c.getName();
        System.out.println(indent + name);

        inspectedObjects.add(obj);

        // isArray from here https://www.educative.io/answers/how-to-check-if-an-object-is-an-array-in-java
        if (c.isArray()) {
            Class compType = c.getComponentType();
            System.out.printf(indent + "  Component Type: " + compType);

            int arrayLength = Array.getLength(obj);
            System.out.printf(indent + " Length: " + Integer.toString(arrayLength));

            for(int index = 0; index < arrayLength; index++) {
                Object arrayValue = Array.get(obj, index);

                if (arrayValue == null) {
                    System.out.println(indent.repeat(depth) + " Current Value: NULL");
                } else if (compType.isPrimitive()) {
                    System.out.println(indent.repeat(depth) + " Value: " + arrayValue.toString());
                } else if (!recursive) {
                    System.out.println(indent.repeat(depth) + " Current Value: " + arrayValue.getClass().getName() +
                            "@" + Integer.toHexString(System.identityHashCode(arrayValue)));
                } else {
                    System.out.println(indent.repeat(depth) + " Current Value: " + arrayValue.getClass().getName() +
                            "@" + Integer.toHexString(System.identityHashCode(arrayValue)));
                    System.out.println(indent.repeat(depth) + " Recursively Inspect -->");
                    inspectClass(arrayValue.getClass(), arrayValue, recursive, depth+1);
                }

            }
        }

        //inspectSuperclass(c, obj, recursive, depth);
        //inspectInterfaces(c, obj, recursive, depth);
        //inspectConstructors(c, obj, recursive, depth);
        //inspectMethods(c, obj, recursive, depth);
        inspectFields(c, obj, recursive, depth);
    }
/*
    private void inspectSuperclass(Class c, Object obj, boolean recursive, int depth) {
        String indent = "\t";
        System.out.println(indent + "Superclass");
        Class superclass = c.getSuperclass();
        if (superclass != null) {
            System.out.println(indent + " " + superclass.getName());
            System.out.println(indent + " Recursively Inspect -->");
            inspectClass(superclass, obj, recursive, depth + 1);
        } else {
            System.out.println(indent + " Superclass: NONE");
        }

    }

    private void inspectInterfaces(Class c, Object obj, boolean recursive, int depth) {
        String indent = "\t";

        System.out.println(indent.repeat(depth) + "Interfaces: " + c.getName());
        Class[] interfaces = c.getInterfaces();

        if (interfaces.length == 0) {
            System.out.println(indent + " Interface: NONE");
        } else {
            for (Class anInterface : interfaces) {
                System.out.println(indent.repeat(depth) + "Class");
                String name1 = c.getName();
                System.out.println(indent.repeat(depth) + " " + name1);
                System.out.println(indent.repeat(depth) + anInterface);
            }
        }

    }
    private void inspectConstructors(Class c, Object obj, boolean recursive, int depth) {
        String indent = "\t";

        System.out.println(indent.repeat(depth) + "Constructors: " + c.getName());
        Constructor[] constructors = c.getDeclaredConstructors();

        if (constructors.length == 0) {
            System.out.println(indent + " Constructors: NONE");
        } else {
            Arrays.stream(constructors).forEach(con -> {
                int finalDepth1 = depth + 1;
                System.out.println(indent.repeat(finalDepth1) + "Name: " + con.getName());
                Class[] conExceptionArr = con.getExceptionTypes();
                if (conExceptionArr.length == 0) {
                    System.out.println(indent.repeat(finalDepth1) + " Exception Type: NONE");
                } else {
                    System.out.println(indent.repeat(finalDepth1) + " Exception Type: " + conExceptionArr[0]);
                }
                Class[] conParameterArr = con.getParameterTypes();
                if (conParameterArr.length == 0) {
                    System.out.println(indent.repeat(finalDepth1) + " Parameter Type: NONE");
                } else {
                    System.out.println(indent.repeat(finalDepth1) + " Parameter Type: " + conParameterArr[0]);
                }
                int modifiers = con.getModifiers();

                if (modifiers == 0) {
                    System.out.println(indent.repeat(finalDepth1) + " Modifiers: NONE");
                } else {
                    String s1 = Modifier.toString(modifiers);
                    System.out.println(indent.repeat(finalDepth1) + " Modifiers: " + s1);
                }

            });
        }
    }

    private void inspectMethods(Class c, Object obj, boolean recursive, int depth) {
        String indent = "\t";

        System.out.println(indent.repeat(depth) + "Methods: " + c.getName());
        Method[] methods = c.getDeclaredMethods();

        if (methods.length == 0) {
            System.out.println(indent + " Methods: NONE");
        } else {
            Arrays.stream(methods).forEach(mtd -> {
                int finalDepth2 = depth + 1;
                System.out.println(indent.repeat(finalDepth2) + "Name: " + mtd.getName());
                Class[] mtdExceptionArr = mtd.getExceptionTypes();
                if (mtdExceptionArr.length == 0) {
                    System.out.println(indent.repeat(finalDepth2) + " Parameter Type: NONE");
                } else {
                    System.out.println(indent.repeat(finalDepth2) + " Parameter Type: " + mtdExceptionArr[0]);
                }
                Class[] mtdParameterArr = mtd.getParameterTypes();
                if (mtdParameterArr.length == 0) {
                    System.out.println(indent.repeat(finalDepth2) + " Parameter Type: NONE");
                } else {
                    System.out.println(indent.repeat(finalDepth2) + " Parameter Type: " + mtdParameterArr[0]);
                }
                System.out.println(indent.repeat(finalDepth2) + " Return Type: " + mtd.getReturnType());
                int modifiers = mtd.getModifiers();
                if (modifiers == 0) {
                    System.out.println(indent.repeat(finalDepth2) + " Modifiers: NONE");
                } else {
                    String s1 = Modifier.toString(modifiers);
                    System.out.println(indent.repeat(finalDepth2) + " Modifiers: " + s1);
                }
            });
        }
    }
*/
    private void inspectFields(Class c, Object obj, boolean recursive, int depth) {
        String indent = "\t";

        System.out.println(indent.repeat(depth) + "Fields: " + c.getName());
        Field[] fields = c.getDeclaredFields();

        if (fields.length == 0) {
            System.out.println(indent + " Fields: NONE");
        } else {
            Arrays.stream(fields).forEach(fld -> {
                int finalDepth3 = depth + 1;
                System.out.println(indent.repeat(finalDepth3) + "Name: " + fld.getName());
                Class fieldType = fld.getType();
                System.out.println(indent.repeat(finalDepth3) + " Type: " + fieldType);
                int modifiers = fld.getModifiers();
                if (modifiers == 0) {
                    System.out.println(indent.repeat(finalDepth3) + " Modifiers: NONE");
                } else {
                    String s1 = Modifier.toString(modifiers);
                    System.out.println(indent.repeat(finalDepth3) + " Modifiers: " + s1);
                }

                fld.setAccessible(true);
                Object fieldValue = null;
                try {
                    fieldValue = fld.get(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                if(fieldValue == null) {
                    System.out.println(indent.repeat(finalDepth3) + " Current Value: NULL");
                } else if (fieldType.isArray()) {
                    Class compFieldType = fieldType.getComponentType();
                    System.out.printf(indent + "  Component Type: " + compFieldType);

                    int arrayLength = Array.getLength(fieldValue);
                    System.out.printf(indent + " Length: " + Integer.toString(arrayLength));

                    for(int index = 0; index < arrayLength; index++) {
                        Object arrayValue = Array.get(fieldValue, index);

                        if (arrayValue == null) {
                            System.out.println(indent.repeat(finalDepth3) + " Current Value: NULL");
                        } else if (compFieldType.isPrimitive()) {
                            System.out.println(indent.repeat(finalDepth3) + " Value: " + arrayValue.toString());
                        } else if (!recursive) {
                            System.out.println(indent.repeat(finalDepth3) + " Current Value: " + arrayValue.getClass().getName() +
                                    "@" + Integer.toHexString(System.identityHashCode(arrayValue)));
                        } else {
                            System.out.println(indent.repeat(finalDepth3) + " Current Value: " + arrayValue.getClass().getName() +
                                    "@" + Integer.toHexString(System.identityHashCode(arrayValue)));
                            System.out.println(indent.repeat(depth) + " Recursively Inspect -->");
                            inspectClass(arrayValue.getClass(), arrayValue, recursive, depth+1);
                        }

                    }
                } else if(fieldType.isPrimitive()) {
                    System.out.println(indent.repeat(finalDepth3) + " Current Value: " + fieldType.toString());
                } else if(!recursive) {
                    // Got this from here https://stackoverflow.com/questions/909843/how-to-get-the-unique-id-of-an-object-which-overrides-hashcode
                    System.out.println(indent.repeat(finalDepth3) + " Current Value: " + fieldType.getName() +
                            "@" + Integer.toHexString(System.identityHashCode(fieldValue)));
                } else {
                    System.out.println(indent.repeat(finalDepth3) + " Current Value: " + fieldType.getName() +
                            "@" + Integer.toHexString(System.identityHashCode(fieldValue)));
                    System.out.println(indent.repeat(depth) + " Recursively Inspect -->");
                    inspectClass(fieldValue.getClass(), fieldValue, recursive, depth+1);
                }
            });
        }
    }


}