package cat.teknos.cconverter

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickExchange(v: View?) {
        val SpnBaseCurrency = findViewById<Spinner>(R.id.SpnBaseCurrency)
        val SpnQuoteCurrency = findViewById<Spinner>(R.id.SpnQuoteCurrency)
        val baseQuantity = findViewById<EditText>(R.id.baseQuantity)
        val convertedAmount = findViewById<TextView>(R.id.convertedAmount)
        val BaseCoin = SpnBaseCurrency.selectedItem.toString()
        val QuoteCoin = SpnQuoteCurrency.selectedItem.toString()
        val changeValue = baseQuantity.text.toString().toDouble()
        val result = exchange(BaseCoin, QuoteCoin, changeValue)
        if (result > 0) {
            convertedAmount.text = String.format(
                Locale.US,
                "For %5.2f %s, you will receive %5.2f %s",
                changeValue,
                BaseCoin,
                result,
                QuoteCoin
            )
            baseQuantity.setText("")
        } else {
            Toast.makeText(
                this@MainActivity,
                "This option has not an exchange value",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun exchange(baseCurrency: String, quoteCurrency: String, lastPrice: Double): Double {
        var convertedAmount = 0.0
        val USDtoEUR = 0.95
        val MXNtoUSD = 0.06
        val MXNtoEUR = 0.05
        when (baseCurrency) {
            "USD" -> {
                if (quoteCurrency == "EUR") convertedAmount = lastPrice * USDtoEUR
                if (quoteCurrency == "MXN") convertedAmount = lastPrice / MXNtoUSD
            }

            "EUR" -> {
                if (quoteCurrency == "USD") convertedAmount = lastPrice / USDtoEUR
                if (quoteCurrency == "MXN") convertedAmount = lastPrice / MXNtoEUR
            }

            "MXN" -> {
                if (quoteCurrency == "USD") convertedAmount = lastPrice * MXNtoUSD
                if (quoteCurrency == "EUR") convertedAmount = lastPrice * MXNtoEUR
            }
        }
        return convertedAmount
    }
}