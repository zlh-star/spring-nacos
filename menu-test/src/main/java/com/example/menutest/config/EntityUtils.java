package com.example.menutest.config;

import com.alibaba.fastjson.JSONObject;

import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Clob;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
//import net.sf.json.JSONObject;

public class EntityUtils {
    public EntityUtils() {
    }

    public static Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new HashMap(16);
        Field[] var2 = object.getClass().getDeclaredFields();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];

            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

        return map;
    }

    //将map转换为实体类型，并除去map中在实体类中不存在的字段值
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        T t = null;

        try {
            t = entity.newInstance();
            Field[] var3 = entity.getDeclaredFields();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object != null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }

                    field.setAccessible(flag);
                }
            }

            return t;
        } catch (InstantiationException | IllegalAccessException var9) {
            var9.printStackTrace();
        }

        return t;
    }

    public static Map<String, Object> clobToString(Map<String, Object> map) {
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();

        while(iterator.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry)iterator.next();
            String key = (String)entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Clob) {
                Clob clob = (Clob)value;

                try {
                    Reader inStream = clob.getCharacterStream();
                    long length = clob.length();
                    char[] c = new char[(int)length];
                    inStream.read(c);
                    String attrMap = new String(c);
                    map.put(key, attrMap);
                } catch (Exception var12) {
                    var12.printStackTrace();
                }
            }
        }

        return map;
    }

    public static String getKey(JSONObject attrMappJson, String value) {
        String keyValue = "";
        Iterator<String> keys = attrMappJson.keySet().iterator();

        while(keys.hasNext()) {
            String key = (String)keys.next();
            String string = attrMappJson.getString(key);
            if (value.equalsIgnoreCase(string)) {
                keyValue = key;
                break;
            }
        }

        return keyValue;
    }

    public static <T> T mapToEntityClobToString(Map<String, Object> map, Class<T> entity) {
        T t = null;

        try {
            t = entity.newInstance();
            Field[] var3 = entity.getDeclaredFields();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object != null && field.getType().isAssignableFrom(object.getClass())) {
                        if (object instanceof Clob) {
                            Clob clob = (Clob)object;

                            try {
                                Reader inStream = clob.getCharacterStream();
                                long length = clob.length();
                                char[] c = new char[(int)length];
                                inStream.read(c);
                                object = new String(c);
                            } catch (Exception var14) {
                                var14.printStackTrace();
                            }
                        }

                        field.set(t, object);
                    }

                    field.setAccessible(flag);
                }
            }

            return t;
        } catch (InstantiationException var15) {
            var15.printStackTrace();
        } catch (IllegalAccessException var16) {
            var16.printStackTrace();
        }

        return t;
    }
}

