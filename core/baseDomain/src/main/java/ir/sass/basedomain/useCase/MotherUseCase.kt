package ir.sass.basedomain.useCase

import ir.sass.basedomain.model.Domain
import kotlinx.coroutines.flow.Flow

abstract class MotherUseCase<in Input, Output>{
    abstract operator fun invoke(input : Input) : Flow<Domain<Output>>
}

abstract class MotherUseCaseWithOnlyInput<in Input>(){
    abstract operator fun invoke(input : Input)
}

abstract class MotherUseCaseWithOnlyOutput<Output>{
    abstract operator fun invoke() : Flow<Domain<Output>>
}
