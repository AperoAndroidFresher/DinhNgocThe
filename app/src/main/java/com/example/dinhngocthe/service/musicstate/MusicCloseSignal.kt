package com.example.dinhngocthe.service.musicstate

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

object MusicCloseSignal {
    private var deferred: CompletableDeferred<Unit>? = null

    fun awaitClose(): Deferred<Unit> {
        deferred = CompletableDeferred()
        return deferred!!
    }

    fun notifyClosed() {
        deferred?.complete(Unit)
        deferred = null
    }
}
