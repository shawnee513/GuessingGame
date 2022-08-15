package com.example.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import java.util.*

class GameViewModel : ViewModel(){
    private val words = listOf("cat", "day", "night", "car", "dog", "work", "father", "art", "house", "man",
        "boy", "girl", "water", "old", "help", "tree", "run", "family", "person",
        "church", "time", "number", "mother", "sister", "brother", "face", "child",
        "teacher", "money", "party", "morning", "parent", "door", "school", "book", "hand",
        "kid", "night", "skip", "horse", "cow", "fox")
    private var allGuesses = ""
    private val word = randomWordGenerator()

    //set up live data
    private val _wordHint = MutableLiveData<String>()
    val wordHint: LiveData<String> get() = _wordHint

    private val _incorrectGuesses = MutableLiveData<String>()
    val incorrectGuesses: LiveData<String> get() = _incorrectGuesses

    private val _lives = MutableLiveData<Int>()
    val lives: LiveData<Int> get() = _lives

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    init{
        //initialize live data
        _wordHint.value = ""
        _incorrectGuesses.value = "Incorrect Guesses: "
        _lives.value = 8
        _result.value = ""

        //wordHint will be the number of dashes as there are letters in the word
        _wordHint.value = "_".repeat(word.length)
    }

    //picks a random word from words array
    private fun randomWordGenerator(): String{
        val index = (0..(words.size - 1)).random()
        return words[index]
    }

    fun makeGuess(guess: String){
        //verify letter hasn't been guessed already
        if(!allGuesses.contains(guess)){
            allGuesses += guess
            //check if guess is in the word, update ui
            if(word.contains(guess)){
                var index = 0
                while (index >= 0){
                    index = word.indexOf(guess, index)
                    if(index >= 0){
                        _wordHint.value = _wordHint.value!!.substring(0, index) + guess + _wordHint.value!!.substring(index + 1)
                        index++
                    }
                }
            } else {
                _incorrectGuesses.value = _incorrectGuesses.value + "$guess "
                _lives.value = _lives.value?.plus(-1)
            }
        }
        isGameOver()
    }

    private fun isGameOver() {
        if(_lives.value!! <= 0){
            _result.value = "You lose. The word was $word"
        }
        if(!(wordHint.value!!.contains("_"))){
            _result.value = "You win! The word was ${word.uppercase(Locale.getDefault())}"
        }
    }

}