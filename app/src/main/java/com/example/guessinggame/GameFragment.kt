package com.example.guessinggame

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding
import java.util.*

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private var word = ""
    private var wordHint = ""
    private var incorrectGuesses = "Incorrect Guesses: "
    private var lives = 8
    private var allGuesses = ""

    val words = listOf("cat", "day", "night", "car", "dog", "work", "father", "art", "house", "man",
                        "boy", "girl", "water", "old", "help", "tree", "run", "family", "person",
                        "church", "time", "number", "mother", "sister", "brother", "face", "child",
                        "teacher", "money", "party", "morning", "parent", "door", "school", "book", "hand",
                        "kid", "night", "skip", "horse", "cow", "fox")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater)

        //Get a random word and put the correct number of dashes on the ui, according to the length of the word
        word = randomWordGenerator()
        wordHint = "_".repeat(word.length)
        binding.gameTvWord.text = wordHint

        //add number of lives left to ui
        binding.gameTvLives.text = "You have $lives lives left"

        //add incorrect guesses to the ui
        binding.gameTvIncorrectGuesses.text = incorrectGuesses

        //set on click listeners
        binding.gameBtGuess.setOnClickListener { makeGuess() }


        return binding.root
    }

    //picks a random word from words array
    private fun randomWordGenerator(): String{
        val index = (0..(words.size - 1)).random()
        return words[index]
    }

    private fun makeGuess(){
        val guess = binding.gameEtGuess.text.toString()
        //verify letter hasn't been guessed already
        if(!allGuesses.contains(guess)){
            allGuesses += guess
            //check if guess is in the word, update ui
            if(word.contains(guess)){
                var index = 0
                while (index >= 0){
                    index = word.indexOf(guess, index)
                    if(index >= 0){
                        wordHint = wordHint.substring(0, index) + guess + wordHint.substring(index + 1)
                        binding.gameTvWord.text = wordHint
                        index++
                    }
                }
            } else {
                incorrectGuesses += "$guess "
                lives -= 1
                binding.gameTvIncorrectGuesses.text = incorrectGuesses
                binding.gameTvLives.text = "You have $lives left"
            }
        }
        binding.gameEtGuess.setText("")
        isGameOver()
    }

   private fun isGameOver() {
       var result = ""
       var gameOver = false
        if(lives <= 0){
            result = "You lose. The word was $word"
            gameOver = true
        }
        if(!(wordHint.contains("_"))){
            result = "You win! The word was ${word.uppercase(Locale.getDefault())}"
            gameOver = true
        }
       if(gameOver){
           findNavController().navigate(GameFragmentDirections.actionGameFragmentToResultFragment(result))
       }
    }
}