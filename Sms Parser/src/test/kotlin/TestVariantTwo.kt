import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.StringBuilder

class TestVariantTwo {

    @Test
    fun oneSms() {
        val stringList = smsParser("Hello, world! Hey, bro, how are you?", 120)
        val correctStringList = listOf("Hello, world! Hey, bro, how are you?")
        assertEquals(stringList, correctStringList)
    }

    @Test
    fun simpleThreeSms() {
        val stringList = smsParserVariantTwo("Hello, world! Hey, bro, how are you?", 20)
        println(stringList.toString())
        val correctStringList = listOf("Hello, world!  1/3", "Hey, bro, how  2/3", "are you? 3/3")
        assertEquals(stringList, correctStringList)
    }

    @Test
    fun spaceTest() {
        val stringList = smsParserVariantTwo("                              ", 10)
        println(stringList.toString())
        val correctStringList = listOf("       1/5", "       2/5", "       3/5", "       4/5", "       5/5")
        assertEquals(stringList, correctStringList)
    }

    @Test
    fun optimizeTest() {
        val stringBuilder = StringBuilder()
        for (i in 0..2052) {
            stringBuilder.append("a ")
        }
        val correctStringList = mutableListOf<String>()
        //5*3
        //5*2
        var j = 1
        while (j < 8) {
            correctStringList.add("a a a $j/1500")
            ++j
            correctStringList.add(" a a  ${j}/1500")
            ++j
        }
        correctStringList.add("a a a $j/1500")


        //89*2
        for (i in 10..99) {
            correctStringList.add(" a a $i/1500")
        }
        //
        var k = 100
        while (k < 999) {

            correctStringList.add(" a  $k/1500")
            ++k
            correctStringList.add("a a $k/1500")
            ++k
        }
        for (i in 1000..1499) {
            correctStringList.add(" a $i/1500")

        }
        correctStringList.add("  1500/1500")


        //a a a 1/1800
        val stringList = smsParserVariantTwo(String(stringBuilder), 12)
        println(stringBuilder)
        assertEquals(stringList, correctStringList)
    }

    @Test
    fun optimizeTestWithSpaces() {
        val stringBuilder = StringBuilder()
        for (i in 0..821) {
            stringBuilder.append("a    ")
        }
        val correctStringList = mutableListOf<String>()
        for (i in 1..9) {
            correctStringList.add("a     $i/1502")
        }

        var j = 10
        while(j < 96) {
            correctStringList.add("a    $j/1502")
            ++j
            correctStringList.add(" a   $j/1502")
            ++j
            correctStringList.add("  a  $j/1502")
            ++j
            correctStringList.add("   a $j/1502")
            ++j
            correctStringList.add("     $j/1502")
            ++j
        }

        //
        var k = 100
        while (k < 996) {

            correctStringList.add("a   $k/1502")
            ++k
            correctStringList.add("  a $k/1502")
            ++k
            correctStringList.add("    $k/1502")
            ++k
            correctStringList.add(" a  $k/1502")
            ++k
            correctStringList.add("    $k/1502")
            ++k
        }
        //1000
        correctStringList.add("a  $k/1502")
        ++k
        //1001
        correctStringList.add("   $k/1502")
        ++k
        //1002
        correctStringList.add(" a $k/1502")
        ++k
        //1003
        correctStringList.add("   $k/1502")
        ++k
        //1004
        correctStringList.add("   $k/1502")
        ++k

        while(k < 1500) {
            //1005
            correctStringList.add("a  $k/1502")
            ++k
            //1006
            correctStringList.add("   $k/1502")
            ++k
            //1007
            correctStringList.add(" a $k/1502")
            ++k
            //1008
            correctStringList.add("   $k/1502")
            ++k
            //1009
            correctStringList.add("   $k/1502")
            ++k
        }

        //1500
        correctStringList.add("a  $k/1502")
        ++k
        //1501
        correctStringList.add("   $k/1502")
        ++k
        //1502
        correctStringList.add("  $k/1502")
        ++k


        val stringList = smsParserVariantTwo(String(stringBuilder), 12)
        println(stringList.toString())
        assertEquals(stringList, correctStringList)
    }

}