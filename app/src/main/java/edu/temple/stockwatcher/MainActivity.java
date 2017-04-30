package edu.temple.stockwatcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity implements PortfolioFragment.stockSelectedInterface {

    StockDetailsFragment receiver;
    PortfolioFragment portfolioFragment;
    PortfolioFragment sender;
    boolean twoPanes;
    private final int POPUP_ACTIVITY = 1;
    String newStock;
    public Portfolio portfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        portfolio = new Portfolio();
        portfolio.add(new Stock("Microsoft", "MSFT"));
        portfolio.add(new Stock("Google", "Goog"));

        twoPanes = (findViewById(R.id.stockdetails_frag)!= null);
        receiver = new StockDetailsFragment();


        sender = new PortfolioFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(PortfolioFragment.BUNDLE_KEY, portfolio);
        sender.setArguments(bundle);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_button :
                //if add button is clicked
//                Toast.makeText(this, "Add button selected", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, StockSearchActivity.class);
//                startActivity(intent);
                searchPopUp();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fragTransition(){
        Logger log = Logger.getAnonymousLogger();
        log.info("fragTransition() method executed");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.portfolio_frag, receiver)
                .addToBackStack(null)
                .commit();
        getFragmentManager().executePendingTransactions();
    }

    @Override
    public void stockSelected(Stock stock) {
        if (!twoPanes){
            fragTransition();
        }
        receiver.showStockInfo(stock);
        getFragmentManager().executePendingTransactions();
    }

    private void searchPopUp() {
        Intent intent = new Intent(this, StockSearchActivity.class);
        startActivityForResult(intent, POPUP_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == POPUP_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                newStock = data.getStringExtra("symbol");
                sender.addStock(new Stock("", newStock));
                System.out.println(data.getStringExtra("symbol"));
                System.out.println("success");
            }
            else if (resultCode == RESULT_CANCELED) {
                return;
            }
        }
    }
}
