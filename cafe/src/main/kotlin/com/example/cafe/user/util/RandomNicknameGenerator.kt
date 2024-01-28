package com.example.cafe.user.util

import kotlin.random.Random

class RandomNicknameGenerator {
    fun generate(): String {
        val randomDeterminer = Determiner.entries[Random.nextInt(Determiner.entries.size)]
        val randomAnimal = Animal.entries[Random.nextInt(Animal.entries.size)]
        val randomNumber = java.util.Random().nextInt(9000) + 1000
        return "${randomDeterminer.name} ${randomAnimal.name}$randomNumber"
    }
}
enum class Determiner {
    예쁜,
    화난,
    귀여운,
    배고픈,
    철학적인,
    현학적인,
    슬픈,
    푸른,
    비싼,
    밝은
}

enum class Animal {
    호랑이,
    비버,
    강아지,
    부엉이,
    여우,
    치타,
    문어,
    고양이,
    미어캣,
    다람쥐
}