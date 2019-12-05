package com.nerdstone.neatandroidstepper.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nerdstone.neatandroidstepper.core.model.StepModel
import com.nerdstone.neatandroidstepper.core.stepper.Step
import com.nerdstone.neatandroidstepper.core.stepper.StepVerificationState

/**
 * A simple [Fragment] subclass.
 */
class StepOneFragment : Step {

    constructor()

    constructor(stepModel: StepModel) : super(stepModel)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step_one_fragment, container, false)
        val linearLayout = view.findViewById<LinearLayout>(R.id.fragmentLinearLayout)
        for (i in 1..400) {
            val text = TextView(context)
            text.setPadding(16, 16, 16, 16)
            text.text = "Current count $i"
            linearLayout.addView(text)
        }
        return view
    }

    override fun verifyStep(): StepVerificationState {
        TODO("not implemented")
    }

    override fun onSelected() {
        TODO("not implemented")
    }

    override fun onError(stepVerificationState: StepVerificationState) {
        TODO("not implemented")
    }
}

/**
 * A simple [Fragment] subclass.
 */
class StepTwoFragment : Step() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step_two_fragment, container, false)
    }

    override fun verifyStep(): StepVerificationState {
        TODO("not implemented")
    }

    override fun onSelected() {
        TODO("not implemented")
    }

    override fun onError(stepVerificationState: StepVerificationState) {
        TODO("not implemented")
    }

}
