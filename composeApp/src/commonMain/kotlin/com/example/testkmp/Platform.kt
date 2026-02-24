package com.example.testkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform