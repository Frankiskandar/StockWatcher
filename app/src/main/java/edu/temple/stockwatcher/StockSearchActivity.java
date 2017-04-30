package edu.temple.stockwatcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class StockSearchActivity extends Activity {

    AutoCompleteTextView stockSearchText;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_search);

        stockSearchText = (AutoCompleteTextView) findViewById(R.id.searchEditText);
        addButton = (Button) findViewById(R.id.addStock_button);

    }
}
