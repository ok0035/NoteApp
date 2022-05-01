package com.zerodeg.memoapp.ui.main

import android.text.Editable

interface MainInterface {

    fun clickWrite()
    fun clickDelete()
    fun changedSearchingTest(edit: Editable?)
}