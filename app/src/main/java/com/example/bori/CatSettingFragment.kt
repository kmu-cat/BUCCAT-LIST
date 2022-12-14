package com.example.bori


import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date

class CatSettingFragment : Fragment() {
    lateinit var catInfoJSON: JSONObject
    lateinit var applyButton: Button
    lateinit var background: ImageView

    lateinit var adapteritem_color: AdapterItem
    lateinit var adapteritem_hair: AdapterItem
    lateinit var adapteritem_face: AdapterItem
    lateinit var adapteritem_body: AdapterItem
    lateinit var adapteritem_foot: AdapterItem
    lateinit var adapteritem_etc: AdapterItem

    private val datasColor = mutableListOf<DataItem>()
    private val datasHair = mutableListOf<DataItem>()
    private val datasFace = mutableListOf<DataItem>()
    private val datasBody = mutableListOf<DataItem>()
    private val datasFoot = mutableListOf<DataItem>()
    private val datasEtc = mutableListOf<DataItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val prefs: SharedPreferences = this.requireActivity().getSharedPreferences("CatInfo", 0)
        val edit: Editor = prefs.edit()

        var item1_unlock = false
        var item2_unlock = false
        var item3_unlock = false

        val db = Firebase.firestore
        val user = FirebaseAuth.getInstance().currentUser!!
        val email = user.email.toString()
        val docRef = db.collection("users").document(email)
        docRef.get().addOnSuccessListener { document ->
            item1_unlock = document.data!!["item1"] as Boolean
            item2_unlock = document.data!!["item2"] as Boolean
            item3_unlock = document.data!!["item3"] as Boolean
            if (item1_unlock) { datasFace.apply { add(DataItem("?????? ??????", "????????? ??????", R.drawable.rc_item_face_teeth, R.drawable.face_teeth)) } }
            if (item2_unlock) { datasFace.apply { add(DataItem("????????? ?????????", "????????? ?????????", R.drawable.rc_item_face_eyelashes, R.drawable.face_eyelashes)) } }
            if (item3_unlock) { datasFace.apply { add(DataItem("??????", "???????????? ????????? ???????????????", R.drawable.rc_item_face_heart, R.drawable.face_heart)) } }
        }

        catInfoJSON = JSONObject(prefs.getString("CatInfo", null))

        var infoColor: Int; var infoFace: Int; var infoBody: Int
        var infoFoot: Int; var infoHair: Int; var infoEtc: Int;

        if (catInfoJSON == null) {
            infoColor = R.drawable.cat_snowwhite
            infoHair = R.drawable.item_none
            infoFace = R.drawable.item_none
            infoBody = R.drawable.item_none
            infoFoot = R.drawable.item_none
            infoEtc = R.drawable.item_none
        } else {
            infoColor = catInfoJSON.getInt("Color")
            infoHair = catInfoJSON.getInt("Hair")
            infoFace = catInfoJSON.getInt("Face")
            infoBody = catInfoJSON.getInt("Body")
            infoFoot = catInfoJSON.getInt("Foot")
            infoEtc = catInfoJSON.getInt("Etc")
        }

        val view = inflater.inflate(R.layout.fragment_catsetting, container, false)

        applyButton = view.findViewById(R.id.catSetting_confirmButton)
        background = view.findViewById(R.id.cat_bg)

