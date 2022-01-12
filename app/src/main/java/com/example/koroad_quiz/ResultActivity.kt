package com.example.koroad_quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val mockScore = intent.getIntExtra("answer", 0)
        if (mockScore >= 60) {
            score.text = getString(R.string.show_score_pass, mockScore)
        } else {
            score.text = getString(R.string.show_score_fail, mockScore)
        }

        restart_button.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}