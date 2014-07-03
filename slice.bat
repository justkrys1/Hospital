@echo off

set filename=SCC-Map.gif

set /a tilesize=256
set /a downsamplesize=1000

set tilesfolder=tiles
set downsamplesfolder=downsamples

mkdir %tilesfolder% %downsamplesfolder%

echo Generate the smaller map images (divide by 2 each time)
echo Generate 1/2
convert %filename% -resize 50%%  map500.jpg
echo Generate 1/4
convert %filename% -resize 25%%  map250.jpg
echo Generate 1/8
convert %filename% -resize 12.5%%  map125.jpg

echo Generate the smaller map images (divide by 2 each time)
convert %filename% -resize %downsamplesize%x%downsamplesize%  ./%downsamplesfolder%/map.gif


echo Generate the downsample map
convert %filename% -crop %tilesize%x%tilesize% -set filename:tile "%%[fx:page.x/%tilesize%]_%%[fx:page.y/%tilesize%]" +repage +adjoin "./%tilesfolder%/1000_%%[filename:tile].gif"

echo Generate the tile for the half size map
convert map500.jpg -crop %tilesize%x%tilesize% -set filename:tile "%%[fx:page.x/%tilesize%]_%%[fx:page.y/%tilesize%]" +repage +adjoin "./%tilesfolder%/500_%%[filename:tile].gif"

echo Generate the tile for the 1/4 size map
convert map250.jpg -crop %tilesize%x%tilesize% -set filename:tile "%%[fx:page.x/%tilesize%]_%%[fx:page.y/%tilesize%]" +repage +adjoin "./%tilesfolder%/250_%%[filename:tile].gif"

echo Generate the tile for the 1/8 size map
convert map125.jpg -crop %tilesize%x%tilesize% -set filename:tile "%%[fx:page.x/%tilesize%]_%%[fx:page.y/%tilesize%]" +repage +adjoin "./%tilesfolder%/125_%%[filename:tile].gif"

echo DONE
pause