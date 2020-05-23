package com.example.stockmanagement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Home")
        setContentView(R.layout.activity_login)

        val bundle : Bundle? = intent.extras
        //getting the username from main activity
        val id = bundle!!.getString("id")

        bt_checkStock.setOnClickListener {
            val intent = Intent(this, RecyclerActivity :: class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        retriveData(id)
    }

    private fun retriveData(id: String?) {
        var name: String
        var phone: String

        var context = this
        var db = DatabaseHandler(context)
        var user = db.readData(context, id!!)

        name = user.name
        phone = user.phone.toString()

        textViewphoto.text = name
        textViewId.text = id
        textViewPhoeo.text = phone
    }
}