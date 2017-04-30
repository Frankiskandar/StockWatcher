package edu.temple.stockwatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


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
                Toast.makeText(this, "Add button selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, StockSearchActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
