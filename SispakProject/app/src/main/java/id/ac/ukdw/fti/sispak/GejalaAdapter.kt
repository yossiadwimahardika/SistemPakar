package id.ac.ukdw.fti.sispak

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class GejalaAdapter(var listGejala:ArrayList<Gejala>, var context: Context): RecyclerView.Adapter<GejalaAdapter.GejalaHolder>() {

    lateinit var mListener : onItemCLickLisetener

    interface onItemCLickLisetener{
        fun onItemClick(position: Int)
    }
    fun setOnclickListener(listener: onItemCLickLisetener){
        mListener = listener
    }

    class GejalaHolder(val view: View, listener: onItemCLickLisetener): RecyclerView.ViewHolder(view){
        var firestore: FirebaseFirestore? = null
        fun bind (gejala:Gejala, context: Context){
            firestore = FirebaseFirestore.getInstance()

            val mainBtn = view.findViewById<RadioGroup>(R.id.mainLinearLayoutButton)
            val btn1 = view.findViewById<RadioButton>(R.id.btn0_3)
            val btn2 = view.findViewById<RadioButton>(R.id.btn0_6)
            val btn3 = view.findViewById<RadioButton>(R.id.btn1)

            val hasil = view.findViewById<TextView>(R.id.hasil)


            val chckboxGejala = view.findViewById<CheckBox>(R.id.checkBox)
            chckboxGejala.setText(gejala.nama)

            val txtIdGejala = view.findViewById<TextView>(R.id.idGejala)
            txtIdGejala.setText(gejala.id)


            chckboxGejala.setOnCheckedChangeListener { group, isChecked ->

                if(isChecked){
                    mainBtn.visibility = View.VISIBLE
//                    hasil.visibility = View.VISIBLE
                }
                else if(!isChecked){
                    btn1.isChecked = false
                    btn2.isChecked =false
                    btn3.isChecked = false
                    mainBtn.visibility = View.GONE
                    hasil.visibility = View.GONE
                    firestore?.collection("tempData")?.document(gejala.id)
                        ?.delete()
                        ?.addOnSuccessListener { Log.d(TAG, "successfully deleted!") }
                        ?.addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                }

            }


            var valueBtn = ""
            mainBtn.setOnCheckedChangeListener { radioGroup, checked ->
                valueBtn = when(checked){
                    R.id.btn0_3 -> "0.3"
                    R.id.btn0_6 -> "0.6"
                    R.id.btn1 -> "1"
                    else -> ""
                }
                val temp = TempData(gejala.id, gejala.nama, valueBtn.toDouble())
                firestore?.collection("tempData")?.document(gejala.id)?.set(temp)

                var hasilPilih = when(valueBtn){
                    "Kurang Yakin" -> "0.3"
                    "Cukup Yakin" -> "0.6"
                    "Sangat Yakin" -> "1"
                    else -> ""
                }

                hasil.setText(valueBtn)

            }

//        }
//        init {
//            view.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GejalaHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent,false)
        return GejalaHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: GejalaHolder, position: Int) {
        holder.bind(listGejala[position],context)


    }

    override fun getItemCount(): Int {
        return listGejala.size
    }
}