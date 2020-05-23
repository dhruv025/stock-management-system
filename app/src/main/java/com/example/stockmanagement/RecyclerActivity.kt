package com.example.stockmanagement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler.*

var id: String? = null
class RecyclerActivity : AppCompatActivity(){

    companion object{

        lateinit var dbstock : DatabaseStock

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Stock")
        setContentView(R.layout.activity_recycler)

        val bundle : Bundle? = intent.extras
        //getting the username from main activity
        id = bundle!!.getString("id")

        dbstock = DatabaseStock(this,null, null, 1)
        viewStock(id!!)
        fab.setOnClickListener {

            val i = Intent(this,AddStockActivity::class.java)
            i.putExtra("id", id)
            startActivity(i)
        }
    }
     private fun viewStock(id:String){
        //ArrayList class is used to create a dynamic array which means the size of ArrayList class can be increased or decreased according to requirement.
        val stockList : ArrayList<Stock> = dbstock.getStock(this, id)
        val adapter = StockAdapter(this,stockList)
        val rv : RecyclerView = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv.adapter = adapter

    }

    override fun onResume() {
        viewStock(id!!)
        super.onResume()
    }
}