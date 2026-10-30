# FarmZ
FarmZ adds some features to farming like golden crops, watering cans, sprinklers and fixed timed crop growth.

### Installation
FarmZ is a mod built for the [Fabric Loader](https://fabricmc.net/). It requires [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) and [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config) to be installed separately; all other dependencies are installed with the mod.

### License
FarmZ is licensed under MIT.

### Datapacks
Golden crop chance items and fixed timed crop growth are data driven.

If you don't know how to create a datapack check out [Data Pack Wiki](https://minecraft.wiki/w/Data_Pack)
website and try to create your first one for the vanilla game.

#### Golden crop chance
Worn items increase the chance that a planted crop turns into a golden crop. The value is the added chance in percent.
Put the json file inside data/modid/farmer/YOURFILE.json

Example:
```json
{
  "replace": false,
  "farmz:farmers_hat": 5,
  "minecraft:iron_chestplate": 2
}
```

#### Fixed timed crop growth
If the config option `timedCropGrowth` is enabled (default), listed crops no longer grow by random ticks. Instead each crop grows one stage after a fixed time, so the full growth from planting to harvest takes the set amount of ticks (20 ticks = 1 second, 24000 ticks = 1 Minecraft day).
Only crop blocks (blocks extending the vanilla crop block) are supported. A value of 0 removes the crop from fixed timed growth, so it grows by random ticks again.
Put the json file inside data/modid/crop_growth/YOURFILE.json

FarmZ provides data/farmz/crop_growth/default.json:
```json
{
  "replace": false,
  "minecraft:wheat": 24000,
  "minecraft:carrots": 24000,
  "minecraft:potatoes": 24000,
  "minecraft:beetroots": 18000,
  "minecraft:torchflower_crop": 12000
}
```
