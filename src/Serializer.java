import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

// Took starter code from prof hudson inside /Reflection4GeneralPurpose/JSON

public class Serializer {

    public static JsonObject serializeObject(Object object) throws Exception {
        JsonArrayBuilder object_list = Json.createArrayBuilder();
        serializeHelper(object, object_list, new IdentityHashMap());
        JsonObjectBuilder json_base_object = Json.createObjectBuilder();
        json_base_object.add("objects", object_list);
        return json_base_object.build();
    }

    private static void serializeHelper(Object source, JsonArrayBuilder object_list, Map object_tracking_map) throws Exception {
        String object_id = Integer.toString(object_tracking_map.size());
        if (source == null) return;
        object_tracking_map.put(source, object_id);
        Class object_class = source.getClass();
        JsonObjectBuilder object_info = Json.createObjectBuilder();
        JsonArrayBuilder field_list = Json.createArrayBuilder();
        JsonObjectBuilder array_Object = Json.createObjectBuilder();

        if (object_class == null) {
            object_info.add("reference", "null");
        } else if (object_class.isArray()) {
            JsonObjectBuilder array_info = Json.createObjectBuilder();
            int length = Array.getLength(source);
            Class componentType = source.getClass().getComponentType();
            object_info.add("class", object_class.getName());
            object_info.add("id", object_id);
            JsonArrayBuilder array_list = Json.createArrayBuilder();
            object_info.add("type", "array");
            object_info.add("length", Integer.toString(Array.getLength(source)));

            for (int i = 0; i < length; i++) {
                String fields = String.valueOf(Array.get(source, i));
                Object arrayObject = Array.get(source, i);
                if (!componentType.isPrimitive()) {
                    array_info.add("reference", Integer.toString(object_tracking_map.size()));
                    serializeHelper(arrayObject, object_list, object_tracking_map);
                } else if (arrayObject == null) {
                    array_info.add("reference", "null");
                } else {
                    array_info.add("value", arrayObject.toString());
                }
                array_list.add(array_info);
            }
            object_info.add("entries", array_list);
            // Im not sure if I have to split this up into different things (array, arraylist etc?)
        } else if (source instanceof ArrayList<?>) {
                object_info.add("class", object_class.getName());
                object_info.add("id", object_id);
                object_info.add("type", "object");
                for (Field f : object_class.getDeclaredFields()) {
                    if (!Modifier.isStatic(f.getModifiers())) {
                        f.setAccessible(true);

                        JsonObjectBuilder arraylist_info = Json.createObjectBuilder();
                        Class field_declaring_class = f.getDeclaringClass();
                        Object arrayfield_object = f.get(source);


                        arraylist_info.add("name", f.getName());
                        arraylist_info.add("declaring class", field_declaring_class.getName());

                        if (field_declaring_class == object_class) {
                            if (arrayfield_object == null) {
                                arraylist_info.add("reference", "null");
                            }


                            else if (!f.getType().isPrimitive()) {
                                if (object_tracking_map.containsKey(arrayfield_object)) {
                                    arraylist_info.add("reference", object_tracking_map.get(arrayfield_object).toString());
                                } else {
                                    arraylist_info.add("reference", Integer.toString(object_tracking_map.size()));
                                    serializeHelper(arrayfield_object, object_list, object_tracking_map);
                                }
                            } else {
                                arraylist_info.add("type", f.getType().toString());
                                arraylist_info.add("value", arrayfield_object.toString());
                            }
                        } else {
                            arraylist_info.add("reference", Integer.toString(object_tracking_map.size()));
                            serializeHelper(arrayfield_object, object_list, object_tracking_map);
                        }

                        field_list.add(arraylist_info);

                    }

                }
                object_info.add("fields", field_list);

            } else {
                object_info.add("class", object_class.getName());
                object_info.add("id", object_id);
                object_info.add("type", "object");
                for (Field f : object_class.getDeclaredFields()) {
                    if (!Modifier.isStatic(f.getModifiers())) {
                        f.setAccessible(true);

                        JsonObjectBuilder arraylist_info = Json.createObjectBuilder();
                        Class field_declaring_class = f.getDeclaringClass();
                        Object field_object = f.get(source);

                        arraylist_info.add("name", f.getName());
                        arraylist_info.add("declaring class", field_declaring_class.getName());

                        if (field_declaring_class == object_class) {

                            if (field_object == null) {
                                arraylist_info.add("reference", "null");
                            }

                            else if (!f.getType().isPrimitive()) {
                                if (object_tracking_map.containsKey(field_object)) {
                                    arraylist_info.add("reference", object_tracking_map.get(field_object).toString());
                                } else {
                                    arraylist_info.add("reference", Integer.toString(object_tracking_map.size()));
                                    serializeHelper(field_object, object_list, object_tracking_map);
                                }
                            } else {
                                arraylist_info.add("type", f.getType().toString());
                                arraylist_info.add("value", field_object.toString());
                            }
                        } else {
                            arraylist_info.add("reference", Integer.toString(object_tracking_map.size()));
                            serializeHelper(field_object, object_list, object_tracking_map);
                        }

                        field_list.add(arraylist_info);

                    }

                }
            object_info.add("fields", field_list);

        }
        object_list.add(object_info);
    }


}
