package com.koroad.koroad_quiz

data class Question(
    val problem_num : Int,
    val problem : String,
    val example1 : String,
    val example2 : String,
    val example3 : String,
    val example4 : String,
    val example5 : String,
    val explanation : String,
    val answer : String,
    val video : Boolean,
    val image : Boolean
)
