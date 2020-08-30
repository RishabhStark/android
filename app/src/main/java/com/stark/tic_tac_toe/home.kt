package com.stark.tic_tac_toe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*

class home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        button.setOnClickListener(View.OnClickListener {
            val intent:Intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        })
        button2.setOnClickListener(View.OnClickListener {
            val intent:Intent= Intent(this,humanactivity::class.java)
            startActivity(intent)
        })
    }
}
