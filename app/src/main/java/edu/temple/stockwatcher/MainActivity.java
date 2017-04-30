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
    boolean twoPanes;
    private final int POPUP_ACTIVITY = 11;

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
        startActivityForResult(new Intent(this, StockSearchActivity.class), POPUP_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == POPUP_ACTIVITY && resultCode == 11) {
            portfolioFragment.addStock(new Stock("", data.getStringExtra(StockSearchActivity.STOCK_SYMBOL)));

        }
    }
}
