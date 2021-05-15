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
fun smsParser(input: String, maxLength: Int): ArrayList<String> {
    val inputSms = input.replace("\\s+".toRegex(), " ")
    var resultStringList = arrayListOf<String>()
    val inputLength = inputSms.length
    if (inputLength <= maxLength) {
        resultStringList.add(inputSms)
    } else {
        val currentCount: Int = inputLength / maxLength

        resultStringList = inputSms.split(" ") as ArrayList<String>

        val result = parser(resultStringList, maxLength, getCountsOfDigits(currentCount))
        resultStringList = arrayListOf()
        for (i in result.indices) {
            resultStringList.add(String(result[i]))
        }
    }
    return resultStringList
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
fun smsParserVariantTwo(inputSms: String, maxLength: Int): ArrayList<String> {
    val inputLength = inputSms.length
    val resultStringList = arrayListOf<String>()
    if (inputLength <= maxLength) {
        resultStringList.add(inputSms)
    } else {
        val currentCount: Int = inputLength / maxLength

        val result = stringParser(inputSms, maxLength, getCountsOfDigits(currentCount))
        for (i in result.indices) {
            resultStringList.add(String(result[i]))
        }
    }
    return resultStringList
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

