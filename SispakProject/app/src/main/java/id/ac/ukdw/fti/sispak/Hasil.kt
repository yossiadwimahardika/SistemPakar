package id.ac.ukdw.fti.sispak

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.roundToInt

class Hasil : AppCompatActivity() {
    var firestore: FirebaseFirestore? = null
    val data:HashMap<String, ArrayList<Double>> = HashMap<String, ArrayList<Double>>()
    val gangguan = arrayListOf<Gangguan>()
    val tempData = arrayListOf<TempData>()
    val cf = arrayListOf<CertaintyFactor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil)
        firestore = FirebaseFirestore.getInstance()


        val cobaa = findViewById<TextView>(R.id.coba)
        ambilGangguan()
        ambilCertainty()
        ambilTempData()

        var gtemp = arrayListOf<String>()

        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(500)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    //button1.performClick();
                        val btn = findViewById<Button>(R.id.aa)
                    click(btn)

                }
            }
        }
        timer.start()

//        for(i in 1..1000){
//            cobaa.setText(gtemp.size.toString() +"AAAAAAAAAAA")
//        }

//        coba.setText("BBBBB")
//        val data = HashMap<String, ArrayList<Double>>()
//
//
//        firestore?.collection("gangguan_pencernaan")?.get()?.addOnSuccessListener { docs ->
//            for(doc in docs){
////                data["${doc["IDGejala"]}"] = arrayListOf<Double>()
//                data.put("${doc["ID_Jenis_Gangguan"]}",arrayListOf<Double>())
//            }
//        }

//        var h = setOf<String>()
//        val arrayNilai = arrayListOf<Double>()
//        val array = arrayListOf<String>()
//        val data = HashMap<String, ArrayList<Double>>()
//
//        var count = 0
//
//        firestore?.collection("gangguan_pencernaan")?.get()?.addOnSuccessListener { docs ->
//            for(doc in docs){
//                firestore?.collection("tempData")?.get()?.addOnSuccessListener { docs2 ->
//
//                    for(doc2 in docs2){
//                        firestore?.collection("certainty_factor")?.whereEqualTo("rule", doc2["id"])?.get()?.addOnSuccessListener { hasilnya ->
//                            for(hsl in hasilnya){
//                                if(hsl["hasil"] == doc["ID_Jenis_Gangguan"]){
//                                    arrayNilai.add((hsl["bobot"].toString().toDouble()*doc2["bobot"].toString().toDouble()))
//                                    array.add(hsl["hasil"].toString())
////                                    hh += array.toString() +"\n"
//                                }
//
//                            }
////                            var a = array.toString() +"\n" + arrayNilai.toString()
////                            val result = array.zip(arrayNilai) { a, b -> "$a, $b" }
////                            coba.setText(result.toString())
////                            count += 1
////                            coba.setText(count.toString())
//                            h = array.toSet()
//
//                        }
//                        coba.setText("BBBBB")
//                    }
//                    coba.setText("CCCC")
//                }
////                arrayNilai.clear()
//                coba.setText("DDDD")
//            }
//        }
//        coba.setText("EEEEEEEEEE")
//
//        coba.setText("FFFFFFFF")
//        coba.setText(array.toString())

//        coba.setText(hh)
//        firestore?.collection("tempData")?.get()?.addOnSuccessListener { docs ->
//            var hasil = ""
//            for (doc in docs) {
//                hasil += "${doc["id"]}"
//                val bbt: Double = "${doc["bobot"]}".toDouble()
//                firestore?.collection("certainty_factor")?.whereEqualTo("rule",doc["id"])?.get()?.addOnSuccessListener { hasilnya ->
//                    for(hsl in hasilnya){
////                        data.get("${hsl["hasil"]}")!!.add("${hsl["bobot"]}".toDouble() *bbt)
////                        data["${hsl["hasil"]}"]?.add("${hsl["bobot"]}".toDouble()*bbt)
////                        val a = ("${hsl["bobot"]}".toDouble()*bbt).toString() + " " + "${hsl["hasil"]}"
////                        coba.setText(data.get("${hsl["hasil"]}")!!.size.toString())
//                        data[hsl["hasil"]]?.add(hsl["bobot"].toString().toDouble() * bbt)
//                        coba.setText(data["GG04"].toString())
//                    }
//                }
//            }
//
//
////            coba.setText(data["GG01"]?.get(0).toString())
//            val hasilAkhir = HashMap<String, Double>()
//            var terbesar = 0.0
//            var keyTerbesar: String? = null
//
//
//            for(key in data.keys){
//                var temp = 0.0
//                for(i in 0..data[key]!!.size-1){
//                    if(i == 0){
//                        temp = data[key]!!.get(i)
//                    }
//
//                    else{
//                        temp = temp + (data[key]!!.get(i) * (1-temp))
//                    }
//                }
//
//                hasilAkhir[key] = temp
//                if(temp > terbesar){
//                    terbesar = temp
//                    keyTerbesar = key
//                }
//
//            }
//        }

        val btnMasuk = findViewById<Button>(R.id.btnKeAwal)
        btnMasuk.setOnClickListener {
//            coba.setText(gangguan.size.toString())
            val intent = Intent(this, MainActivity::class.java)

            firestore?.collection("tempData")?.get()?.addOnSuccessListener {
                docs ->
                for (doc in docs){
                    firestore?.collection("tempData")?.document("${doc["id"]}")?.delete()
                }
            }
            startActivity(intent)
        }
