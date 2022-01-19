package com.example.koroad_quiz

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_special_quiz.*
import org.json.JSONObject
import java.util.*

class SpecialQuizActivity : AppCompatActivity() {
    private var mCurrentIndex: Int = 0
    private var mockScore : Int = 0
    private var lastSelectedAnswer : String = ""
    private val mQuizList = ArrayList<Question>(1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val assetManager = resources.assets
        val inputStream= assetManager.open("quiz.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("QUIZ")
        val testQuiz = createRandomNumberList()
        for(i in testQuiz) {
            val obj = jArray.getJSONObject(i)
            val problem = obj.getString("problem")
            val example1 = obj.getString("examples1")
            val example2 = obj.getString("examples2")
            val example3 = obj.getString("examples3")
            val example4 = obj.getString("examples4")
            val example5 = obj.getString("examples5")
            val explanation = obj.getString("explanation")
            val answer = obj.getString("answer")
            val video = obj.getBoolean("video")
            val image = obj.getBoolean("image")
            Log.d("test", problem)
            val data = Question(
                i+1, problem, example1, example2, example3, example4,
                example5, explanation, answer, video, image
            )
            mQuizList.add(data)
        }

//            963, "a", "b", "c", "d",
//            "e", "f", "g", "1", video = true, image = false)
//        mQuizList.add(testdata)
//        val testdata = Question(

//        val testdata2 = Question(
//            700, "a", "b", "c", "d",
//            "e", "f", "g", "1", video = false, image = true)
//        mQuizList.add(testdata2)

        setContentView(R.layout.activity_special_quiz)
        updateQuestion()
        setButton()
        next_button.isEnabled = false

    }

    private fun updateQuestion() {
        tv_question.text = mQuizList[mCurrentIndex].problem
        tv_option_one.text = mQuizList[mCurrentIndex].example1
        tv_option_two.text = mQuizList[mCurrentIndex].example2
        tv_option_three.text = mQuizList[mCurrentIndex].example3
        tv_option_four.text = mQuizList[mCurrentIndex].example4
        tv_option_five.text = mQuizList[mCurrentIndex].example5
        tv_option_five.isEnabled = mQuizList[mCurrentIndex].example5 != ""

        if (mQuizList[mCurrentIndex].video) {
            val videoParams: ViewGroup.LayoutParams = tv_video.layoutParams
            videoParams.height = dpToPx(this, 200f)
            val imageParams: ViewGroup.LayoutParams = tv_image.layoutParams
            imageParams.height = dpToPx(this, 0f)

            val quizNumber = "q" + mQuizList[mCurrentIndex].problem_num
            val videoPath = "android.resource://$packageName/raw/$quizNumber"
            val uri: Uri = Uri.parse(videoPath)
            tv_video.setVideoURI(uri)
            tv_video.setMediaController(MediaController(this)) // 없으면 에러
            tv_video.requestFocus() // 준비하는 과정을 미리함

            tv_video.setOnPreparedListener {
                Toast.makeText(applicationContext, "동영상 재생 준비 완료", Toast.LENGTH_SHORT).show()
                tv_video.start() // 동영상 재개
            }
        } else if (mQuizList[mCurrentIndex].image) {
            val imageParams: ViewGroup.LayoutParams = tv_image.layoutParams
            imageParams.height = dpToPx(this, 200f)
            val videoParams: ViewGroup.LayoutParams = tv_video.layoutParams
            videoParams.height = dpToPx(this, 0f)

//          Log.d("test", "ELIF OK")
            val quizNumber = "q" + mQuizList[mCurrentIndex].problem_num
            val imagePath = "android.resource://$packageName/raw/$quizNumber"
            Glide.with(this)
                .load(imagePath)
                .into(tv_image)
        } else {
            val videoParams: ViewGroup.LayoutParams = tv_video.layoutParams
            videoParams.height = dpToPx(this, 0f)
            val imageParams: ViewGroup.LayoutParams = tv_image.layoutParams
            imageParams.height = dpToPx(this, 0f)
        }
    }
    private fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

    private fun isRightAnswer(): Boolean {
        val ans = mQuizList[mCurrentIndex].answer.split(", ")
        return ans.contains(lastSelectedAnswer)
    }
    private fun resetBackground() {
        tv_option_one.background = ContextCompat.getDrawable(
            this, R.drawable.default_option_border_bg)
        tv_option_two.background = ContextCompat.getDrawable(
            this, R.drawable.default_option_border_bg)
        tv_option_three.background = ContextCompat.getDrawable(
            this, R.drawable.default_option_border_bg)
        tv_option_four.background = ContextCompat.getDrawable(
            this, R.drawable.default_option_border_bg)
        tv_option_five.background = ContextCompat.getDrawable(
            this, R.drawable.default_option_border_bg)
    }

    private fun createRandomNumberList(): List<Int> {
        val quizindex = mutableListOf<Int>()
        val range = (610..996)
        while (quizindex.size < 10) {
            val randomNumber = range.random()
            if (quizindex.contains(randomNumber)) {
                continue
            }
            quizindex.add(randomNumber)
        }
        quizindex.sort()
        return quizindex
    }

    private fun setButton() {
        explanation_button.setOnClickListener {
            val explanationText = mQuizList[mCurrentIndex].explanation
            // 다이얼로그를 생성하기 위해 Builder 클래스 생성자 사용
            val builder = AlertDialog.Builder(this)
            builder.setTitle("문제 해설")
                .setMessage(explanationText)
            // 다이얼로그를 띄워주기
            builder.show()
        }

        next_button.setOnClickListener {
            val rightAnswer = isRightAnswer()
            if (rightAnswer) {
                mockScore += 1
            }
            mCurrentIndex = (mCurrentIndex + 1)
            if (mCurrentIndex == mQuizList.size) {
                val intent = Intent(this, ResultActivity::class.java)
                val testscore = mockScore * 100 / mQuizList.size
                intent.putExtra("answer", testscore)
                startActivity(intent)
            } else {
                tv_image.setImageResource(0)
                updateQuestion()
                resetBackground()
                next_button.isEnabled = false
                progressBar.progress = mCurrentIndex + 1
                tv_progress.text = getString(R.string.progress_text, mCurrentIndex+1, mQuizList.size)
            }
        }

        tv_option_one.setOnClickListener {
            resetBackground()
            lastSelectedAnswer = "1"
            if (isRightAnswer()) {
                tv_option_one.background = ContextCompat.getDrawable(
                    this, R.drawable.correct_option_border_bg)
            } else {
                tv_option_one.background = ContextCompat.getDrawable(
                    this, R.drawable.wrong_option_border_bg)
            }
            next_button.isEnabled = true
        }

        tv_option_two.setOnClickListener {
            resetBackground()
            lastSelectedAnswer = "2"
            if (isRightAnswer()) {
                tv_option_two.background = ContextCompat.getDrawable(
                    this, R.drawable.correct_option_border_bg)
            } else {
                tv_option_two.background = ContextCompat.getDrawable(
                    this, R.drawable.wrong_option_border_bg)
            }
            next_button.isEnabled = true
        }

        tv_option_three.setOnClickListener {
            resetBackground()
            lastSelectedAnswer = "3"
            if (isRightAnswer()) {
                tv_option_three.background = ContextCompat.getDrawable(
                    this, R.drawable.correct_option_border_bg)
            } else {
                tv_option_three.background = ContextCompat.getDrawable(
                    this, R.drawable.wrong_option_border_bg)
            }
            next_button.isEnabled = true
        }

        tv_option_four.setOnClickListener {
            resetBackground()
            lastSelectedAnswer = "4"
            if (isRightAnswer()) {
                tv_option_four.background = ContextCompat.getDrawable(
                    this, R.drawable.correct_option_border_bg)
            } else {
                tv_option_four.background = ContextCompat.getDrawable(
                    this, R.drawable.wrong_option_border_bg)
            }
            next_button.isEnabled = true

        }

        tv_option_five.setOnClickListener {
            resetBackground()
            lastSelectedAnswer = "5"
            if (isRightAnswer()) {
                tv_option_five.background = ContextCompat.getDrawable(
                    this, R.drawable.correct_option_border_bg)
            } else {
                tv_option_five.background = ContextCompat.getDrawable(
                    this, R.drawable.wrong_option_border_bg)
            }
            next_button.isEnabled = true
        }
    }
}