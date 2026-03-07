# FarmZ
FarmZ adds some features to farming like golden crops.

### Installation
FarmZ is a mod built for the [Fabric Loader](https://fabricmc.net/). It requires [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) and [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config) to be installed separately; all other dependencies are installed with the mod.

### License
FarmZ is licensed under MIT.

### Datapacks
Worn item golden crop chance is data driven.

If you don't know how to create a datapack check out [Data Pack Wiki](https://minecraft.wiki/w/Data_Pack)
website and try to create your first one for the vanilla game. Each existing file can be overriden by setting replace = true.
Put the json file inside data/modid/farmer/YOURFILE.json

Example:
```json
{
  "replace": false,
  "farmz:farmers_hat": 5,
  "minecraft:iron_chestplate": 2
}
```