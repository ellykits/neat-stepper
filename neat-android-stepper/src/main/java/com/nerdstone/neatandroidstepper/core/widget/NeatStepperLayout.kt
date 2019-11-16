package com.nerdstone.neatandroidstepper.core.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.nerdstone.neatandroidstepper.core.domain.StepperActions
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.view.ContextThemeWrapper
import androidx.viewpager.widget.ViewPager
import com.nerdstone.neatandroidstepper.core.R
import com.nerdstone.neatandroidstepper.core.stepper.StepperPagerAdapter


class NeatStepperLayout : LinearLayout, StepperActions {

    constructor(context: Context?) : super(context) {
        initViews()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initViews()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )


    var fragmentList = mutableListOf<Fragment>()

    val stepperPagerAdapter =
        StepperPagerAdapter(
            (context as FragmentActivity).supportFragmentManager,
            fragmentList
        )

    lateinit var stepperViewPager: ViewPager
    lateinit var startButton: Button
    lateinit var endButton: Button
    lateinit var progressBarIndicator: ProgressBar

    private fun initViews() {
        val contextThemeWrapper = ContextThemeWrapper(context, context.theme)
        LayoutInflater.from(contextThemeWrapper).inflate(R.layout.stepper_main_layout, this, true)
        stepperViewPager = findViewById(R.id.stepperViewPager)
        startButton = findViewById(R.id.startButton)
        endButton = findViewById(R.id.endButton)
        progressBarIndicator = findViewById(R.id.progressBar)
    }


    override fun onNextClick() {

    }

    override fun onBackClick() {
        TODO("not implemented")
    }

    override fun onComplete() {
        TODO("not implemented")
    }

}