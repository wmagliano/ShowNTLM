package com.wmagliano.showntlm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.io.File
import kotlin.concurrent.thread

class LogsFragment : Fragment() {

    private lateinit var editLogPath: EditText
    private lateinit var btnLoadLog: Button
    private lateinit var txtLogContent: TextView

    private val defaultLogPath = "/data/local/nhsystem/output.log"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_logs, container, false)

        editLogPath = view.findViewById(R.id.editLogPath)
        btnLoadLog = view.findViewById(R.id.btnLoadLog)
        txtLogContent = view.findViewById(R.id.txtLogContent)

        editLogPath.setText(defaultLogPath)

        btnLoadLog.setOnClickListener {
            val path = editLogPath.text.toString().trim().ifEmpty { defaultLogPath }
            txtLogContent.text = "Cargando log..."
            loadLog(path)
        }

        return view
    }

    private fun loadLog(path: String) {
        thread {
            try {
                val file = File(path)

                val content = if (!file.exists()) {
                    "No existe el archivo:\n$path"
                } else if (!file.canRead()) {
                    "No se puede leer el archivo:\n$path"
                } else {
                    file.readText()
                }

                activity?.runOnUiThread {
                    txtLogContent.text = if (content.isBlank()) "(archivo vacío)" else content
                }
            } catch (e: Exception) {
                activity?.runOnUiThread {
                    txtLogContent.text = "Error al leer el log:\n${e.message}"
                }
            }
        }
    }
}
