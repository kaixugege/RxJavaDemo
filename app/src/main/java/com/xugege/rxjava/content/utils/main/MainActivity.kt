package com.xugege.rxjava.content.utils.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xugege.rxjava.content.utils.content.Main2Activity
import com.xugege.rxjava.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, Main2Activity::class.java))
    }
}
