# BukkitUtil

Various utilities for easier programming in bukkit. Some might require protocol lib (eks [AdvancedChat](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/chat/AdvancedChat.java#L68)). Everything is version independent and no NMS code has been used. 

### List of utilities

#### Major Utilities 

* [Countdown](https://github.com/kh498/BukkitUtil/tree/master/src/main/java/no/kh498/util/countdown) - Easy way of creating a countdown. (requires ProtocolLib for lower than 1.9.2)
* [ItemMenus](https://github.com/kh498/BukkitUtil/tree/master/src/main/java/no/kh498/util/itemMenus) - Take an OOP approach to GUI based menus.
* [RegionEvents](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/regionEvents) - Region exit/enter events for WorldGuard. (requires WorldGuard 6.x, tested on WorldGuard 6.2)
* [OOP Bukkit Style Commands]() Create commands with sub commands, that support basic tabulation.
* [BookUtils](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/book/Bookutil.java) Create and open books. *Note: It is using NMS code, so it does not support all versions*

#### Single Class Utilities

Sorted from greatest to least usefulness (for an average plugin)

* [ConfigUtil](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/ConfigUtil.java) Various utilities with bukkit YAML configuration.
* [ChatUtil](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/ChatUtil.java) - Easier chat usage, including newline and tab fixer.
* [FileUtils](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/FileUtils.java) - Easily read and write files for a plugin.
* [AdvancedChat](https://github.com/kh498/BukkitUtil/tree/master/src/main/java/no/kh498/util/chat) - Use System and Action Bar for minecraft versions that doesn't support them, 1.8.x being one. (requires ProtocolLib for lower than 1.9.2)
* [InventoryUtil](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/InventoryUtil.java) - Get more control over Bukkit's inventories.
* [MCConstans](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/MCConstants.java) - Convert millisecond to tick and visa versa, also contains some time constants.
* [OnJoinMessage](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/OnJoinMessage.java) - Let players get messages even if they're not on.
* [WorldUtil](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/WorldUtil.java) - Get the highest valid block from a location.
* [MultiPage](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/MultiPage.java) - Create a help-ish menu but with any text.
* [InterfaceImplManager](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/InterfaceImplManager.java) Add an API layer to implementation of interfaces.

#### Interfaces

* [Debuggable](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/Debuggable.java) Simplify debugging of complex objects
* [Nameable](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/Nameable.java) Forces an object to have a name and, optionally, a description
* [Saveable](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/Saveable.java) Simple save and load methods
* [ConfigurationSectionSavable](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/ConfigurationSectionSavable.java) Save/load an object to a ConfigurationSection

For more general java utilities see [CommonUtilsJava](https://github.com/kh498/CommonUtilsJava)

## Install

### About the versions

Although the current version, 4.0.0-beta.2, has a beta tag, it does not mean that the older versions are more stable. The 3.x versions is not longer maintained and, due to some class renaming, is not compatible with the current version. 4.x versions are tagged as beta, so I may refactor any part as I see fit in the future. Though if I decide to remove/rename a class/method I will deprecate it for one version, before removing it.

### maven

Add [my repo](https://github.com/kh498/maven2) to your `pom.xml`. `https://raw.githubusercontent.com/rjenkinsjr/maven2/repo` and `http://maven.sk89q.com/repo/` might also be needed


Then add this as a dependency under the `dependencies` tag

```xml
<dependency>
    <groupId>no.kh498.util</groupId>
    <artifactId>BukkitUtil</artifactId>
    <version>4.0.0-beta.2</version>
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

//setup shade
configurations {
    shade
    compile.extendsFrom shade
}

repositories {
    mavenCentral()
    
    //your other repositories goes here
    
    //my repo
    maven { url "https://raw.githubusercontent.com/kh498/maven2/repo" }
    //these might be required
    maven { url "https://raw.githubusercontent.com/rjenkinsjr/maven2/repo" } // needed by slf4bukkit (a logger)
    maven { url "http://maven.sk89q.com/repo/" } //needed by WorldGuard
}


dependencies {
    //your other decencies goes here
    
    shade group: 'no.kh498.util', name: 'BukkitUtil', version: '4.0.0-beta.2'
}

jar {
    configurations.shade.each { dep ->
        from(project.zipTree(dep)) {
            exclude 'META-INF', 'META-INF/**', 'plugin.yml' //delete unwanted/duplicate stuff
        }
    }
}
```
