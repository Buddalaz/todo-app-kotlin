package com.example.sandunsampath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sandunsampath.adapter.ToDoAdapter
import com.example.sandunsampath.model.ToDoModel
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    //declaring property's
    private var myToolbar: Toolbar? = null
    private lateinit var database: DatabaseReference
    private lateinit var todoRecycleView : RecyclerView
    private lateinit var todoList : ArrayList<ToDoModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hide the toolbar programmatically
//        supportActionBar?.hide()

        setSupportActionBar(myToolbar)

        // get reference to ImageView
        val image = findViewById<ImageView>(R.id.addToDoScreen)
        todoRecycleView = findViewById<RecyclerView>(R.id.todoList)

        todoRecycleView.layoutManager = LinearLayoutManager(this)
        todoRecycleView.setHasFixedSize(true)

        todoList = arrayListOf<ToDoModel>()

        getToDoData()

        // set on-click listener
        image.setOnClickListener {
            //move to addtodo activity
            val intent = Intent(this,AddToDo::class.java)
            startActivity(intent)
            // your code to perform when the user clicks on the ImageView
            Toast.makeText(this@MainActivity, "You clicked on ImageView.", Toast.LENGTH_SHORT).show()
        }
    }

    //method to get todo's from firebase
    private fun getToDoData() {
        database = FirebaseDatabase.getInstance().getReference("ToDo")

        database.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (todoSnapshot in snapshot.children){

                        val todo = todoSnapshot.getValue(ToDoModel::class.java)
                        todoList.add(todo!!)

                    }
                    var adapterToDo = ToDoAdapter(todoList)
                    todoRecycleView.adapter = adapterToDo
                    adapterToDo.setOnItemClickListener(object : ToDoAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

//                            Toast.makeText(this@MainActivity,"You Clicked Item no $position",Toast.LENGTH_SHORT).show()

                            //passing the data to detail Activity
                            val intent = Intent(this@MainActivity,DetailToDo::class.java)
                            intent.putExtra("title",todoList[position].title)
                            intent.putExtra("description",todoList[position].description)
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
    }

}