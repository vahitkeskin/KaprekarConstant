package com.vahitkeskin.kaprekarconstant

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform