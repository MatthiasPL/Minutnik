package com.example.mateuszsiedlarz.minutnik

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_minutnik.*
import android.content.Context.VIBRATOR_SERVICE
import android.media.MediaPlayer
import android.media.tv.TvView
import android.os.*
import android.support.v4.content.ContextCompat.getSystemService



class Minutnik : AppCompatActivity() {

    var miliTime = 0
    var cTimer: CountDownTimer? = null
    var tState = TimerState.NEW
    var time = Time(0,0,0,0)

    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minutnik)
        time = Time(tvTenMin.text.toString().toInt(), tvOneMin.text.toString().toInt(), tvTenSec.text.toString().toInt(),tvOneSec.text.toString().toInt())

        mp = MediaPlayer.create (this, R.raw.notification)

        bAddOneSec.setOnClickListener(){
            if(tState==TimerState.NEW){
                time.addOneSec()
                UpdateTime(time)
            }
        }
        bAddTenSec.setOnClickListener(){
            if(tState==TimerState.NEW) {
                time.addTenSec()
                UpdateTime(time)
            }
        }
        bAddOneMin.setOnClickListener(){
            if(tState==TimerState.NEW) {
                time.addOneMin()
                UpdateTime(time)
            }
        }
        bAddTenMin.setOnClickListener(){
            if(tState==TimerState.NEW) {
                time.addTenMin()
                UpdateTime(time)
            }
        }
        bRemOneSec.setOnClickListener(){
            if(tState==TimerState.NEW) {
                time.remOneSec()
                UpdateTime(time)
            }
        }
        bRemTenSec.setOnClickListener(){
            if(tState==TimerState.NEW) {
                time.remTenSec()
                UpdateTime(time)
            }
        }
        bRemOneMin.setOnClickListener(){
            if(tState==TimerState.NEW) {
                time.remOneMin()
                UpdateTime(time)
            }
        }
        bRemTenMin.setOnClickListener(){
            if(tState==TimerState.NEW) {
                time.remTenMin()
                UpdateTime(time)
            }
        }

        bStart.setOnClickListener(){
            if(tState==TimerState.NEW) {
                startTimer(time)
            }
        }

        bReset.setOnClickListener(){
            resetTimer(time)
        }

        bPause.setOnClickListener(){
            if(tState==TimerState.WORKING){
                pauseTimer(time)
            }else if (tState==TimerState.PAUSED){
                unpauseTimer(time)
            }
        }

    }

    fun UpdateTime(time:Time){
        tvOneSec.text=time.returnOneSec()
        tvOneMin.text=time.returnOneMin()
        tvTenSec.text=time.returnTenSec()
        tvTenMin.text=time.returnTenMin()
    }


    fun startTimer(time: Time){
        miliTime = (time.oneSec + time.tenSec*10 + time.oneMin*60 + time.tenMin*600)*1000

        cTimer = object :CountDownTimer(miliTime.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                time.remOneSec()
                UpdateTime(time)
            }

            override fun onFinish() {
                resetTimer(time)
                tState=TimerState.NEW
                val v = getSystemService(VIBRATOR_SERVICE) as Vibrator
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    //deprecated in API 26
                    v.vibrate(1000)
                }
                toast("Odliczanie skończone")

                mp?.start() // no need to call prepare(); create() does that for you
            }
        }
        tState=TimerState.WORKING
        cTimer?.start()
    }

    fun resetTimer(time: Time){
        cTimer?.cancel()
        time.oneSec=0
        time.tenSec=0
        time.oneMin=0
        time.tenMin=0
        UpdateTime(time)
        tState=TimerState.NEW
    }

    fun pauseTimer(time: Time){
        cTimer?.cancel()
        miliTime = (time.oneSec + time.tenSec*10 + time.oneMin*60 + time.tenMin*600)*1000
        bPause.text="UNPAUSE"
        tState=TimerState.PAUSED
    }

    fun unpauseTimer(time: Time){
        tState=TimerState.WORKING
        bPause.text="PAUSE"
        startTimer(time)
    }

    fun toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if(outState!=null){
            outState.putInt("oneSec", time.oneSec)
            outState.putInt("tenSec", time.tenSec)
            outState.putInt("oneMin", time.oneMin)
            outState.putInt("tenMin", time.tenMin)
            outState.putString("state", tState.toString())
            cTimer?.cancel()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if(savedInstanceState!=null){
            time.oneSec=savedInstanceState.getInt("oneSec")
            time.tenSec=savedInstanceState.getInt("tenSec")
            time.oneMin=savedInstanceState.getInt("oneMin")
            time.tenMin=savedInstanceState.getInt("tenMin")
            UpdateTime(time)
            if(savedInstanceState.getString("state")==TimerState.WORKING.toString()){
                startTimer(time)
            }
            else if(savedInstanceState.getString("state")==TimerState.PAUSED.toString()){
                pauseTimer(time)
            }
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}