        // ?????? ????????? ????????? Date ??????
        val date = Date(System.currentTimeMillis())
        val sdf = SimpleDateFormat("MM")
        when (sdf.format(date)) {
            in arrayListOf("03", "04", "05") -> { // 3??? ~ 5??? (???)
                background.setImageResource(R.drawable.bg_set_spring)
                view.findViewById<TextView>(R.id.item_script).text = "???????????? ?????????!"
            }
            in arrayListOf("06", "07", "08") -> { // 6??? ~ 8??? (??????)
                background.setImageResource(R.drawable.bg_set_summer)
                view.findViewById<TextView>(R.id.item_script).text = "????????? ????????? ??????~"
            }
            in arrayListOf("09", "10", "11") -> { // 9??? ~ 11??? (??????)
                background.setImageResource(R.drawable.bg_set_fall)
                view.findViewById<TextView>(R.id.item_script).text = "????????? ????????? ?????????..."
            }
            in arrayListOf("12", "01", "02") -> { // 12??? ~ 2??? (??????)
                background.setImageResource(R.drawable.bg_set_winter)
                view.findViewById<TextView>(R.id.item_script).text = "????????? ?????? ?????? ??????"
            }
        }

        adapteritem_color = AdapterItem(view.context)
        adapteritem_hair = AdapterItem(view.context)
        adapteritem_face = AdapterItem(view.context)
        adapteritem_body = AdapterItem(view.context)
        adapteritem_foot = AdapterItem(view.context)
        adapteritem_etc = AdapterItem(view.context)

        val rvColor: RecyclerView = view.findViewById(R.id.rv_item_color)
        val rvHair: RecyclerView = view.findViewById(R.id.rv_item_hair)
        val rvFace: RecyclerView = view.findViewById(R.id.rv_item_face)
        val rvBody: RecyclerView = view.findViewById(R.id.rv_item_body)
        val rvFoot: RecyclerView = view.findViewById(R.id.rv_item_foot)
        val rvEtc: RecyclerView = view.findViewById(R.id.rv_item_etc)

        rvColor.adapter = adapteritem_color
        rvHair.adapter = adapteritem_hair
        rvFace.adapter = adapteritem_face
        rvBody.adapter = adapteritem_body
        rvFoot.adapter = adapteritem_foot
        rvEtc.adapter = adapteritem_etc

        view.findViewById<ImageView>(R.id.cat_color).setImageResource(infoColor)
        view.findViewById<ImageView>(R.id.cat_hair).setImageResource(infoHair)
        view.findViewById<ImageView>(R.id.cat_face).setImageResource(infoFace)
        view.findViewById<ImageView>(R.id.cat_body).setImageResource(infoBody)
        view.findViewById<ImageView>(R.id.cat_foot).setImageResource(infoFoot)
        view.findViewById<ImageView>(R.id.cat_etc).setImageResource(infoEtc)


        applyButton.setOnClickListener {
            Toast.makeText(this.context, "???????????? ??????????????????~", Toast.LENGTH_SHORT).show()
            edit.putString("CatInfo", catInfoJSON.toString())
            edit.apply()
        }

        adapteritem_color.setItemClickListener(object: AdapterItem.OnItemClickListener{
            override fun onClick(v: View, position: Int, src: Int, name: String, explain: String) {
                view.findViewById<ImageView>(R.id.cat_color).setImageResource(src)
                view.findViewById<TextView>(R.id.item_name).text = name
                view.findViewById<TextView>(R.id.item_name).setTypeface(null, Typeface.BOLD)
                view.findViewById<TextView>(R.id.item_script).text = explain
                catInfoJSON.remove("Color")
                catInfoJSON.put("Color", src)
            }
        })

        adapteritem_hair.setItemClickListener(object : AdapterItem.OnItemClickListener {
            override fun onClick(v: View, position: Int, src: Int, name: String, explain: String) {
                view.findViewById<ImageView>(R.id.cat_hair).setImageResource(src)
                view.findViewById<TextView>(R.id.item_name).text = name
                view.findViewById<TextView>(R.id.item_name).setTypeface(null, Typeface.BOLD)
                view.findViewById<TextView>(R.id.item_script).text = explain
                catInfoJSON.remove("Hair")
                catInfoJSON.put("Hair", src)
            }
        })

