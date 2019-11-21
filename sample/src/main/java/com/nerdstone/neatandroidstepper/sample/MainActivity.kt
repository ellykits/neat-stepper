package com.nerdstone.neatandroidstepper.sample

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.nerdstone.neatandroidstepper.core.domain.StepperActions
import com.nerdstone.neatandroidstepper.core.model.StepModel
import com.nerdstone.neatandroidstepper.core.model.StepperModel
import com.nerdstone.neatandroidstepper.core.stepper.Step
import com.nerdstone.neatandroidstepper.core.stepper.StepVerificationState
import com.nerdstone.neatandroidstepper.core.stepper.StepperPagerAdapter
import com.nerdstone.neatandroidstepper.core.widget.DotIndicator
import com.nerdstone.neatandroidstepper.core.widget.NeatStepperLayout

class MainActivity : FragmentActivity(), StepperActions {

    lateinit var neatStepperLayout: NeatStepperLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        neatStepperLayout = findViewById(R.id.neatStepperLayout)
        neatStepperLayout.stepperActions = this
        neatStepperLayout.setUpViewWithAdapter(
            StepperPagerAdapter(
                supportFragmentManager,
                mutableListOf(
                    StepOneFragment(StepModel.Builder().title("Profile").subTitle("Demographic Info").build()),
                    StepTwoFragment(StepModel.Builder().title("Profile").subTitle("Medications").build()),
                    StepOneFragment(StepModel.Builder().title("Profile").subTitle("Demographic Info").build()),
                    StepTwoFragment(StepModel.Builder().title("Profile").subTitle("Current Pregnancy").build()),
                    StepTwoFragment(StepModel.Builder().title("Profile").subTitle("Medical History").build()),
                    StepOneFragment(StepModel.Builder().title("Profile").subTitle("Obstetric Section").build())
                )
            )
        )
    }

    override fun onStepError(stepVerificationState: StepVerificationState) {
    }

    override fun onButtonNextClick(step: Step) {
    }

    override fun onButtonPreviousClick(step: Step) {
    }

    override fun onStepComplete(step: Step) {
        Toast.makeText(this, "Stepper completed", Toast.LENGTH_SHORT).show()
    }

    override fun onExitStepper() {
        val confirmCloseDialog = AlertDialog.Builder(this)
        confirmCloseDialog.apply {
            setTitle("Confirm close")
            setMessage("All the unsaved data will get lost if you quit")
            setPositiveButton("Exit") { _, _ -> finish() }
            setNegativeButton("Cancel") { _, _ -> return@setNegativeButton }
            create()
        }
        confirmCloseDialog.show()
    }

    override fun onCompleteStepper() {
        Toast.makeText(this, "Completed entire step", Toast.LENGTH_SHORT).show()
    }
}
