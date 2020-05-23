package com.example.stockmanagement

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context,database_name,null,db_version){

    companion object{

        private val database_name = "MyDBASE.db"
        private val db_version = 1

        val table_name = "User"
        val col_userid = "user_id"
        val col_name = "name"
        val col_username = "username"
        val col_password = "password"
        val col_phone = "phone"
    }
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable : String = ("CREATE TABLE $table_name (" +
                "$col_userid INTEGER PRIMARY KEY," +
                "$col_name TEXT," +
                "$col_username TEXT," +
                "$col_password TEXT," +
                "$col_phone LONG LONG INT)")

        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates
    }
    fun insertData(mCty : Context, user : User){

        val value = ContentValues()

        value.put(col_name,user.name)
        value.put(col_username,user.username)
        value.put(col_password,user.password)
        value.put(col_phone,user.phone)

        val db = this.writableDatabase

        db.insert(table_name,null,value)
    }

    fun readData(mCty: Context, id:String) : User{

        val qry = "Select * From $table_name where $col_username = '$id' "
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry,null)
        val usr = User()

        if(cursor.count == 0)
            Toast.makeText(mCty,"No records found", Toast.LENGTH_SHORT).show() else{

            cursor.moveToNext()
            while (!cursor.isAfterLast){

                usr.user_id = cursor.getInt(cursor.getColumnIndex(col_userid))
                usr.name = cursor.getString(cursor.getColumnIndex(col_name))
                usr.username = cursor.getString(cursor.getColumnIndex(col_username))
                usr.password = cursor.getString(cursor.getColumnIndex(col_password))
                usr.phone = cursor.getInt(cursor.getColumnIndex(col_phone))

                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return usr
    }

    fun chkusername(username : String) : Boolean{

        //array of columns to fetch
        val col = arrayOf(col_userid)
        val db = this.readableDatabase

        //selection criteria
        val selection = "$col_username = ?"

        //selection arguments
        val selectionArgs = arrayOf(username)

        //Select user_id from user where username is ?
        val cursor = db.query(table_name, col, selection, selectionArgs, null, null, null)

        val cursorCount = cursor.count
        cursor.close()

        if(cursorCount > 0){
            return true
        }
        return false
    }

    fun validLogin(username : String, password : String) : Boolean{

        //array of columns to fetch
        val col = arrayOf(col_userid)
        val db = this.readableDatabase

        //selection criteria
        val selection = "$col_username = ? and $col_password = ?"

        //selection arguments
        val selectionArgs = arrayOf(username,password)

        //Select user_id from user where username is ? and password is ?
        val cursor = db.query(table_name, col, selection, selectionArgs, null, null, null)

        val cursorCount = cursor.count
        cursor.close()

        if(cursorCount > 0){
            return true
        }
        return false
    }
}