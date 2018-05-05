package com.reeman.hotword.entity


/**
 * Created by YSAN on 2018/04/25
 */
data class ContentData(val userword: MutableList<ContentItem>)
data class ContentItem(val name: String, var words: MutableList<String>)
data class ItemData(var word: String, var delete: Boolean) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            !is ItemData -> false
            else -> this === other || word == other.word
        }
    }
}