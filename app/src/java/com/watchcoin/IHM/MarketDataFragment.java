package com.watchcoin.IHM;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watchcoin.Data.ErrorMessageEvent;
import com.watchcoin.Data.UpdateMarketDataEvent;
import com.watchcoin.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * MarketDataFragment shows the market data
 */
public class MarketDataFragment extends Fragment {

    private static final String TAG = "MarketDataFragment";

    private TextView title;
    private TextView dateRefresh;
    private TextView askPrice;
    private TextView bidPrice;
    private TextView lastPrice;
    private TextView highPrice;
    private TextView lowPrice;
    private TextView avgPrice;
    private TextView openPrice;
    private TextView volume;
    private TextView volume24H;
    private TextView tradeVolume;
    private TextView tradeVolume24H;

    private String titleValue;
    private String dateRefreshValue;
    private String askPriceValue;
    private String bidPriceValue;
    private String lastPriceValue;
    private String highPriceValue;
    private String lowPriceValue;
    private String avgPriceValue;
    private String openPriceValue;
    private String volumeValue;
    private String volume24HValue;
    private String tradeVolumeValue;
    private String tradeVolume24HValue;

    private Boolean marketDataDisplayed = false;
    private Boolean errorMessageDisplayed = false;

    private IHMConsole ihmConsoleActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.market_data, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Market Data displayed
        title = (TextView) view.findViewById(R.id.MarketDataTitle);
        dateRefresh = (TextView) view.findViewById(R.id.DateRefreshLabel);
        askPrice = (TextView) view.findViewById(R.id.AskPriceValue);
        bidPrice = (TextView) view.findViewById(R.id.BidPriceValue);
        lastPrice = (TextView) view.findViewById(R.id.LastPriceValue);
        highPrice = (TextView) view.findViewById(R.id.HighPriceValue);
        lowPrice = (TextView) view.findViewById(R.id.LowPriceValue);
        avgPrice = (TextView) view.findViewById(R.id.AvgPriceValue);
        openPrice = (TextView) view.findViewById(R.id.OpenPriceValue);
        volume = (TextView) view.findViewById(R.id.VolumeValue);
        volume24H = (TextView) view.findViewById(R.id.Volume24hValue);
        tradeVolume = (TextView) view.findViewById(R.id.TradeVolumeValue);
        tradeVolume24H = (TextView) view.findViewById(R.id.TradeVolume24Value);


