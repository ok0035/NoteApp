package com.zerodeg.memoapp.ui

import androidx.fragment.app.Fragment
import com.zerodeg.memoapp.R
import com.zerodeg.memoapp.ui.note.NoteFragment

open class BaseFragment: Fragment() {

    fun replace(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun replaceNoBackStack(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

}