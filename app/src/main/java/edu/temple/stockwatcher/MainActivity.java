package edu.temple.stockwatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    StockDetailsFragment receiver;
    boolean twoPanes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twoPanes = (findViewById(R.id.stockdetails_frag)!= null);
        receiver = new StockDetailsFragment();

        PortfolioFragment sender = new PortfolioFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.portfolio_frag, sender)
                .commit();

        receiver = new StockDetailsFragment();
        //if canvas fragment exists show the fragment
        if(twoPanes){
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.stockdetails_frag, receiver)
                    .commit();
        }
    }
}
