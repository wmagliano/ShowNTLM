package com.wmagliano.showntlm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.concurrent.thread

class ResponderFragment : Fragment() {

    private lateinit var editInterface: EditText
    private lateinit var btnRun: Button
    private lateinit var txtOutput: TextView

    private val scriptPath = "/data/local/nhsystem/run_responder.sh"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_responder, container, false)

        editInterface = view.findViewById(R.id.editInterface)
        btnRun = view.findViewById(R.id.btnRunResponder)
        txtOutput = view.findViewById(R.id.txtResponderOutput)

        btnRun.setOnClickListener {
            val iface = editInterface.text.toString().trim()

            if (iface.isEmpty()) {
                txtOutput.text = "Completa la interfaz antes de ejecutar."
                return@setOnClickListener
            }

            txtOutput.text = "Ejecutando script..."

            runScript(iface)
        }

        return view
    }

    private fun runScript(iface: String) {
        thread {
            try {
                val shellCommand = "/system/bin/sh $scriptPath '$iface'"

                val process = ProcessBuilder("su", "-c", shellCommand)
                    .redirectErrorStream(true)
                    .start()

                val output = process.inputStream.bufferedReader().use { it.readText() }
                val exitCode = process.waitFor()

                activity?.runOnUiThread {
                    txtOutput.text = buildString {
                        appendLine("Script: $scriptPath")
                        appendLine("Exit code: $exitCode")
                        appendLine()
                        appendLine(output.ifBlank { "(sin salida)" })
                    }
                }
            } catch (e: Exception) {
                activity?.runOnUiThread {
                    txtOutput.text = "Error al ejecutar el script:\n${e.message}"
                }
            }
        }
    }
}
