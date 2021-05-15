import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.StringBuilder

class Test {
    @Test
    fun oneSms() {
        val stringList = smsParser("Hello, world! Hey, bro, how are you?", 120)
        val correctStringList = listOf("Hello, world! Hey, bro, how are you?")
        assertEquals(stringList, correctStringList)
    }

    @Test
    fun simpleThreeSms() {
        val stringList = smsParser("Hello, world! Hey, bro, how are you?", 20)
        println(stringList.toString())
        val correctStringList = listOf("Hello, world! 1/3", "Hey, bro, how 2/3", "are you? 3/3")
        assertEquals(stringList, correctStringList)
    }

    @Test
    fun spaceTest() {
        val stringList = smsParser("                              ", 10)
        println(stringList.toString())
        val correctStringList = listOf(" ")
        assertEquals(stringList, correctStringList)
    }

    @Test
    fun optimizeTest() {
        val stringBuilder = StringBuilder()
        for (i in 0..2507) {
            stringBuilder.append("a ")
        }
        val correctStringList = arrayListOf<String>()

        for(i in 1..9) {
            correctStringList.add("a a a $i/1500")
        }

        for (i in 10..99) {
            correctStringList.add("a a $i/1500")
        }

        for(i in 100..999) {
            correctStringList.add("a a $i/1500")
        }

        for (i in 1000..1499) {
            correctStringList.add("a $i/1500")
        }

        correctStringList.add("a 1500/1500")

        val stringList = smsParser(String(stringBuilder), 12)
        assertEquals(stringList, correctStringList)
    }

    @Test
    fun optimizeTestWithSpaces() {
        val stringBuilder = StringBuilder()
        for (i in 0..2507) {
            stringBuilder.append("a   ")
        }
        val correctStringList = arrayListOf<String>()

        for(i in 1..9) {
            correctStringList.add("a a a $i/1500")
        }

        for (i in 10..99) {
            correctStringList.add("a a $i/1500")
        }

        for(i in 100..999) {
            correctStringList.add("a a $i/1500")
        }
        for (i in 1000..1499) {
            correctStringList.add("a $i/1500")

        }
        correctStringList.add("a 1500/1500")


        val stringList = smsParser(String(stringBuilder), 12)
        assertEquals(stringList, correctStringList)
    }
}
