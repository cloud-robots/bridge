package cloud.robots.bridge.server.application

import cloud.robots.bridge.server.main
import org.amshove.kluent.`should be instance of`
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(ServerApplication::class))
class ServerApplicationTest {

  @Test
  fun `spring boot context could be loaded`() {
  }

  @Test
  fun `application class can be created`() {
    val application = ServerApplication()

    application `should be instance of` ServerApplication::class
  }

  @Test
  fun `main could be invoked`() {
    main(arrayOf<String>())
  }
}