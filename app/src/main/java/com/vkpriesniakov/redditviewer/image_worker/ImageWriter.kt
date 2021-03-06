package com.vkpriesniakov.redditviewer.image_worker

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.vkpriesniakov.redditviewer.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


private const val IMAGE_DIRECTORY = "RedditViewer"

class ImageWriter(private val context: Context) {

    private fun createDirectory() {
        val directory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                File.separator + IMAGE_DIRECTORY
            )
        } else {
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                File.separator + IMAGE_DIRECTORY
            )
        }

        if (!directory.exists()) {
            directory.mkdirs()
        }
    }

    fun saveImage(imageUrl: String?) {

        if (Utils.nowIsSawing) {
            return
        } else {
            Utils.nowIsSawing = true
        }


        Glide.with(context as Context)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                    GlobalScope.launch {
                        createDirectory()
                        writeImage(resource)
                        Utils.nowIsSawing = false
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private fun writeImage(resource: Bitmap) {
        val contentValues = initiateContentValues("path")
        val contentResolver = (context).contentResolver

        if (Build.VERSION.SDK_INT >= 29) {
            writeWithUri(contentResolver, contentValues, resource)
        } else {
            writeWithFile(resource)
        }
    }

    private fun initiateContentValues(path: String): ContentValues {

        val relativeLocation =
            Environment.DIRECTORY_PICTURES + File.separator + IMAGE_DIRECTORY

        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            put(MediaStore.MediaColumns.DISPLAY_NAME, IMAGE_DIRECTORY);

            if (Build.VERSION.SDK_INT >= 29) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            } else {
//                put(MediaStore.Images.Media.DATA, path)
                // .DATA is deprecated in API 29
                put(MediaStore.Images.Media.DATA, relativeLocation);
            }
        }
        return contentValues
    }

    private fun writeWithFile(resource: Bitmap) {
        val directory = File(
            Environment.getExternalStorageDirectory()
                .toString() + File.separator + Environment.DIRECTORY_PICTURES + File.separator + IMAGE_DIRECTORY
        )
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val fileName = System.currentTimeMillis().toString() + ".png"
        val file = File(directory, fileName)

        FileOutputStream(file).use {
            resource.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        val contentValues = initiateContentValues(file.absolutePath)
        context.contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }


    //TODO: Did not checked workability on Android > 29

    private fun writeWithUri(
        contentResolver: ContentResolver,
        contentValues: ContentValues,
        resource: Bitmap
    ) {

        var uri: Uri? = null
        try {
            uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            uri?.let { uri ->

                val stream = contentResolver.openOutputStream(uri)
                stream?.let { stream ->
                    if (!resource.compress(
                            Bitmap.CompressFormat.JPEG,
                            100,
                            stream
                        )
                    ) {
                        throw IOException("Failed to save bitmap.")
                    }
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)

                    contentValues.clear()
                    stream.close()
                } ?: throw IOException("Failed to get output stream.")
            } ?: throw IOException("Failed to create new MediaStore record")
        } catch (e: IOException) {
            e.printStackTrace()
            uri?.let {
                contentResolver.delete(it, null, null)
                contentValues.clear()
            }
            throw IOException(e)
        }
    }


}