package com.android.app.android_baraka_demo.data.models

class LoadingItem: Section {
    override fun getType(): Int {
        return Section.LOADING
    }

    override fun getLabel(): String {
        return "Loading"
    }
}