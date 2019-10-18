package eu.thesimplecloud.lib.servicegroup.impl

import eu.thesimplecloud.lib.service.ServiceVersion
import eu.thesimplecloud.lib.servicegroup.grouptype.ICloudServerGroup

open class DefaultServerGroup(
        name: String,
        templateName: String,
        maxMemory: Int,
        maxPlayers: Int,
        minimumOnlineServiceCount: Int,
        maximumOnlineServiceCount: Int,
        maintenance: Boolean,
        static: Boolean,
        percentToStartNewService: Int,
        wrapperName: String?,
        serviceVersion: ServiceVersion,
        private val hiddenAtProxyGroups: List<String> = emptyList()
) : AbstractServiceGroup(
        name,
        templateName,
        maxMemory,
        maxPlayers,
        minimumOnlineServiceCount,
        maximumOnlineServiceCount,
        maintenance,
        static,
        percentToStartNewService,
        wrapperName,
        serviceVersion
), ICloudServerGroup {

    override fun getHiddenAtProxyGroups(): List<String> = this.hiddenAtProxyGroups
}