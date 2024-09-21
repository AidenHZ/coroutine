package com.example.coroutine

import java.text.SimpleDateFormat
import java.util.Date

val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")

val now = {
    dateFormat.format(Date(System.currentTimeMillis()))
}

fun log(vararg msg: Any?) = println("${now()} [${Thread.currentThread().name}] ${msg.joinToString(" ")}")

fun stackTrace(){
    Throwable().printStackTrace(System.out)
}
