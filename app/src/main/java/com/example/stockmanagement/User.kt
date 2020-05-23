package com.example.stockmanagement

class User {

    var user_id : Int = 0
    var name : String = ""
    var username : String = ""
    var password : String = ""
    var phone : Int = 0

    constructor(name : String, username : String, password : String, phone : Int){

        this.name = name
        this.username = username
        this.password = password
        this.phone = phone
    }

    constructor(){

    }
}