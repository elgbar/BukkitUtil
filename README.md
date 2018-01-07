# BukkitUtil

Various utilities for easier programming in bukkit. Some might require protocol lib (eks [AdvancedChat](https://github.com/kh498/Bukkit-Util/blob/master/src/main/java/no/kh498/util/AdvancedChat.java#L70)). Everything is version independent and no NMS code has been used. 

For more general java utilities see [CommonUtilsJava](https://github.com/kh498/CommonUtilsJava) and for easier command creation you can use [BukkitCommandAPI](https://github.com/kh498/BukkitCommandAPI). Both can be used with maven.

## Maven/Install

Add [my repo](https://github.com/kh498/maven2)

```
<dependency>
    <groupId>no.kh498.util</groupId>
    <artifactId>BukkitUtil</artifactId>
    <version>3.2.0</version>
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