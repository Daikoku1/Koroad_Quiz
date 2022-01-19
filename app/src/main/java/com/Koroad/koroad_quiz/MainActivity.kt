package com.Koroad.koroad_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.koroad_quiz.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_normal.setOnClickListener {
            val intent = Intent(this, NormalQuizActivity::class.java)
            startActivity(intent)
        }
        btn_special.setOnClickListener {
            val intent = Intent(this, SpecialQuizActivity::class.java)
            startActivity(intent)
        }
    }
}