package com.lukaslechner.playground.fundamentals

import kotlinx.coroutines.delay
import kotlin.concurrent.thread

fun main(){
    repeat(1000000){
        thread {
            Thread.sleep(500)
            print(".")
        }
    }
}