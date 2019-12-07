# WorldGuard Events

Add bukkit events for entry and exit of WorldGuard protected regions.

## Supported versions

* __7.0.1__ (tested, plugin.yml v[7.0.1;f2118aa](https://dev.bukkit.org/projects/worldguard/files/2831137))
* 7.0.0 (untested)
* __6.2.2__ (tested, plugin.yml v[6.2.2-SNAPSHOT;8eeab68](https://dev.bukkit.org/projects/worldguard/files/2610618))
* 6.2.1 (untested)
* __6.2.0__ (tested, plugin.yml v[6.1.3-SNAPSHOT;c904242](https://dev.bukkit.org/projects/worldguard/files/956770))
* Probably all future `7.0.x` and maybe `7.x.y` onwards

## Initiate

To register the events you need to call the method below.
```
no.kh498.util.regionEvents.RegionEvents.initiate();
```

If you only want to enable this for either WorldGuard 6 or 7 you can call the individual methods.
```
no.kh498.util.regionEvents.RegionEvents.initiate6x();
```
or
```
no.kh498.util.regionEvents.RegionEvents.initiate7x();
```


## Events

Currently there are only two events
* `RegionEnterEvent`*
* `RegionExitEvent`* 

\* Cancelling the event results in the player being teleported back to the previous location, unless the `MoveType` is not cancellable.
