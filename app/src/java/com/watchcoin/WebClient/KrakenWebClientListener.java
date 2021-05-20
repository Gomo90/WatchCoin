package com.watchcoin.WebClient;

public interface KrakenWebClientListener {

    void onError(String message);

    void onResponse(Object response);
}
