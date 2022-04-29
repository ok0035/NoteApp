package com.zerodeg.memoapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zerodeg.memoapp.room.Note
import com.zerodeg.memoapp.room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun checkNoteDatabase() {
        println("checkNoteDatabase")
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val noteEntity = Note("First", "Hello !!")

        val db = NoteDatabase.getInstance(appContext)
        CoroutineScope(Dispatchers.IO).launch {
            db!!.noteDao().insert(noteEntity)
            println("test note -> ${db.noteDao().getAll()}")
//            db.noteDao().delete(0)
//            println("delete note -> ${db.noteDao().getAll()}")
        }
    }




}