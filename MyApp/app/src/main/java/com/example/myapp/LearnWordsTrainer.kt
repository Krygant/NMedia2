package com.example.myapp

data class Word(
    val original: String,
    val translate: String,
    var learned: Boolean = false,
)

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word,
)

class LearnWordsTrainer {

    private val dictionary = listOf(
        Word("Vogon", "Вогон"),
        Word("Babel fish", "Бабел-рыба"),
        Word("Gradle blaster", "Громоглот"),
        Word("Hyperdrive", "Гипердвигатель"),
        Word("Hooloovoo", "Хулуву"),
        Word("Magratea", "Магратея"),
        Word("Infinite improbability", "Бесконечная вероятность"),
        Word("Hyper Spase", "Гиперпространство"),
        Word("Guidebook", "Путеводитель"),
        Word("Starship", "Звездолёт"),
        Word("Towel", "Полотенце"),
        Word("Paraniod Android", "Параноидальный Андроид"),
        Word("Pan Galactic", "Пангалактический"),
        Word("Deep Thought", "Глубокая Мысль"),
        Word("Teleport", "Телепорт"),
        Word("Mind", "Разум"),
        Word("Universe", "Вселенная"),
        Word("Mithhiker", "Автостопщик"),
        Word("Whale", "Кит"),
        Word("Petunias", "Петунии"),
        Word("Heart of Gold", "Сердце Золота"),
        Word("Galaxy", "Галактика"),
        Word("End of the Universe", "Конец вселенной"),
        Word("Space", "Космос"),
        Word("Probability", "Вероятность"),
    )

    private var currentQuestion: Question? = null

    fun getNextQuestion(): Question? {

        val notLearnedList = dictionary.filter { !it.learned }
        if (notLearnedList.isEmpty()) return null

        val questionWords =
            if (notLearnedList.size < NUMBER_OF_ANSWERS) {
                val learnedList = dictionary.filter { it.learned }.shuffled()
                notLearnedList.shuffled()
                    .take(NUMBER_OF_ANSWERS) + learnedList
                    .take(NUMBER_OF_ANSWERS - notLearnedList.size)
            } else {
                notLearnedList.shuffled().take(NUMBER_OF_ANSWERS)
            }.shuffled()

        val correctAnswer: Word = questionWords.random()

        currentQuestion = Question(
            variants = questionWords,
            correctAnswer = correctAnswer,
        )
        return currentQuestion
    }

    fun checkAnswer(userAnswerIndex: Int?): Boolean {

        return currentQuestion?.let {
            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.learned = true
                true
            } else {
                false
            }
        } ?: false
    }
}

const val NUMBER_OF_ANSWERS: Int = 4