package edu.temple.stockwatcher;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
    String stockSymbol = "MSFT";

    public StockDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_stock_details, container, false);

        graphImageView = (ImageView) v.findViewById(R.id.graph);
        companyName = (TextView) v.findViewById(R.id.stock_name);
        stockPrice = (TextView) v.findViewById(R.id.stock_price);

        showGraph();

        return v;
    }

    public void showGraph() {//will take stock param
        Picasso.with(graphImageView.getContext()).load("https://chart.yahoo.com/z?t=1d&s="+stockSymbol).into(graphImageView);
    }


}
