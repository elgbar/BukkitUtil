# Jackson for Bukkit

This package contains serialization and deserialization tools to serialize a couple of different bukkit classes. The reason to choose this over GSON is that I've created a [tool](https://github.com/kh498/HandcraftObjectsTool) that helps creating serialized JVM objects using Jackson as it's converter. 

## Installation

To use this module you only need to add this as a module for you object mapper.

```java
Object mapper = new ObjectMapper();
mapper.registerModule(new BukkitModule());
```

## Configuration

When creating the bukkit modules you have a couple of options. 

### colorizeStringsByDefault

If `colorizeStringsByDefault` is `true` the default string deserializer is replaced with a custom deserializer that will convert `&` prefix with the standard `§` for valid color codes. It will also fix any newline error (replacing `\r\n` and `\r` with `\n`) and replace tab character (`\t`) with four spaces.

To use this deserializer with jackson add `@JsonDeserialize(using = ColoredStringDeserializer.class)` above the wanted string variable.

### noCustomItemMetaSerialization

Internal variable, if set to true item meta serialization will not work.

## Compatibility with native Bukkit serialization

This modules tires to be compatible with Bukkit's native serialization in the sense that same keys are being used to denote where each property is. As Yaml 1.2 is a subset of Json i should be trivial to convert between what this modules produces and Bukkit's serialization. 

The main difference between the two is that this module display type information more explicitly. For example Bukkit will produce `{x=2.0, y=1.0, z=-1.5}` if you serialize both `Vector` and a `BlockVector`. `BlockVector` is a sub class of `Vector` and only checks integer value for equality, but otherwise identical. However it is not given what Bukkit will deserialize this into if the user do not explicitly say that they want a `BlockVector`. This module will to solve this, potentially non-existent, problem by specifying type information. The same instance will be serialized into `{"==" : "BlockVector","x" : 2.0,"y" : 1.0,"z" : -1.5}` by this module.

There are however a few differences between Bukkit and this module:

1. `World` and `OfflinePlayer` will be serialized as `UUID`
    * Name of `World` are supposed to be unique but
    * For `OfflinePlayer` see point two
2. `OfflinePlayer` can be only be deserialized as `UUID`
    * Deserialization from name for players have been deprecated for a very long time in bukkit.
3. More information is serialized for `ItemStack`s
    * The amount is serialized even if it is 1
    
       
