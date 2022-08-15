package com.example.guessinggame

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding
import java.util.*

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        //Set up observers
        viewModel.wordHint.observe(viewLifecycleOwner, Observer {
                wordHint -> binding.gameTvWord.text = wordHint.toString()
        })
        viewModel.lives.observe(viewLifecycleOwner, Observer {
                lives -> binding.gameTvLives.text = "You have $lives lives left"
        })
        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer {
                incorrectGuesses -> binding.gameTvIncorrectGuesses.text = incorrectGuesses
        })
        viewModel.result.observe(viewLifecycleOwner, Observer{
            result -> if(result != "") {findNavController().navigate(GameFragmentDirections.actionGameFragmentToResultFragment(result))}
        })

        //set on click listeners
        binding.gameBtGuess.setOnClickListener {
            viewModel.makeGuess(binding.gameEtGuess.text.toString())
            binding.gameEtGuess.setText("")
        }

        return binding.root
    }
}