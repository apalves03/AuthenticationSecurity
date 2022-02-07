package com.apalves03.authenticationsecurity.data.source.local

interface Storage {
    fun setString(key: String, value: String)
    fun getString(key: String): String
}
