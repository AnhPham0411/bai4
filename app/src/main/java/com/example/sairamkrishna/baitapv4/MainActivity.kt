package com.example.sairamkrishna.baitapv4

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var etFrom: EditText
    private lateinit var etTo: EditText
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var tvSymbolFrom: TextView
    private lateinit var tvSymbolTo: TextView
    private lateinit var tvRate: TextView

    private val currencyList = listOf(
        "USD - United States Dollar",
        "VND - Vietnam Dong",
        "EUR - Euro",
        "JPY - Japanese Yen",
        "GBP - British Pound",
        "AUD - Australian Dollar",
        "CAD - Canadian Dollar",
        "CHF - Swiss Franc",
        "CNY - Chinese Yuan",
        "KRW - South Korean Won"
    )

    private val rates = mapOf(
        "USD" to 1.0,
        "VND" to 26035.02,
        "EUR" to 0.91,
        "JPY" to 110.0,
        "GBP" to 0.78,
        "AUD" to 1.45,
        "CAD" to 1.34,
        "CHF" to 0.92,
        "CNY" to 7.08,
        "KRW" to 1350.0
    )

    private val symbols = mapOf(
        "USD" to "$",
        "VND" to "₫",
        "EUR" to "€",
        "JPY" to "¥",
        "GBP" to "£",
        "AUD" to "A$",
        "CAD" to "C$",
        "CHF" to "Fr",
        "CNY" to "¥",
        "KRW" to "₩"
    )

    private var fromFocused = true
    private val decimalFormat = DecimalFormat("#,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etFrom = findViewById(R.id.etFrom)
        etTo = findViewById(R.id.etTo)
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        tvSymbolFrom = findViewById(R.id.tvSymbolFrom)
        tvSymbolTo = findViewById(R.id.tvSymbolTo)
        tvRate = findViewById(R.id.tvRate)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        spinnerFrom.setSelection(0)
        spinnerTo.setSelection(1)

        etFrom.setOnFocusChangeListener { _, hasFocus ->
            fromFocused = hasFocus
        }

        etFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { updateConversion() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateConversion()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateConversion()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        updateConversion()
    }

    private fun updateConversion() {
        val fromCode = spinnerFrom.selectedItem.toString().substringBefore(" -")
        val toCode = spinnerTo.selectedItem.toString().substringBefore(" -")

        tvSymbolFrom.text = symbols[fromCode]
        tvSymbolTo.text = symbols[toCode]
        tvRate.text = "1 $fromCode = ${decimalFormat.format(rates[toCode]!! / rates[fromCode]!!)} $toCode"

        val input = etFrom.text.toString().toDoubleOrNull()
        if (input != null) {
            val result = input * rates[toCode]!! / rates[fromCode]!!
            etTo.setText(decimalFormat.format(result))
        } else {
            etTo.setText("")
        }
    }
}
