package cat.teknos.cconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final String[] data = new String[] {"USD", "EUR", "MXN"};

    private Spinner baseCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, data);

        baseCurrency = findViewById(R.id.baseCurrency);
        baseCurrency.setAdapter(adapter);
    }

    public void clickExchange(View v){

        baseCurrency = findViewById(R.id.baseCurrency);
        Spinner quoteCurrency = findViewById(R.id.quoteCurrency);
        EditText baseQuantity = findViewById(R.id.baseQuantity);
        TextView convertedAmount = findViewById(R.id.convertedAmount);

        String monedaActual = baseCurrency.getSelectedItem().toString();
        String monedaCambio = quoteCurrency.getSelectedItem().toString();

        double valorCambio = Double.parseDouble(baseQuantity.getText().toString());
        double resultado = exchange(monedaActual,monedaCambio,valorCambio);

        if(resultado>0){
            convertedAmount.setText(String.format("Por %5.2f %s, usted recibirá %5.2f %s",valorCambio,monedaActual,resultado,monedaCambio));
            baseQuantity.setText("");

        }else{
            convertedAmount.setText(String.format("Usted recibirá"));
            Toast.makeText(MainActivity.this, "La opción a elegir no tiene un factor de conversión", Toast.LENGTH_SHORT).show();

        }
    }

    private double exchange(String baseCurrency, String quoteCurrency, double lastPrice){

        double convertedAmount = 0;
        double USDtoEUR = 0.95;
        double MXNtoUSD = 0.06;
        double MXNtoEUR = 0.05;

        switch (baseCurrency){
            case "DÓLAR":
                if(quoteCurrency.equals("EURO"))
                    convertedAmount = lastPrice * USDtoEUR;
                if(quoteCurrency.equals("PESO MEXICANO"))
                    convertedAmount = lastPrice * MXNtoUSD;
                break;

            case "EURO":
                if(quoteCurrency.equals("DÓLAR"))
                    convertedAmount = lastPrice / USDtoEUR;
                if(quoteCurrency.equals("PESO MEXICANO"))
                    convertedAmount = lastPrice * USDtoEUR;

                break;

            case "PESO MEXICANO":
                if(quoteCurrency.equals("DÓLAR"))
                    convertedAmount = lastPrice / MXNtoUSD;
                if(quoteCurrency.equals("EURO"))
                    convertedAmount = lastPrice / MXNtoEUR;
                break;
        }
        return convertedAmount;
    }
}