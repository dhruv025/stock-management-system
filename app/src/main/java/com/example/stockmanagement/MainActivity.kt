package com.example.stockmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //onCreate is where you start your activity, when activity is started then both onCreate() will be called.
    override fun onCreate(savedInstanceState: Bundle?)
    {
    //super keyword is used to call parent class constructor
        super.onCreate(savedInstanceState)
        setTitle("Login")
    //setContentView is used to set the xml
        setContentView(R.layout.activity_main)

        var context = this
        var db = DatabaseHandler(context)

        bt_login.setOnClickListener {
            if(db.validLogin(et_username.text.toString(),et_password.text.toString()) == true){

                //Intent is used to perform an operation.
                val intent = Intent(this, LoginActivity::class.java)
                //sending username to loginActivity using putExtra method.
                intent.putExtra("id",et_username.text.toString())
                startActivity(intent)
                finish()
            }
            else {
                clearEdts()
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        tv_register.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private  fun clearEdts(){

        et_username.text.clear()
        et_password.text.clear()
    }
}