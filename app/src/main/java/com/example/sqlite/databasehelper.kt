package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class databasehelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VERSION){

    //DEFINE our contsant variabels
    companion object{
        private val DATABASE_VERSION =1
        private val DATABASE_NAME ="users_Database"
        private val TABLE_USERS ="users_Table"
        private val KEY_ID = "id"
        private  val KEY_NAME = "name"
        private  val KEY_EMAIL ="email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
      //define our query
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT" + ")")
        // EXECUTE THE QUERY
        db?.execSQL(CREATE_CONTACTS_TABLE)
        Log.d("response","error is" + db?.execSQL(CREATE_CONTACTS_TABLE))

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
     db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_USERS)
        onCreate(db)
    }

    //saving data
    fun addusers(sqliteModel: sqliteModel):Long{
        // telling the db what to do

        val db = this.writableDatabase
        //define and place our values into the table
        val contentvalue = ContentValues()
        //put data to respective fields.
        contentvalue.put(KEY_ID,sqliteModel.userId)
        contentvalue.put(KEY_NAME,sqliteModel.userName)
        contentvalue.put(KEY_EMAIL,sqliteModel.userEmail)

        //QUERY TO INSERT TO DB
         val success = db.insert(TABLE_USERS, null,contentvalue)
        //closing the database
        db.close()

        //return output
        return success


    }
    // function to view your user details
    fun read_data(): List<sqliteModel>{
        // get resizable array

        val user_array:ArrayList<sqliteModel> = ArrayList<sqliteModel>()

        // define our fetch query
        val selectQuery = "SELECT * FROM $TABLE_USERS"
        // DEFINE what sqlite should do
        val db = this.readableDatabase
        // reading our data
        var cursor: Cursor? = null

       //declare a try and catch incase the data is not there or the database goes an upgrade

        try {
            cursor =db.rawQuery(selectQuery, null)

        }catch (e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()


        }
        // iterate over records in db and store them in model class
         var user_id:Int
         var user_name:String
         var user_email:String

         //using cursor to pick records
         if (cursor.moveToFirst()){
             // create a loop for the fetching process
              do {
                  user_id = cursor.getInt(cursor.getColumnIndex("id"))
                  user_name = cursor.getString(cursor.getColumnIndex("name"))
                  user_email= cursor.getString(cursor.getColumnIndex("email"))


                  // taking the data to the model class.
                  val users = sqliteModel(userId = user_id, userName = user_name, userEmail = user_email)
                  user_array.add(users)

              }
                  // taking the data to the model class.

                  while (cursor.moveToNext())
         }
        return user_array
    }
}