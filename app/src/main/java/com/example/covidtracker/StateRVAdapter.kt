package com.example.covidtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.view.menu.ActionMenuItemView
class StateRVAdapter(private val stateList:List<StateModel>) :
    RecyclerView.Adapter<StateRVAdapter.StatRVViewHolder>() {

    class StatRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stateNameTv: TextView = itemView.findViewById(R.id.idTVState)
        val casesTv: TextView = itemView.findViewById(R.id.idTVCases)
        val deathsTv: TextView = itemView.findViewById(R.id.idTVDeaths)
        val recoveredTv: TextView = itemView.findViewById(R.id.idTVRecovered)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatRVViewHolder {
       val itemView=LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StatRVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StatRVViewHolder, position: Int)
    {
val stateData=stateList[position]
        holder.casesTv.text=stateData.cases.toString()
        holder.stateNameTv.text=stateData.state
        holder.deathsTv.text=stateData.toString()
        holder.recoveredTv.text=stateData.recovered.toString()

    }

    override fun getItemCount(): Int {
        return stateList.size
    }






}
