@file:Suppress("DEPRECATION")

package com.sosa.final_project.ui

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sosa.final_project.databinding.FragmentWeatherBinding
import org.json.JSONObject
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 */
class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Get binding
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        WeatherTask().execute()
        //Inflate the layout for this fragment
        return binding.root

    }

    @SuppressLint("StaticFieldLeak")
    inner class WeatherTask : AsyncTask<String, Void, String>() {
        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            binding.loader.visibility = View.VISIBLE
            binding.mainContainer.visibility = View.GONE
            binding.errorText.visibility = View.GONE
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): String? {
            val response:String? = try{
                URL("https://api.openweathermap.org/data/2.5/forecast?lat=30.267153&lon=-97.743057&appid=06c921750b9a82d8f5d1294e1586276f&units=imperial")
                    .readText(Charsets.UTF_8)
            } catch (e: Exception){
                null
            }
            return response
        }

        @Deprecated("Deprecated in Java")
        @SuppressLint("SimpleDateFormat")
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            try {

                // Used to format dates
                val givenFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                var index = 0

                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)

                // Information for Day 0 (current day)
                val day0 = jsonObj.getJSONArray("list").getJSONObject(index)
                val main0 = day0.getJSONObject("main")
                val weather = day0.getJSONArray("weather").getJSONObject(index)
                val weatherDescription = weather.getString("description")
                // format the date
                val calendar0 = Calendar.getInstance()
                calendar0.time = givenFormat.parse(day0.getString("dt_txt"))!!
                val currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar0.time)
                val currentDay = currentDate.split(",")[0]
                // temp and humidity
                val temp0 = main0.getString("temp") + "°F"
                val humidity = "Humidity " + main0.getString("humidity") + "%"
                index++

                // Information for Day 1
                var dayOfWeek1 : String
                var temp1 : String
                do {
                    val day1 = jsonObj.getJSONArray("list").getJSONObject(index)
                    val main1 = day1.getJSONObject("main")
                    //format the date to be just the day of the week
                    val calendar1 = Calendar.getInstance()
                    calendar1.time = givenFormat.parse(day1.getString("dt_txt"))!!
                    dayOfWeek1 =
                        DateFormat.getDateInstance(DateFormat.FULL).format(calendar1.time)
                            .split(",")[0]
                    // temp
                    temp1 = main1.getString("temp") + "°F"
                    index++
                } while (dayOfWeek1 == currentDay)

                // Information for Day 2
                var dayOfWeek2 : String
                var temp2 : String
                do {
                    val day2 = jsonObj.getJSONArray("list").getJSONObject(index)
                    val main2 = day2.getJSONObject("main")
                    // format the date to be just the day of the week
                    val calendar2 = Calendar.getInstance()
                    calendar2.time = givenFormat.parse(day2.getString("dt_txt"))!!
                    dayOfWeek2 = DateFormat.getDateInstance(DateFormat.FULL).format(calendar2.time)
                        .split(",")[0]
                    // temp
                    temp2 = main2.getString("temp") + "°F"
                    index++
                } while (dayOfWeek2 == dayOfWeek1)

                // Information for Day 3
                var dayOfWeek3 : String
                var temp3 : String
                do {
                    val day3 = jsonObj.getJSONArray("list").getJSONObject(index)
                    val main3 = day3.getJSONObject("main")
                    // format the date to be just the day of the week
                    val calendar3 = Calendar.getInstance()
                    calendar3.time = givenFormat.parse(day3.getString("dt_txt"))!!
                    dayOfWeek3 = DateFormat.getDateInstance(DateFormat.FULL).format(calendar3.time)
                        .split(",")[0]
                    // temp
                    temp3 = main3.getString("temp") + "°F"
                    index++
                } while (dayOfWeek3 == dayOfWeek2)

                // Information for Day 4
                var dayOfWeek4 : String
                var temp4 : String
                do {
                    val day4 = jsonObj.getJSONArray("list").getJSONObject(index)
                    val main4 = day4.getJSONObject("main")
                    // format the date to be just the day of the week
                    val calendar4 = Calendar.getInstance()
                    calendar4.time = givenFormat.parse(day4.getString("dt_txt"))!!
                    dayOfWeek4 = DateFormat.getDateInstance(DateFormat.FULL).format(calendar4.time)
                        .split(",")[0]
                    // temp
                    temp4 = main4.getString("temp") + "°F"
                    index++
                } while (dayOfWeek4 == dayOfWeek3)

                // Get other information to display about location
                val address = JSONObject(result).getJSONObject("city")
                val city = address.getString("name")
                val country = address.getString("country")
                val fullAddress = "$city, $country"

                // Populate extracted data into our views
                binding.address.text = fullAddress
                binding.updatedAt.text = currentDate
                binding.status.text = weatherDescription.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                binding.temp.text = temp0
                binding.humidity.text = humidity

                binding.day1.text = dayOfWeek1
                binding.day1Temp.text = temp1

                binding.day2.text = dayOfWeek2
                binding.day2Temp.text = temp2

                binding.day3.text = dayOfWeek3
                binding.day3Temp.text = temp3

                binding.day4.text = dayOfWeek4
                binding.day4Temp.text = temp4

                /* Views populated, Hiding the loader, Showing the main design */
                binding.loader.visibility = View.GONE
                binding.mainContainer.visibility = View.VISIBLE
            } catch (e: Exception) {
                binding.loader.visibility = View.GONE
                binding.errorText.visibility = View.VISIBLE
            }
        }
    }
}