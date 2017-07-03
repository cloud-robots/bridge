package ev3dev.bridge.server

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
        var application: Application
    }

    @Test
    fun `main could be invoked`() {
        main(arrayOf())
    }
}