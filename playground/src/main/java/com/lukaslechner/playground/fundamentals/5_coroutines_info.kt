package com.lukaslechner.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

var startTime5 = 0L
fun  timeCompleted5() : Long = System.currentTimeMillis() - startTime

fun main() = runBlocking{
    startTime = System.currentTimeMillis()
    println("main start ${timeCompleted5()} info ${Thread.currentThread().name}")
    joinAll(
        async { routine5(1, 500) },
        async { routine5(2, 300)}
    )
    println("main end ${timeCompleted5()} info ${Thread.currentThread().name}")
}

suspend fun routine5(i: Int, i2: Int) {
    println("routine $i start ${timeCompleted5()} info ${Thread.currentThread().name}")
    delay(i2.toLong())
    println("routine $i end ${timeCompleted5()} info ${Thread.currentThread().name}")
}

