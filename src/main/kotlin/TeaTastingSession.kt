package dev.teaguild.thoughtsntea

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.UserId
import dev.inmo.tgbotapi.types.chat.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

internal class TeaTastingSession(
    val scope: CoroutineScope,
    val bot: TelegramBot,
    val targetChatID: ChatId,
    config: TeaTastingConfig = TeaTastingConfig.Default,
    tastingState: TastingState = TastingState.DEFAULT,
) {
    private val _config: MutableStateFlow<TeaTastingConfig> = MutableStateFlow(config)
    private val _participants: MutableStateFlow<Map<UserId, User>> = MutableStateFlow(emptyMap())
    private val _tastingState: MutableStateFlow<TastingState> = MutableStateFlow(tastingState)

    val config: StateFlow<TeaTastingConfig>
        get() = _config

    val tastingState: StateFlow<TastingState>
        get() = _tastingState

    val participants: StateFlow<Map<UserId, User>>
        get() = _participants

    fun addParticipant(userId: UserId, user: User): Boolean = if (userId in _participants.value) {
        false
    } else {
        _participants.update { it + (userId to user) }
        true
    }

    fun removeParticipant(userId: UserId): Boolean = if (userId in _participants.value) {
        _participants.update { it - userId }
        true
    } else {
        false
    }

    fun clearParticipants() {
        _participants.value = emptyMap()
    }

    fun setTastingState(tastingState: TastingState) {
        _tastingState.value = tastingState
    }

    inline fun updateConfig(function: (TeaTastingConfig) -> TeaTastingConfig) = _config.update(function)
}
