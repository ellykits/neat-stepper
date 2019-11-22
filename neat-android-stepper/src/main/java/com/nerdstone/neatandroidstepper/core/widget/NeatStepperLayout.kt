package com.nerdstone.neatandroidstepper.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.nerdstone.neatandroidstepper.core.R
import com.nerdstone.neatandroidstepper.core.domain.StepperActions
import com.nerdstone.neatandroidstepper.core.stepper.DepthPageTransformer
import com.nerdstone.neatandroidstepper.core.stepper.StepperPagerAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.nerdstone.neatandroidstepper.core.model.StepperModel
import com.nerdstone.neatandroidstepper.core.model.StepperModel.IndicatorType.*
import com.nerdstone.neatandroidstepper.core.stepper.Step
import com.nerdstone.neatandroidstepper.core.utils.TintUtil

class NeatStepperLayout : LinearLayout {

    private lateinit var stepperPagerAdapter: StepperPagerAdapter
    private lateinit var stepperViewPager: ViewPager
    private lateinit var stepperToolbar: Toolbar
    private lateinit var bottomNavigationLayout: ConstraintLayout
    private lateinit var startButton: Button
    private lateinit var endButton: Button
    private lateinit var progressBarIndicator: ProgressBar
    private lateinit var dotIndicator: DotIndicator
    private lateinit var titleTextView: TextView
    private lateinit var subTitleTextView: TextView
    private var currentStepIndex = 0
    private var numberOfSteps = 1
    var stepperModel: StepperModel = StepperModel.Builder().build()
    var stepperActions: StepperActions? = null
    lateinit var completeButton: ImageButton
    lateinit var exitButton: ImageButton

