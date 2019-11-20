package com.nerdstone.neatandroidstepper.core.domain

import androidx.annotation.NonNull
import com.nerdstone.neatandroidstepper.core.stepper.Step
import com.nerdstone.neatandroidstepper.core.stepper.StepVerificationState

interface StepperActions {

    fun onButtonNextClick(step: Step)

    fun onButtonPreviousClick(step: Step)

    fun onStepComplete(step: Step)

    fun onStepError(@NonNull stepVerificationState: StepVerificationState)

    fun onExitStepper()

    fun onCompleteStepper()

}