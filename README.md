# Custom Durability Plugin
Increases the durability of tools

## Configuration
config.yml has a single configuration ```durability-multiplier``` that specifies how much longer all tools will last. It defaults to 3, which means that tools will last 3x longer.

## Commands

### ```/durability```
Returns the basis and projected durabilities of the item in your
priamry hand. Basis is the durability tracked by this plugin, and projected
is the projection of that durability onto the inherent durability of the item in Minecraft. 

## Development
Development was done in IntelliJ with OpenJDK 13.0.2, the latest version
of the JDK that is still compatible with Minecraft 1.14. 