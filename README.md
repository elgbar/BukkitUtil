# BukkitUtil

Various utilities for easier programming in bukkit. Some might require protocol lib (eks [AdvancedChat](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/chat/AdvancedChat.java#L68)). Everything is version independent and no NMS code has been used. 

### List of utilities

* [Countdown](https://github.com/kh498/BukkitUtil/tree/master/src/main/java/no/kh498/util/countdown) - Easy way of creating a countdown. (requires ProtocolLib for lower than 1.9.2)
* [ItemMenus](https://github.com/kh498/BukkitUtil/tree/master/src/main/java/no/kh498/util/itemMenus) - Take an OOP approach to GUI based menus.
* [AdvancedChat](https://github.com/kh498/BukkitUtil/tree/master/src/main/java/no/kh498/util/chat) - Use System and Action Bar for minecraft versions that doesn't support them (1.8.x being one). (requires ProtocolLib for lower than 1.9.2)
* [FileUtils](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/FileUtils.java) - Easily read and write files for a plugin.
* [InventoryUtil](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/InventoryUtil.java) - Get more control over Bukkit's inventories.
* [MCConstans](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/MCConstants.java) - Convert millisecond to tick and visa versa, also contains some time constants.
* [OnJoinMessage](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/OnJoinMessage.java) - Let players get messages even if they're not on.
* [WorldUtil](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/WorldUtil.java) - Get the highest valid block from a location
* [MultiPage](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/MultiPage.java) - Create a help-ish menu but with any text
* [ChtUtil](https://github.com/kh498/BukkitUtil/blob/master/src/main/java/no/kh498/util/ChtUtil.java) - Easier chat usage, including newline and tab fixer

For more general java utilities see [CommonUtilsJava](https://github.com/kh498/CommonUtilsJava) and for easier command creation you can use [BukkitCommandAPI](https://github.com/kh498/BukkitCommandAPI). Both can be used with maven.

## Maven/Install

Add [my repo](https://github.com/kh498/maven2)

```
<dependency>
    <groupId>no.kh498.util</groupId>
    <artifactId>BukkitUtil</artifactId>
    <version>3.4.1</version>
</dependency>
```

To use CommandAPI you also need to shade it into your project to do so add the following to your pom.xml 

```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.0.0</version>
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
