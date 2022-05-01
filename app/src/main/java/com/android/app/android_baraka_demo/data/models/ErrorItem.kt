package com.android.app.android_baraka_demo.data.models

class ErrorItem(val message: String) : Section {
    override fun getType(): Int {
        return Section.ERROR
    }

    override fun getLabel(): String {
        return "Error"
    }
}