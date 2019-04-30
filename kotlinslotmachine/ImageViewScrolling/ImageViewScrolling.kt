package com.example.kotlinslotmachine.ImageViewScrolling

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.kotlinslotmachine.R
import kotlinx.android.synthetic.main.image_view_scrolling.view.*

class ImageViewScrolling:FrameLayout {
    internal lateinit var eventEnd: IEventEnd

    internal var last_result=0
    internal var oldValue=0


    companion object {
        private val ANIMATION_DURATION=150
    }

    val value:Int
        get() = Integer.parseInt(nextImage.tag.toString())
    fun setEventEnd(eventEnd:IEventEnd)
    {
        this.eventEnd = eventEnd
    }

    constructor(context: Context):super(context){
        init(context)
    }

    constructor(context: Context,attrs:AttributeSet):super(context,attrs){
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.image_view_scrolling, this)

        nextImage.translationY = height.toFloat()

}

    fun setValueRandom(image:Int,num_rotate:Int)
    {
        currentImage.animate()
            .translationY((-height).toFloat())
            .setDuration(ANIMATION_DURATION.toLong()).start()

        nextImage.translationY = nextImage.height.toFloat()

        nextImage.animate().translationY(0f).setDuration(ANIMATION_DURATION.toLong())
            .setListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    setImage(currentImage,oldValue%6)
                    currentImage.translationY=0f
                    if (oldValue != num_rotate) // If still have rotate
                    {
                        setValueRandom(image,num_rotate)
                        oldValue++;
                    }
                    else
                    {
                        last_result = 0
                        oldValue = 0
                        setImage(nextImage,image)
                        eventEnd.eventEnd(image%6,num_rotate) // Because we have 6 images :
                    }
                }



                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }

            }).start()
    }

    private fun setImage(img: ImageView?, value: Int) {
            if (value == Util.bar)
                img!!.setImageResource(R.drawable.bar_done)
            else if (value == Util.lemon)
                img!!.setImageResource(R.drawable.lemon_done)
            else if (value == Util.orange)
                img!!.setImageResource(R.drawable.orange_done)
            else if (value == Util.seven)
                img!!.setImageResource(R.drawable.sevent_done)
            else if (value == Util.triple)
                img!!.setImageResource(R.drawable.triple_done)
            else if (value == Util.watermelon)
                img!!.setImageResource(R.drawable.waternelon_done)

        img!!.tag = value
        last_result = value
    }
}
