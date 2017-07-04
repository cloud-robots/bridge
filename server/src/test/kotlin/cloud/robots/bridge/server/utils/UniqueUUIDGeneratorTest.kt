package cloud.robots.bridge.server.utils

import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.shouldNotBeBlank
import org.junit.Test

class UniqueUUIDGeneratorTest {

    companion object {
        const val UUIDS_TO_GENERATE = 10_000
    }

    @Test
    fun `class could be create`(){
        val generator = UniqueUUIDGenerator()

        generator `should be instance of` UniqueUUIDGenerator::class
    }

    @Test
    fun `new should work`(){
        val uuid = UniqueUUIDGenerator.new

        uuid.shouldNotBeBlank()
    }

    @Test

    fun `several UUID must not repeat`(){
        val UUIDs = (1..UUIDS_TO_GENERATE).map{ UniqueUUIDGenerator.new }.toList()
        val uniqueUUIDs = UUIDs.distinct()

        uniqueUUIDs.size `should equal to` UUIDS_TO_GENERATE
    }
}
