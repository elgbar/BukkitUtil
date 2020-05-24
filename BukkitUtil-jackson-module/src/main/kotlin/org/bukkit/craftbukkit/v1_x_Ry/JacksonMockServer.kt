package org.bukkit.craftbukkit.v1_x_Ry

import com.avaje.ebean.config.ServerConfig
import org.bukkit.*
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemFactory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.plugin.Plugin
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import java.util.logging.Logger
import kotlin.collections.ArrayList

/**
 * @author Elg
 */
object JacksonMockServer : Server {

    private val itemFactor: ItemFactory = CraftItemFactory.instance()
    private val backingWorlds = ArrayList<World>()

    val offlinePlayers = ArrayList<OfflinePlayer>()

    init {
        Bukkit.setServer(this)
        assert(Bukkit.getServer() == this) {
            "Bukkit server not set, if are you mocking Bukkit class the setServer and getServer should call the real methods"
        }
    }

    override fun getItemFactory() = itemFactor


    override fun spigot(): Server.Spigot? {
        return null
    }

    override fun getName() = "Jackson Mock Server"
    override fun getServerName() = name

    override fun getBukkitVersion() = "Mock of v1.9 R1"
    override fun getVersion() = bukkitVersion

    override fun getLogger(): Logger = Logger.getGlobal()

    override fun getIp() = "localhost"
//    override fun getEntity(p0: UUID?) = null

    /////////////////////////////////////////////
    // Methods that actually holds information //
    /////////////////////////////////////////////

    override fun getWorlds() = backingWorlds
    override fun configureDbConfig(config: ServerConfig?) {
        TODO("not implemented")
    }

    override fun getWorld(name: String?) = backingWorlds.firstOrNull { it.name == name }
    override fun getWorld(uid: UUID?) = backingWorlds.firstOrNull { it.uid == uid }


    override fun getOfflinePlayers() = offlinePlayers.toTypedArray()
    override fun getOfflinePlayer(name: String?) = offlinePlayers.firstOrNull { it.name == name }
    override fun getOfflinePlayer(id: UUID?) = offlinePlayers.firstOrNull { it.uniqueId == id }

    // use the reload method to clean up stored data

    override fun reload() {
        worlds.clear()
        offlinePlayers.clear()
    }

    //////////////////////////////////////////
    // Stubs returning null or does nothing //
    //////////////////////////////////////////

    //    override fun getPlayerExact(name: String?) = players.firstOrNull { it.name == name }
//    override fun getPlayer(name: String?) = players.firstOrNull { it.name == name }
//    override fun getPlayer(id: UUID?) = players.firstOrNull { it.uniqueId == id }
    override fun getPlayerExact(name: String?) = null
    override fun getPlayer(name: String?) = null
    override fun getPlayer(id: UUID?) = null

    override fun getScheduler() = null
    override fun createInventory(owner: InventoryHolder?, type: InventoryType?) = null
    override fun createInventory(owner: InventoryHolder?, type: InventoryType?, title: String?) = null
    override fun createInventory(owner: InventoryHolder?, size: Int) = null
    override fun createInventory(owner: InventoryHolder?, size: Int, title: String?) = null
    override fun isHardcore() = false
    override fun getSpawnRadius() = 0
    override fun getOperators() = emptySet<OfflinePlayer>()
    override fun savePlayers() {}
    override fun addRecipe(recipe: Recipe?) = false
    override fun getTicksPerAnimalSpawns() = 0
    override fun getBannedPlayers() = emptySet<OfflinePlayer>()
    override fun getUpdateFolderFile() = null
    override fun getConsoleSender() = null
    override fun banIP(address: String?) {}
    override fun getServerIcon() = null
    override fun getAnimalSpawnLimit() = 0
    override fun getListeningPluginChannels() = emptySet<String>()
    override fun getPort() = 0
    override fun getBanList(type: BanList.Type?) = null
    override fun getMap(id: Short) = null
    override fun useExactLoginLocation() = false

    override fun getMonsterSpawnLimit() = 0

    //    override fun createMerchant(p0: String?) = null
    override fun getScoreboardManager() = null
    override fun createChunkData(world: World?) = null
    override fun setWhitelist(value: Boolean) {}
    override fun getIdleTimeout() = 0
    override fun getMaxPlayers() = 0
    override fun _INVALID_getOnlinePlayers(): Array<Player> = emptyArray()
    override fun getViewDistance() = 0
    override fun unloadWorld(name: String?, save: Boolean) = false
    override fun unloadWorld(world: World?, save: Boolean) = false
    override fun getMotd() = ""
    override fun getAllowEnd() = false
    override fun getAmbientSpawnLimit() = 0
    override fun dispatchCommand(sender: CommandSender?, commandLine: String?) = false
    override fun getIPBans() = emptySet<String>()
    override fun getWarningState() = Warning.WarningState.OFF

    //    override fun advancementIterator() = null
    override fun getAllowNether() = false
    override fun getAllowFlight() = false
    override fun getServicesManager() = null
    override fun unbanIP(address: String?) {}
    override fun getHelpMap() = null
    override fun broadcastMessage(message: String?) = 0
    override fun getCommandAliases() = emptyMap<String, Array<String>>()
    override fun getWaterAnimalSpawnLimit() = 0
    override fun getWorldContainer() = null
    override fun createBossBar(title: String?, color: BarColor?, style: BarStyle?, vararg flags: BarFlag?) = null
    override fun sendPluginMessage(source: Plugin?, channel: String?, message: ByteArray?) {}
    override fun setSpawnRadius(value: Int) {}
    override fun setIdleTimeout(threshold: Int) {}
    override fun getPluginCommand(name: String?) = null
    override fun getOnlinePlayers() = emptyList<Player>()
    override fun getTicksPerMonsterSpawns() = 0
    override fun getGenerateStructures() = false
    override fun getWhitelistedPlayers() = emptySet<OfflinePlayer>()
    override fun createMap(world: World?) = null
    override fun recipeIterator() = emptySet<Recipe>().iterator()
    override fun getDefaultGameMode() = GameMode.SPECTATOR
    override fun getUnsafe() = null
    override fun setDefaultGameMode(mode: GameMode?) {}

    //    override fun reloadData() {}
    override fun createWorld(creator: WorldCreator?) = null
    override fun isPrimaryThread() = true
    override fun resetRecipes() {}
    override fun getShutdownMessage() = null
    override fun getConnectionThrottle() = 0L
    override fun getServerId() = null
    override fun broadcast(message: String?, permission: String?) = 0
    override fun loadServerIcon(file: File?) = null
    override fun loadServerIcon(image: BufferedImage?) = null
    override fun getRecipesFor(result: ItemStack?) = emptyList<Recipe>()
    override fun hasWhitelist() = false
    override fun getUpdateFolder() = null
    override fun getPluginManager() = null
    override fun matchPlayer(name: String?) = emptyList<Player>()
    override fun reloadWhitelist() {}
    override fun clearRecipes() {}
    override fun shutdown() {}
    override fun getMessenger() = null
    override fun getOnlineMode() = false
    override fun getWorldType() = null
//    override fun getAdvancement(p0: NamespacedKey?) = null
}
