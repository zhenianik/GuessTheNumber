package com.zhenianik.guessthenumber

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.time.Duration
import java.time.LocalDateTime

class MyGame : AppCompatActivity() {
    private lateinit var game_container: ConstraintLayout
    private lateinit var rangeNumber: EditText
    private lateinit var attemeptsCounter: TextView
    private lateinit var yourNumber: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var checkNumberButton: Button
    private lateinit var infoTextView: TextView
    private var sum = 0
    private var maxForSeekBar = 0
    private var startTime: LocalDateTime? = null
    private var endTime: LocalDateTime? = null
    private var random: Double = 0.0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_main)

        game_container = findViewById(R.id.game_container)
        rangeNumber = findViewById(R.id.rangeNumber)
        attemeptsCounter = findViewById(R.id.attemeptsCounter)
        yourNumber = findViewById(R.id.yourNumber)
        seekBar = findViewById(R.id.seekBar)
        checkNumberButton = findViewById(R.id.checkNumberButton)
        infoTextView = findViewById(R.id.infoTextView)

        rangeNumber.setText("1")
        infoTextView.setText(R.string.win)
        infoTextView.visibility = View.INVISIBLE

        setRandom(rangeNumber)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                yourNumber.setText(" ${p0?.progress}")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkTheNumber(view: View) {
        val num1 = rangeNumber.text.toString().trim().toInt()
        val num2 = yourNumber.text.toString().trim().toInt()
        val random_number = (random * num1).toInt()

        if (startTime == null) startTime = LocalDateTime.now()

        if (random_number == num2) {

            endTime = LocalDateTime.now()
            val duration: Duration = Duration.between(endTime, startTime)
            val diff = Math.abs(duration.seconds).toString()

            checkNumberButton.setTextColor(Color.GREEN)
            yourNumber.setTextColor(Color.BLUE)
            checkNumberButton.isClickable = false
            infoTextView.visibility = View.VISIBLE
        }
        if (random_number > num2) {
            showMessage("Загаданное число больше!")
            seekBar.setProgress(0,true)
            seekBar.min = num2+1
        }

        if (random_number < num2) {
            showMessage("Загаданное число меньше!")
            seekBar.setProgress(0,true)
            seekBar.max = num2-1
        }

        attemeptsCounter.setText((++sum).toString())

    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.N)
    fun setRandom(view: View) {

        checkNumberButton.setTextColor(Color.WHITE)
        yourNumber.setTextColor(R.color.indigo)
        checkNumberButton.isClickable = true
        infoTextView.visibility = View.INVISIBLE

        startTime = null
        endTime = null
        seekBar.setProgress(0,true)
        maxForSeekBar = rangeNumber.text.toString().trim().toInt()

        seekBar.max = maxForSeekBar

        random = Math.random().toDouble()
        attemeptsCounter.setText("0")
        yourNumber.setText("0")
        sum = 0
    }

    fun showMessage(messageText: String) {
        Toast.makeText(
            this@MyGame,
            messageText,
            Toast.LENGTH_SHORT
        ).show()
    }
}

