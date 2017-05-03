package edu.temple.stockwatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class StockSearchActivity extends Activity {

    AutoCompleteTextView stockSearchText;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_search);

        stockSearchText = (AutoCompleteTextView) findViewById(R.id.searchEditText);
        addButton = (Button) findViewById(R.id.addStock_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!stockSearchText.getText().toString().isEmpty()) { // if the text field is not empty
                    System.out.println(stockSearchText.getText().toString());
                    String toSend = stockSearchText.getText().toString(); //send the string in the textfield as intent to mainActivity
                    Intent passIntent = new Intent();
                    passIntent.putExtra("symbol", toSend);
                    setResult(RESULT_OK, passIntent);
                    finish();
                } else {
                    //Toast.makeText(getApplicationContext(), "Symbol does not exist", Toast.LENGTH_SHORT).show();
                    System.out.println("failed");
                }
            }
        });
    }
}