//        coba.setText("AAAAAAA")
//        coba.setText(gangguan.size.toString())
    }


//    fun ambilGangguan(){
//        firestore?.collection("gangguan_pencernaan")?.get()?.addOnSuccessListener { docs ->
//            var hasil = ""
//            for(doc in docs){
//                hasil += "${doc["Jenis_Gangguan_Pencernaan"]}"
//                gangguan.add(doc["ID_Jenis_Gangguan"].toString())
////                val gejala = Gejala("${doc["ID_Jenis_Gangguan"]}","${doc["Jenis_Gangguan_Pencernaan"]}")
////                gangguan.add(gejala)
//            }
//        }
//    }

    fun ambilGangguan(){
        firestore?.collection("gangguan_pencernaan")?.get()?.addOnSuccessListener {
            docs ->
            for(doc in docs){
                gangguan.add(Gangguan("${doc["ID_Jenis_Gangguan"]}","${doc["Jenis_Gangguan_Pencernaan"]}"))
            }
        }
    }

    fun ambilCertainty(){
        firestore?.collection("certainty_factor")?.get()?.addOnSuccessListener {
            docs ->
            for (doc in docs){
                cf.add(CertaintyFactor("${doc["rule"]}","${doc["hasil"]}","${doc["bobot"]}".toDouble()))

            }
        }
    }

    fun ambilTempData(){
        firestore?.collection("tempData")?.get()?.addOnSuccessListener {
            docs ->
            for(doc in docs){
                tempData.add(TempData("${doc["id"]}", "${doc["gejala"]}","${doc["bobot"]}".toDouble()))
            }
        }
    }



    fun click(view: View) {
        val hasilMap = HashMap<String, ArrayList<Double>>()
        val coba = findViewById<TextView>(R.id.coba)

        for(data in gangguan){
            val arrayDouble = ArrayList<Double>()
            for(data2 in tempData){
                val bobot = data2.bobot
                for(data3 in cf){
                    if(data3.hasil.equals(data.id) && data3.rule.equals(data2.id)){
                        arrayDouble.add(bobot * data3.bobot)
                    }
                }

            }
            hasilMap.put(data.id,arrayDouble)


        }

        val hasilAkhir = HashMap<String, Double>()
        for (key in hasilMap.keys){
            var temp = 0.0
            for(i in 0 .. hasilMap[key]!!.size -1){
                if(i == 0){
                    temp = hasilMap[key]!!.get(i)
                }
                else{
                    temp = temp + (hasilMap[key]!!.get(i) * (1 - temp))
                }
            }
            hasilAkhir.put(key,temp)

        }

        var output = ""
        var tampung = 0.0
        for(hsl in hasilAkhir.keys){
            if (hasilAkhir[hsl]!! > tampung){
                tampung = hasilAkhir[hsl]!!
//                var a = (tampung * 100.0).roundToInt().toString() + "% "
//                coba.setText(a)
                for(gg in gangguan){
                    if (gg.id.equals(hsl)){
                        var a = (tampung * 100.0).roundToInt().toString() + "% " + gg.nama
                        coba.setText(a)

                    }
//                    coba.setText(gg.id.equals(hsl).toString())
                }

            }
//            coba.setText(hasilAkhir[hsl].toString())
        }
    }
}