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

class DetailToDo : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_to_do)

        //initializing the xml components
        val detailTitle = findViewById<EditText>(R.id.detail_txtTitle)
        val detailDesc = findViewById<EditText>(R.id.detail_txtDescription)
        val btnDelete = findViewById<Button>(R.id.detail_btnDelete)
        val btnEdit = findViewById<Button>(R.id.detail_btnEdit)
        val btnBackHome = findViewById<ImageView>(R.id.btnBackHome)

        //get the passing value and assign the values to components
        val bundle : Bundle?= intent.extras
        val title = bundle!!.getString("title")
        val description = bundle!!.getString("description")

        //set those passed values to editText
        detailTitle.setText(title)
        detailDesc.setText(description)

        val txtTitle = detailTitle.text.toString()
        val txtDesc = detailDesc.text.toString()

        //bind a listener to button to delete the todo
        btnDelete.setOnClickListener{

            var deleteTitle = detailTitle.text.trim().toString()
            if (deleteTitle.isNotEmpty())
                deleteData(deleteTitle)
            else
                Toast.makeText(this,"ToDo Not Deleted",Toast.LENGTH_SHORT).show()
        }

        //bind a listener to image to move edit activity
        btnEdit.setOnClickListener {
            var intent = Intent(this,EditToDo::class.java)
            intent.putExtra("title",txtTitle)
            intent.putExtra("description",txtDesc)
            startActivity(intent)
        }

        //bind a listener to image to move home activity
        btnBackHome.setOnClickListener {
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    //method to delete todo in firebase
    private fun deleteData(deleteTitle: String) {
        database = FirebaseDatabase.getInstance().getReference("ToDo")
        database.child(deleteTitle).removeValue().addOnSuccessListener {
            Toast.makeText(this,"ToDo Delete Successful",Toast.LENGTH_SHORT).show()
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this,"Failed to Delete ToDo",Toast.LENGTH_SHORT).show()
        }
    }
}