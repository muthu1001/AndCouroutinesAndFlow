package com.lukaslechner.playground.fundamentals

import kotlin.concurrent.thread

var startTime3 = 0L
fun  timeCompleted3() : Long = System.currentTimeMillis() - startTime3

fun main() {
    startTime3 = System.currentTimeMillis()
    println("main start ${timeCompleted3()}")
    routine3(1,500)
    routine3(2,300)
    println("main end ${timeCompleted3()}")
}

fun routine3(i: Int, i2: Int) {
    thread {
        println("routine3 $i start ${timeCompleted3()}")
        Thread.sleep(i2.toLong())
        println("routine3 $i end ${timeCompleted3()}")
    }
}
