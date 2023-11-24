## LDTS_T1G06 - Wolfenstein Mock

> (Pequeno resumo do jogo).

This project was developed by Henrique Fernandes, Rafael Magalhães and Ricardo Oliveira for LDTS 23/24.

### IMPLEMENTED FEATURES

- **3D View** - The game character has a 3D view based on raycasting.

### PLANNED FEATURES

- **Multiple Rooms** - The overall game will be a sequence of rooms/levels connected by doors to one another.
- **Enemies** - The game will present different enemies with different behaviours.
- **Shooting** - The player will possess a gun and ammunition and my launch projectiles to defeat the enemy.
- **Health and Ammo Packs** - The player will be able to pick up packs in order to replenish his health and ammo.

### DESIGN

#### DRAWING THE MAP AND PLAYER VIEW ON THE SCREEN

**Problem in Context**

The game screen consists of two parts, the player view and the map of the room he is in. However drawing the player camera depends on the map and drawing the map depends on the player. This is a violation of the DIP(Dependency Inversion Principle)

**The Pattern**

The Factory Design Pattern can help us to avoid the circular dependencies. By creating a class to act as an interface between the player and map, both of these will depend on the inferface, making the dependency non-circular.

**Implementation**

A class Camera (it was not named Screen to avoid confusion with the Lanterna Library) was created in order to act as an interface between the Player and Map classes.

**Consequences**

- The Player and Map classes were sucessfully isolated and no longer depend on eachother.
- The Camera class allows for further extension to the screen if desired (for example to switch to the menu screen).
- Ensures simplicity for the client, who only needs to request screen drawing from the Camera class, instead of every sing individual component.

#### KNOWN CODE SMELLS

We have not searched for code smells yet.

### TESTING

Testing?

### SELF-EVALUATION

- Henrique Fernandes: 33%
- Rafael Magalhães: 33%
- Ricardo Oliveira: 33%
