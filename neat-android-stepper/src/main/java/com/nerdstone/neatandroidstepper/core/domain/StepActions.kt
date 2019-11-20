package com.nerdstone.neatandroidstepper.core.domain

import androidx.annotation.NonNull
import com.nerdstone.neatandroidstepper.core.model.StepModel
import com.nerdstone.neatandroidstepper.core.stepper.StepVerificationState

interface StepActions {

    var stepModel: StepModel

    fun verifyStep(): StepVerificationState

    fun onSelected()

    fun onError(@NonNull stepVerificationState: StepVerificationState)

}