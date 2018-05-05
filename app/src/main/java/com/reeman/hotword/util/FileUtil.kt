package com.reeman.hotword.util

import java.io.File


/**
 * Created by YSAN on 2018/04/25
 */
fun readFile(path: String): String {
    val file = File(path)
    val res = file.readText()
    return res
}

fun writeFile(path: String, content: String) {
    val file = File(path)
    file.writeText(content)
}
