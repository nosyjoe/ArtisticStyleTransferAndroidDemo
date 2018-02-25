package de.philippengel.artisticstyletransferdemo

import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), GenericCallback<Bitmap?> {

    private var uri: Uri? = null
    private var stylizeTask: StylizeTask? = null
    private var sourceBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        takePhotoButton.setOnClickListener {
            launchImageCapture()
        }
        stylizeButton.setOnClickListener {
            setLoading(true)
            val task = StylizeTask(assets, this)
            stylizeTask = task
            task.execute(sourceBitmap)
        }
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
            val captureUri = uri ?: return
            loadAndCrop(captureUri)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun loadAndCrop(captureUri: Uri) {
        val bitmap = loadImageFromUri(captureUri)!!
        sourceBitmap = pepareBitmap(bitmap)
        image.setImageBitmap(sourceBitmap)
    }

    /**
     * Scale & crop to 500x500
     */
    private fun pepareBitmap(bitmap: Bitmap): Bitmap? {
        val scaledHeight: Int
        val scaledWidth: Int
        if (bitmap.width > bitmap.height) {
            scaledHeight = 500
            scaledWidth = bitmap.width / (bitmap.height / 500)
        } else {
            scaledWidth = 500
            scaledHeight = bitmap.height / (bitmap.width / 500)
        }
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, false)
        val croppedBitmap = Bitmap.createBitmap(scaledBitmap, (scaledWidth - 500) / 2, (scaledHeight - 500) / 2, 500, 500)
        return croppedBitmap
    }

    private fun loadImageFromUri(captureUri: Uri): Bitmap? {
        // add exception handling, offload to worker-thread
        val parcelFileDesc = contentResolver.openFileDescriptor(captureUri, "r")
        val fd = parcelFileDesc.fileDescriptor
        val bitmap = BitmapFactory.decodeFileDescriptor(fd)
        parcelFileDesc.close()

        return bitmap
    }

    override fun <T> done(op: T, value: Bitmap?) {
        when (op) {
            stylizeTask -> {
                stylizeTask = null
                image.setImageBitmap(value)
                setLoading(false)
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        container_spinner.visibility = if (loading) View.VISIBLE else View.GONE
    }

    companion object {
        const val REQUEST_CODE_CAPTURE = 1
    }
}

interface GenericCallback<in K> {

    fun <T> done(op: T, value: K)

}
