package com.example.guessinggame

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import java.util.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.findNavController

class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        // Inflate the layout for this fragment

        //Set up observers
        viewModel.result.observe(viewLifecycleOwner, Observer{
                result -> if(result != "") {findNavController().navigate(GameFragmentDirections.actionGameFragmentToResultFragment(result))}
        })

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Surface {
                        GameFragmentContent(viewModel)
                    }
                }
            }
        }
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
fun IncorrectGuessesText (viewModel: GameViewModel) {
    val incorrectGuesses = viewModel.incorrectGuesses.observeAsState()
    incorrectGuesses.value?.let{
        //incorrect_guesses is a string resource, and we are passing incorrectGuesses.value as an argument to it
        Text(stringResource(R.string.incorrect_guesses, it))
    }
}

@Composable
fun LivesLeftText (viewModel: GameViewModel) {
    val livesLeft = viewModel.lives.observeAsState()
    livesLeft.value?.let{
        Text(stringResource(R.string.lives_left, it))
    }
}

@Composable
fun WordHintDisplay (viewModel: GameViewModel) {
    val wordHint = viewModel.wordHint.observeAsState()
    wordHint.value?.let {
        Text(text = it,
        letterSpacing = 0.1 .em,
        fontSize = 36 .sp)
    }
}

@Composable
fun GameFragmentContent(viewModel: GameViewModel) {
    val guess = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
            WordHintDisplay(viewModel)
        }
        LivesLeftText(viewModel)
        IncorrectGuessesText(viewModel)
        EnterGuess(guess.value) { if(guess.value.length > 1) guess.value = it[0].toString() else guess.value = it }

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            GuessButton {
                viewModel.makeGuess(guess.value)
                guess.value = ""
            }
            FinishGameButton { viewModel.endGame()}
        }
    }



}