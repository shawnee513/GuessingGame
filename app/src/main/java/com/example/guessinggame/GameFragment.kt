package com.example.guessinggame

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding
import java.util.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.findNavController

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater).apply {
            gameCvComposeView.setContent {
                MaterialTheme {
                    Surface {
                        GameFragmentContent(viewModel)
                    }
                }
            }
        }
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        //Set up dataBinding
        binding.gameViewModel = viewModel
        //for live data
        binding.lifecycleOwner = viewLifecycleOwner

        /* Don't need these observers since they are using data binding
        viewModel.wordHint.observe(viewLifecycleOwner, Observer {
                wordHint -> binding.gameTvWord.text = wordHint.toString()
        })
        viewModel.lives.observe(viewLifecycleOwner, Observer {
                lives -> binding.gameTvLives.text = "You have $lives lives left"
        })
        viewModel.incorrectGuesses.observe(viewLifecycleOwner, Observer {
                incorrectGuesses -> binding.gameTvIncorrectGuesses.text = incorrectGuesses
        })

        */
        //Set up observers
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
@Composable
fun FinishGameButton(clicked: () -> Unit) {
    Button(onClick = clicked,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF539987),
            contentColor = Color(0xFFFFFFFF)
        )) {
        Text("END GAME")
    }
}

@Composable
fun EnterGuess(guess: String, changed: (String) -> Unit) {
    TextField(
        value = guess,
        label = { Text("Guess a letter") },
        onValueChange = changed,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFfaf9f9)
        )
    )
}

@Composable
fun GuessButton(clicked: () -> Unit) {
    Button(onClick = clicked,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF539987),
            contentColor = Color(0xFFFFFFFF)
        )) {
        Text("GUESS!")
    }
}

@Composable
fun GameFragmentContent(viewModel: GameViewModel) {
    val guess = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        EnterGuess(guess.value) { guess.value = it[0].toString() }
    }

    Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
        GuessButton {
            viewModel.makeGuess(guess.value)
            guess.value = ""
        }
        FinishGameButton { viewModel.endGame()}
    }

}