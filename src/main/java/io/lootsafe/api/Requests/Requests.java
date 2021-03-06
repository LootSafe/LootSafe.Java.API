package io.lootsafe.api.Requests;

import io.lootsafe.api.ServiceProvider;
import io.lootsafe.api.U;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import java.util.*;

/**
 * Created by Adam Sanchez on 4/5/2018.
 */
public class Requests {


    /**
     * V1
     */
    public static final String balanceToken = "balance/token";
    public static final String balanceItem = "balance/item";
    public static final String balanceItems = "balance/items";

    public static final String craftables = "craftables";
    public static final String deconstructables = "deconstructables";
    public static final String craftingRecipe = "recipe/get";
    public static final String deconstructionRecipe = "recipe/deconstruction/get";

    public static final String newRecipe = "recipe/new";
    public static final String removeRecipe = "recipe/remove";

    public static final String events = "events";

    public static final String meta = "";
    public static final String newItem = "item/new";
    public static final String spawnItem = "item/spawn";
    public static final String clearAvailability = "item/clearAvailability";
    public static final String tokenAddress = "address/token";

    public static final String items = "item/list";
    public static final String item = "item/get";
    public static final String itemByAddress = "item/get/address";
    public static final String itemAddresses = "item/addresses/get";
    public static final String ledger = "item/ledger";

    public static final String chances = "lootbox/chances";
    public static final String lootboxItems = "lootbox/items";
    public static final String lootboxCost = "lootbox/cost";
    public static final String lootboxAdd = "lootbox/item/add";
    public static final String lootboxCostUpdate = "lootbox/cost";
    public static final String lootboxChanceUpdate = "lootbox/chances/update";

    /**
     * Core V2
     */

    public static final String DOC_URL_Text = "See <a href=\"https://documenter.getpostman.com/view/254497/RzZ3N3Z6\">LootSafe Core Docs</a>";

    public static final String GetRegAssets = "registry/assets";
    public static final String GetRegOwner = "registry/owner";

    public static final String PostAsset = "registry/create";
    public static final String GetFindAddress = "registry/find/%1";

    public static final String GetAssetOwner = "asset/owner/%1";
    public static final String PostSetMetaData = "asset/metadata/set/%1";

    public static final String GetListAssets = "asset/list";
    public static final String PostSetMetadataFile = "asset/metadata/file/set/%1/%2";

    public static final String GetAsset = "asset/get/%1";
    public static final String GetMint = "asset/mint/%1/%2/%3";

    public static final String GetMetadata = "";



}
