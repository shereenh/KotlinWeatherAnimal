package shereen.weather.animal

import android.text.format.DateUtils
import org.junit.Test

import org.junit.Assert.*
import shereen.weather.animal.model.Helper
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val sample = 1552582800L

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    fun convertToUnix(date: Date): Long{
        return date.time/1000
    }

//    @Test
//    fun unixToDateTimeTest(){
//        val now = Date()
//        val rightNow = now.time / 1000
////        assertEquals(now.time, Helper.convertUnixToDateTime(rightNow).time)
//    }

    @Test
    fun dayChecker(){
        assertEquals("Thur", Helper.convertUnixToDay(sample))
    }

    @Test
    fun hourChecker(){
        assertEquals("1 PM", Helper.convertUnixToHour(sample))
    }
}
