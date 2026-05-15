# PlantVSZombie

A Minecraft 1.20.1 Fabric mod inspired by Plants vs. Zombies.

## Requirements

- **JDK 17** (set via `JAVA_HOME`)
- **Minecraft 1.20.1**
- **Fabric Loader** 0.14.22+
- **Fabric API** 0.92.2+1.20.1

## Development

Run a dev client (downloads Minecraft, deobfuscates, launches):
```powershell
.\gradlew.bat runClient
```

Run a dev server:
```powershell
.\gradlew.bat runServer
```

Build the mod jar (output in `build/libs/`):
```powershell
.\gradlew.bat build
```

Generate Minecraft sources for navigation:
```powershell
.\gradlew.bat genSources
```

## Project Layout

- `src/main/java/com/saharat/plantvszombie/` - common (server + client) code
- `src/client/java/com/saharat/plantvszombie/client/` - client-only code
- `src/main/resources/fabric.mod.json` - mod metadata
- `src/main/resources/plantvszombie.mixins.json` - common mixins
- `src/main/resources/plantvszombie.client.mixins.json` - client mixins

## VS Code

This project ships `.vscode/settings.json` and `.vscode/tasks.json`. Required extensions:

- Extension Pack for Java (`vscjava.vscode-java-pack`)
- Gradle for Java (`vscjava.vscode-gradle`)

Use the **Run Task** menu (Ctrl+Shift+P -> "Run Task") to invoke `gradle: runClient`.
