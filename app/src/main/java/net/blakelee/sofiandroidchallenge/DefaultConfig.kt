package net.blakelee.sofiandroidchallenge

import net.blakelee.model.ServiceConfig

class DefaultConfig(
    override val baseUrl: String = BuildConfig.BASE_URL,
    override val clientId: String = BuildConfig.CLIENT_ID
) : ServiceConfig