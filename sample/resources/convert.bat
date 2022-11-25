echo off
if %1.==. goto help
if %1 == help goto help2
if %2.==. goto help
if %3.==. goto help

goto convert

goto end

:help
echo Argument 1: fomap file
echo Argument 2: items and crittes proto id folder
echo Argument 3: output folder

goto end

:help2

echo Help2

goto end

:convert

echo Converting!!!
java -jar target\foclassic-map-converter.jar -gipm %~dp1\out\items_verbose.mapping -pms %~dp1\resources/tlamk2/proto/items/animals.fopro %~dp1\resources/tlamk2/proto/items/ammo.fopro %~dp1\resources/tlamk2/proto/items/armor.fopro %~dp1\resources/tlamk2/proto/items/book.fopro %~dp1\resources/tlamk2/proto/items/car.fopro %~dp1\resources/tlamk2/proto/items/container.fopro %~dp1\resources/tlamk2/proto/items/door.fopro %~dp1\resources/tlamk2/proto/items/drug.fopro %~dp1\resources/tlamk2/proto/items/generic.fopro %~dp1\resources/tlamk2/proto/items/grid.fopro %~dp1\resources/tlamk2/proto/items/key.fopro %~dp1\resources/tlamk2/proto/items/misc.fopro %~dp1\resources/tlamk2/proto/items/wall.fopro %~dp1\resources/tlamk2/proto/items/weapon.fopro -pmt %~dp1\resources/foclassic/proto/items/ammo.fopro %~dp1\resources/foclassic/proto/items/armor.fopro %~dp1\resources/foclassic/proto/items/blueprint.fopro %~dp1\resources/foclassic/proto/items/car.fopro %~dp1\resources/foclassic/proto/items/container.fopro %~dp1\resources/foclassic/proto/items/movable_container.fopro %~dp1\resources/foclassic/proto/items/door.fopro %~dp1\resources/foclassic/proto/items/drug.fopro %~dp1\resources/foclassic/proto/items/dynamic.fopro %~dp1\resources/foclassic/proto/items/generic.fopro %~dp1\resources/foclassic/proto/items/grid.fopro %~dp1\resources/foclassic/proto/items/helmet.fopro %~dp1\resources/foclassic/proto/items/key.fopro %~dp1\resources/foclassic/proto/items/map_object.fopro %~dp1\resources/foclassic/proto/items/misc.fopro %~dp1\resources/foclassic/proto/items/smo.fopro %~dp1\resources/foclassic/proto/items/spot.fopro %~dp1\resources/foclassic/proto/items/transfer.fopro %~dp1\resources/foclassic/proto/items/trigger.fopro %~dp1\resources/foclassic/proto/items/wall.fopro %~dp1\resources/foclassic/proto/items/weapon.fopro -lp out -ll warning 

java -jar ../target/foclassic-map-converter.jar -gcpm out/critters.mapping -pms %~dp1\resources/tlamk2/proto/critters/fallout2.fopro %~dp1\resources/tlamk2/proto/critters/tla.fopro %~dp1\resources/tlamk2/proto/critters/tlamk2.fopro %~dp1\resources/tlamk2/proto/critters/TlaMK2Dex.fopro -pmt %~dp1\resources/foclassic/proto/critters/aliens %~dp1\resources/foclassic/proto/critters/brahmins %~dp1\resources/foclassic/proto/critters/deathclaws %~dp1\resources/foclassic/proto/critters/dogs %~dp1\resources/foclassic/proto/critters/geckos %~dp1\resources/foclassic/proto/critters/ghouls %~dp1\resources/foclassic/proto/critters/insects %~dp1\resources/foclassic/proto/critters/mutants %~dp1\resources/foclassic/proto/critters/plants %~dp1\resources/foclassic/proto/critters/radscorpions %~dp1\resources/foclassic/proto/critters/rats %~dp1\resources/foclassic/proto/critters/robots %~dp1\resources/foclassic/proto/critters/bandits %~dp1\resources/foclassic/proto/critters/citizens %~dp1\resources/foclassic/proto/critters/encounter %~dp1\resources/foclassic/proto/critters/guards %~dp1\resources/foclassic/proto/critters/merchants %~dp1\resources/foclassic/proto/critters/slavers %~dp1\resources/foclassic/proto/critters/slaves %~dp1\resources/foclassic/proto/critters/tribals %~dp1\resources/foclassic/proto/critters/vips %~dp1\resources/foclassic/proto/critters/2238 %~dp1\resources/foclassic/proto/critters/bounty %~dp1\resources/foclassic/proto/critters/companions %~dp1\resources/foclassic/proto/critters/strangers %~dp1\resources/foclassic/proto/critters/invalid %~dp1\resources/foclassic/proto/critters/dungeons %~dp1\resources/foclassic/proto/critters/crv_encounter %~dp1\resources/foclassic/proto/critters/docan_critters %~dp1\resources/foclassic/proto/critters/crv_guards %~dp1\resources/foclassic/proto/critters/quests %~dp1\resources/foclassic/proto/critters/mob_dynamic -lp out -ll warning 

java -jar ../target/foclassic-map-converter.jar -ms %~dp1\resources/tlamk2/maps/barter_ground.fomap -mt out/barter_ground_converted.fomap -ipm out/items_verbose.mapping -cpm out/critters.mapping -mph remove -lp out -ll warning 

:end
