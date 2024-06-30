package com.seanutf.demo.switchpagetabdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.google.gson.Gson
import com.seanutf.demo.switchpagetabdemo.data.HomeConfig
import com.seanutf.demo.switchpagetabdemo.data.HomeDataUseCase
import com.seanutf.demo.switchpagetabdemo.ui.MainScreen
import com.seanutf.demo.switchpagetabdemo.ui.theme.SwitchPageTabDemoTheme
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class MainActivity : ComponentActivity() {
    private fun readRes() {
        val inputStream = resources.openRawResource(R.raw.home_config)
        val jsonStr = readTextFile(inputStream)
        val config = Gson().fromJson(jsonStr, HomeConfig::class.java)
        HomeDataUseCase.originalHomeConfig = config
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readRes()
        enableEdgeToEdge()
        setContent {
            SwitchPageTabDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun readTextFile(inputStream: InputStream): String {
        val outputStream = ByteArrayOutputStream()

        val buf = ByteArray(1024)
        var len: Int
        try {
            while ((inputStream.read(buf).also { len = it }) != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (_: IOException) {
        }
        return outputStream.toString()
    }
}