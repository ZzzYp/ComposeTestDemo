package com.hahaha.composeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hahaha.composeapplication.factory.FlowCallAdapterFactory
import retrofit2.Retrofit

class NativeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)

        Retrofit.Builder().addCallAdapterFactory(FlowCallAdapterFactory.create())
    }
}