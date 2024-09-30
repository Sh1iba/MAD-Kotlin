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
import androidx.navigation.fragment.findNavController
import com.example.task_2.R


@Suppress("UNREACHABLE_CODE")
class ThirdFragment : Fragment() {

    private lateinit var editText: EditText
    private lateinit var textview: TextView
    private lateinit var button: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third, container, false)

        textview = view.findViewById(R.id.textView)
        editText = view.findViewById(R.id.editText)
        button = view.findViewById(R.id.buttonInfo)

        button.setOnClickListener {
            var getValue = editText.text.toString()
            textview.text = "Ваш текст: $getValue"
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