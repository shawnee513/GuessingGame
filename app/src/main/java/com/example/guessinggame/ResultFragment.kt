package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.compose.ui.platform.ComposeView

class ResultFragment : Fragment() {
    private lateinit var viewModel: ResultViewModel
    private lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //code that doesn't use compose goes here
        val args = ResultFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = ResultViewModelFactory(args.result)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultViewModel::class.java)

        //return a compose view
        return ComposeView(requireContext()).apply {
            setContent {
                //any compose code that defines the UI goes here
                MaterialTheme {
                    Surface {
                        view?.let { ResultFragmentContent(it, viewModel) }
                    }
                }
            }
        }
    }
}
@Composable
fun ResultText(result: String) {
    Text(text = result,
        fontSize = 28 .sp,
        textAlign = TextAlign.Center)
}

@Composable
fun NewGameButton(clicked: () -> Unit) {
    Button(onClick = clicked,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF539987),
                contentColor = Color(0xFFFFFFFF)
            )) {
        Text("PLAY AGAIN")
    }
}

@Composable
fun ResultFragmentContent(view: View, viewModel: ResultViewModel) {
    Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
        ResultText(viewModel.result.value.toString())
        NewGameButton {
            view.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
    }
}