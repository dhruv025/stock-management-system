package com.example.stockmanagement

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Register")
        setContentView(R.layout.activity_register)

        var context = this
        var db = DatabaseHandler(context)
        bt_register.setOnClickListener({

            if (et_ownersname.text.toString().length > 0 && et_usrname.text.toString().length > 0  &&
                et_passwrd.text.toString().length > 0  && et_confpassword.text.toString().length > 0  &&
                et_phone.text.toString().length > 0) {

                if(db.chkusername(et_usrname.text.toString()) == true){
                    Toast.makeText(context, "username already exists", Toast.LENGTH_SHORT).show()
                }

                else if(et_passwrd.text.toString() != et_confpassword.text.toString()){
                    Toast.makeText(context, "password do not match", Toast.LENGTH_SHORT).show()
                }
                else {
                    var user = User(et_ownersname.text.toString(),et_usrname.text.toString(),et_passwrd.text.toString(),et_phone.text.toString().toInt())
                    db.insertData(this,user)
                    Toast.makeText(context, "Register Sucessfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            else {

                Toast.makeText(context, "Please fill all the entries", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
