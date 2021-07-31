package com.example.sandunsampath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sandunsampath.databinding.ActivityMainBinding
import com.example.sandunsampath.model.ToDoModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddToDo : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        //initializing the xml components
        val btnAdd = findViewById<Button>(R.id.addButton)
        val title = findViewById<EditText>(R.id.todoTitle)
        val description = findViewById<EditText>(R.id.todoDescription)
        val btnBackHome = findViewById<ImageView>(R.id.todoHome)

        //event listener to a addToDo button
        btnAdd.setOnClickListener {

            val todoTitle = title.text.trim().toString()
            val todoDescription = description.text.trim().toString()

            database = FirebaseDatabase.getInstance().getReference("ToDo")

            val ToDoModel = ToDoModel(todoTitle, todoDescription)

            database.child(todoTitle).setValue(ToDoModel).addOnSuccessListener {
                Toast.makeText(this, "ToDo Added Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, "There Something Wrong Pleas check", Toast.LENGTH_SHORT).show()
            }

            title.text.clear()
            description.text.clear()

        }

        //bind a listener to image to move home activity
        btnBackHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
//        val actionBar = supportActionBar
//        actionBar!!.title = "Add ToDo Activity"

//        actionBar.setDisplayHomeAsUpEnabled(true)


    }
}