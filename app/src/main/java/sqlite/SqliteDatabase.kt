package sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "Tarefas"
val COL_ID = "id"
val COL_TAREFAS = "tarefa"

class SqliteDatabase (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE" + TABLE_NAME+ "("+
                COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TAREFAS + "VARCHAR(256)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(tarefas:SqliteTarefas) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_TAREFAS,tarefas.tarefa)
        var result = db.insert(TABLE_NAME,null,cv)
        if(result == -1.toLong())
            Toast.makeText(context,"Falha ao Salvar", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Salvo", Toast.LENGTH_SHORT).show()
    }
}