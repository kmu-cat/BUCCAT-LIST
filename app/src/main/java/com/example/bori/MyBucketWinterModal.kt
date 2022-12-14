package com.example.bori

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View.inflate
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import com.example.bori.databinding.FragmentInventoryBinding.inflate
import com.example.bori.databinding.FragmentRecommendBucketWinterBinding.inflate
import java.security.AccessController.getContext

class MyBucketWinterModal (holder: MyBucketWinterAdapter.CustomViewHolder, position: Int, heartInterface: heartInterface){
    private val context = holder.itemView.context
    private val dialog = Dialog(context)
    private  val position = position
    private val heartInterface = heartInterface
    fun myDig(bucketTitle:String, bucketChallenger:String, bucketHeart:Boolean){
//        val view = LayoutInflater.from(context).inflate(R.layout.activity_bucketlist_modal, null, false)
//        view.findViewById<TextView>(R.id.bucketListModal_titleTextView).text = "dfd"
        dialog.setContentView(R.layout.activity_bucketlist_modal)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val title = dialog.findViewById<TextView>(R.id.bucketListModal_titleTextView)
        title.text = bucketTitle
        val heart = dialog.findViewById<androidx.appcompat.widget.AppCompatCheckBox>(R.id.bucketListModal_heartCheckBox)
        heart.isChecked = bucketHeart
        if(bucketHeart==true){
            val uploadButton = dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.bucketListModal_uploadButton)
            uploadButton.isEnabled = true
        }

        dialog.setCancelable(false)
        dialog.show()

        val xButton = dialog.findViewById<ImageButton>(R.id.bucketListModal_xButton)
        xButton.setOnClickListener{
            heartInterface.heartControl(position,heart.isChecked)
            dialog.dismiss()
        }
        val heartButton = dialog.findViewById<androidx.appcompat.widget.AppCompatCheckBox>(R.id.bucketListModal_heartCheckBox)
        heartButton.setOnClickListener {
            val uploadButton = dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.bucketListModal_uploadButton)
            uploadButton.isEnabled = heartButton.isChecked
        }
        val certifyingShotButton = dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.bucketListModal_lookAroundButton)
        certifyingShotButton.setOnClickListener{
            val intent = Intent(context, Main::class.java)
            intent.putExtra("Tag",bucketTitle)
            intent.putExtra("pageNum", 1)
            context.startActivity(intent)
        }
        val uploadButton = dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.bucketListModal_uploadButton)
        uploadButton.setOnClickListener {
            val intent = Intent(context, Post::class.java)
            intent.putExtra("title",bucketTitle)
            context.startActivity(intent)
        }
    }
    interface  ButtonClickListener{
        fun onClicked(myName:String)
    }
    private lateinit var onClickedListener : ButtonClickListener
    fun setOnClickedListener(listener : ButtonClickListener){
        onClickedListener = listener
    }
}