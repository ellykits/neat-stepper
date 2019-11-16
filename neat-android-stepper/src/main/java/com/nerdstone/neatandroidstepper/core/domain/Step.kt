package com.nerdstone.neatandroidstepper.core.domain

import androidx.annotation.NonNull
import com.nerdstone.neatandroidstepper.core.stepper.StepVerificationState

interface Step {

    fun verifyStep(): StepVerificationState

    fun onSelected()

    fun onError(@NonNull stepVerificationState: StepVerificationState)

}