package com.lukaslechner.playground.fundamentals

var startTime = 0L
fun  timeCompleted() : Long = System.currentTimeMillis() - startTime

fun main() {
    startTime = System.currentTimeMillis()
    println("main start ${timeCompleted()}")
    routine(1,500)
    routine(2,300)
    println("main end ${timeCompleted()}")
}

fun routine(i: Int, i2: Int) {
    println("routine $i start ${timeCompleted()}")
    Thread.sleep(i2.toLong())
    println("routine $i end ${timeCompleted()}")
}