        adapteritem_face.setItemClickListener(object : AdapterItem.OnItemClickListener {
            override fun onClick(v: View, position: Int, src: Int, name: String, explain: String) {
                view.findViewById<ImageView>(R.id.cat_face).setImageResource(src)
                view.findViewById<TextView>(R.id.item_name).text = name
                view.findViewById<TextView>(R.id.item_name).setTypeface(null, Typeface.BOLD)
                view.findViewById<TextView>(R.id.item_script).text = explain
                catInfoJSON.remove("Face")
                catInfoJSON.put("Face", src)
            }
        })

        adapteritem_body.setItemClickListener(object : AdapterItem.OnItemClickListener {
            override fun onClick(v: View, position: Int, src: Int, name: String, explain: String) {
                view.findViewById<ImageView>(R.id.cat_body).setImageResource(src)
                view.findViewById<TextView>(R.id.item_name).text = name
                view.findViewById<TextView>(R.id.item_name).setTypeface(null, Typeface.BOLD)
                view.findViewById<TextView>(R.id.item_script).text = explain
                catInfoJSON.remove("Body")
                catInfoJSON.put("Body", src)
            }
        })

        adapteritem_foot.setItemClickListener(object : AdapterItem.OnItemClickListener {
            override fun onClick(v: View, position: Int, src: Int, name: String, explain: String) {
                view.findViewById<ImageView>(R.id.cat_foot).setImageResource(src)
                view.findViewById<TextView>(R.id.item_name).text = name
                view.findViewById<TextView>(R.id.item_name).setTypeface(null, Typeface.BOLD)
                view.findViewById<TextView>(R.id.item_script).text = explain
                catInfoJSON.remove("Foot")
                catInfoJSON.put("Foot", src)
            }
        })

        adapteritem_etc.setItemClickListener(object : AdapterItem.OnItemClickListener {
            override fun onClick(v: View, position: Int, src: Int, name: String, explain: String) {
                view.findViewById<ImageView>(R.id.cat_etc).setImageResource(src)
                view.findViewById<TextView>(R.id.item_name).text = name
                view.findViewById<TextView>(R.id.item_name).setTypeface(null, Typeface.BOLD)
                view.findViewById<TextView>(R.id.item_script).text = explain
                catInfoJSON.remove("Etc")
                catInfoJSON.put("Etc", src)
            }
        })

        datasColor.apply {
            add(DataItem("?????????", "???????????? ???????????????", R.drawable.rc_color_samsagi, R.drawable.cat_samsagi))
            add(DataItem("??????", "??? ?????? ?????? ?????????", R.drawable.rc_color_cheese, R.drawable.cat_cheese))
            add(DataItem("?????????", "?????? ????????? ???????????????", R.drawable.rc_color_tiger, R.drawable.cat_tiger))
            add(DataItem("?????????", "??????????????? ??????????????? ???", R.drawable.rc_color_snowwhite, R.drawable.cat_snowwhite))
            add(DataItem("??????", "????????? ???????????????", R.drawable.rc_color_siamese, R.drawable.cat_siamese))
            add(DataItem("?????????", "????????? ????????????", R.drawable.rc_color_black, R.drawable.cat_black))
        }

        datasHair.apply {
            add(DataItem("?????? ??????", "?????? ?????? ???????????? ????????????", R.drawable.rc_item_none, R.drawable.item_none))
            add(DataItem("?????? ????????? ??????", "???????????? ????????? ??????", R.drawable.rc_item_ribbon_red, R.drawable.item_test))
            add(DataItem("?????? ????????? ??????", "????????? ????????? ??????", R.drawable.rc_item_yellow_tail_ribbon, R.drawable.yellow_tail_ribbon))
            add(DataItem("?????? ???????????????", "????????? ????????? ????????????", R.drawable.rc_item_x_mas, R.drawable.x_mas))
            add(DataItem("?????????", "?????? ????????? ?????? ????????? ????????? ???", R.drawable.rc_item_hermit_crab, R.drawable.hermit_crab))
        }

