package com.example.test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.*
import java.lang.reflect.Type
import java.net.URL
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity()  {
    private var charCodeList = mutableListOf<String>()
    private var nameList = mutableListOf<String>()
    private var volueList = mutableListOf<Double>()
    private var previousList = mutableListOf<Double>()
    private var nominalList = mutableListOf<Int>()
    private var fileName = "DATA.json"
    private var urlName = "https://www.cbr-xml-daily.ru/daily_json.js"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!File(this.filesDir, fileName).exists()) {
            getJsonDataFromURL(this)
        }

        PutDataIntoRecyclerView(this)

        btn_update.setOnClickListener(){
            dataUpdater()
        }
    }

    //Получаем Json-файл и сохраняем на устройстве
    private fun getJsonDataFromURL(context: Context){
        GlobalScope.launch (Dispatchers.Main){
            val data = withContext(Dispatchers.IO){
                URL(urlName).readText()
            }
            try {
                context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                    it.write(data.toByteArray())
                }

            }catch (ex:Exception){
                ex.printStackTrace()
            }
        }.start()
    }

    //Парсим json-файл и формируем список для RecyclerView
    private fun postToList(context: Context){
        try {
            val file = File(context.filesDir, fileName)
            val json = file.readText()

            val rates : Info = Gson().fromJson(json.toString(), Info::class.java)

            //получаем список объектов валют
            val valutes: MutableList<CURR> = rates.Valute.getValutes()

            val date = rates.Date
            txt2.text = ("По данным ЦБ на " + date.slice(0..9))

            for (i in 0..33){ addToList(valutes[i].CharCode,valutes[i].Name, valutes[i].Value, valutes[i].Previous, valutes[i].Nominal) }

        }catch (ex:Exception){
            Toast.makeText(applicationContext, "Нажмите \"Обновить\"", Toast.LENGTH_SHORT).show()
        }
    }

    //функция для отправки данных в адаптер RecyclerView
    private fun  addToList(charName: String, name: String, volue: Double, previous: Double, nominal: Int){
        charCodeList.add(charName)
        nameList.add(name)
        volueList.add(volue)
        previousList.add(previous)
        nominalList.add(nominal)
    }

    //Создание RecyclerView с валютами
    private fun PutDataIntoRecyclerView(context: Context){
        postToList(context)

        rv_recyclerView.layoutManager = LinearLayoutManager(this)
        rv_recyclerView.adapter = RecyclerAdapter(charCodeList, nameList, volueList, previousList,nominalList, object : CurrencyOnClickListener{
            override fun onClicked(v: View, position: Int) {
                val intent = Intent(v.getContext(), ConvertActivity::class.java)

                //передаём данные в ConvertActivity
                intent.putExtra("charCode", charCodeList[position])
                intent.putExtra("name", nameList[position])
                intent.putExtra("volue", volueList[position]/nominalList[position]);

                startActivity(intent)
                finish()
            }
        })
    }


    //обновление курса валют
    private fun dataUpdater(){
        rv_recyclerView.removeAllViewsInLayout();
        charCodeList.clear()
        nameList.clear()
        volueList.clear()
        previousList.clear()
        nominalList.clear()

        getJsonDataFromURL(this)
        PutDataIntoRecyclerView(this)
    }

}

data class Info (val Date: String, val Valute: Valute)

//Валюты
data class Valute (val AUD: CURR, val AZN: CURR, val GBP: CURR, val AMD: CURR,
                   val BYN: CURR, val BGN: CURR, val BRL: CURR, val HUF: CURR,
                   val HKD: CURR, val DKK: CURR, val USD: CURR, val EUR: CURR,
                   val INR: CURR, val KZT: CURR, val CAD: CURR, val KGS: CURR,
                   val CNY: CURR, val MDL: CURR, val NOK: CURR, val PLN: CURR,
                   val RON: CURR, val XDR: CURR, val SGD: CURR, val TJS: CURR,
                   val TRY: CURR, val TMT: CURR, val UZS: CURR, val UAH: CURR,
                   val CZK: CURR, val SEK: CURR, val CHF: CURR, val ZAR: CURR,
                   val KRW: CURR, val JPY: CURR){

    //формирование списка объектов валют
    fun getValutes(): MutableList<CURR> {
        val Valutes: MutableList<CURR> = mutableListOf(
            AUD, AZN, GBP, AMD, BYN, BGN, BRL, HUF, HKD, DKK, USD, EUR,
            INR, KZT, CAD, KGS, CNY, MDL, NOK, PLN, RON, XDR, SGD, TJS,
            TRY, TMT, UZS, UAH, CZK, SEK, CHF, ZAR, KRW, JPY
        )

        return Valutes
    }
}

//Характеристики валюты
data class CURR (val CharCode: String,
                     val Nominal: Int,
                     var Name: String,
                     val Value: Double,
                     val Previous: Double)








