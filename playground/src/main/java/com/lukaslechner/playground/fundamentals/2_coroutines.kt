package com.lukaslechner.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

var startTime2 = 0L
fun  timeCompleted2() : Long = System.currentTimeMillis() - startTime

fun main() = runBlocking{
    startTime = System.currentTimeMillis()
    println("main start ${timeCompleted2()}")
    joinAll(
        async { routine2(1, 500) },
        async { routine2(2, 300)}
    )
    println("main end ${timeCompleted2()}")
}

suspend fun routine2(i: Int, i2: Int) {
    println("routine $i start ${timeCompleted2()}")
    delay(i2.toLong())
    println("routine $i end ${timeCompleted2()}")
}

