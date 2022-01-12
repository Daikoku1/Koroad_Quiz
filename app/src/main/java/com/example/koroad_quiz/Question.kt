package com.example.koroad_quiz

data class Question(
    val problem : String,
    val example1 : String,
    val example2 : String,
    val example3 : String,
    val example4 : String,
    val explanation : String,
    val answer : String,
    val video : Boolean
)