        if (marketDataDisplayed) {

            if (errorMessageDisplayed) {

                errorDateRefreshTextView();
            }

            title.setText(titleValue);
            dateRefresh.setText(dateRefreshValue);
            askPrice.setText(askPriceValue);
            bidPrice.setText(bidPriceValue);
            lastPrice.setText(lastPriceValue);
            highPrice.setText(highPriceValue);
            lowPrice.setText(lowPriceValue);
            avgPrice.setText(avgPriceValue);
            openPrice.setText(openPriceValue);
            volume.setText(volumeValue);
            volume24H.setText(volume24HValue);
            tradeVolume.setText(tradeVolumeValue);
            tradeVolume24H.setText(tradeVolume24HValue);
        }
        else if (errorMessageDisplayed) {

            errorDateRefreshTextView();
            dateRefresh.setText(dateRefreshValue);
        }

    }


    @Override
    public void onStart() {
        super.onStart();

        // EventBus register
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();

        // EventBus unregister
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();

        ihmConsoleActivity = null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Recuperation of IHMConsole activity
        if (context instanceof IHMConsole) {

            ihmConsoleActivity = (IHMConsole) context;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        // If the market data is stored when the marketDataFragment is restored
        // And if the date of refresh is more recently
        if (ihmConsoleActivity.getCurrencyDataMap() != null &&
                !dateRefreshValue.equals(ihmConsoleActivity.getCurrencyDataMap().get("marketDateRefresh"))) {

            // Display the new market data
            onUpdateMarketDataEvent(new UpdateMarketDataEvent(ihmConsoleActivity.getCurrencyDataMap()));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save data displayed
        outState.putString("dateRefresh", dateRefresh.toString());
    }


    /**
     * Function to display the market data (eventbus process used)
     * @param updateMarketDataEvent : Market data to display
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateMarketDataEvent(UpdateMarketDataEvent updateMarketDataEvent) {

        // If the update market data event is recuperated
        if (updateMarketDataEvent != null) {

            // Market Data storage
            titleValue = updateMarketDataEvent.getMarketDataCurrency();
            askPriceValue = updateMarketDataEvent.getAskPriceValue();
            bidPriceValue = updateMarketDataEvent.getBidPriceValue();
            lastPriceValue = updateMarketDataEvent.getLastPriceValue();
            highPriceValue = updateMarketDataEvent.getHighPriceValue();
            lowPriceValue = updateMarketDataEvent.getLowPriceValue();
            avgPriceValue = updateMarketDataEvent.getAvgPriceValue();
            openPriceValue = updateMarketDataEvent.getOpenPriceValue();
            volumeValue = updateMarketDataEvent.getVolumeValue();
            volume24HValue = updateMarketDataEvent.getVolume24Value();
            tradeVolumeValue = updateMarketDataEvent.getTradeVolumeValue();
            tradeVolume24HValue = updateMarketDataEvent.getTradeVolume24Value();


            // Market Data displayed

            // If the date of refresh data is recuperated (case of manual currency selection is null)
            if (updateMarketDataEvent.getMarketDateRefresh() != null) {

                // If the error message was previously displayed
                if (errorMessageDisplayed) {

                    // Init date refresh textview
                    initDateRefreshTextView();
                }

                dateRefreshValue = updateMarketDataEvent.getMarketDateRefresh();
                dateRefresh.setText(dateRefreshValue);
            }

            title.setText(titleValue);
            askPrice.setText(askPriceValue);
            bidPrice.setText(bidPriceValue);
            lastPrice.setText(lastPriceValue);
            highPrice.setText(highPriceValue);
            lowPrice.setText(lowPriceValue);
            avgPrice.setText(avgPriceValue);
            openPrice.setText(openPriceValue);
            volume.setText(volumeValue);
            volume24H.setText(volume24HValue);
            tradeVolume.setText(tradeVolumeValue);
            tradeVolume24H.setText(tradeVolume24HValue);

            marketDataDisplayed = true;
        }
        else {

            marketDataDisplayed = false;
        }
    }


    /**
     * Function to display the error message (eventbus process used)
     * @param errorMessageEvent : Error message to display
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorMessageEvent(ErrorMessageEvent errorMessageEvent) {

        // Error display pattern
        errorDateRefreshTextView();

        switch (errorMessageEvent.getErrorMessageType()) {

            case "networkKO" :
                dateRefreshValue = getString(R.string.NetworkError_Message);
                dateRefresh.setText(dateRefreshValue);
                break;

            case "requestExecutionError" :
                dateRefreshValue = getString(R.string.RequestError_Message);
                dateRefresh.setText(dateRefreshValue);
                break;

            case "noMarketData" :
                dateRefreshValue = getString(R.string.NoMarketDataError_Message);
                dateRefresh.setText(dateRefreshValue);
                break;
        }

        errorMessageDisplayed = true;
    }


    /**
     * Initialisation of dateRefresh textview
     */
    private void initDateRefreshTextView() {

        dateRefresh.setTextColor(lastPrice.getTextColors());
        dateRefresh.setTypeface(null, Typeface.BOLD);

        errorMessageDisplayed = false;
    }


    /**
     * Error display pattern of dateRefresh textview
     */
    private void errorDateRefreshTextView() {

        dateRefresh.setTextColor(Color.RED);
        dateRefresh.setTypeface(null, Typeface.BOLD);
    }


    /*** Getters data displayed ***/
    public TextView getDateRefresh () {

        return dateRefresh;
    }

    public String getAskPriceValue () {

        return askPriceValue;
    }

    public String getBidPriceValue () {

        return bidPriceValue;
    }

    public String getLastPriceValue () {

        return lastPriceValue;
    }

    public String getHighPriceValue () {

        return highPriceValue;
    }

    public String getLowPriceValue () {

        return lowPriceValue;
    }

    public String getAvgPriceValue () {

        return avgPriceValue;
    }

    public String getOpenPriceValue () {

        return openPriceValue;
    }

    public String getVolumeValue () {

        return volumeValue;
    }

    public String getVolume24Value () {

        return volume24HValue;
    }

    public String getTradeVolumeValue () {

        return tradeVolumeValue;
    }

    public String getTradeVolume24Value () {

        return tradeVolume24HValue;
    }
}
