package edu.temple.stockwatcher;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class StockDetailsFragment extends Fragment {

    View v;
    ImageView graphImageView;
    TextView companyName;
    TextView stockPrice;
    //String stockSymbol = "MSFT";


    public StockDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_stock_details, container, false);

        graphImageView = (ImageView) v.findViewById(R.id.graph);
        companyName = (TextView) v.findViewById(R.id.stock_name);
        stockPrice = (TextView) v.findViewById(R.id.stock_price);

        return v;
    }

    public void showGraph(Stock stock) {
        Picasso.with(graphImageView.getContext()).load("https://chart.yahoo.com/z?t=1d&s="+stock.getSymbol()).into(graphImageView);
    }

    public void showCompanyName(Stock stock) {
        companyName.setText(String.valueOf(stock.getCompanyName()));
    }

    public void showStockPrice(Stock stock) {
        //stockPrice.setText(String.valueOf(stock.getPrice()));
    }

    public void showStockInfo(Stock stock){
        showCompanyName(stock);
        showGraph(stock);
        //showStockPrice(stock);
    }


}
