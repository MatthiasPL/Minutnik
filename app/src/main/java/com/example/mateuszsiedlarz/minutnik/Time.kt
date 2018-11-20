package com.example.mateuszsiedlarz.minutnik

class Time (TenMin: Int, OneMin: Int, TenSec: Int, OneSec: Int) {

    var oneSec=OneSec
    var tenSec=TenSec
    var oneMin=OneMin
    var tenMin=TenMin

    fun addOneSec(){
        if(oneSec>=9){
            addTenSec()
            oneSec=0
        }else{
            oneSec++
        }
    }
    fun addTenSec(){
        if(tenSec>=5){
            addOneMin()
            tenSec=0
        }else{
            tenSec++
        }
    }
    fun addOneMin(){
        if(oneMin>=9){
            addTenMin()
            oneMin=0
        }else{
            oneMin++
        }
    }
    fun addTenMin(){
        if(tenMin>=9){

        }else{
            tenMin++
        }
    }
    fun remOneSec(){
        if(oneSec==0){
            oneSec=9
            remTenSec()
        }else{
            oneSec--
        }
    }
    fun remTenSec(){
        if(tenSec==0){
            tenSec=5
            remOneMin()
        }else{
            tenSec--
        }
    }
    fun remOneMin(){
        if(oneMin==0){
            oneMin=9
            remTenMin()
        }else{
            oneMin--
        }
    }
    fun remTenMin(){
        if(tenMin==0){

        }else{
            tenMin--
        }
    }
    fun returnOneSec():String{
        return oneSec.toString()
    }
    fun returnOneMin():String{
        return oneMin.toString()
    }
    fun returnTenSec():String{
        return tenSec.toString()
    }
    fun returnTenMin():String{
        return tenMin.toString()
    }
}