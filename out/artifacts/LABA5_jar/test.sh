#!/usr/bin/bash
mkdir ./lab0
mkdir ./lab0/haunter1
mkdir ./lab0/hypno3
mkdir ./lab0/zubat5
touch ./lab0/gardevoir2
touch ./lab0/golbat7
touch ./lab0/gothita8
touch ./lab0/haunter1/venonat
touch ./lab0/haunter1/ducklett
mkdir ./lab0/haunter1/joltik
touch ./lab0/haunter1/vespiquen
touch ./lab0/hypno3/togepi
touch ./lab0/hypno3/pupitar
mkdir ./lab0/hypno3/monferno
mkdir ./lab0/zubat5/ursaring
touch ./lab0/zubat5/pineco
mkdir ./lab0/zubat5/bayleef
touch ./lab0/zubat5/drapion
touch ./lab0/zubat5/electrike
echo "Способности Mind Mold Synhronize">>./lab0/gardevoir2
echo "Trace">>./lab0/gardevoir2
echo "Способности Astonish Bite Wing Attack">>./lab0/golbat7
echo "Confuse Ray Swift Air Cutter Acrobatics Mean Look Poison Fang Haze Air">>./lab0/golbat7
echo "Slash">>./lab0/golbat7
echo "Возможности Overland=6 Surface=4 Jump=1 Power=1">>./lab0/gothita8
echo "Intelligence=4 Telekinetic=0">>./lab0/gothita8
echo "Способности Sawrm Venom">>./lab0/haunter1/venonat
echo "Compoundeyes Tinted Lens">>./lab0/haunter1/venonat
echo "Возможности Overland=2 Surface=6">>/lab0/haunter1/ducklett
echo "Sky=6 Underwater=4 Jump3 Power=1 Intelligense=3">>/lab0/haunter1/ducklett
echo "Fountain=0">>/lab0/haunter1/ducklett
echo "satk=8 sdef=10 spd=4">>/lab0/haunter1/vespiquen
echo "Развитые">>./lab0/hypno3/togepi
echo "Способности Super Luck">>./lab0/hypno3/togepi
echo "Способности Sandstorm Screech Chip">>/lab0/hypno3/pupitar
echo "Away Rock Slide Scary Face Thrash Dark Pulse Paybak Crunch Earthquake">>/lab0/hypno3/pupitar
echo "Stone Edge Hyper Beam">>/lab0/hypno3/pupitar
echo "Возможности Overland=3 Jump=4 Power=1">>./lab0/zubat5/pineco
echo "Intelligence=3"Sinker=0 Threaded=0">>./lab0/zubat5/pineco
echo "Ходы Aqua Tail Bite Bug">>./lab0/zubat5/drapion
echo "Bite Dark Pulse Fire Fang Fury Cutter Ice Fang Iron Tail Knock Off">>./lab0/zubat5/drapion
echo "Mude-Slap Pin Missile Rock Climb Sleep Talk Snore Thunder">>./lab0/zubat5/drapion
echo "Fang">>./lab0/zubat5/drapion
echo "Тип покемона ELECTRIC NONE">>./lab0/zubat5/drapion 
chmod go-r gardevoir2
chmod g+w gardevoir2
chmod go-r golbat7
chmod u-w golbat7
chmod ug-r gothita8
chmod u-w gothita8
chmod 333 haunter1
chmod 755 hypno3
chmod 551 zubat5
cd haunter1
chmod u-w venonat
chmod o+w venonat
chmod 375 joltik
chmod g-r+w vespiquen
cd ..
cd hypno3
chmod 440 togepi
chmod 006 pupitar
chmod 375 monferno
cd ../zubat5
chmod 361 ursaring
chmod 044 pineco
chmod 700 bayleef
chmod 444 drapion
chmod 604 electrike
cd ..
ln -s haunter1 Copy_58
cp -r zubat5 zubat5/ursaring/
chmod u+rw gothita8
chmod u+w zubat5
ln -s gothita8 zubat5/drapiongothita
chmod u-rw gothita8
chmod u-w zubat5
chmod u+w zubat5
ln gardevoir2 zubat5/drapiongardevoir
chmod u-w zubat5
cp golbat7 haunter1/joltik
cp golbat hypno3/pupitargolbat
chmod u+r hypno3/pupitar
cat hypno3/pupitar haunter1/vespiquen>gardevoir2_47
chmod u-r hypno3/pupitar
wc -l ./*t ./*/*t ./*/*/*t  ./*/*/*/*t >/tmp/lion1 2>1
ls -R | sort -k9
grep -ine “m$” hypno3/togepi hypno3/pupitar haunter1/venonat haunter1/vespiquen haunter1/ducklett
ls -Rl | sort -nk5 | tail -2
cat hypno3/togepi haunter1/ducklett haunter1/vespiquen | sort | 2>tmp/err2
ls -all | grep -v “^l” | sort -nrk2
rm gothita8
chmod u+w gothita8
rm gothita8
chmod u+w haunter1/venonat
rm haunter1/venonat
chmod u+w zubat5
rm zuba5/drapiongothi*
rm zubat5/drapiongarde* 
chmod u+w haunter1/joltik/golbat7 
rm haunter1/joltik/golbat7 | rm haunter1/joltik/golbat7 | rm haunter1/joltik
rm haunter1/ducklet
chmod u+w haunter1/venonat | rm haunter1/venonat
rm haunter1/vespiquen
rmdir haunter1
