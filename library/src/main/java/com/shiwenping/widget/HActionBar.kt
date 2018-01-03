package com.shiwenping.widget


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import java.lang.Exception


/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-4-11 11:16
 */

open class HActionBar : RelativeLayout {

    private var contentView: RelativeLayout? = null
    var actionBarView: View? = null
        private set
    private var leftView: RelativeLayout? = null
    private var rightView: RelativeLayout? = null

    private var title: String? = null
    private var backText: String? = null
    private var isBackDissmis = true
    private var isShowMore = false
    private var isShowTitle = true
    private var isShowBack = true
    private var backIcon: Drawable? = null
    private var moreDrawable: Drawable? = null
    private var moreText: String? = null
    private var backIconGravity: Int = 0
    private var backTextColor: Int = 0
    private var titleTextColor: Int = 0
    private var backIconDrawablePadding: Float = 0.toFloat()
    private var title_text_size: Int = 0
    private var lineColor = Color.parseColor("#e5e5e5")
    private var moreTextColor = Color.parseColor("#333333")
    private var lineHeight = 1
    /**
     * 线条View
     */
    /**
     * 获取line   View对象
     *
     * @return 返回lineView对象
     */
    var lineView: View? = null
        private set

    val backView: TextView
        get() = leftView!!.findViewById<View>(R.id.back) as TextView

    private val titleView: TextView
        get() = contentView!!.findViewById<View>(R.id.title) as TextView

