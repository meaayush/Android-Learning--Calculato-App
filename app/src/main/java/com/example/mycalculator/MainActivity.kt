package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
//        lastDot = false
    }

    fun onClear(view: View){
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{

            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")

                    var first = splitValue[0]
                    val second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvInput?.text = removeZeroAfterDot((first.toDouble() - second.toDouble()).toString())
                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var first = splitValue[0]
                    val second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvInput?.text = removeZeroAfterDot((first.toDouble() + second.toDouble()).toString())
                }else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var first = splitValue[0]
                    val second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvInput?.text = removeZeroAfterDot((first.toDouble() * second.toDouble()).toString())
                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var first = splitValue[0]
                    val second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    if(second == "0"){
                        tvInput?.text = "NaN"
                    }
                    tvInput?.text = removeZeroAfterDot((first.toDouble() / second.toDouble()).toString())
                }




            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result[result.length - 1] == '0' && result[result.length - 2] == '.'){
            value = result.substring(0, result.length - 2)
        }

        return value;
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){
            false
        } else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}