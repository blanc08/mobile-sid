package com.blanc08.sid.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val sidDispatcher: SidDispatchers)

enum class SidDispatchers {
    Default,
    IO,
}
