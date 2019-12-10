#As a one liner:
#mkdir buildtools ; cd buildtools && curl -o BuildTools.jar "https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar" && java -jar BuildTools.jar --rev 1.14.4

#Create a new subfolder and go into it
mkdir buildtools
cd buildtools
#Download the lastest BuildTools
curl -o BuildTools.jar "https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar"
#Compile the wanted versions
java -jar BuildTools.jar --rev 1.14.4 #We need 1.14.4 for WorldGuard Bukkit 7
