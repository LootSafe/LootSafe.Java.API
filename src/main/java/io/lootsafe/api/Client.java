package io.lootsafe.api;

/**
 * Created by Adam Sanchez on 3/23/2018.
 */
public interface Client {

    void notifyPlayer(String playerID);
    void notifySuccess(String transactionID);
    void notifyFailure(String transactionID);

}
