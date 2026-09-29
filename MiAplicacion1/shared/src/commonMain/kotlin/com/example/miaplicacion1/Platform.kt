package com.example.miaplicacion1

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform