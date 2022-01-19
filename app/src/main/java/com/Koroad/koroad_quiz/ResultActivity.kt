package com.Koroad.koroad_quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.koroad_quiz.R
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        supportActionBar?.apply {
            title = "Display Logo On ActionBar"
            // documentation source developer.android.com
            // Set whether to include the application home affordance in the
            // action bar. Home is presented as either an activity icon or logo.
            setDisplayShowHomeEnabled(true)
            // Set whether to display the activity logo rather than the
            // activity icon. A logo is often a wider, more detailed image.
            setDisplayUseLogoEnabled(true)
            // Set the logo to display in the 'home' section of the action bar.
            setLogo(R.drawable.gangseo_logo)
        }


        val mockScore = intent.getIntExtra("answer", 0)
        if (mockScore >= 60) {
            score.text = getString(R.string.show_score_pass, mockScore)
        } else {
            score.text = getString(R.string.show_score_fail, mockScore)
        }

        restart_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}