    val more: TextView
        get() = rightView!!.findViewById<View>(R.id.more) as TextView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttr(context, attrs)
        init(context)
    }

    private fun initAttr(context: Context, attrs: AttributeSet) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.HActionBar)
        title = array.getString(R.styleable.HActionBar_title)
        backText = array.getString(R.styleable.HActionBar_back_text)
        moreTextColor = array.getColor(R.styleable.HActionBar_more_text_color, Color.parseColor("#333333"))
        isBackDissmis = array.getBoolean(R.styleable.HActionBar_back_is_dissmis, true)
        isShowBack = array.getBoolean(R.styleable.HActionBar_isShowBack, true)
        isShowMore = array.getBoolean(R.styleable.HActionBar_isShowMore, false)
        moreDrawable = array.getDrawable(R.styleable.HActionBar_more_icon)
        isShowTitle = array.getBoolean(R.styleable.HActionBar_isShowTitle, true)
        title_text_size = array.getDimensionPixelOffset(R.styleable.HActionBar_title_text_size, 16)
        moreText = array.getString(R.styleable.HActionBar_more_text_str)
        backIcon = array.getDrawable(R.styleable.HActionBar_back_icon)
        backIconGravity = array.getInt(R.styleable.HActionBar_back_icon_gravity, 1)
        backIconDrawablePadding = array.getDimension(R.styleable.HActionBar_back_icon_drawablePadding, 8f)
        titleTextColor = array.getColor(R.styleable.HActionBar_title_text_color, ContextCompat.getColor(getContext(), R.color.white))
        backTextColor = array.getColor(R.styleable.HActionBar_back_text_color, ContextCompat.getColor(getContext(), R.color.white))
        lineColor = array.getColor(R.styleable.HActionBar_line_color, Color.parseColor("#e5e5e5"))
        lineHeight = array.getDimensionPixelOffset(R.styleable.HActionBar_line_height, 1)
        array.recycle()
    }

    fun setTitleTextColor(titleTextColor: Int) {
        this.titleTextColor = titleTextColor
        titleView.setTextColor(this.titleTextColor)
    }

    fun setBackTextColor(backTextColor: Int) {
        this.backTextColor = backTextColor
        backView.setTextColor(this.backTextColor)
    }

    fun setBackIconDrawablePadding(backIconDrawablePadding: Float) {
        this.backIconDrawablePadding = backIconDrawablePadding
        backView.compoundDrawablePadding = this.backIconDrawablePadding.toInt()

    }

    fun setBackIcon(backIcona: Drawable) {
        this.backIcon = backIcona
        backView.setCompoundDrawablesWithIntrinsicBounds(
                if (backIconGravity == 1) backIcon else null,
                if (backIconGravity == 3) backIcon else null,
                if (backIconGravity == 2) backIcon else null,
                if (backIconGravity == 4) backIcon else null
        )
    }

    fun setBackIconGravity(backIconGravity: Int) {
        this.backIconGravity = backIconGravity
        backView.setCompoundDrawablesWithIntrinsicBounds(
                if (backIconGravity == 1) backIcon else null,
                if (backIconGravity == 3) backIcon else null,
                if (backIconGravity == 2) backIcon else null,
                if (backIconGravity == 4) backIcon else null
        )
    }

    @TargetApi(14)
    private fun getActionBarHeight(context: Context): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            val tv = TypedValue()
            context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)
            result = context.resources.getDimensionPixelSize(tv.resourceId)
        }
        return result
    }

    @SuppressLint("PrivateResource")
    private fun init(context: Context) {
        val inflate = LayoutInflater.from(context)
        actionBarView = inflate.inflate(R.layout.action_bar_layout, this, false)
        contentView = actionBarView!!.findViewById<View>(R.id.contentView) as RelativeLayout
        leftView = actionBarView!!.findViewById<View>(R.id.leftView) as RelativeLayout
        rightView = actionBarView!!.findViewById<View>(R.id.rightView) as RelativeLayout
        lineView = actionBarView!!.findViewById(R.id.lineView)
        if (isBackDissmis) {
            backView.setOnClickListener { (context as Activity).finish() }
        }
        val tv = TypedValue()
        context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)
        var r = 0
        try {
            r = context.resources.getDimensionPixelSize(tv.resourceId)
        } catch (e: Exception) {
            r = resources.getDimension(R.dimen.abc_action_bar_default_height_material).toInt()
        }

        addView(actionBarView, RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, r))
        setTitle(title)
        setBackText(backText)
        if (backIcon == null) {
            backIcon = ContextCompat.getDrawable(getContext(), R.drawable.btn_back)
        }
        backView.setCompoundDrawablesWithIntrinsicBounds(
                if (backIconGravity == 1) backIcon else null,
                if (backIconGravity == 3) backIcon else null,
                if (backIconGravity == 2) backIcon else null,
                if (backIconGravity == 4) backIcon else null
        )
        backView.compoundDrawablePadding = backIconDrawablePadding.toInt()
        more.visibility = if (isShowMore) View.VISIBLE else View.INVISIBLE
        more.text = moreText
        more.setTextColor(moreTextColor)
        more.setCompoundDrawablesWithIntrinsicBounds(moreDrawable, null, null, null)
        titleView.setTextColor(titleTextColor)
        titleView.text = title
        titleView.textSize = title_text_size.toFloat()
        backView.setTextColor(backTextColor)
        titleView.visibility = if (isShowTitle) View.VISIBLE else View.INVISIBLE
        backView.visibility = if (isShowBack) View.VISIBLE else View.INVISIBLE
        lineView!!.setBackgroundColor(lineColor)
        lineView!!.layoutParams.height = lineHeight
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr(context, attrs)
        init(context)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val customTitleView = findViewById<View>(R.id.custom_title_layout)
        if (customTitleView != null) {
            removeView(customTitleView)
            contentView!!.addView(customTitleView)
        }
        val moreLayout = findViewById<View>(R.id.custom_more_layout)
        if (moreLayout != null) {
            removeView(moreLayout)
            rightView!!.addView(moreLayout)
        }
    }

    fun setTitle(title: String?) {
        if (contentView != null) {
            titleView.text = title
        }
    }


    fun setBackText(backStr: String?) {
        backView.text = backStr
    }

    fun getContentView(): ViewGroup? {
        return contentView
    }

    fun getLeftView(): ViewGroup? {
        return leftView
    }

    fun getRightView(): ViewGroup? {
        return rightView
    }

}
