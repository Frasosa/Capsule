package com.sosa.final_project.ui

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.sosa.final_project.R
import com.sosa.final_project.databinding.FragmentWeatherBinding
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {

    val CITY: String = "austin, tx, us"
    val API: String = "06c921750b9a82d8f5d1294e1586276f"

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Get binding
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        weatherTask().execute()
        //Inflate the layout for this fragment
        return binding.root

    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            binding.loader.visibility = View.VISIBLE
            binding.mainContainer.visibility = View.GONE
            binding.errorText.visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response =
                    // https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API
                    URL("http://api.openweathermap.org/data/2.5/forecast?lat=30.267153&lon=-97.743057&appid=06c921750b9a82d8f5d1294e1586276f&units=imperial")
                    .readText(Charsets.UTF_8)
            } catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)

                // Information for Day 0 (current day)
                val day0 = jsonObj.getJSONArray("list").getJSONObject(0)
                val main0 = day0.getJSONObject("main")
                val weather0 = jsonObj.getJSONArray("weather").getJSONObject(0)
                val weatherDescription0 = weather0.getString("description")

                val updatedAt0Text = day0.getString("dt_txt")
                val temp0 = main0.getString("temp") + "°F"
                val tempMin = "Minimum: " + main0.getString("temp_min") + "°F"
                val tempMax = "Maximum: " + main0.getString("temp_max") + "°F"

                // Information for Day 1
                val day1 = jsonObj.getJSONArray("list").getJSONObject(1)
                val main1 = day1.getJSONObject("main")

                val updatedAt1Text = day1.getString("dt_text")
                val temp1 = main1.getString("temp") + "°F"

                // Information for Day 2
                val day2 = jsonObj.getJSONArray("list").getJSONObject(2)
                val main2 = day2.getJSONObject("main")

                val updatedAt2Text = day2.getString("dt_text")
                val temp2 = main2.getString("temp") + "°F"

                // Information for Day 3
                val day3 = jsonObj.getJSONArray("list").getJSONObject(3)
                val main3 = day3.getJSONObject("main")

                val updatedAt3Text = day3.getString("dt_text")
                val temp3 = main3.getString("temp") + "°F"

                // Information for Day 4
                val day4 = jsonObj.getJSONArray("list").getJSONObject(4)
                val main4 = day4.getJSONObject("main")

                val updatedAt4Text = day4.getString("dt_text")
                val temp4 = main4.getString("temp") + "°F"

                // Information for Day 5
                val day5 = jsonObj.getJSONArray("list").getJSONObject(5)
                val main5 = day5.getJSONObject("main")

                val updatedAt5Text = day5.getString("dt_text")
                val temp5 = main5.getString("temp") + "°F"

                // Information for Day 6
                val day6 = jsonObj.getJSONArray("list").getJSONObject(6)
                val main6 = day6.getJSONObject("main")

                val updatedAt6Text = day6.getString("dt_text")
                val temp6 = main6.getString("temp") + "°F"

                val address = JSONObject(result).getJSONObject("city")
                val city = address.getString("name")
                val country = address.getString("country")
                val fullAddress = city + ", " + country

                // Populating extracted data into our views
                binding.address.text = fullAddress
                binding.updatedAt.text = updatedAt0Text
                binding.status.text = weatherDescription0.capitalize()
                binding.temp.text = temp0
                binding.tempMin.text = tempMin
                binding.tempMax.text = tempMax

                binding.day1.text = updatedAt1Text
                binding.day1Temp.text = temp1

                binding.day2.text = updatedAt2Text
                binding.day2Temp.text = temp2

                binding.day3.text = updatedAt3Text
                binding.day3Temp.text = temp3

                binding.day4.text = updatedAt4Text
                binding.day4Temp.text = temp4

                binding.day5.text = updatedAt5Text
                binding.day5Temp.text = temp5

                binding.day6.text = updatedAt6Text
                binding.day6Temp.text = temp6

//                binding.sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
//                binding.sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
//                binding.wind.text = windSpeed
//                binding.pressure.text = pressure
//                binding.humidity.text = humidity

//                val main = jsonObj.getJSONObject("main")
//                val sys = jsonObj.getJSONObject("sys")
//                val wind = jsonObj.getJSONObject("wind")
//                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
//
//                val updatedAt:Long = jsonObj.getLong("dt")
//                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
//                val temp = main.getString("temp")+"°C"
//                val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
//                val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
//                val pressure = main.getString("pressure")
//                val humidity = main.getString("humidity")
//
//                val sunrise:Long = sys.getLong("sunrise")
//                val sunset:Long = sys.getLong("sunset")
//                val windSpeed = wind.getString("speed")
//                val weatherDescription = weather.getString("description")
//
//                val address = jsonObj.getString("name")+", "+sys.getString("country")
//
//                /* Populating extracted data into our views */
//                binding.address.text = address
//                binding.updatedAt.text = updatedAtText
//                binding.status.text = weatherDescription.capitalize()
//                binding.temp.text = temp
//                binding.tempMin.text = tempMin
//                binding.tempMax.text = tempMax
//                binding.sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
//                binding.sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
//                binding.wind.text = windSpeed
//                binding.pressure.text = pressure
//                binding.humidity.text = humidity

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