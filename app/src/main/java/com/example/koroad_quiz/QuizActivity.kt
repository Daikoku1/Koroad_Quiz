package com.example.koroad_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import org.json.JSONObject
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz.*
import android.util.TypedValue

import android.view.ViewGroup




class QuizActivity : AppCompatActivity() {
    private var mCurrentIndex: Int = 0
    private var mockScore : Int = 0
    private var lastSelectedAnswer : String = ""
    private val mQuizList = ArrayList<Question>(1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val assetManager = resources.assets
        val inputStream= assetManager.open("quiz2.json")
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
                i, problem, example1, example2, example3, example4,
                example5, explanation, answer, video, image
            )
            mQuizList.add(data)
        }
        val testdata = Question(
            966, "a", "b", "c", "d",
            "e", "f", "g", "1", video = true, image = false)
        mQuizList.add(testdata)

        setContentView(R.layout.activity_quiz)
        updateQuestion()
        setButton()
        next_button.isEnabled = false

    }

    private fun createRandomNumberList(): List<Int> {
        val quizindex = mutableListOf<Int>()
        val range = (0..680)
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

    private fun updateQuestion() {
        tv_question.text = mQuizList[mCurrentIndex].problem
        tv_option_one.text = mQuizList[mCurrentIndex].example1
        tv_option_two.text = mQuizList[mCurrentIndex].example2
        tv_option_three.text = mQuizList[mCurrentIndex].example3
        tv_option_four.text = mQuizList[mCurrentIndex].example4
        tv_option_five.text = mQuizList[mCurrentIndex].example5
        tv_option_five.isEnabled = mQuizList[mCurrentIndex].example5 != ""

        if (mQuizList[mCurrentIndex].video) {
            val layoutParams: ViewGroup.LayoutParams = tv_video.layoutParams
            val height =
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    200f,
                    resources.displayMetrics
                )
                    .toInt()
            layoutParams.height = height

            val videoPath = "android.resource://" + packageName + "/" + R.raw.t966
            val uri: Uri = Uri.parse(videoPath)
            tv_video.setVideoURI(uri)
            tv_video.setMediaController(MediaController(this)) // 없으면 에러
            tv_video.requestFocus() // 준비하는 과정을 미리함

            tv_video.setOnPreparedListener {
                Toast.makeText(applicationContext, "동영상 재생 준비 완료", Toast.LENGTH_SHORT).show()
                tv_video.start() // 동영상 재개
            }
        } else {
            val layoutParams: ViewGroup.LayoutParams = tv_video.layoutParams
            val height =
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    0f,
                    resources.displayMetrics
                )
                    .toInt()
            layoutParams.height = height
        }
    }
    private fun isRightAnswer(): Boolean {
        val ans = mQuizList[mCurrentIndex].answer.split(",")
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
            val ans = mQuizList[mCurrentIndex].answer.split(",")
            val rightAnswer = ans.contains(lastSelectedAnswer)
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