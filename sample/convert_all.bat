@echo off
if %1.==. goto help
if %2.==. goto help
if %3.==. goto help
if %4.==. goto help

goto convert

:help
echo Argument 1: relative path to fomap files 
echo Argument 2: items and crittes proto id SOURCE folder
echo Argument 3: items and crittes proto id TARGET folder
echo Argument 4: output folder relative path for converted maps and logs

goto end

:convert

rem echo Item proto source files:
setlocal enabledelayedexpansion enableextensions
set ItemProtosSource=
for %%x in (%2\items\*.fopro) do (
	set ItemProtosSource=!ItemProtosSource! %%x
)
set ItemProtosSource=%ItemProtosSource:~1%

rem echo %ItemProtosSource%

rem echo Item proto target files:
set ItemProtosTarget=
for %%x in (%3\items\*.fopro) do (
	set ItemProtosTarget=!ItemProtosTarget! %%x
)
set ItemProtosTarget=%ItemProtosTarget:~1%

rem echo %ItemProtosTarget%


echo Generating items proto mapping file
java -jar foclassic-map-converter.jar -gipm %4\items_verbose.mapping -pms %ItemProtosSource% -pmt %ItemProtosTarget% -lp out -ll error

rem echo Critter proto source files:
setlocal enabledelayedexpansion enableextensions
set CritterProtosSource=
set asdf=
for %%x in (%2\critters\*.fopro) do (
	set CritterProtosSource=!CritterProtosSource! %%x
)
set CritterProtosSource=%CritterProtosSource:~1%

rem echo %CritterProtosSource%

rem echo Critter proto target files:
set CritterProtosTarget=
for %%x in (%3\critters\*.fopro) do (
	set CritterProtosTarget=!CritterProtosTarget! %%x
)
set CritterProtosTarget=%CritterProtosTarget:~1%

rem echo %CritterProtosTarget%

echo Generating items proto mapping file
java -jar foclassic-map-converter.jar -gcpm %4\critters.mapping -pms %CritterProtosSource% -pmt %CritterProtosTarget% -lp out -ll error

echo ASDFASDFASFFSAD


set maps=
for %%x in (%1\*.fomap) do (
	set map=%%x
	echo %%x %4\%%~nx_converted.fomap
    java -jar foclassic-map-converter.jar -ms %%x -mt %4\%%~nx_converted.fomap -ipm %4\items_verbose.mapping -cpm %4\critters.mapping -mph remove -lp out -ll error
)
set maps=%maps:~1%

rem echo %maps%

echo Converting map %1
rem java -jar foclassic-map-converter.jar -ms %1 -mt %4\%~n1_converted.fomap -ipm %4\items_verbose.mapping -cpm %4\critters.mapping -mph remove -lp out -ll error

:end
