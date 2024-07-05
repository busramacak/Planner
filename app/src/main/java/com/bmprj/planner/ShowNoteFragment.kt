package com.bmprj.planner

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.navigation.fragment.navArgs
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentShowNoteBinding
import com.bmprj.planner.databinding.ShareImageLayoutBinding
import com.bmprj.planner.utils.changeColor
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ShowNoteFragment : BaseFragment<FragmentShowNoteBinding>(R.layout.fragment_show_note) {

    private val bundle: ShowNoteFragmentArgs by navArgs()
    private val title: String by lazy { bundle.title }
    private val content: String by lazy { bundle.content }
    private val imageLayoutBinding by lazy {
        ShareImageLayoutBinding.inflate(
            LayoutInflater.from(
                requireContext()
            )
        )
    }

    override fun initView(view: View) {
        binding.imageViewLayout.title.text = title
        binding.imageViewLayout.content.text = content
        binding.changeBackgroundButton.setOnClickListener { changeBackgroundButtonClicked(it) }
        binding.shareButton.setOnClickListener { shareNoteAsImage() }
    }

    private fun changeBackgroundButtonClicked(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.background_menu, popup.menu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popup.setForceShowIcon(true)
        }
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.pink -> {
                    changeColor(
                        binding.imageViewLayout,
                        imageLayoutBinding,
                        R.color.pinkBack,
                        R.color.pink,
                        binding.shareButton
                    )
                }

                R.id.green -> {
                    changeColor(
                        binding.imageViewLayout,
                        imageLayoutBinding,
                        R.color.greenBack,
                        R.color.green,
                        binding.shareButton
                    )
                }

                R.id.blue -> {
                    changeColor(
                        binding.imageViewLayout,
                        imageLayoutBinding,
                        R.color.blueBack,
                        R.color.blue,
                        binding.shareButton
                    )
                }

                R.id.brown -> {
                    changeColor(
                        binding.imageViewLayout,
                        imageLayoutBinding,
                        R.color.brownBack,
                        R.color.brown,
                        binding.shareButton
                    )
                }
            }

            true
        }
        popup.show()

    }

    @SuppressLint("Range")
    private fun shareNoteAsImage() {
        val width = 800  // Set your desired width
        val height = ViewGroup.LayoutParams.WRAP_CONTENT

        imageLayoutBinding.title.text = title
        imageLayoutBinding.content.text = content

        imageLayoutBinding.root.layoutParams = ViewGroup.LayoutParams(width, height)

        val specWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val specHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED)
        imageLayoutBinding.root.measure(specWidth, specHeight)
        imageLayoutBinding.root.layout(
            0,
            0,
            imageLayoutBinding.root.measuredWidth,
            imageLayoutBinding.root.measuredHeight
        )

        val bitmap = Bitmap.createBitmap(
            imageLayoutBinding.root.measuredWidth,
            imageLayoutBinding.root.measuredHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        imageLayoutBinding.root.draw(canvas)
        saveBitmap(bitmap)
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val fileName = "my_note_image.png"
        val file = File(requireContext().getExternalFilesDir(null), fileName)
        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
            shareImage(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun shareImage(file: File) {
        val uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().packageName + ".provider",
            file
        )
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Share image"))
    }
}