# WorldGuard Events

**NOTE THIS REQUIRE WORLDGUARD 6.2 AND UP**

Add some events to WordGuard's region

## Initiate

To register the events you need to call the method below.
```
no.kh498.util.regionEvents.RegionEvents.initiate();
```

## Events

Currently there is only two events:
* `RegionEnterEvent` *
* `RegionExitEvent` * 

\* Cancelling the event results in the player being teleported back to the previous location, unless the MoveType is not cancellable.
