package com.example.task_2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.task_2.R
import com.example.task_2.ViewModel.CounterViewModel


class FirstFragment : Fragment() {
    private lateinit var viewModel: CounterViewModel
    private lateinit var textViewCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        textViewCount = view.findViewById(R.id.textViewCount)

        // Инициализируем ViewModel
        viewModel = ViewModelProvider(this).get(CounterViewModel::class.java)

        // Наблюдаем за изменениями счетчика
        viewModel.counter.observe(viewLifecycleOwner) { count ->
            textViewCount.text = count.toString()
        }

        val buttonPlus: Button = view.findViewById(R.id.buttonPlus)
        buttonPlus.setOnClickListener {
            viewModel.incrementCounter() // Увеличиваем счетчик
            Log.d("TAG", "ты нажал должен быть +1")
        }
        val buttonman: Button = view.findViewById(R.id.buttonManually)
        buttonman.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, SecondFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        //переход через navigation
        val buttonNavigate: Button = view.findViewById(R.id.buttonNavi)
        buttonNavigate.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
            Log.d("FirstFragment", "Переход на SecondFragment")
        }

        return view
    }
}
