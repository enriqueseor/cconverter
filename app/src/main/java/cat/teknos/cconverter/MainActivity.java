package cat.teknos.cconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickExchange(View v){

        Spinner SpnBaseCurrency = findViewById(R.id.SpnBaseCurrency);
        Spinner SpnQuoteCurrency = findViewById(R.id.SpnQuoteCurrency);
        EditText baseQuantity = findViewById(R.id.baseQuantity);
        TextView convertedAmount = findViewById(R.id.convertedAmount);

        String BaseCoin = SpnBaseCurrency.getSelectedItem().toString();
        String QuoteCoin = SpnQuoteCurrency.getSelectedItem().toString();

        double changeValue = Double.parseDouble(baseQuantity.getText().toString());
        double result = exchange(BaseCoin,QuoteCoin,changeValue);

        if(result > 0){
            convertedAmount.setText(String.format(Locale.US, "For %5.2f %s, you will receive %5.2f %s",changeValue,BaseCoin,result,QuoteCoin));
            baseQuantity.setText("");
        }else{
            Toast.makeText(MainActivity.this, "This option has not an exchange value", Toast.LENGTH_SHORT).show();
        }
    }

    private double exchange(String baseCurrency, String quoteCurrency, double lastPrice){

        double convertedAmount = 0;
        double USDtoEUR = 0.95;
        double MXNtoUSD = 0.06;
        double MXNtoEUR = 0.05;

        switch (baseCurrency){
            case "USD":
                if(quoteCurrency.equals("EUR"))
                    convertedAmount = lastPrice * USDtoEUR;
                if(quoteCurrency.equals("MXN"))
                    convertedAmount = lastPrice / MXNtoUSD;
                break;

            case "EUR":
                if(quoteCurrency.equals("USD"))
                    convertedAmount = lastPrice / USDtoEUR;
                if(quoteCurrency.equals("MXN"))
                    convertedAmount = lastPrice / MXNtoEUR;

                break;

            case "MXN":
                if(quoteCurrency.equals("USD"))
                    convertedAmount = lastPrice * MXNtoUSD;
                if(quoteCurrency.equals("EUR"))
                    convertedAmount = lastPrice * MXNtoEUR;
                break;
        }
        return convertedAmount;
    }
}