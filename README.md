# PlantVSZombie

A Minecraft 1.20.1 Fabric mod that turns a flat test world into a small lane-defense combat sandbox. Players place plants, generate sun, block incoming zombies, and use Peashooters to fire pea projectiles at hostile mobs.

This is an unofficial fan-made modding experiment. It is not affiliated with Mojang, Microsoft, PopCap, or EA.

![PlantVSZombie lawn defense scene](docs/screenshots/lawn-defense-wide.png)

## Overview

PlantVSZombie is built around a simple loop:

1. Place Sunflowers to generate sun.
2. Spend sun on Peashooters and Wall-nuts.
3. Aim plants by facing the direction they should defend.
4. Spawn zombies and watch the plant line hold the lane.

The mod focuses on making the interaction readable in-game: plants have distinct silhouettes, zombies pick plant targets, and Peashooter shots are visible while crossing the lane.

## Screenshots

### Peashooter Combat

Peashooters scan for hostile `Monster` targets in their forward cone and fire no-gravity pea projectiles.

![Peashooter projectiles hitting zombies](docs/screenshots/peashooter-projectiles.png)

### Plant Lineup

Sunflowers support the economy, Wall-nuts absorb pressure, and Peashooters handle damage.

![Plant lineup fighting zombies](docs/screenshots/plant-lineup-combat.png)

## Features

- **Placeable plants**: Seed packet items place Peashooters, Sunflowers, and Wall-nuts into the world.
- **Directional defense**: Plants remember the player's facing direction when placed or adjusted.
- **Peashooter targeting**: Peashooters find visible hostile monsters inside a forward cone and shoot at them.
- **Projectile combat**: Pea projectiles deal damage, ignore friendly plants, and despawn after impact or a short lifetime.
- **Sun economy**: Sunflowers produce sun, and stronger plants consume sun when placed.
- **Zombie pressure**: Vanilla zombies can target plant entities, with Wall-nuts given higher priority.
- **Plant guide book**: Gives seed packets, changes plant facing, and returns placed plants back into packet form.
- **Custom rendering**: Plants use custom client renderers and item-model-based visuals for quick iteration.

## Tech Stack

- Minecraft 1.20.1
- Fabric Loader
- Fabric API
- Java 17
- Gradle / Fabric Loom
- Client-side entity renderers
- JSON item models and Minecraft resource assets

## Implemented Systems

- Custom entity registration for Peashooter, Sunflower, Wall-nut, and pea projectiles.
- Server-side plant behavior that keeps plants stationary while still ticking combat logic.
- Peashooter aim logic based on line of sight, distance, facing cone, and hostile mob filtering.
- Projectile hit handling that damages enemies without knocking them around or harming plant entities.
- Zombie AI mixin that adds plant target goals to vanilla zombie behavior.
- Seed packet placement logic with sun-cost checks and collision checks.
- Guide book interactions for quality-of-life plant control.

## Implementation Details

- Peashooters stop navigation and zero velocity every tick so they behave like planted defenses instead of wandering mobs.
- The forward cone uses the plant's saved manual yaw, so a placed plant defends the lane it was pointed toward.
- Pea spawn position is offset from the Peashooter's head direction so shots appear to come from the mouth.
- Projectiles use no gravity to keep shots readable and lane-like.
- Zombie behavior is extended through mixins rather than replacing vanilla zombie classes.

## How To Run

Requirements:

- JDK 17
- Minecraft 1.20.1
- Fabric Loader
- Fabric API

Run the development client:

```powershell
.\gradlew.bat runClient
```

Build the mod jar:

```powershell
.\gradlew.bat build
```

The built jar will be generated in:

```text
build/libs/
```

## Quick Gameplay Test

1. Launch with `.\gradlew.bat runClient`.
2. Create or open a creative test world.
3. Use the Plant Guide Book or seed packets to place Sunflowers, Peashooters, and Wall-nuts.
4. Face the direction you want a plant to defend before placing it.
5. Spawn zombies or use the zombie spawner block.
6. Watch Peashooters fire at hostile monsters while Wall-nuts hold the front line.

Useful summon command:

```mcfunction
/summon plantvszombie:peashooter
```

## Project Layout

```text
src/main/java/com/saharat/plantvszombie/
  entity/      Plant and projectile entities
  item/        Seed packets and plant guide book
  mixin/       Vanilla zombie behavior changes
  registry/   Entity, item, and block registration

src/client/java/com/saharat/plantvszombie/client/
  render/      Client-side entity renderers and model layers

src/main/resources/
  assets/      Models, textures, lang files, and blockstates
  fabric.mod.json
  plantvszombie.mixins.json
```

## Notes

This project is a learning prototype. Review assets, naming, and third-party inspiration before using it outside a personal or educational context.
