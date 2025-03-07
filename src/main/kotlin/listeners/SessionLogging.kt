package io.github.commandertvis.teatastingsbot.listeners

import dev.inmo.kslog.common.TagLogger
import dev.inmo.kslog.common.i
import io.github.commandertvis.teatastingsbot.TeaTastingSession
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private val logger = TagLogger("SessionLogging")

internal fun observeSessionToLog(session: TeaTastingSession) = with(session) {
    fun logFlow(flow: StateFlow<Any?>, name: String) {
        flow
            .onEach { value ->
                logger.i { "$name: $value" }
            }
            .launchIn(scope)
    }

    logFlow(config, "Config")
    logFlow(participants, "Participants")
    logFlow(tastingState, "State")
}
