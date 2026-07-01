# Shellshocked — 2-Player Tank Game

A local two-player artillery/tank game in Java (Swing/AWT). Two tanks trade shots across the screen; each hit chips away at the opponent's health bar until one is destroyed.

## Gameplay
- Two players on one keyboard
- Aim and fire projectiles across the arena
- Health bars, hit detection, and animated visuals (tanks, projectiles, drifting clouds)

## Tech
Java · Swing / AWT (custom `paintComponent` rendering) · `Timer`-driven game loop · `KeyListener` input · `BufferedImage` sprites

## Structure
```
src/
  Shellshocked.java   entry point + game loop / rendering panel
  Tank.java           player tank (position, aiming, health)
  Projectile.java     fired shells + trajectory
  HealthBar.java      per-player health display
  Cloud.java          background animation
```

## Run
```bash
cd src
javac *.java
java Shellshocked
```
