package com.nangokuman.studytestproject

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JetpackTest {

    @Test
    fun gettingContextTest() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val appName = context.getString(R.string.app_name)
        assertThat(appName).isEqualTo("StudyTestProject")
    }
}