package edu.temple.stockwatcher;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Frank on 4/30/2017.
 */

public class PortfolioAdapter extends BaseAdapter {

    Context context;
    Portfolio portfolio;

    public PortfolioAdapter(Context context, Portfolio portfolio) {
        this.context = context;
        this.portfolio = portfolio;
    }

    @Override
    public int getCount() {
        return portfolio.size();
    }

    @Override
    public Object getItem(int i) {
        return portfolio.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv = new TextView(context);

        //show the stock symbol only
        tv.setText(portfolio.get(i).getSymbol());
        return tv;
    }
}
