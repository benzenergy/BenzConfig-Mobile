package com.example.benzconfig

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.text.format
import kotlin.text.toDouble
import kotlin.text.toDoubleOrNull
import android.widget.ImageView
import android.widget.LinearLayout
import android.view.Gravity

class MainActivity : AppCompatActivity() {

    private lateinit var inputSummer: EditText
    private lateinit var resultSummer: TextView
    private lateinit var inputWinter: EditText
    private lateinit var resultWinter: TextView
    private lateinit var inputCityProp: EditText
    private lateinit var inputRoadProp: EditText
    private lateinit var inputCityRate: EditText
    private lateinit var inputRoadRate: EditText
    private lateinit var btnAbout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputSummer = findViewById(R.id.inputSummer)
        resultSummer = findViewById(R.id.resultSummer)
        inputWinter = findViewById(R.id.inputWinter)
        resultWinter = findViewById(R.id.resultWinter)
        inputCityProp = findViewById(R.id.inputCityProp)
        inputRoadProp = findViewById(R.id.inputRoadProp)
        inputCityRate = findViewById(R.id.inputCityRate)
        inputRoadRate = findViewById(R.id.inputRoadRate)
        btnAbout = findViewById(R.id.btnAbout)

        findViewById<Button>(R.id.btnSummer).setOnClickListener {
            try {
                val distance = inputSummer.text.toString().toDouble()
                val cityRate = inputCityRate.text.toString().toDoubleOrNull() ?: 11.5
                val roadRate = inputRoadRate.text.toString().toDoubleOrNull() ?: 8.5
                resultSummer.text = calculateFuel(distance, cityRate, roadRate)
            } catch (e: Exception) {
                resultSummer.text = "Введите корректные числовые значения"
            }
        }

        findViewById<Button>(R.id.btnWinter).setOnClickListener {
            try {
                val distance = inputWinter.text.toString().toDouble()
                val cityRate = inputCityRate.text.toString().toDoubleOrNull() ?: 13.8
                val roadRate = inputRoadRate.text.toString().toDoubleOrNull() ?: 10.2
                resultWinter.text = calculateFuel(distance, cityRate, roadRate)
            } catch (e: Exception) {
                resultWinter.text = "Введите корректные числовые значения"
            }
        }

        btnAbout.setOnClickListener {
            val imageView = ImageView(this)
            imageView.setImageResource(R.mipmap.ic_launcher)
            imageView.adjustViewBounds = true
            imageView.layoutParams = LinearLayout.LayoutParams(200, 200)
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            val textView = TextView(this)
            textView.text =
                    "BenzConfig Mobile\n\n" +
                    "Лицензия: GNU GPL v3.0 \n" +
                    "Материалы: www.flaticon.com \n" +
                    "Исходный код: github.com/benzenergy \n" +
                    "Автор: В.А. Чекаев"

            textView.setPadding(0, 16, 0, 0)
            textView.gravity = Gravity.CENTER
            // Вертикальный LinearLayout для иконки и текста
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            layout.gravity = Gravity.CENTER
            layout.setPadding(32, 32, 32, 32)
            layout.addView(imageView)
            layout.addView(textView)

            AlertDialog.Builder(this)
                .setView(layout)
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun calculateFuel(distance: Double, cityRate: Double, roadRate: Double): String {
        val cityProp = inputCityProp.text.toString().toDoubleOrNull()?.div(100) ?: 0.3
        val roadProp = inputRoadProp.text.toString().toDoubleOrNull()?.div(100) ?: 0.7

        val cityDistance = distance * cityProp
        val roadDistance = distance * roadProp

        val cityFuel = cityDistance * cityRate / 100
        val roadFuel = roadDistance * roadRate / 100
        val totalFuel = cityFuel + roadFuel

        return "Общий расход ${"%.2f".format(totalFuel)} л\n\n" +
                "Детализация\n" +
                "Пробег по городу ${"%.2f".format(cityDistance)} км\n" +
                "Пробег по трассе ${"%.2f".format(roadDistance)} км\n\n" +
                "Нормы расхода\n" +
                "Город $cityRate л на 100 км\n" +
                "Трасса $roadRate л на 100 км\n\n" +
                "Пропорции\n" +
                "Городской режим: ${"%.0f".format(cityProp*100)}%\n" +
                "Трассовый режим: ${"%.0f".format(roadProp*100)}%"
    }
}