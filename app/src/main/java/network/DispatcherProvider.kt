package network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    val Main: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val Default: CoroutineDispatcher
}

class DefaultDispatcherProvider : DispatcherProvider {
    override val Main: CoroutineDispatcher = Dispatchers.Main
    override val IO: CoroutineDispatcher = Dispatchers.IO
    override val Default: CoroutineDispatcher = Dispatchers.Default
}