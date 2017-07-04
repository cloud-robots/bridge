package cloud.robots.bridge.server

import org.amshove.kluent.`should be instance of`
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class ApplicationTests {

    @Test
    fun `spring boot context could be loaded`() {
    }

    @Test
    fun `application class can be created`() {
        val application = Application()

        application `should be instance of` application::class
    }

    @Test
    fun `main could be invoked`() {
        main(arrayOf<String>())
    }
}