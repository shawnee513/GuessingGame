package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.guessinggame.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)
        val args = ResultFragmentArgs.fromBundle(requireArguments())

        //tell user the result of the game
        binding.resultTvResult.text = args.result

        //set onclick listeners
        binding.resultBtPlayAgain.setOnClickListener {
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToGameFragment())}

        // Inflate the layout for this fragment
        return binding.root
    }
}