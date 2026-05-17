# Repository Guidelines

## Project Structure & Module Organization

This is a Minecraft 1.20.1 Fabric mod. Common code lives in `src/main/java/com/saharat/plantvszombie/`, including entities, registries, blocks, items, and mixins. Client-only renderers and model-layer code live in `src/client/java/com/saharat/plantvszombie/client/`. Runtime metadata and assets live in `src/main/resources/`: `fabric.mod.json`, mixin configs, language files, item/block models, and textures under `assets/plantvszombie/`. Blender source assets are kept in `art/blender/` when present.

## Build, Test, and Development Commands

- `.\gradlew.bat build`: compiles common/client sources, processes resources, runs checks, and creates jars in `build/libs/`.
- `.\gradlew.bat runClient`: launches a local Minecraft client with the mod loaded for gameplay testing.
- `.\gradlew.bat runServer`: launches a local dedicated server.
- `.\gradlew.bat genSources`: generates mapped Minecraft sources for IDE navigation.

Use JDK 17 and keep `gradle.properties` aligned with Minecraft `1.20.1`, Fabric Loader, Yarn mappings, and Fabric API versions.

## Coding Style & Naming Conventions

Use Java 17. Follow the existing style: 4-space indentation, braces on the same line, `PascalCase` classes, `camelCase` methods/fields, and constants in `UPPER_SNAKE_CASE`. Registry constants should mirror resource IDs, for example `WALLNUT` maps to `plantvszombie:wallnut`. Keep server-safe logic in `src/main`; put renderers, models, and client registries only in `src/client`.

## Testing Guidelines

There is no formal test suite yet; Gradle currently reports test tasks as no-source. Always run `.\gradlew.bat build` before handing off changes. For gameplay behavior, also run `.\gradlew.bat runClient` and test relevant commands, such as `/summon plantvszombie:wallnut`, spawn eggs, projectiles, and renderer visibility.

## Commit & Pull Request Guidelines

The history currently only contains `Initial Fabric mod scaffold`, so use clear imperative subjects going forward, such as `Add wallnut entity renderer` or `Fix peashooter monster targeting`. Pull requests should include a short summary, affected gameplay/assets, verification steps, and screenshots or short clips for visual model or renderer changes. Link issues when applicable.

## Agent-Specific Instructions

Do not revert unrelated local changes. Keep edits scoped to the requested feature or bug. When adding assets, include both the Minecraft resource path and any source file needed to edit the asset later.
