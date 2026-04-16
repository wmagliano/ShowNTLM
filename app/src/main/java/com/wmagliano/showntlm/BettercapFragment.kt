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

class BettercapFragment : Fragment() {

    private lateinit var editLocalIp: EditText
    private lateinit var editNetHunterIp: EditText
    private lateinit var btnRun: Button
    private lateinit var txtOutput: TextView

    // Ruta fija al script dentro del teléfono.
    // Cambiala luego por la que uses realmente.
    private val scriptPath = "/data/local/nhsystem/run_better.sh"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_bettercap, container, false)

        editLocalIp = view.findViewById(R.id.editLocalIp)
        editNetHunterIp = view.findViewById(R.id.editNetHunterIp)
        btnRun = view.findViewById(R.id.btnRunBettercap)
        txtOutput = view.findViewById(R.id.txtBettercapOutput)

        btnRun.setOnClickListener {
            val localIp = editLocalIp.text.toString().trim()
            val netHunterIp = editNetHunterIp.text.toString().trim()

            if (localIp.isEmpty() || netHunterIp.isEmpty()) {
                txtOutput.text = "Completa ambas IP antes de ejecutar."
                return@setOnClickListener
            }

            txtOutput.text = "Ejecutando script..."

            runScript(localIp, netHunterIp)
        }

        return view
    }

    private fun runScript(localIp: String, netHunterIp: String) {
        thread {
            try {
                val shellCommand = "sh $scriptPath '$localIp' '$netHunterIp'"

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