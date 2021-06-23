package com.example.sqlite

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class sqliteAdapter (private val context:Activity, private val id:Array<String>, private val name:Array<String>, private val email: Array<String>)
    :ArrayAdapter<String>(context,R.layout.custom_list,name) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // create an inflater refeence

        val inflater = context.layoutInflater
       val rootview=  inflater.inflate(R.layout.custom_list,null,true)

        // view identification

        val textId :TextView = rootview.findViewById(R.id.userId)
        val textName :TextView = rootview.findViewById(R.id.userName)
        val textEmail :TextView = rootview.findViewById(R.id.userEmail)


        // set data according to position
        textId.text = "ID: ${id[position]}"
        textName.text = "Name: ${name[position]}"
        textEmail.text = "Email: ${email[position]}"

        return rootview

    }
}