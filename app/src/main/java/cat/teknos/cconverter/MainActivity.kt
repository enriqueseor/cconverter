package cat.teknos.cconverter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val exchangeRates = mapOf(
        Pair("USD", mapOf("EUR" to 0.9444, "MXN" to 17.3692)),
        Pair("EUR", mapOf("USD" to 1.0589, "MXN" to 18.3937)),
        Pair("MXN", mapOf("USD" to 0.0576, "EUR" to 0.0544))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        findViewById<Button>(R.id.btnCalculate).setOnClickListener {
            val spnBaseCurrency = findViewById<Spinner>(R.id.SpnBaseCurrency)
            val spnQuoteCurrency = findViewById<Spinner>(R.id.SpnQuoteCurrency)
            val baseQuantity = findViewById<EditText>(R.id.baseQuantity)
            val convertedAmount = findViewById<TextView>(R.id.convertedAmount)

            val baseCoin = spnBaseCurrency.selectedItem.toString()
            val quoteCoin = spnQuoteCurrency.selectedItem.toString()
            val changeValueText = baseQuantity.text.toString()

            if (changeValueText.isNotBlank()) {
                try {
                    val changeValue = changeValueText.toDouble()
                    val result = exchange(baseCoin, quoteCoin, changeValue)

                    if (result > 0) {
                        convertedAmount.text = String.format(
                            Locale.US,
                            "%.2f %s",
                            result,
                            quoteCoin
                        )
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "This option does not have an exchange value",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        this@MainActivity,
                        "Invalid input. Please enter a valid number.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter a quantity to convert.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun exchange(baseCurrency: String, quoteCurrency: String, lastPrice: Double): Double {
        return exchangeRates[baseCurrency]?.get(quoteCurrency)?.let {
            if (it > 0) {
                lastPrice * it
            } else {
                -1.0
            }
        } ?: -1.0
    }
}
