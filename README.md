# BukkitUtil

Various utilities for easier programming in bukkit. Some might require protocol lib (eks [AdvancedChat](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/chat/AdvancedChat.java#L80)). Everything is version independent and no NMS code has been used. 

### List of utilities

#### Major Utilities 

* [Countdown](https://github.com/elgbar/BukkitUtil/tree/master/BukkitUtil-core/src/main/java/no/kh498/util/countdown) - Easy way of creating a countdown. (requires ProtocolLib for lower than 1.9.2)
* [ItemMenus](https://github.com/elgbar/BukkitUtil/tree/master/BukkitUtil-core/src/main/java/no/kh498/util/itemMenus) - Take an OOP approach to GUI based menus.
* [RegionEvents](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/regionEvents) - Region exit/enter events for WorldGuard. Supports 6.2 and up (including 7.0)
* [OOP Bukkit Style Commands](https://github.com/elgbar/BukkitUtil/tree/master/BukkitUtil-core/src/main/java/no/kh498/util/command) Create commands with sub commands, that support basic tabulation.
* [<del>BookUtils</del>](https://github.com/elgbar/BukkitUtil/tree/18492589683c49435b7a7969999a73d0176a6439/src/main/java/no/kh498/util/book) Use <https://github.com/upperlevel/book-api> this API is removed as other better apis exists. Link in to final published version.

#### Single Class Utilities

Sorted from most to the least useful (for an average plugin)

* [ConfigUtil](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/ConfigUtil.java) Various utilities with bukkit YAML configuration.
* [ChatUtil](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/ChatUtil.java) - Easier chat usage, including newline and tab fixer.
* [FileUtils](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/FileUtils.java) - Easily read and write files for a plugin.
* [AdvancedChat](https://github.com/elgbar/BukkitUtil/tree/master/BukkitUtil-core/src/main/java/no/kh498/util/chat) - Use System and Action Bar for minecraft versions that doesn't support them, 1.8.x being one. (requires ProtocolLib for lower than 1.9.2)
* [InventoryUtil](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/InventoryUtil.java) - Get more control over Bukkit's inventories.
* [MCConstans](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/MCConstants.java) - Convert millisecond to tick and visa versa, also contains some time constants.
* [OnJoinMessage](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/OnJoinMessage.java) - Let players get messages even if they're not on.
* [WorldUtil](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/WorldUtil.java) - Get the highest valid block from a location.
* [MultiPage](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/MultiPage.java) - Create a help-ish menu but with any text.
* [InterfaceImplManager](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/InterfaceImplManager.java) Add an API layer to implementation of interfaces.
* [EntityUtil](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/EntityUtil.java) Metadata utility for entities.

#### Interfaces

* [Debuggable](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/Debuggable.java) Simplify debugging of complex objects
* [Nameable](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/Nameable.java) Forces an object to have a name and, optionally, a description
* [Saveable](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/Saveable.java) Simple save and load methods
* [ConfigurationSectionSavable](https://github.com/elgbar/BukkitUtil/blob/master/BukkitUtil-core/src/main/java/no/kh498/util/ConfigurationSectionSavable.java) Save/load an object to a ConfigurationSection

For more general java utilities see [CommonUtilsJava](https://github.com/elgbar/CommonUtilsJava)

## Install

If BukkitUtil fails to build because it cannot find `org.bukkit:bukkit:1.14.4-R0.1-SNAPSHOT` (or something similar) you need to locally build the spigot using its [BuildTools](https://www.spigotmc.org/wiki/buildtools/) you can either do this manually (remember to build the correct version) or run the script `setup.sh` found in the root folder.

### maven

Add [my repo](https://github.com/elgbar/maven2) to your `pom.xml`. `https://raw.githubusercontent.com/rjenkinsjr/maven2/repo` and `http://maven.sk89q.com/repo/` might also be needed


Then add this as a dependency under the `dependencies` tag

```xml
<dependency>
    <groupId>no.kh498.util</groupId>
    <artifactId>BukkitUtil</artifactId>
    <version>4.1.0-beta1</version>
</dependency>
```

You might also need to shade (read copy) the used classes into your jar. Add the following into your `pom.xml`

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.2.1</version>
            <executions>
                <execution>
                    <id>shade</id>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <minimizeJar>true</minimizeJar> <!-- Only include packages that you are using. Note: Requires Java 1.5 or higher. -->
                <artifactSet>
                    <includes>
                        <include>no.kh498.util:BukkitUtil</include>
                    </includes>
                </artifactSet>
            </configuration>
        </plugin>
    </plugins>
</build>       
```

### gradle

The following is a skeleton gradle.build file. **NOTE** the order of the methods is required as is below.

```groovy

//setup shade (ie include the whole of this lib in your jar)
configurations {
    shade
    implementation.extendsFrom shade
}

repositories {
    mavenCentral()
    
    //your other repositories goes here
    
    //my repo
    maven { url "https://raw.githubusercontent.com/elgbar/maven2/repo" }
    //these might be required
    maven { url "https://raw.githubusercontent.com/rjenkinsjr/maven2/repo" } // needed by slf4bukkit (a logger)
    maven { url "http://maven.sk89q.com/repo/" } //needed by WorldGuard
}


dependencies {
    //your other decencies goes here
    
    //use implementation if you do not want to include it in your jar
    shade "no.kh498.util:BukkitUtil:4.1.0-beta1"
}

jar {
    configurations.shade.each { dep ->
        from(project.zipTree(dep)) {
            exclude 'META-INF', 'META-INF/**', 'plugin.yml' //delete unwanted/duplicate stuff
        }
    }
}
```
