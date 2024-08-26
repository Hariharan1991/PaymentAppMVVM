package com.example.paymentapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.paymentapp.databinding.ActivityQrcodeScannerBinding
import com.example.paymentapp.helper.DBManager
import com.example.paymentapp.viewmodel.QRCodeViewModel
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class QRCodeScannerActivity : AppCompatActivity() {

    lateinit var binding:ActivityQrcodeScannerBinding
    lateinit var barcodeDetector: BarcodeDetector
    lateinit var cameraSource: CameraSource
    lateinit var qrCodeViewModel: QRCodeViewModel
    lateinit var dbManager: DBManager
    private var contactName:String = ""
    private var amount:String = ""
    private var isScannedStatus = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        contactName = bundle!!.getString("ContactName")!!
        amount = bundle.getString("Amount")!!
        qrCodeViewModel = ViewModelProviders.of(this)[QRCodeViewModel::class.java]
        dbManager = DBManager(this)
        dbManager.initDB()

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),102)
        }

        binding.btnClose.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    if (isScannedStatus)
                        qrCodeViewModel.insertTransactionDetails(dbManager, contactName, amount)
                }
                withContext(Dispatchers.Main){
                    if (isScannedStatus)
                        Toast.makeText(
                            applicationContext,
                            "Transaction Added Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()

        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {

            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    binding.tvScanStatus.post {
                        isScannedStatus = true
                        binding.tvScanStatus.text = barcodes.valueAt(0).displayValue
                    }
                }
            }
        })



        binding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return
                    }
                    cameraSource.start(binding.surfaceView.holder);
                }catch (ex:Exception){
                    ex.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int) {}


            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })
    }


    override fun onPause() {
        super.onPause()

        cameraSource.release()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}