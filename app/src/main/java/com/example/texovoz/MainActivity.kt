package com.example.texovoz

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {


    lateinit var imagenBocina: ImageView
    lateinit var Editor: EditText
    lateinit var locutor: TextToSpeech
    lateinit var voz: SeekBar
    lateinit var velocidadVoz: SeekBar
    lateinit var Idiomas: Spinner
    var controladorVoz = 1.0
    var controlVelocidad = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imagenBocina = findViewById(R.id.ImagenVocina)
        Editor = findViewById(R.id.sentencia)
        voz = findViewById(R.id.VozMH)
        velocidadVoz = findViewById(R.id.velocidadHabla)
        locutor = TextToSpeech(this, this)
        Idiomas = findViewById(R.id.idiomasSpin)
        //primer seek
        voz.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, posicion: Int, fromUser: Boolean) {
                controladorVoz = (posicion.toFloat() / (seekBar.max / 2)).toDouble()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        // segundo seek
        velocidadVoz.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                controlVelocidad = (progress.toFloat() / (seekBar.max / 2)).toDouble()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        imagenBocina.setOnClickListener { speech() }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onInit(Status: Int) {
        if (Status == TextToSpeech.SUCCESS) {
            val Espaniol = Locale("es", "Es")
            locutor.language = Espaniol
            val conjuntodeVoz = locutor.voices
            for (voz in conjuntodeVoz) {
                System.err.println("Nombre")
                System.err.println(voz.name)//agregar los nombres y los idiomas a un array
                System.err.println("idioma")
                System.err.println(voz.locale)
                if (voz.name == "idiomas") {
                    locutor.voice = voz
                }
                Idiomas.adapter = ArrayAdapter<String>(this, Status)


            }
        } else {
            Toast.makeText(
                this,
                "No se pudo realizar tu Accion con tu celular chafa",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun speech() {
        locutor.setPitch(controladorVoz.toFloat())
        locutor.setSpeechRate(controlVelocidad.toFloat())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            locutor.speak(Editor.text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            locutor.speak(Editor.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
        }
    }
}