package id.ac.ukdw.fti.sispak

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class HalamanUtama : AppCompatActivity() {

    var firestore: FirebaseFirestore? = null
    var arrayGejala = arrayListOf<Gejala>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_utama)
        firestore = FirebaseFirestore.getInstance()

        ambilData()

        val rcyGejala = findViewById<RecyclerView>(R.id.recView)
        rcyGejala.layoutManager = LinearLayoutManager(this)
        val adapter = GejalaAdapter(arrayGejala,this)
        rcyGejala.adapter = adapter

        adapter.setOnclickListener(object : GejalaAdapter.onItemCLickLisetener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@HalamanUtama,"klik id. ${position}",Toast.LENGTH_SHORT).show()
            }

        })


        val txt = findViewById<TextView>(R.id.txt)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            val intent = Intent(this, Hasil::class.java)
            startActivity(intent)
        }



    }

    fun ambilData(){
        val t = findViewById<TextView>(R.id.ttt)
        val pd = ProgressDialog(this)
        pd.setMessage("Menyiapkan pertanyaan")
        pd.show()
        firestore?.collection("gejala")?.get()?.addOnSuccessListener {
                docs ->
            var hasil = ""
            for(doc in docs){
                hasil += "${doc["Gejala"]}"
                val gejala = Gejala("${doc["IDGejala"]}","${doc["Gejala"]}")
                arrayGejala.add(gejala)
//                t.setText(arrayGejala.size.toString())
            }
            t.setText(hasil)
            t.visibility = View.GONE
            pd.dismiss()
        }
    }
}