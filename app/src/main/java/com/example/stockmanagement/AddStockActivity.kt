package com.example.stockmanagement

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addstock.*

class AddStockActivity : AppCompatActivity(){
//base class of all activities -> AppCompatActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Add Item")
        setContentView(R.layout.activity_addstock)
        val bundle : Bundle? = intent.extras
        val id = bundle!!.getString("id")

        buttonSave.setOnClickListener {
            if(edtTxtname.text.isEmpty()){

                Toast.makeText(this,"Enter Item Name", Toast.LENGTH_SHORT).show()
                edtTxtname.requestFocus()
            }
            else{
                val s = Stock()
                s.itemname = edtTxtname.text.toString()

                if(edtTxtquan.text.isEmpty() || editTextPrice.text.isEmpty()){

                    s.itemquan = 0
                    s.itemprice = 0
                }
                else{
                    s.itemquan = edtTxtquan.text.toString().toInt()
                    s.itemprice = editTextPrice.text.toString().toInt()
                }
                RecyclerActivity.dbstock.addStock(this,s, id!!)
                clearEdits()
                edtTxtname.requestFocus()
            }
        }

        btnCancel.setOnClickListener {
            clearEdits()
            finish()
        }
    }
    private fun clearEdits(){

        edtTxtname.text.clear()
        edtTxtquan.text.clear()
        editTextPrice.text.clear()
    }
}