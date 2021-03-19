package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.renderscript.Sampler
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_convert.*
import kotlin.math.round


class ConvertActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert)

        val CharCode = intent.getStringExtra("charCode")
        val Name = intent.getStringExtra("name")

        CharCodeFrom.text = CharCode
        NameFrom.text = Name

        btn_1.setOnClickListener{ setTextVolueFrom("1")
            apdaterValueRUB(volue_from.text.toString())}
        btn_2.setOnClickListener{ setTextVolueFrom("2")
            apdaterValueRUB(volue_from.text.toString())}
        btn_3.setOnClickListener{ setTextVolueFrom("3")
            apdaterValueRUB(volue_from.text.toString())}
        btn_4.setOnClickListener{ setTextVolueFrom("4")
            apdaterValueRUB(volue_from.text.toString())}
        btn_5.setOnClickListener{ setTextVolueFrom("5")
            apdaterValueRUB(volue_from.text.toString())}
        btn_6.setOnClickListener{ setTextVolueFrom("6")
            apdaterValueRUB(volue_from.text.toString())}
        btn_7.setOnClickListener{ setTextVolueFrom("7")
            apdaterValueRUB(volue_from.text.toString())}
        btn_8.setOnClickListener{ setTextVolueFrom("8")
            apdaterValueRUB(volue_from.text.toString())}
        btn_9.setOnClickListener{ setTextVolueFrom("9")
            apdaterValueRUB(volue_from.text.toString())}
        btn_0.setOnClickListener{ setTextVolueFrom("0")
            apdaterValueRUB(volue_from.text.toString())}

        btn_Clear.setOnClickListener{
            volue_from.text = "0"
            volueRUB.text = "0.0"
        }

        btn_Del.setOnClickListener{
            val str = volue_from.text.toString()
            if(str.isNotEmpty()){
                if(str.length!=1) {
                    volue_from.text = str.substring(0, str.length - 1)
                    apdaterValueRUB(volue_from.text.toString())
                }
                else {
                    volue_from.text = "0"
                    volueRUB.text = "0.0"
                }
            }
        }

        back.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun setTextVolueFrom(str: String){
        if(volue_from.text.toString() == "0") volue_from.text = str
        else volue_from.append(str)
    }

    fun apdaterValueRUB(str: String){
        val Volue = intent.getDoubleExtra("volue", 1.0)

        volueRUB.text = (round((str.toInt() * Volue)*1000)/1000).toString()
    }
}