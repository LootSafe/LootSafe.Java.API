package io.lootsafe.api;

import io.lootsafe.api.Data.AssetInfo;

import java.util.*;

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

    public static void debugList(List<String> list) {
        for (String string : list) {
            U.debug(string);
        }
    }

    public static void debugAssetList(List<AssetInfo> list){
        for(AssetInfo a : list){
            U.debug("***" + a.getName() );
            U.debug("   identifier: " + a.getIdentifier());
            U.debug("   symbol: " + a.getSymbol());
            U.debug("   _id: " + a.getId());
            U.debug("   ___v: " + a.getV());
        }
    }
}
