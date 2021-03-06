package com.vkpriesniakov.redditviewer.image_worker

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.vkpriesniakov.redditviewer.R
import com.vkpriesniakov.redditviewer.utils.Utils
import org.koin.android.ext.android.inject


private const val URL_KEY = "imageURL"
private const val REQUEST_CODE: Int = 54


class ImageDialog : DialogFragment() {

    //    private var mPermissionAllowed = false
    private var savedUrl: String? = null
    private lateinit var myImage: ImageView
    private lateinit var downloadIcon: ImageView
    private lateinit var imagePermission: WriteImagePermission
    private val imageWriter: ImageWriter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            savedUrl = it.getString(URL_KEY)
        }
        setStyle(STYLE_NO_TITLE, R.style.Theme_RedditViewer)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.custom_image_dialog, container, false)

        myImage = view.findViewById(R.id.image_dialog_view)

        downloadIcon = view.findViewById<ImageView>(R.id.download_icon)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagePermission = WriteImagePermission(this)

        Glide.with(context as Context).load(savedUrl).into(myImage)

        downloadIcon.setOnClickListener {

            if (!Utils.nowIsSawing) {
                if (imagePermission.checkPermissionToSaveImage()) {
                    Toast.makeText(context, "Now saving", Toast.LENGTH_SHORT).show()
                    imageWriter.saveImage(savedUrl)
                    Toast.makeText(activity, "Image saved", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    companion object {
        fun newInstance(imageUrl: String) =
            ImageDialog().apply {
                arguments = Bundle().apply {
                    putString(URL_KEY, imageUrl)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_SWIPE_TO_DISMISS)
        dialog.setCanceledOnTouchOutside(true)
        return dialog

    }
}