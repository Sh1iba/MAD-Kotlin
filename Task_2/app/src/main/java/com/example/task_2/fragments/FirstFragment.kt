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
import androidx.navigation.fragment.findNavController
import com.example.task_2.R


class FirstFragment : Fragment() {
    private var counter: Int = 0
    private lateinit var textViewCount: TextView

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        // Найдите элементы на разметке
        textViewCount = view.findViewById(R.id.textViewCount)
        val buttonPlus: Button = view.findViewById(R.id.buttonPlus)

        // Установим слушатель для кнопки
        buttonPlus.setOnClickListener {
            counter++ // Увеличиваем счетчик
            Log.d("TAG", "ты нажал должен быть +1")
            updateCounter() // Обновляем текст в TextView

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

    private fun updateCounter() {
        textViewCount.text = counter.toString()
    }



}
