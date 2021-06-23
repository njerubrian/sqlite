 package com.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun save_data(view:View){
        // capture user data
        val id = userId.text.toString()
        val name = user_name.text.toString()
        val email = user_email.text.toString()
        Log.d("data","details " + id + name + email)
        // instance of the database helper
        val Databasehelper = databasehelper(this)

        //validation
        if (id.trim()!="" && name.trim()!="" && email.trim()!=""){
            //if all is not equal to blank
            val status = Databasehelper.addusers(sqliteModel(Integer.valueOf(id),name,email))
            if (status>-1){
                Toast.makeText(applicationContext,"record saved", Toast.LENGTH_LONG).show()

                userId.text?.clear()
                user_name.text?.clear()
                user_email.text?.clear()

            }
            else{
                Toast.makeText(applicationContext, "try again!! something went wrong",Toast.LENGTH_LONG).show()
            }


        }
        else{
         Toast.makeText(applicationContext, "fields cannot be Empty",Toast.LENGTH_LONG).show()
        }

    }

    fun viewdata(view: View){
        // define an instance of the database helper class
        val Databasehelper = databasehelper(this)
        // make reference to the read data method

        val view_data: List<sqliteModel> = Databasehelper.read_data()
        // define activity variables to store each record detail

        val arrayId = Array<String>(view_data.size){"0"}
        val arrayName = Array<String>(view_data.size){"null"}
        val arrayEmail = Array<String>(view_data.size){"null"}

        // looping our records to save in the variables above
        var index = 0
        for (e in view_data){
            arrayId[index]= e.userId.toString()
            arrayName[index]= e.userName
            arrayEmail[index]=e.userEmail
            index++
        }
        // create details reference for our adapter and set the adapter to the list view.

        val myadapter = sqliteAdapter(this,arrayId,arrayName,arrayEmail)

        //set the list view to the adapter.
        listview.adapter = myadapter

    }
}