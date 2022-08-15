package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guessinggame.databinding.FragmentResultBinding
import androidx.lifecycle.Observer

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private lateinit var viewModel: ResultViewModel
    private lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)
        val args = ResultFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = ResultViewModelFactory(args.result)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultViewModel::class.java)

        //tell user the result of the game
        viewModel.result.observe(viewLifecycleOwner, Observer {
                result -> binding.resultTvResult.text = result
        })

        //set onclick listeners
        binding.resultBtPlayAgain.setOnClickListener {
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToGameFragment())}

        // Inflate the layout for this fragment
        return binding.root
    }
}