    constructor(context: Context?) : super(context, null) {
        initViews()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initViews(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initViews(attrs)
    }

    private fun initViews(attrs: AttributeSet? = null) {

        if (attrs != null) setupViewAttributes(attrs)

        bindViews()

        stepperViewPager.apply {
            setPageTransformer(true, DepthPageTransformer())
            addOnPageChangeListener(object : OnPageChangeListener {

                override fun onPageSelected(position: Int) {
                    currentStepIndex = position
                    dotIndicator.setCurrentDot(position, true)
                    applyStepViewProperties()
                    setNextButtonDrawableEnd()
                    showOrHidePreviousButton()
                    updateProgress()
                    updateToolbar()
                }

                override fun onPageScrolled(
                    position: Int, positionOffset: Float, positionOffsetPixels: Int
                ) {
                    //Implementation pending
                }

                override fun onPageScrollStateChanged(state: Int) {
                    //Implementation pending
                }
            })
        }

        startButton.apply {
            visibility = View.INVISIBLE
            setOnClickListener {
                stepperActions?.onButtonPreviousClick(getCurrentStep())
                stepperViewPager.currentItem--
            }
        }

        endButton.apply {
            setOnClickListener {
                when {
                    currentStepIndex != numberOfSteps - 1 && currentStepIndex >= 0 -> {
                        stepperActions?.onButtonNextClick(getCurrentStep())
                        stepperViewPager.currentItem++
                    }
                    currentStepIndex == numberOfSteps - 1 ->
                        stepperActions?.onStepComplete(getCurrentStep())
                }
            }
        }

        exitButton.apply {
            setOnClickListener {
                if (currentStepIndex >= 0) {
                    stepperViewPager.currentItem--
                    if (currentStepIndex == 0) {
                        stepperActions?.onExitStepper()
                    }
                }
                if (currentStepIndex > 0) {
                    currentStepIndex--
                }
            }
        }

        completeButton.apply {
            setOnClickListener {
                stepperActions?.onCompleteStepper()
            }
        }

        updateProgress()
    }

    private fun setNextButtonDrawableEnd() {
        if (currentStepIndex == numberOfSteps - 1) {
            endButton.apply {
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                val completeStepperLabel =
                    getCurrentStep().stepModel.completeStepperLabel
                        ?: stepperModel.completeStepperLabel
                        ?: context.getString(R.string.complete)
                text = completeStepperLabel
                enableCompleteButton(true)
            }
        } else {
            val nextButtonDrawableResId =
                getCurrentStep().stepModel.nextButtonDrawableResId
                    ?: stepperModel.nextButtonDrawableResId
            endButton.apply {
                setCompoundDrawablesWithIntrinsicBounds(
                    null, null,
                    ContextCompat.getDrawable(context, nextButtonDrawableResId!!), null
                )
                val nextButtonLabel = getCurrentStep().stepModel.nextButtonLabel
                    ?: stepperModel.nextButtonLabel ?: context.getString(R.string.next)
                text = nextButtonLabel
            }
            enableCompleteButton(false)
        }
    }

    /**
     * Obtain custom xml attributes passed for the stepper view
     */
    private fun setupViewAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.NeatStepperLayout, 0, 0
        )
        try {
            stepperModel.also {
                it.dotIndicatorColors = Pair(
                    typedArray.getResourceId(
                        R.styleable.NeatStepperLayout_dot_indicator_default_color,
                        R.color.dotIndicatorDefault
                    ),
                    typedArray.getResourceId(
                        R.styleable.NeatStepperLayout_dot_indicator_selected_color,
                        R.color.dotIndicatorSelected
                    )
                )
                it.indicatorType =
                    if (typedArray.getInt(R.styleable.NeatStepperLayout_indicator_type, 1) == 0)
                        DOT_INDICATOR else PROGRESS_BAR_INDICATOR

                it.nextButtonDrawableResId =
                    typedArray.getResourceId(
                        R.styleable.NeatStepperLayout_next_button_drawable_end,
                        R.drawable.ic_chevron_right
                    )
                it.previousButtonDrawableResId =
                    typedArray.getResourceId(
                        R.styleable.NeatStepperLayout_previous_button_drawable_start,
                        R.drawable.ic_chevron_left
                    )
                it.toolbarColorResId =
                    typedArray.getResourceId(
                        R.styleable.NeatStepperLayout_toolbar_color,
                        R.color.stepperToolbar
                    )
                it.bottomNavigationColorResId =
                    typedArray.getResourceId(
                        R.styleable.NeatStepperLayout_bottom_navigation_background_color,
                        android.R.color.transparent
                    )
                it.exitButtonDrawableResId =
                    typedArray.getResourceId(
                        R.styleable.NeatStepperLayout_exit_button_drawable,
                        R.drawable.ic_clear
                    )
                it.completeButtonDrawableResId =
                    typedArray.getResourceId(
                        R.styleable.NeatStepperLayout_complete_button_drawable,
                        R.drawable.ic_check
                    )

                it.showToolbar = typedArray.getBoolean(
                    R.styleable.NeatStepperLayout_show_toolbar, true
                )

                it.showProgressIndicator = typedArray.getBoolean(
                    R.styleable.NeatStepperLayout_show_progress_indicator, true
                )

                it.showBottomNavigationButtons = typedArray.getBoolean(
                    R.styleable.NeatStepperLayout_show_bottom_navigation_buttons, true
                )

                it.nextButtonLabel = typedArray.getString(
                    R.styleable.NeatStepperLayout_next_button_label
                )

                it.previousButtonLabel = typedArray.getString(
                    R.styleable.NeatStepperLayout_previous_button_label
                )
                it.completeStepperLabel = typedArray.getString(
                    R.styleable.NeatStepperLayout_complete_button_label
                )
            }
        } finally {
            typedArray.recycle()
        }
    }

    private fun bindViews() {
        val contextThemeWrapper = ContextThemeWrapper(context, context.theme)
        LayoutInflater.from(contextThemeWrapper).inflate(R.layout.stepper_main_layout, this, true)
        progressBarIndicator = findViewById(R.id.progressBar)
        dotIndicator = findViewById(R.id.dotIndicator)
        titleTextView = findViewById(R.id.titleTextView)
        subTitleTextView = findViewById(R.id.subTitleTextView)
        bottomNavigationLayout = findViewById(R.id.bottomNavigationLayout)
        stepperToolbar = findViewById(R.id.stepperToolBar)
        completeButton = findViewById(R.id.completeButton)
        exitButton = findViewById(R.id.exitButton)
        endButton = findViewById(R.id.endButton)
        startButton = findViewById(R.id.startButton)
        stepperViewPager = findViewById(R.id.stepperViewPager)
    }

    private fun showOrHidePreviousButton() {
        when {
            currentStepIndex > 0 && getCurrentStep().stepModel.isPreviousButtonVisible!!
            -> startButton.visibility = View.VISIBLE
            currentStepIndex == 0 -> startButton.visibility = View.INVISIBLE
        }
    }

    fun setUpViewWithAdapter(stepperPagerAdapter: StepperPagerAdapter) {
        this.stepperPagerAdapter = stepperPagerAdapter
        if (this::stepperViewPager.isInitialized) {
            stepperViewPager.adapter = this.stepperPagerAdapter
            progressBarIndicator.max = this.stepperPagerAdapter.count
            numberOfSteps = this.stepperPagerAdapter.count
            if (numberOfSteps == 1) {
                bottomNavigationLayout.visibility = GONE
            }
            val bottomNavigationColor =
                getCurrentStep().stepModel.bottomNavigationColorResId ?: stepperModel.bottomNavigationColorResId
            updateBottomNavigationBackgroundColor(bottomNavigationColor!!)
            setupDotIndicatorView()
            updateToolbar()
            applyStepperViewProperties()
        }
    }

    private fun setupDotIndicatorView() {
        dotIndicator.apply {
            addDots(numberOfSteps)
            stepperModel.dotIndicatorColors?.also { pair ->
                this.indicatorColorDefault = ContextCompat.getColor(context, pair.first)
                this.indicatorColorSelected = ContextCompat.getColor(context, pair.second)
            }
            setCurrentDot(0, false)
        }
    }

    /***
     * Update the title and And toolbar background color
     */
    private fun updateToolbar() {
        val toolbarColorResId =
            getCurrentStep().stepModel.toolbarColorResId ?: stepperModel.toolbarColorResId
        stepperToolbar.apply {
            setBackgroundResource(toolbarColorResId!!)
        }

        val step = getCurrentStep()
        titleTextView.text = step.stepModel.title
        subTitleTextView.apply {
            if (step.stepModel.subTitle != null) {
                visibility = View.VISIBLE
                text = step.stepModel.subTitle
            } else {
                visibility = View.GONE
                text = ""
            }
        }
    }

    private fun updateProgress() {
        progressBarIndicator.progress = currentStepIndex + 1
    }

    private fun getCurrentStep(): Step {
        return stepperPagerAdapter.getItem(stepperViewPager.currentItem) as Step
    }

    private fun applyStepperViewProperties() {
        stepperModel.also {
            bottomNavigationLayout.apply {
                visibility = toggleVisibility(it.showBottomNavigationButtons!! && numberOfSteps > 1)
            }
            stepperToolbar.apply {
                setBackgroundResource(it.toolbarColorResId!!)
                visibility = toggleVisibility(it.showToolbar!!)
            }
            exitButton.apply {
                setImageDrawable(ContextCompat.getDrawable(context, it.exitButtonDrawableResId!!))
            }
            completeButton.apply {
                val drawable = ContextCompat.getDrawable(context, it.completeButtonDrawableResId!!)
                setImageDrawable(drawable)
                when {
                    numberOfSteps > 1 -> enableCompleteButton(false)
                    numberOfSteps == 1 -> enableCompleteButton(true)
                }
            }
            startButton.apply {
                text = stepperModel.previousButtonLabel
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(context, stepperModel.previousButtonDrawableResId!!),
                    null, null, null
                )
            }
            progressBarIndicator.apply {
                visibility = toggleVisibility(stepperModel.showProgressIndicator!!)
            }

            chooseIndicatorType(it.indicatorType!!)
            enableCompleteButton(numberOfSteps == 1)
            updateBottomNavigationBackgroundColor(stepperModel.bottomNavigationColorResId!!)
            setNextButtonDrawableEnd()
        }
    }

    private fun updateBottomNavigationBackgroundColor(@ColorRes colorRes: Int) {
        if (colorRes != R.color.colorWhite && colorRes != android.R.color.transparent) {
            bottomNavigationLayout.apply {
                setBackgroundResource(colorRes)
                endButton.apply {
                    setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                    TintUtil.tintDrawable(
                        this.compoundDrawables[2],
                        ContextCompat.getColor(context, R.color.colorWhite)
                    )
                }
                startButton.apply {
                    setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                    TintUtil.tintDrawable(
                        startButton.compoundDrawables[0],
                        ContextCompat.getColor(context, R.color.colorWhite)
                    )
                }
            }
        }
    }

    private fun enableCompleteButton(enabled: Boolean) {
        completeButton.apply {
            isEnabled = enabled
            TintUtil.tintDrawable(
                this.drawable, if (enabled)
                    ContextCompat.getColor(context, R.color.colorWhite)
                else
                    ContextCompat.getColor(context, R.color.colorDarkGrey)
            )
        }
    }

    private fun chooseIndicatorType(indicatorType: StepperModel.IndicatorType) {
        if (stepperModel.showProgressIndicator!!) {
            when (indicatorType) {
                DOT_INDICATOR -> {
                    dotIndicator.visibility = View.VISIBLE
                    progressBarIndicator.visibility = View.GONE
                }
                PROGRESS_BAR_INDICATOR -> {
                    dotIndicator.visibility = View.GONE
                    progressBarIndicator.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun applyStepViewProperties() {
        getCurrentStep().stepModel.also {
            val nextButtonLabel = it.previousButtonLabel ?: stepperModel.nextButtonLabel
            ?: context.getString(R.string.next)
            val nextButtonDrawableResId =
                it.nextButtonDrawableResId ?: stepperModel.nextButtonDrawableResId
            endButton.apply {
                text = nextButtonLabel
                visibility = toggleVisibility(it.isNextButtonVisible!!, VisibilityState.INVISIBLE)
                setCompoundDrawablesWithIntrinsicBounds(
                    null, null,
                    ContextCompat.getDrawable(context, nextButtonDrawableResId!!), null
                )
            }

            val previousButtonLabel = it.previousButtonLabel ?: stepperModel.previousButtonLabel
            ?: context.getString(R.string.back)

            val previousButtonDrawableResId =
                it.previousButtonDrawableResId ?: stepperModel.previousButtonDrawableResId
            startButton.apply {
                text = previousButtonLabel
                visibility =
                    toggleVisibility(it.isPreviousButtonVisible!!, VisibilityState.INVISIBLE)
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(context, previousButtonDrawableResId!!), null,
                    null, null
                )
            }
            val bottomNavigationColor =
                it.bottomNavigationColorResId ?: stepperModel.bottomNavigationColorResId
            updateBottomNavigationBackgroundColor(bottomNavigationColor!!)
        }
    }

    private fun toggleVisibility(
        visible: Boolean, visibilityState: VisibilityState = VisibilityState.GONE
    ): Int {
        return if (visible) View.VISIBLE else {
            when (visibilityState) {
                VisibilityState.GONE -> View.GONE
                else -> View.INVISIBLE
            }
        }
    }

    enum class VisibilityState {
        INVISIBLE,
        GONE
    }
}