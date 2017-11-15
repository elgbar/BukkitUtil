# Countdown

This api gives you an easy way of making a countdown. 

## Examples

### Creating a countdown

```java
Plugin plugin = ... //your plugin instance
String text = ChatColor.GRAY+"%s left"; // must contain a %s
long time = 10000L; // time in ms
long delay = 10L // time between each actionbar update
TimeFormat format = new CountdownHHMMSS(); //or new CountdownSeconds();

Countdown c = new CountdownWorld(plugin, text, time, delay, format, world);
//or 
Countdown c = new CountdownList(plugin, text, time, delay, format, list);

//start the countdown
c.start()
```

### Listening to the end of a countdown
```java
public class CommonListener implements Listener {
        @EventHandler
        public static void onCountdownEnds(final CountdownFinishedEvent event) {
            if (event.getCountdown() instanceof CountdownWorld) {
                final CountdownWorld cw = (CountdownWorld) event.getCountdown();
                //Do something
            }
        }
}
```

### Making an interrupt

```java
Countdown c = ... // any countdown

Player player = ... // the player to interrupt
String interruptText = "You stepped on gold!"; // some text to interrupt with
String time = 2000L; // How long to interrupt for in ms
boolean force = false; // Given that there is already an interrupt, should this override that?

c.interrupt(player, interruptText, time, force);

//Or if you always want to send an action bar, but you dont care if the countdown is running
c.tryInterrupt(player, interruptText, time, force);
```
