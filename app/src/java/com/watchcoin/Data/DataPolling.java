package com.watchcoin.Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * DataPolling provide the public market data of cryptocurrency from
 * differents cryptocurrency exchange platforms in the internet
 * Only Kraken exchange platform is used for this moment (API : https://www.kraken.com/help/api)
 */
public class DataPolling {


    private String krakenApiAddress;
    private String krakenAssets;
    private int krakenApiVersion = 0;


    /**
     * Request for get the public market data of cryptocurrency
     * @return : public market data (JSONObject)
     */
    public JSONObject PublicDataQuery () throws JSONException {

        HttpsURLConnection httpsConnection = null;
        InputStream is = null;
        OutputStream os;
        String line;
        StringBuilder result = new StringBuilder();

        try {

            String krakenAddress = String.format("%s/%s/public/%s", krakenApiAddress, krakenApiVersion, "Ticker");
            URL url = new URL(krakenAddress);

            httpsConnection = (HttpsURLConnection)url.openConnection();
            httpsConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpsConnection.setRequestMethod("POST");

            os = httpsConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(krakenAssets);
            writer.flush();
            writer.close();
            os.close();

            httpsConnection.connect();

            is = httpsConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while ((line = reader.readLine()) != null) {

                result.append(line);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            // Close Stream and disconnect HTTPS connection.
            if (is != null) {

                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpsConnection != null) httpsConnection.disconnect();
        }

        return new JSONObject(result.toString());
    }


    /*** Getter/Setter data ***/

    public String getKrakenApiAddress() {

        return this.krakenApiAddress;
    }

    public void setKrakenApiAddress (String krakenApiAddress) {

        this.krakenApiAddress = krakenApiAddress;
    }

    public String getKrakenAssets() {

        return this.krakenAssets;
    }

    public void setKrakenAssets (String krakenAssets) {

        this.krakenAssets = krakenAssets;
    }
}
