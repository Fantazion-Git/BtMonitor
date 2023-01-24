package com.example.btmonitor

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


private var btAdapter : BluetoothAdapter?=null


class MainActivity : AppCompatActivity() {
    private lateinit var pLauncher : ActivityResultLauncher<String>
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerPermissionListener()
        checkPermissions()
        init()
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun checkPermissions(){
        when{
            ContextCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_CONNECT)
                    ==PackageManager.PERMISSION_GRANTED ->{
                        Toast.makeText(this,"BT granted",Toast.LENGTH_LONG).show()
                    }
            shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_CONNECT) ->{
                Toast.makeText(this,"Permission дал, а то достану ж",Toast.LENGTH_LONG).show()
            }
            else -> {
            pLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
            }
        }
    }
    private fun registerPermissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it) {
                Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show()
            }else{Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show()}
        }

    }

    private fun init(){
        val btManager= getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter=btManager.adapter
        getPairedDevices()

    }
    @SuppressLint("MissingPermission")
    private fun getPairedDevices(){
        val pairedDevices :Set<BluetoothDevice>? = btAdapter?.bondedDevices
        pairedDevices?.forEach {
            Log.d("MyLog","Name: ${it.name}")
        }
    }
}