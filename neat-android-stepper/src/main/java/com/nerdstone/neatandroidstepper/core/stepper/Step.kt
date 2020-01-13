package com.nerdstone.neatandroidstepper.core.stepper

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nerdstone.neatandroidstepper.core.domain.StepActions
import com.nerdstone.neatandroidstepper.core.model.StepModel

private const val STEP_TITLE = "step_title"
private const val STEP_SUB_TITLE = "step_subtitle"

abstract class Step : Fragment, StepActions {

   final override var stepModel = StepModel.Builder().build()

    constructor()
    constructor(stepModel: StepModel) {
        this.stepModel = stepModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments==null)
            arguments =  Bundle()

        arguments!!.putString(STEP_TITLE, stepModel.title.toString())
        arguments!!.putString(STEP_SUB_TITLE, stepModel.subTitle.toString())

    }
}