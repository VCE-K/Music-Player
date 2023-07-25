package cn.vce.musicplayer.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InsetsConstraintLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int): ConstraintLayout(context, attrs, defStyleAttr) {

    // 如果需要在代码中创建该视图，可以提供一个带有Context参数的构造函数
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        ViewCompat.setOnApplyWindowInsetsListener(this ) { _, insets ->
            //setWindowInsets(insets)
            insets.consumeSystemWindowInsets()
        }
    }

    private fun setWindowInsets(insets: WindowInsetsCompat) {
        // Now dispatch them to our children
        var insets = insets
        var i = 0
        val z = childCount
        while (i < z) {
            val child = getChildAt(i)
            insets = ViewCompat.dispatchApplyWindowInsets(child, insets)
            if (insets.isConsumed) {
                break
            }
            i++
        }
    }
}