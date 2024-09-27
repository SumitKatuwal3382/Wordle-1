package com.example.wordlep

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val wordToGuess = "WORD" // The word the player needs to guess
    private var guessCount = 1 // Counter to track the number of guesses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge() // Enable edge-to-edge for a better UI experience
        setContentView(R.layout.activity_main)

        // Get references to the input field and button
        val wordEntry = findViewById<EditText>(R.id.wordEntry)
        val button = findViewById<Button>(R.id.button)

        // Get references to the TextViews that will display the guesses and results
        val guessTextViews = arrayOf(
            findViewById<TextView>(R.id.textView7),  // For Guess #1
            findViewById<TextView>(R.id.textView8),  // For Guess #1 Check
            findViewById<TextView>(R.id.textView9),  // For Guess #2
            findViewById<TextView>(R.id.textView10), // For Guess #2 Check
            findViewById<TextView>(R.id.textView11), // For Guess #3
            findViewById<TextView>(R.id.textView12)  // For Guess #3 Check
        )

        // Set the button click listener
        button.setOnClickListener {
            // Get the guess entered by the user and convert it to uppercase
            val guess = wordEntry.text.toString().uppercase()

            // Check if the guess is valid (4 letters long) and within 3 attempts
            if (guess.length == 4 && guessCount <= 3) {
                val result = checkGuess(guess) // Check the guess
                // Update the guess and result TextViews based on the current guess count
                guessTextViews[guessCount * 2 - 2].text = guess // Display the guessed word
                guessTextViews[guessCount * 2 - 1].text = result // Display the result (O, +, X)
                guessCount++ // Increment the guess count
                wordEntry.text.clear() // Clear the input field after submitting the guess
            } else {
                // Show an error message if the guess is invalid
                if (guess.length != 4) {
                    wordEntry.error = "Please enter a valid 4-letter word!"
                } else {
                    wordEntry.error = "You have exceeded the maximum attempts!"
                }
            }
        }

        // Handle window insets for edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Function to check the guess against the wordToGuess
    fun checkGuess(guess: String): String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O" // Correct letter in the correct position
            } else if (guess[i] in wordToGuess) {
                result += "+" // Correct letter in the wrong position
            } else {
                result += "X" // Incorrect letter
            }
        }
        return result
    }
}
