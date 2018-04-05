package io.lootsafe.api.Requests;

import io.lootsafe.api.ServiceProvider;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Created by Adam Sanchez on 4/5/2018.
 */
public class Requests {

    private static final String balanceToken = "/balance/token";
    private static final String balanceItem = "/balance/item";
    private static final String balanceItems = "/balance/items";


    public static String getBalanceToken(String ethAccount){
        JsonObject json = getInstance().genericRequest(balanceToken + "/" + ethAccount);
        return json == null ? json.getString("data") : "";

    }

    public static void getBalanceItem(String itemAddress, String ethAccount){
        JsonObject json = getInstance().genericRequest(balanceToken + "/" + ethAccount);

    }


    public static void getBalanceItems(String ethAccount){

    }

    private static NodeHandler getInstance(){
        return ServiceProvider.getInstance().getNodeHandler();
    }




}
