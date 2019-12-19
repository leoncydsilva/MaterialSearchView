package com.leo.materialsearchviewexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leo.materialsearchviewexample.activities.JavaActivity
import com.leo.materialsearchviewexample.activities.KotlinActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        materialButtonKotlin.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    KotlinActivity::class.java
                )
            )
        }
        materialButtonJava.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    JavaActivity::class.java
                )
            )
        }
    }
}
