package com.example.sketchapp

// George Dinev
// Android Team Project
// 10/19/2022

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.sketchapp.SketchView.Companion.colorList
import com.example.sketchapp.SketchView.Companion.currentPen
import com.example.sketchapp.SketchView.Companion.pathList

class MainActivity : AppCompatActivity() {

    companion object{
        var path = Path()
        var pen = Paint()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val redButton = findViewById<ImageButton>(R.id.red)
        val greenButton = findViewById<ImageButton>(R.id.green)
        val blueButton = findViewById<ImageButton>(R.id.blue)
        val whiteButton = findViewById<ImageButton>(R.id.white)
        val saveButton = findViewById<Button>(R.id.saveButton)

        //unsure of how to save canvas' to the ListView,
        //so I hardcoded some as an example.
        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf(
            "Sketch 1", "Sketch 2","Sketch 3", "Sketch 4"
        )

        var mListView = findViewById<ListView>(R.id.userlist)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        mListView.adapter = arrayAdapter


        // Buttons with visual display upon selection
        redButton.setOnClickListener {
            Toast.makeText(this,"Red",Toast.LENGTH_SHORT).show()
            pen.color = Color.RED
            currentColor(pen.color)
        }
        greenButton.setOnClickListener {
            Toast.makeText(this,"Green",Toast.LENGTH_SHORT).show()
            pen.color = Color.GREEN
            currentColor(pen.color)
        }
        blueButton.setOnClickListener {
            Toast.makeText(this,"Blue",Toast.LENGTH_SHORT).show()
            pen.color = Color.BLUE
            currentColor(pen.color)
        }
        whiteButton.setOnClickListener {
            Toast.makeText(this,"Cleared",Toast.LENGTH_SHORT).show()
            pathList.clear()
            colorList.clear()
            path.reset()
        }
        saveButton.setOnClickListener {
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
        }
    }

    private fun currentColor(color: Int){
        currentPen = color
        path = Path()
    }
}