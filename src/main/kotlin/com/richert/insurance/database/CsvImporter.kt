package com.richert.insurance.database

interface CsvImporter {
    fun import(rows: List<Map<String, String>>)
}