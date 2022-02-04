package com.koroad.koroad_quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.koroad_quiz.R
import com.loukwn.stagestepbar.StageStepBar
import kotlinx.android.synthetic.main.activity_normal_quiz.*
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.*

class NormalQuizActivity : AppCompatActivity() {
    private var mCurrentIndex: Int = 0
    private var mockScore : Int = 0
    private val mQuizList = ArrayList<Question>(1)
    private var ans = emptyList<String>()
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
        setContentView(R.layout.activity_normal_quiz)
        updateQuestion()
        setButton()
        next_button.isEnabled = false
    }

    private fun createRandomNumberList(): List<Int> {
        val quizindex = mutableListOf<Int>()
        val range = (0..609)
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

    private fun resetExamples() {
        tv_option_one.isSelected = false
        tv_option_two.isSelected = false
        tv_option_three.isSelected = false
        tv_option_four.isSelected = false
        tv_option_five.isSelected = false
    }

    private fun updateQuestion() {
        tv_question.text = mQuizList[mCurrentIndex].problem
        tv_option_one.text = mQuizList[mCurrentIndex].example1
        tv_option_two.text = mQuizList[mCurrentIndex].example2
        tv_option_three.text = mQuizList[mCurrentIndex].example3
        tv_option_four.text = mQuizList[mCurrentIndex].example4
        tv_option_five.text = mQuizList[mCurrentIndex].example5
        tv_option_five.isEnabled = mQuizList[mCurrentIndex].example5 != ""
        ans = mQuizList[mCurrentIndex].answer.split(", ")
        resetExamples()
    }

    private fun isRightAnswer(): Boolean {
        if (ans.contains("1") && !tv_option_one.isSelected){ return false }
        if (ans.contains("2") && !tv_option_two.isSelected){ return false }
        if (ans.contains("3") && !tv_option_three.isSelected){ return false }
        if (ans.contains("4") && !tv_option_four.isSelected){ return false }
        if (ans.contains("5") && !tv_option_five.isSelected){ return false }
        return true
    }

    private fun countSelectedAnswer(): Boolean {
        var selectedAnswer = 0
        if (tv_option_one.isSelected){ selectedAnswer += 1 }
        if (tv_option_two.isSelected){ selectedAnswer += 1 }
        if (tv_option_three.isSelected){ selectedAnswer += 1 }
        if (tv_option_four.isSelected){ selectedAnswer += 1 }
        if (tv_option_five.isSelected){ selectedAnswer += 1 }
        return ans.size == selectedAnswer
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

        answercheck.setOnClickListener {
            val rightAnswer = isRightAnswer()
            if (rightAnswer) {
                MotionToast.createColorToast(this,
                    "정답입니다. 😍",
                    "",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_CENTER,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this,R.font.helvetica_regular))
            } else {
                MotionToast.createColorToast(this,
                    "오답입니다. ☹️",
                    "다시 풀어보세요",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_CENTER,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this,R.font.helvetica_regular))
                resetExamples()
            }
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
                updateQuestion()
                next_button.isEnabled = false
                val stageStepBar = findViewById<StageStepBar>(R.id.stageStepBar)
                stageStepBar.setCurrentState(
                    StageStepBar.State(
                        stage = mCurrentIndex,
                        step = 0,
                    )
                )
            }
        }

        tv_option_one.setOnClickListener {
            if (ans.size == 1) {resetExamples()}
            tv_option_one.isSelected = tv_option_one.isSelected != true
            next_button.isEnabled = countSelectedAnswer()
        }

        tv_option_two.setOnClickListener {
            if (ans.size == 1) {resetExamples()}
            tv_option_two.isSelected = tv_option_two.isSelected != true
            next_button.isEnabled = countSelectedAnswer()
        }

        tv_option_three.setOnClickListener {
            if (ans.size == 1) {resetExamples()}
            tv_option_three.isSelected = tv_option_three.isSelected != true
            next_button.isEnabled = countSelectedAnswer()
        }

        tv_option_four.setOnClickListener {
            if (ans.size == 1) {resetExamples()}
            tv_option_four.isSelected = tv_option_four.isSelected != true
            next_button.isEnabled = countSelectedAnswer()
        }

        tv_option_five.setOnClickListener {
            if (ans.size == 1) {resetExamples()}
            tv_option_five.isSelected = tv_option_five.isSelected != true
            next_button.isEnabled = countSelectedAnswer()
        }
    }
}