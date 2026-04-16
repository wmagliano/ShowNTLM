package com.wmagliano.showntlm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)

        tabLayout.addTab(tabLayout.newTab().setText("Bettercap"))
        tabLayout.addTab(tabLayout.newTab().setText("Responder"))
        tabLayout.addTab(tabLayout.newTab().setText("Logs"))
        tabLayout.addTab(tabLayout.newTab().setText("About"))

        if (savedInstanceState == null) {
            loadFragment(BettercapFragment())
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.position) {
                    0 -> BettercapFragment()
                    1 -> ResponderFragment()
                    2 -> LogsFragment()
                    3 -> AboutFragment()
                    else -> BettercapFragment()
                }
                loadFragment(fragment)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}