package de.philippengel.artisticstyletransferdemo

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.os.AsyncTask
import android.widget.ImageView
import org.tensorflow.contrib.android.TensorFlowInferenceInterface


/**
 *
 * @author Philipp Engel <philipp@filzip.com>
 */
class StylizeTask(private val assets: AssetManager, private val callback: GenericCallback<Bitmap?>) : AsyncTask<Bitmap, Void, Bitmap>() {

    private var intValues: IntArray = IntArray(500 * 500)
//    private var floatValues: FloatArray = FloatArray(500 * 500 * 3)

    override fun doInBackground(vararg params: Bitmap?): Bitmap {
        val sourceBitmap = params[0]!!
        val inputNode = "img_placeholder"
        val outputNode = "preds"
        val modelFile = "file:///android_asset/style.pb"

        // size: 3 * width * height
        val floatValues = toFloatArray(sourceBitmap)

        val inf = TensorFlowInferenceInterface(assets, modelFile)

        // Copy the input data into TensorFlow.
        inf.feed(inputNode, floatValues, 500, 500, 3L)
        // Execute the output node's dependency sub-graph.
        inf.run(arrayOf<String>(outputNode), false)
        // Copy the data from TensorFlow back into our array.
        inf.fetch(outputNode, floatValues)

        return toBitmap(floatValues)
    }

    private fun toBitmap(floatValues: FloatArray): Bitmap {
        for (i in intValues.indices) {
            intValues[i] = (-0x1000000
                    or (Math.min(Math.max(floatValues[i * 3], 0.0f), 255.0f).toInt() shl 16)
                    or (Math.min(Math.max(floatValues[i * 3 + 1], 0.0f), 255.0f).toInt() shl 8)
                    or Math.min(Math.max(floatValues[i * 3 + 2], 0.0f), 255.0f).toInt())
        }

        val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        return bitmap
    }

    private fun toFloatArray(bitmap: Bitmap): FloatArray {
        var floatValues: FloatArray = FloatArray(500 * 500 * 3)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for (i in intValues.indices) {
            val value = intValues[i]
            floatValues[i * 3] = (value shr 16 and 0xFF) / 255.0f
            floatValues[i * 3 + 1] = (value shr 8 and 0xFF) / 255.0f
            floatValues[i * 3 + 2] = (value and 0xFF) / 255.0f
        }

        return floatValues
    }

    override fun onPostExecute(result: Bitmap?) {
        callback.done(this, result)
    }
}
