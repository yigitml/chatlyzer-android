package com.ch3x.chatlyzer

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
       
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ch3x.chatlyzer", appContext.packageName)
    }
}