fun getCountsOfDigits(value: Int): Int {
    var number = value
    var count = if (number == 0) 1 else 0
    while (number != 0) {
        ++count
        number /= 10
    }
    return count
}

//первый вариант
fun smsParser(input: String, maxLength: Int): MutableList<String> {
    var inputSms = input.replace("\\s+".toRegex(), " ")
    var stringList = mutableListOf<String>()
    val inputLength = inputSms.length
    if (inputLength <= maxLength) {
        stringList.add(inputSms)
    } else {
        var currentCount: Int = inputLength / maxLength

        stringList = inputSms.split(" ") as MutableList<String>

        var result = parser(stringList, maxLength, getCountsOfDigits(currentCount))
        stringList = arrayListOf()
        for (i in result.indices) {
            stringList.add(String(result[i]))
        }
    }
    return stringList

}

fun parser(inputStringList: List<String>, maxStringLength: Int, lettersOfCountString: Int): ArrayList<StringBuilder> {
    var string: StringBuilder = StringBuilder()
    val outputStringList = arrayListOf<StringBuilder>()
    var counter = 1
    var addedIndex = 0
    var withSpace = false
    for (i in inputStringList.indices) {
        val currentMaxStringLength = maxStringLength - lettersOfCountString - getCountsOfDigits(counter) - 2

        if (string.isNotEmpty() && string.length + 1 <= currentMaxStringLength) {
            string.append(' ')
            withSpace = false
        }

        if (string.length + inputStringList[i].length < currentMaxStringLength) {
            string.append(inputStringList[i])
            addedIndex = i
            withSpace = true
        } else if (string.length + inputStringList[i].length == currentMaxStringLength) {
            string.append(inputStringList[i])
            addedIndex = i
            withSpace = string.last() != ' '
        } else {
            if (withSpace) {
                string.append(" $counter/")
                withSpace = false
            } else {
                string.append("$counter/")
                withSpace = false
            }
            outputStringList.add(string)
            ++counter
            if (addedIndex != i) {
                string = StringBuilder(inputStringList[i])
                withSpace = true
            }
        }
    }

    if (addedIndex < inputStringList.size) {
        if (withSpace) {
            string.append(" $counter/")
        } else {
            string.append("$counter/")
        }
        outputStringList.add(StringBuilder("$string"))
    }

    val lettersOfMaxCount = getCountsOfDigits(counter)
    if (lettersOfMaxCount > lettersOfCountString) {
        return parser(inputStringList, maxStringLength, lettersOfMaxCount)
    }

    for (i in outputStringList.indices) {
        outputStringList[i].append(counter)
    }
    return outputStringList
}


//второй вариант
fun smsParserVariantTwo(inputSms: String, maxLength: Int): MutableList<String> {
    var stringList = mutableListOf<String>()
    val inputLength = inputSms.length
    if (inputLength <= maxLength) {
        stringList.add(inputSms)
    } else {
        var currentCount: Int = inputLength / maxLength

        var result = stringParser(inputSms, maxLength, getCountsOfDigits(currentCount))
        stringList = arrayListOf()
        for (i in result.indices) {
            stringList.add(String(result[i]))
        }
    }
    return stringList
}

fun stringParser(inputString: String, maxStringLength: Int, lettersOfCountString: Int): ArrayList<StringBuilder> {
    var currentWord = StringBuilder()
    var stringBuilder = StringBuilder()
    val outputStringList = arrayListOf<StringBuilder>()
    var counter = 1
    for (i in inputString.indices) {
        val currentMaxStringLength = maxStringLength - lettersOfCountString - getCountsOfDigits(counter) - 2

        val char = inputString[i]

        if (char != ' ') {
            currentWord.append(char)
        } else {
            if (currentWord.isNotEmpty()) {
                if (stringBuilder.length + currentWord.length <= currentMaxStringLength) {

                    stringBuilder.append(currentWord)
                    currentWord = StringBuilder()

                } else {
                    stringBuilder.append(" $counter/")
                    outputStringList.add(stringBuilder)
                    ++counter
                    stringBuilder = StringBuilder(currentWord)
                    currentWord = StringBuilder()
                }
            }
            if (stringBuilder.length + 1 <= currentMaxStringLength) {
                stringBuilder.append(" ")
            } else {
                stringBuilder.append(" $counter/")
                outputStringList.add(stringBuilder)
                ++counter
                stringBuilder = StringBuilder(" ")
            }
        }
    }
    val currentMaxStringLength = maxStringLength - lettersOfCountString - getCountsOfDigits(counter) - 2

    if (currentWord.isNotEmpty()) {
        if (stringBuilder.length + currentWord.length <= currentMaxStringLength) {

            stringBuilder.append(currentWord)
            outputStringList.add(stringBuilder)
            stringBuilder.append(" $counter/")
        } else {
            stringBuilder.append(" $counter/")
            outputStringList.add(stringBuilder)
            ++counter
            stringBuilder = StringBuilder(currentWord)
            stringBuilder.append(" $counter/")
            outputStringList.add(stringBuilder)
        }
    } else {
        if (stringBuilder.isNotEmpty()) {
            stringBuilder.append(" $counter/")
            outputStringList.add(stringBuilder)
        }
    }

    val lettersOfMaxCount = getCountsOfDigits(counter)
    if (lettersOfMaxCount > lettersOfCountString) {
        return stringParser(inputString, maxStringLength, lettersOfMaxCount)
    }

    for (i in outputStringList.indices) {
        outputStringList[i].append(counter)
    }

    return outputStringList
}

