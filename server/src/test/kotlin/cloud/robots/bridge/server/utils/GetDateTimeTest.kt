package cloud.robots.bridge.server.utils

import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldNotBeBlank
import org.junit.Test

class GetDateTimeTest {

    @Test
    fun `we could create the class`(){
        val getDateTime = GetDateTime()

        getDateTime `should be instance of` GetDateTime::class
    }

    @Test
    fun `UTC should work`(){
        val dateTime = GetDateTime.UTC
        dateTime.shouldNotBeBlank()
    }
}
