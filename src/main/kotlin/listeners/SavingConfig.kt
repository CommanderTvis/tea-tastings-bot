package io.github.commandertvis.teatastingsbot.listeners

import io.github.commandertvis.teatastingsbot.TeaTastingSession
import io.github.commandertvis.teatastingsbot.saveConfig
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext

internal fun observeConfigToSave(session: TeaTastingSession) = session.config
    .onEach { value ->
        withContext(Dispatchers.IO) { saveConfig(value) }
    }
    .launchIn(session.scope + CoroutineName("savingConfig"))
