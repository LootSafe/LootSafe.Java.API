package io.lootsafe.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Adam Sanchez on 3/23/2018.
 */
public class U {

    public static void space(){
        System.out.println("______________________________");
    }

    public static void info(String info){
        System.out.println("[Info]: " + info);
    }

    public static void debug(String debug){
        if(ServiceProvider.getInstance().isDebugging()){
            System.out.println("[Debug]: " + debug);
        }
    }
    public static void warn(String warn){
        System.out.println("[WARN]: " + warn);
    }
    public static void error(String error){
        System.out.println("[Error]: " + error);
    }

    public static void error(String error, Exception e){
        System.out.println("[Error]: " + error);
        System.out.println(e.getCause());
    }

    public static void debugMap(Map<String,String> map){
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            U.debug(pair.getKey().toString() + " ::: " + pair.getValue().toString());
        }
    }

    public static void debugSet(Set<String> set) {
        for (String string : set) {
            U.debug(string);
        }
    }
}
