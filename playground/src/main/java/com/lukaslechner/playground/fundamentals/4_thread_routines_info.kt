package com.lukaslechner.playground.fundamentals

import kotlin.concurrent.thread

var startTime4 = 0L
fun  timeCompleted4() : Long = System.currentTimeMillis() - startTime4

fun main() {
    startTime4 = System.currentTimeMillis()
    println("main start ${timeCompleted4()} info ${Thread.currentThread().name}")
    routine4(1,500)
    routine4(2,300)
    println("main end ${timeCompleted4()} info ${Thread.currentThread().name}")
}

fun routine4(i: Int, i2: Int) {
    thread {
        println("routine4 $i start ${timeCompleted4()} info ${Thread.currentThread().name}")
        Thread.sleep(i2.toLong())
        println("routine4 $i end ${timeCompleted4()} info ${Thread.currentThread().name}")
    }
}
