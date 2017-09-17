package com.watchcoin.Json;

import java.util.List;

/**
 * Class used for JSON data deserialization received by the Kraken API
 */
public class CurrencyData {

    private List<String> error;
    private Result result;

    public List<String> getError() {

        return this.error;
    }

    public Result getResult() {

        return this.result;
    }
}
