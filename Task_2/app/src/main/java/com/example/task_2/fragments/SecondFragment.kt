package com.example.task_2.fragments

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.task_2.R


@Suppress("UNREACHABLE_CODE")
class SecondFragment : Fragment() {
    private var isRunning = false
    private var elapsedTime = 0L
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var timerText: TextView
    private lateinit var startStopButton: Button
    private lateinit var resetButton: Button
    private val runnable = object : Runnable {
        override fun run() {
            elapsedTime += 1000
            updateTimerText()
            handler.postDelayed(this, 1000)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_second, container, false)

        // переход с помощью ручной транзакции на третий фрагмент
        val buttonman2: Button = view.findViewById(R.id.buttonManually2)
        buttonman2.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, ThirdFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
            // переход с помощью navigation на третий фрагмент
        val buttonNavigate2: Button = view.findViewById(R.id.buttonNavi2)
        buttonNavigate2.setOnClickListener {

            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment)
            Log.d("FirstFragment", "Переход на SecondFragment")
        }
        // Navigation возврат
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerText = view.findViewById(R.id.timerText)
        startStopButton = view.findViewById(R.id.startStopButton)
        resetButton = view.findViewById(R.id.resetButton)

        startStopButton.setOnClickListener {
            if (isRunning) {
                stopTimer()
            } else {
                startTimer()
            }
        }

        resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        isRunning = true
        startStopButton.text = "Stop"
        handler.postDelayed(runnable, 1000)
    }

    private fun stopTimer() {
        isRunning = false
        startStopButton.text = "Start"
        handler.removeCallbacks(runnable)
    }

    private fun resetTimer() {
        stopTimer()
        elapsedTime = 0
        updateTimerText()
    }

    private fun updateTimerText() {
        val minutes = (elapsedTime / 1000) / 60
        val seconds = (elapsedTime / 1000) % 60
        timerText.text = String.format("%02d:%02d", minutes, seconds)
    }

}


