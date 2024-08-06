package com.example.md_intern_suitmedia

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.md_intern_suitmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkButton.setOnClickListener {
            val sentence = binding.sentenceInput.text.toString().replace(" ", "").lowercase()
            val isPalindrome = sentence == sentence.reversed()
            val message = if (isPalindrome) "Palindrome" else "Not Palindrome"
            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
        }

        binding.nextButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("name", binding.nameInput.text.toString())
            startActivity(intent)
        }
    }
}