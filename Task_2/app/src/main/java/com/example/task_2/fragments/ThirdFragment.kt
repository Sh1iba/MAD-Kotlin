package com.example.task_2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.task_2.R
import com.example.task_2.ViewModel.TextViewModel


class ThirdFragment : Fragment() {
    private lateinit var viewModel: TextViewModel
    private lateinit var editText: EditText
    private lateinit var textview: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third, container, false)
        editText = view.findViewById(R.id.editText)
        textview = view.findViewById(R.id.textView)

        // Инициализируем ViewModel
        viewModel = ViewModelProvider(this).get(TextViewModel::class.java)

        // Наблюдаем за изменениями текста
        viewModel.text.observe(viewLifecycleOwner) { newText ->
            textview.text = "Ваш текст: $newText"
        }

        val button: Button = view.findViewById(R.id.buttonInfo)
        button.setOnClickListener {
            val getValue = editText.text.toString()
            viewModel.updateText(getValue) // Обновляем текст в ViewModel
        }

        val buttonNavBack: Button = view.findViewById(R.id.buttonNavBack)
        buttonNavBack.setOnClickListener {
            findNavController().popBackStack()
        }
        val buttonManBack: Button = view.findViewById(R.id.buttonManBack)
        buttonManBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }

}