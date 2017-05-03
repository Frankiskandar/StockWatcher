package edu.temple.stockwatcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity implements PortfolioFragment.stockSelectedInterface {

    StockDetailsFragment receiver;
    PortfolioFragment portfolioFragment;
    PortfolioFragment sender;
    boolean twoPanes;
    private final int POPUP_ACTIVITY = 1;
    String newStock;
    public Portfolio portfolio;
    File file;
    String fileName = "stockList";
    Logger log = Logger.getAnonymousLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        portfolio = new Portfolio();
//        portfolio.add(new Stock("Microsoft", "MSFT"));
//        portfolio.add(new Stock("Google", "Goog"));

        twoPanes = (findViewById(R.id.stockdetails_frag)!= null);
        receiver = new StockDetailsFragment();
        sender = new PortfolioFragment();

        File dir = this.getFilesDir();
        file = new File(dir, fileName);

        if (file.exists()) { //if file exists when we open the program
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    portfolio.add(new Stock("", line.toString()));
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { //if file doesnt exists
            log.info("No portfolio exists when we open the program");
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(PortfolioFragment.BUNDLE_KEY, portfolio); //send portfolio object to portfolio fragment
        sender.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.portfolio_frag, sender)
                .commit();

        receiver = new StockDetailsFragment();
        //if stockdetails fragment exists show the fragment
        if(twoPanes){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.stockdetails_frag, receiver)
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
                searchPopUp();
                return true;
            case R.id.trash_button :
                boolean deleted;
                if(file.exists()){
                    log.info("file exists");
                    if(deleted = file.delete()){
                        log.info("stockList file deleted ");
                        portfolio.remove(); //clear portofolio object, overkill with below?
                        sender.deletePortfolio(); // clear portfolio inside portfragment and notify the adapter
                        Toast.makeText(getApplicationContext(), "Portfolio Cleared cleared", Toast.LENGTH_SHORT).show();
                    } else {
                        log.info("No file to delete: " + deleted);
                    }
                } else {
                    log.info("file DNE");
                    Toast.makeText(getApplicationContext(), "No portfolio exists", Toast.LENGTH_SHORT).show();
                }
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

    private void searchPopUp() { //popup add activity
        Intent intent = new Intent(this, StockSearchActivity.class);
        startActivityForResult(intent, POPUP_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == POPUP_ACTIVITY) { //receive stock symbol based on user input from popup activity
            if (resultCode == RESULT_OK) {
                newStock = data.getStringExtra("symbol");
                sender.addStock(new Stock("", newStock));
                System.out.println(data.getStringExtra("symbol"));
            }
            else if (resultCode == RESULT_CANCELED) {
                return;
            }
        }
    }
}
