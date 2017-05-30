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
                    portfolio.add(new Stock("", line.toString())); //read the stock and populate the portfolio object
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

    @Override //to show the add and trash button
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //functionality for each menu button
        switch(item.getItemId()) {
            case R.id.add_button : //add button
                searchPopUp();
                return true;
            case R.id.trash_button :  //trash button will delete the stock list file
                boolean deleted;
                if(file.exists()){
                    log.info("file exists");
                    if(deleted = file.delete()){
                        log.info("stockList file deleted ");
                        portfolio.remove(); //clear portfolio object, overkill with below?
                        sender.deletePortfolio(); // clear portfolio inside portfragment and notify the adapter
                        Toast.makeText(getApplicationContext(), R.string.portfolio_deleted, Toast.LENGTH_SHORT).show();
                    } else {
                        log.info("file.delete() result: " + deleted);
                    }
                } else {
                    log.info("no file to delete");
                    Toast.makeText(getApplicationContext(), R.string.no_portfolio_file, Toast.LENGTH_SHORT).show();
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
        receiver.showStockInfo(stock);// show name, price, graph inside stockdetails frag
        getFragmentManager().executePendingTransactions();
    }

    private void searchPopUp() { //popup search activity
        Intent intent = new Intent(this, StockSearchActivity.class);
        startActivityForResult(intent, POPUP_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == POPUP_ACTIVITY) { //receive stock symbol based on user input from StockSearch activity
            if (resultCode == RESULT_OK) {
                newStock = data.getStringExtra("symbol");
                sender.addStock(new Stock("", newStock)); //add the new stock symbol
                System.out.println(data.getStringExtra("symbol"));
            }
            else if (resultCode == RESULT_CANCELED) { //if canceled, do nothing
                return;
            }
        }
    }
}
