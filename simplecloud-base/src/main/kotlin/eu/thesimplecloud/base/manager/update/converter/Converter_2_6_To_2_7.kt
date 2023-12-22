package eu.thesimplecloud.base.manager.update.converter

import eu.thesimplecloud.api.directorypaths.DirectoryPaths
import eu.thesimplecloud.jsonlib.JsonLib
import java.io.File

class Converter_2_6_To_2_7: IVersionConverter {

    override fun getTargetMinorVersion(): Int {
        return 7
    }

    override fun convert() {
        convertGroupsInDirectory(File(DirectoryPaths.paths.proxyGroupsPath))
        convertGroupsInDirectory(File(DirectoryPaths.paths.lobbyGroupsPath))
        convertGroupsInDirectory(File(DirectoryPaths.paths.serverGroupsPath))
    }

    private fun convertGroupsInDirectory(dir: File) {
        dir.listFiles()?.forEach {
            convertSingleGroupByFile(it)
        }
    }

    private fun convertSingleGroupByFile(file: File) {
        val groupJsonLib = JsonLib.fromJsonFile(file) ?: return

        val maxMemory = groupJsonLib.jsonElement.asJsonObject.get("maxMemory").asInt

        if (!groupJsonLib.jsonElement.asJsonObject.has("minimumMemory")) {
            groupJsonLib.jsonElement.asJsonObject.addProperty("minimumMemory", maxMemory / 2)
        }

        groupJsonLib.saveAsFile(file)
    }
}