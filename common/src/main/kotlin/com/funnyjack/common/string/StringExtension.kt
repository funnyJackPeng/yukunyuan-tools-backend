package com.funnyjack.common.string

private val matchedChars = arrayOf(' ', '　', '\t', '\r', '\n')

fun String.fullTrim() = trim { it in matchedChars }
