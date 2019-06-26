package com.example.musicfinal

import com.example.musicfinal.utils.isWordInString
import org.junit.Test

class CheckWorkInString {
    @Test
    fun hasWorkInString(){
        val searchStr="a"
        val str="asjd qwe fqw"
        assert(isWordInString(str,searchStr))
    }

    @Test
    fun hasNoWorkInString(){
        val searchStr="a"
        val str="sjd t ttqwe fqw"
        assert(!isWordInString(str,searchStr))
    }
}