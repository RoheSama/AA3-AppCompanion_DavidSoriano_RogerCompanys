package com.example.appcompanion.utilities.models

interface DbBaseData {
    var id: String?

    fun getTable(): String
}