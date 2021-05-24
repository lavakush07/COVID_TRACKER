package com.example.covidtracker

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    lateinit var worldCasesTv:TextView
    lateinit var worldRecoveredTv:TextView
    lateinit var worldDeathsTv:TextView
    lateinit var countryCasesTv:TextView
    lateinit var countryRecoveredTv:TextView
    lateinit var countryDeathsTv:TextView
    lateinit var stateRv:RecyclerView
    lateinit var stateRVAdapter: StateRVAdapter
    lateinit var stateList:List<StateModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        worldCasesTv=findViewById(R.id.idTVWorldCases)
        worldRecoveredTv=findViewById(R.id.idTVWorldRecovered)
        worldDeathsTv=findViewById(R.id.idTVWorldDeaths)
        countryCasesTv=findViewById(R.id.idTVIndiaCases)
        countryDeathsTv=findViewById(R.id.idTVIndiaDeaths)
        countryRecoveredTv=findViewById(R.id.idTVIndiaRecovered)
        stateRv=findViewById(R.id.idTVState)
        stateList=ArrayList<StateModel>()
        getStateInfo()
        getWorldInfo()

    }
    private fun getStateInfo()
    {
      val url="https://api.rootnet.in/covid19-in/stats/latest"
      val queue=Volley.newRequestQueue(this@MainActivity)
      val request=JsonObjectRequest(Request.Method.GET,url,null,{
response ->
try{
    val dataObj=response.getJSONObject("data")
    val summaryObj=dataObj.getJSONObject("summary")
    val cases:Int=summaryObj.getInt("total")
    val recovered:Int=summaryObj.getInt("discharged")
    val deaths:Int=summaryObj.getInt("deaths")

    countryCasesTv.text=cases.toString()
    countryRecoveredTv.text=recovered.toString()
    countryDeathsTv.text=deaths.toString()

    val regionalArray=dataObj.getJSONArray("regional")
    for(i in 0 until regionalArray.length())
    {
        val regionalObj=regionalArray.getJSONObject(i)
        val stateName:String=regionalObj.getString("loc")
        val cases:Int=regionalObj.getInt("totalConfirmed")
        val deaths:Int=regionalObj.getInt("deaths")
        val recovered:Int=regionalObj.getInt("discharged")
         val stateModel=StateModel(stateName,recovered,deaths,cases)
        stateList=stateList+stateModel

    }
    stateRVAdapter=StateRVAdapter(stateList)
    stateRv.layoutManager=LinearLayoutManager(this)
    stateRv.adapter=stateRVAdapter

}
catch(e:JSONException)
{
    e.printStackTrace()
}


        },{ error->{
          Toast.makeText(this,"Fail to get data",Toast.LENGTH_SHORT).show()
      }
      })


    }





private fun getWorldInfo()
    {
    val url="https://corona.lmao.ninja/v3/covid-19/all"
        val queue=Volley.newRequestQueue(this@MainActivity)
        val request=JsonObjectRequest(Request.Method.GET,url,null, { response ->
         try {
             val worldCases: Int = response.getInt("cases")
             val worldRecovered: Int = response.getInt("recovered")
             val worldDeaths: Int = response.getInt("Deaths")
             worldRecoveredTv.text = worldRecovered.toString()
             worldCasesTv.text = worldCases.toString()
             worldDeathsTv.text = worldDeaths.toString()

         }
         catch(e:JSONException){
             e.printStackTrace()
         }

        },{
            error->
            Toast.makeText(this,"Fail to get data",Toast.LENGTH_SHORT).show()

        })
        queue.add(request)
    }

}