package com.example.pet_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pet_kotlin.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import database.firestore.FirestoreTarefas
import functions.Tarefas
import functions.TarefasControle
import kotlinx.android.synthetic.main.activity_main.*
import sqlite.SqliteDatabase
import sqlite.SqliteTarefas

class MainActivity : AppCompatActivity() {

//    Controle Vars =====================================
    private lateinit var todoAdapter : TarefasControle
//=======================================================



//    Database Vars ====================================
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
//=======================================================



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        SqLite  ==================================================================================
        val context = this

        btnAddTodo.setOnClickListener {
            if (etTodoTitle.text.toString().length > 0) {
                var tarefas = SqliteTarefas(etTodoTitle.text.toString())
                var db = SqliteDatabase(context)
                db.insertData(tarefas)

            } else {
                Toast.makeText(context, "informe a Tarefa", Toast.LENGTH_SHORT).show()
            }


        }
//        SqLite  ==================================================================================

//        Firebase  ================================================================================
        binding.btnAddTodo.setOnClickListener {

            val tarefa = binding.rvTodoItems.context.toString()


            database = FirebaseDatabase.getInstance().getReference("Users")
            val User = FirestoreTarefas(tarefa)
            database.child(tarefa).setValue(User).addOnSuccessListener {

                binding.etTodoTitle.text.clear()

                Toast.makeText(this, "Salvo",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{
                Toast.makeText(this, "Não Salvo",Toast.LENGTH_SHORT).show()
            }


        }
//        Firebase  ================================================================================


//        Funções  =================================================================================
        setContentView(R.layout.activity_main)
        todoAdapter = TarefasControle(mutableListOf())

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if(todoTitle.isNotEmpty()){
                val todo = Tarefas(todoTitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
            }
        }
        btnDeleteDoneTodos.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }
}
//        Funções  =================================================================================