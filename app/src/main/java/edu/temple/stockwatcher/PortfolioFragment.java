package edu.temple.stockwatcher;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import static android.R.attr.defaultValue;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PortfolioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PortfolioFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;

    stockSelectedInterface parent;
    ListView portfolioList;
    Portfolio portfolio;
    public static String BUNDLE_KEY = "portfolio";

    public PortfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_portfolio, container, false);

        portfolioList = (ListView) v.findViewById(R.id.listView);


        //portfolio = new Portfolio();
//        portfolio.add(new Stock("Microsoft", "MSFT"));
//        portfolio.add(new Stock("Google", "Goog"));

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            portfolio = (Portfolio) bundle.getSerializable(BUNDLE_KEY);
        }

        System.out.println(portfolio.size());

        PortfolioAdapter adapter = new PortfolioAdapter(getContext(),portfolio);
        portfolioList.setAdapter(adapter);
        //addStock(new Stock("TESLA", "TSLA"));

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
        ((PortfolioAdapter) portfolioList.getAdapter()).notifyDataSetChanged();
    }
}
