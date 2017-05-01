package edu.temple.stockwatcher;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;



public class PortfolioFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;

    stockSelectedInterface parent;
    ListView portfolioList;
    Portfolio portfolio;
    public static String BUNDLE_KEY = "portfolio";
    Logger log = Logger.getAnonymousLogger();
    String cname;
    String cprice;
    ArrayList<String> symbolList;
    String fileName = "stockList";
    File file;



    public PortfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_portfolio, container, false);

        portfolioList = (ListView) v.findViewById(R.id.listView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            portfolio = (Portfolio) bundle.getSerializable(BUNDLE_KEY);
        }

        System.out.println(portfolio.size());

        PortfolioAdapter adapter = new PortfolioAdapter(getContext(),portfolio);
        portfolioList.setAdapter(adapter);

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
        portfolio.add(stock);
        writeFile(stock);
        ((PortfolioAdapter) portfolioList.getAdapter()).notifyDataSetChanged();
    }


    public void writeFile(Stock stock) {
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