        datasFace.apply {
            add(DataItem("?????? ??????", "?????? ?????? ???????????? ????????????", R.drawable.rc_item_none, R.drawable.item_none))
            add(DataItem("??????", "???????????? ?????????", R.drawable.rc_item_ahchoo, R.drawable.ahchoo))
            add(DataItem("????????????", "?????? ??????????????? ?????????", R.drawable.rc_item_eye_patch, R.drawable.eye_patch))
            add(DataItem("????????? ??????", "????????? ?????? ????????? ??????", R.drawable.rc_item_michole, R.drawable.michole))
            add(DataItem("?????????", "????????? ??????", R.drawable.rc_item_flushing, R.drawable.flushing))
        }

        datasBody.apply {
            add(DataItem("?????? ??????", "?????? ?????? ???????????? ????????????", R.drawable.rc_item_none, R.drawable.item_none))
            add(DataItem("????????? ???", "????????? ?????? ????????? ???????????????", R.drawable.rc_item_body_princess, R.drawable.body_princess))
            add(DataItem("?????? ???", "????????? ????????? ?????????", R.drawable.rc_item_body_cloth_blue, R.drawable.body_cloth_blue))
            add(DataItem("??????", "????????? ????????? ?????????", R.drawable.rc_item_body_ouch, R.drawable.body_ouch))
            add(DataItem("?????? ?????????", "????????? ???????????? ????????????", R.drawable.rc_item_body_scarf_red, R.drawable.body_scarf_red))
        }

        datasFoot.apply {
            add(DataItem("?????? ??????", "?????? ?????? ???????????? ????????????", R.drawable.rc_item_none, R.drawable.item_none))
            add(DataItem("????????? ?????????", "??? ????????? ????????????", R.drawable.rc_item_foot_fish, R.drawable.foot_fish))
            add(DataItem("?????? ?????? ?????????", "????????? ????????? ?????? ???????????? ??????", R.drawable.rc_item_foot_lace_yellow, R.drawable.foot_lace_yellow))
            add(DataItem("?????? ?????? ?????????", "????????? ????????? ?????? ???????????? ??????", R.drawable.rc_item_foot_lace_blue, R.drawable.foot_lace_blue))
        }

        datasEtc.apply {
            add(DataItem("?????? ??????", "?????? ?????? ???????????? ????????????", R.drawable.rc_item_none, R.drawable.item_none))
            add(DataItem("?????? ?????????", "????????????", R.drawable.rc_item_blue_bowl, R.drawable.blue_bowl))
            add(DataItem("?????? ?????????", "????????????", R.drawable.rc_item_orange_bowl, R.drawable.orange_bowl))
            add(DataItem("?????????", "???????????? ????????? ?????? ???????????????", R.drawable.rc_item_white_foot, R.drawable.white_foot))
            add(DataItem("??????", "??? ??? ?????????", R.drawable.rc_item_wing, R.drawable.wing))
        }

        adapteritem_color.datas = datasColor
        adapteritem_hair.datas = datasHair
        adapteritem_face.datas = datasFace
        adapteritem_body.datas = datasBody
        adapteritem_foot.datas = datasFoot
        adapteritem_etc.datas = datasEtc

        adapteritem_color.notifyDataSetChanged()
        adapteritem_hair.notifyDataSetChanged()
        adapteritem_face.notifyDataSetChanged()
        adapteritem_foot.notifyDataSetChanged()
        adapteritem_etc.notifyDataSetChanged()

        rvColor.layoutManager = GridLayoutManager(activity, 5)
        rvHair.layoutManager = GridLayoutManager(activity, 5)
        rvFace.layoutManager = GridLayoutManager(activity, 5)
        rvBody.layoutManager = GridLayoutManager(activity, 5)
        rvFoot.layoutManager = GridLayoutManager(activity, 5)
        rvEtc.layoutManager = GridLayoutManager(activity, 5)

        return view
    }
}