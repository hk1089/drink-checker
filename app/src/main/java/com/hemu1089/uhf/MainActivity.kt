package com.hemu1089.uhf

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.app.drinker.AnalyzerClass
import com.hemu1089.uhf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val drinker = AnalyzerClass()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        drinker.onStatusUpdate = { statusMessage ->
            // Update your UI or log the status here
            binding.breathalyzerStateTextView.text = statusMessage
            drinker.requestBatteryLevelClicked()
            Log.d("YourClass", "Analyzer Status: $statusMessage")
        }
        drinker.onBatteryLevel = { batteryLevel ->
            // Update your UI or log the battery level here
            runOnUiThread {
                binding.batteryStateTextView.text = batteryLevel
            }
            Log.d("YourClass", "Battery Level: $batteryLevel%")
        }
        drinker.onConnectedName = { name ->
            // Update your UI or log the serial number here
            binding.connectionStateTextView.text = name
            Log.d("YourClass", "Serial Number: $name")
        }

        binding.fab.setOnClickListener { view ->
           // binding.tags.text = rfid.start().toString()
            val permissions = drinker.startSDK(this, "caea1ef90e38464eac98c5d6d37439")

            Log.d("MainActivity", "permissions>>> $permissions")
        }
        binding.connectNearestButtonId.setOnClickListener { view ->
           // binding.tags.text = rfid.start().toString()
            drinker.requestAllPermissions(this@MainActivity){
                Log.d("MainActivity", "permissions>>> $it")
                if (it){
                    drinker.connectNearestClicked()
                }
            }


        }
        binding.startBlowProcessButtonId.setOnClickListener { view ->
           // binding.tags.text = rfid.start().toString()
            val permissions = drinker.startBlowProcessClicked()

        }
        binding.getBatteryLevel.setOnClickListener { view ->
           // binding.tags.text = rfid.start().toString()


        }
        binding.getSerialNumberButtonId.setOnClickListener { view ->
           // binding.tags.text = rfid.start().toString()
            val permissions = drinker.getSerialNumberClicked()

        }
        binding.getUseCountButtonId.setOnClickListener { view ->
           // binding.tags.text = rfid.start().toString()
            val permissions = drinker.getUseCount()
            Log.d("MainActivity", "permissions>>> $permissions")
        }
        binding.disconnectButtonId.setOnClickListener { view ->
           // binding.tags.text = rfid.start().toString()
            val permissions = drinker.disconnectClicked()
            Log.d("MainActivity", "permissions>>> $permissions")
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == 139 || keyCode == 280 || keyCode == 293) {
            if (event!!.repeatCount == 0) {
                //binding.connectionStateTextView.text = rfid.start().toString()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}