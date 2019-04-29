package net.blakelee.sofiandroidchallenge.config

import net.blakelee.model.services.config.ServiceConfig
import net.blakelee.sofiandroidchallenge.BuildConfig

class DefaultConfig(
    override val baseUrl: String = BuildConfig.BASE_URL,
    override val clientId: String = BuildConfig.CLIENT_ID
) : ServiceConfig