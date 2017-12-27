package com.example.savvycom.placedemoapi

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by savvycom on 12/27/2017.
 */
class DownloadUrl {

    @Throws(IOException::class)
    fun readUrl(myUrl: String): String {
        var data = ""
        var inputStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null

        try {
            val url = URL(myUrl)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()

            inputStream = urlConnection.inputStream
            val br = BufferedReader(InputStreamReader(inputStream!!))
            val sb = StringBuffer()

            var line: String?

            do{
                line = br.readLine()

                if(line == null){
                    break
                } else{
                    sb.append(line)
                }
            } while(true)

            data = sb.toString()
            br.close()

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream!!.close()
            urlConnection!!.disconnect()
        }
        Log.d("DownloadURL", "Returning data= " + data)

        return data
    }
}