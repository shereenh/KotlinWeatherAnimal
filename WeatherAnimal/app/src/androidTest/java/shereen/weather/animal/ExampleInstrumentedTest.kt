package shereen.weather.animal

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import shereen.weather.animal.model.Helper
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("shereen.weather.animal", appContext.packageName)
    }

//    @Test
//    fun unixToDateTimeTest(){
//        val now: Date = Date()
//        val rightNow = now.time / 1000
//        assertEquals(now, Helper.convertUnixToDateTime(rightNow))
//
//    }
}
