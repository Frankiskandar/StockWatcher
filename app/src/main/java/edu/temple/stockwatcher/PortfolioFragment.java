package edu.temple.stockwatcher;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Logger;



public class PortfolioFragment extends Fragment {


    stockSelectedInterface parent;
    ListView portfolioList;
    Portfolio portfolio;
    public static String BUNDLE_KEY = "portfolio";
    Logger log = Logger.getAnonymousLogger();
    ArrayList<String> symbolList;
    String fileName = "stockList";
    File file;
    TextView emptyPortfolioTV;

    public PortfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_portfolio, container, false);
        emptyPortfolioTV = (TextView) v.findViewById(R.id.notif_textView);


        portfolioList = (ListView) v.findViewById(R.id.listView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            portfolio = (Portfolio) bundle.getSerializable(BUNDLE_KEY); //receives portfolio object from main activity
        }


        //set up adapter
        PortfolioAdapter adapter = new PortfolioAdapter(getContext(),portfolio);
        portfolioList.setAdapter(adapter);

        if (portfolio.size()==0) {
            emptyPortfolioTV.setVisibility(View.VISIBLE);
        } else {
            emptyPortfolioTV.setVisibility(View.GONE);
        }

        File dir = getActivity().getFilesDir();
        file = new File(dir, fileName);

        portfolioList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((stockSelectedInterface) getActivity()).stockSelected(portfolio.get(i));
            }
        });


        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof stockSelectedInterface) {
            parent = (stockSelectedInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement stockSelectedInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }


    public interface stockSelectedInterface {
        public void stockSelected(Stock stock);
    }

    public void addStock(Stock stock) {
        portfolio.add(stock); //add stock to portfolio object
        writeFile(stock); //write the symbol to file
        emptyPortfolioTV.setVisibility(View.GONE);
        ((PortfolioAdapter) portfolioList.getAdapter()).notifyDataSetChanged();
    }

    public void deletePortfolio() {
        portfolio.remove(); //remove stock arraylist inside portfolio object so the is no content at all
        ((PortfolioAdapter) portfolioList.getAdapter()).notifyDataSetChanged();
        emptyPortfolioTV.findViewById(View.VISIBLE);
    }


    public void writeFile(Stock stock) { // append new stock to our file
        try {
            Writer writer;
            writer = new BufferedWriter(new FileWriter(file, true));
            //FileWriter writer = new FileWriter(file);

            writer.append(stock.getSymbol());
            writer.append('\n');
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
