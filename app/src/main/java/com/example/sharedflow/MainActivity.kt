package com.example.sharedflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {
            val result = producer()
            result.collect {
                Log.d("collector-1", "Item $it")
            }
        }
        GlobalScope.launch(Dispatchers.Main) {
            val result= producer()
            result.collect(){
                delay(2500)
                Log.d("collector-2","Item $it")
            }
        }
    }
}
    private fun producer() : Flow<Int> {
            val mutableSharedFlow = MutableSharedFlow<Int>()
            GlobalScope.launch{
                val list= listOf<Int>(1,2,3,4,5)
                list.forEach {
                    mutableSharedFlow.emit(it)
                    delay(1000)
                }
            }
        return mutableSharedFlow
}