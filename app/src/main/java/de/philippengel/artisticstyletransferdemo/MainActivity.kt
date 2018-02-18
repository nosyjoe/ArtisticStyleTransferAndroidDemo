package de.philippengel.artisticstyletransferdemo

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun launchImageCapture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) is ComponentName) {
            val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val tempFile = File(externalFilesDir, "from_camera.jpg")

            uri = FileProvider.getUriForFile(this, packageName + ".fileprovider", tempFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri!!)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

            startActivityForResult(intent, REQUEST_CODE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CAPTURE) {

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        const val REQUEST_CODE_CAPTURE = 1
    }
}
