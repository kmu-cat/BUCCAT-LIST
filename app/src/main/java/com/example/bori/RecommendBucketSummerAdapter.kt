package com.example.bori


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecommendBucketSummerAdapter (val bucketList: ArrayList<BucketListForm>, heartInterface: heartInterface): RecyclerView.Adapter<RecommendBucketSummerAdapter.CustomViewHolder>()
{
    private val heartInterface = heartInterface
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): RecommendBucketSummerAdapter.CustomViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_recommend_bucket_component,parent, false)
        return CustomViewHolder(view)
    }


    override fun getItemCount(): Int{
        return bucketList.size
    }

    override fun onBindViewHolder(holder: RecommendBucketSummerAdapter.CustomViewHolder, position: Int){
        holder.title.text = bucketList.get(position).title.toString()
        holder.heart.isChecked = bucketList.get(position).heartState
        holder.heart.setOnClickListener{
            heartInterface.heartControl(position, holder.heart.isChecked)
        }
        holder.itemView.setOnClickListener{
            val dialog = RecommendBucketSummerModal(holder,position, heartInterface)
            dialog.myDig(bucketList.get(position).title.toString(),bucketList.get(position).challenger.toString(),holder.heart.isChecked)
        }
    }



    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.recommend_bucket_component_titleTextView)
        val heart = itemView.findViewById<androidx.appcompat.widget.AppCompatCheckBox>(R.id.recommend_bucket_component_heartCheckBox)

    }
}