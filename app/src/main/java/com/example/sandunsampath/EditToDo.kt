package com.example.sandunsampath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditToDo : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_to_do)

        //initializing the xml components
        val editTitle = findViewById<EditText>(R.id.edit_txtTitle)
        val editDescription = findViewById<EditText>(R.id.edit_txtDescription)
        val btnEdit = findViewById<Button>(R.id.edit_btnEdit)
        val btnBackDetail = findViewById<ImageView>(R.id.btnBackDetail)

        val bundle : Bundle?= intent.extras
        val passTitle = bundle!!.getString("title")
        val passDesc = bundle!!.getString("description")

        editTitle.setText(passTitle)
        editDescription.setText(passDesc)

        //button to edit todo
        btnEdit.setOnClickListener {

            val title = editTitle.text.trim().toString()
            val description = editDescription.text.trim().toString()

            updateData(title, description)
        }

        //bind a listener to image to move details activity
        btnBackDetail.setOnClickListener {
            val intent = Intent(this, DetailToDo::class.java)
            startActivity(intent)
        }


    }

    //method to update todo in firebase
    private fun updateData(title: String, description: String) {

        database = FirebaseDatabase.getInstance().getReference("ToDo")

        val todo = mapOf<String, String>(
            "title" to title,
            "description" to description
        )

        database.child(title).updateChildren(todo).addOnSuccessListener {
            Toast.makeText(this, "ToDo Edit Successfully", Toast.LENGTH_SHORT).show()
            //move to detail screen if todo edit success
            val intent = Intent(this, DetailToDo::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to Edit ToDo", Toast.LENGTH_SHORT).show()
        }

    }
}