package com.example.stockmanagement

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DatabaseStock (context: Context, name: String?, factory : SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, db_name, factory, database_version){
    //SQLiteOpenHelper class provides the functionality to use the SQLite database.
    companion object{

        private val db_name = "MyData2.db"
        private val database_version = 1

        val tab_name = "Stock"
        val col_itemid = "itemid"
        val col_itemname = "itemname"
        val col_itemquan = "itemquan"
        val col_itemprice = "itemprice"
        val col_userid = "userId"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createStockTable : String = ("CREATE TABLE $tab_name (" +
                "$col_userid TEXT," +
                "$col_itemid INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$col_itemname TEXT," +
                "$col_itemquan INTEGER," +
                "$col_itemprice INTEGER)")

        db?.execSQL(createStockTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    fun getStock(mCtx : Context, id:String) : ArrayList<Stock>{

        val query = "Select * From $tab_name where $col_userid = '$id' "
        val db = this.readableDatabase
        //rawQuery method is same as execSQL, used to execute an SQL query on the database.
        val cursor = db.rawQuery(query,null)
        //Stk -> object of ArrayList<Stock>
        val Stk = ArrayList<Stock>()
        //when we try to retrieve some data using SELECT, then the Database will first a create cursor object and return its reference.
        if(cursor.count == 0)

            Toast.makeText(mCtx,"No records found", Toast.LENGTH_SHORT).show() else{

            cursor.moveToNext()
                while (!cursor.isAfterLast) {
                    val stk = Stock()
                    stk.itemid = cursor.getInt(cursor.getColumnIndex(col_itemid))
                    stk.itemname = cursor.getString(cursor.getColumnIndex(col_itemname))
                    stk.itemquan = cursor.getInt(cursor.getColumnIndex(col_itemquan))
                    stk.itemprice = cursor.getInt(cursor.getColumnIndex(col_itemprice))
                    Stk.add(stk)
                    cursor.moveToNext()
                }
                Toast.makeText(mCtx, "${cursor.count} Records Found", Toast.LENGTH_SHORT).show()
            }
        cursor.close()
        db.close()
        return Stk
    }

    fun addStock(mCtx: Context,s: Stock, id:String){
        //addStock is used to add the values to the database
        val values = ContentValues()
        values.put(col_userid,id)
        values.put(col_itemname,s.itemname)
        values.put(col_itemquan,s.itemquan)
        values.put(col_itemprice,s.itemprice)

        val db = this.writableDatabase
        try {

            db.insert(tab_name,null,values)
            Toast.makeText(mCtx, "Item Added", Toast.LENGTH_SHORT).show()

        }catch (e : Exception){

            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun deleteItem(itemid : Int) : Boolean{

        val query = "Delete From $tab_name where $col_itemid = $itemid"
        val db = this.writableDatabase
        var result : Boolean = false
        try {

            val cursor = db.execSQL(query)
            result = true

        }catch (e : Exception){

            Log.e(ContentValues.TAG, "Error Deleting")
        }
        db.close()
        return result
    }

    fun updateItem(itemid : String, itemname : String, itemquan : String, itemprice : String) : Boolean{

        val db = this.writableDatabase
        val contentValues = ContentValues()
        var res : Boolean = false
        contentValues.put(col_itemname,itemname)
        contentValues.put(col_itemquan, itemquan.toInt())
        contentValues.put(col_itemprice, itemprice.toInt())
        try {

            db.update(tab_name,contentValues,"$col_itemid = ?", arrayOf(itemid))
            res = true
        }catch (e : Exception){
            Log.e(ContentValues.TAG, "Error Updating")
            res = false
        }
        db.close()
        return res
    }
}