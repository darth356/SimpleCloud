package eu.thesimplecloud.base.manager.commands

import eu.thesimplecloud.api.command.ICommandSender
import eu.thesimplecloud.api.service.ICloudService
import eu.thesimplecloud.launcher.console.command.CommandType
import eu.thesimplecloud.launcher.console.command.ICommandHandler
import eu.thesimplecloud.launcher.console.command.annotations.Command
import eu.thesimplecloud.launcher.console.command.annotations.CommandArgument
import eu.thesimplecloud.launcher.console.command.annotations.CommandSubPath
import eu.thesimplecloud.launcher.console.command.provider.ServiceCommandSuggestionProvider
import eu.thesimplecloud.launcher.extension.sendMessage

@Command("shutdown", CommandType.CONSOLE_AND_INGAME, "cloud.command.shutdown")
class ShutdownCommand : ICommandHandler {

    @CommandSubPath("<name>", "Stops a service")
    fun shutdown(commandSender: ICommandSender, @CommandArgument("name", ServiceCommandSuggestionProvider::class) service: ICloudService) {
        service.shutdown()
        commandSender.sendMessage("manager.command.shutdown.success", "Stopping service.")
    }

}