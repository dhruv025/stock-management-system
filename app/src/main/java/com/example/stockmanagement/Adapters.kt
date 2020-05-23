package com.example.stockmanagement

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_stock.view.*
import kotlinx.android.synthetic.main.activity_stock_update.view.*

class StockAdapter(mCtx : Context, val stock : ArrayList<Stock>) : RecyclerView.Adapter<StockAdapter.ViewHolder>(){

    val mCtx = mCtx

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val text_name = itemView.text_name
        val text_quan = itemView.text_quan
        val text_price = itemView.text_price
        val bt_update = itemView.bt_update
        val btn_delete = itemView.btn_delete
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_stock, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
            return stock.size
    }

    override fun onBindViewHolder(holder: StockAdapter.ViewHolder, position: Int) {

        val stk : Stock = stock[position]
        holder.text_name.text = stk.itemname
        holder.text_quan.text = stk.itemquan.toString()
        holder.text_price.text = stk.itemprice.toString()

        holder.btn_delete.setOnClickListener {

            val itemname : String = stk.itemname

            var alertDialog = AlertDialog.Builder(mCtx)
                .setTitle("Warning")
                .setMessage("Are you sure to delete : $itemname ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

                    if(RecyclerActivity.dbstock.deleteItem(stk.itemid)){

                        stock.removeAt(position)
                        //notifyItemRemoved(position) -> to remove the cardview in the recyclerView
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position,stock.size)
                        Toast.makeText(mCtx,"Item $itemname deleted", Toast.LENGTH_SHORT).show()
                    }
                    else{

                        Toast.makeText(mCtx, "Error deleting",Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->  })
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()
        }

        holder.bt_update.setOnClickListener {
            //LayoutInflater is reponsible for taking your xml file and converting into view objects
            val inflater = LayoutInflater.from(mCtx)
            val view = inflater.inflate(R.layout.activity_stock_update,null)

            val txtitemname : TextView = view.findViewById(R.id.upditemname)
            val txtitemquatity : TextView = view.findViewById(R.id.updtitemquantity)
            val txtitemprice : TextView = view.findViewById(R.id.updtitemprice)

            txtitemname.text = stk.itemname
            txtitemquatity.text = stk.itemquan.toString()
            txtitemprice.text = stk.itemprice.toString()

            val builder = AlertDialog.Builder(mCtx)
                .setTitle("Update Item Info")
                .setView(view)
                .setPositiveButton("Update",DialogInterface.OnClickListener { dialog, which ->

                    val isUpdate : Boolean = RecyclerActivity.dbstock.updateItem(
                        stk.itemid.toString(),
                        view.upditemname.text.toString(),
                        view.updtitemquantity.text.toString(),
                        view.updtitemprice.text.toString())
                    if(isUpdate == true){

                        stock[position].itemname = view.upditemname.text.toString()
                        stock[position].itemquan = view.updtitemquantity.text.toString().toInt()
                        stock[position].itemprice = view.updtitemprice.text.toString().toInt()
                        notifyDataSetChanged()
                        Toast.makeText(mCtx,"Updated Sucessful",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(mCtx,"Error Updating",Toast.LENGTH_SHORT).show()
                    }

                }).setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->

                })
            val alert = builder.create()
            alert.show()
        }
    }

}