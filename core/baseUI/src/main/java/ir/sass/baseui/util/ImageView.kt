package ir.sass.baseui.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import ir.sass.baseui.R
import ir.sass.baseui.databinding.LottieImageViewBinding

class LottieImageView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context,attrs,defStyleAttr){
    val binding : LottieImageViewBinding
    init {
        binding = LottieImageViewBinding.inflate(
            LayoutInflater.from(context),this,true
        )
    }
}

@BindingAdapter("app:url")
fun setImageUrl(img: LottieImageView, url: String?) {

    url?.let {
        img.binding.lottie.playAnimation()

        img.setBackgroundResource(R.drawable.back_img)

        Glide.with(img.context)
            .load(url)
            .centerCrop()
            .addListener(imageLoadingListener(img.binding.lottie))
            .into(img.binding.img)
    }
}

@BindingAdapter("app:image")
fun ImageView.setImageLocal(resource: Drawable) {
    this.setImageDrawable(resource)
}

fun imageLoadingListener(pendingImage: LottieAnimationView): RequestListener<Drawable?> {
    return object : RequestListener<Drawable?> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable?>?, isFirstResource: Boolean): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<Drawable?>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            pendingImage.pauseAnimation()
            pendingImage.visibility = View.GONE
            return false
        }
    }
}