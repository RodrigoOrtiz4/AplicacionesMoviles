package com.example.miaplicacionotravez

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform