#!/bin/bash
# population
# parameter 2 = 
# 
export LD_LIBRARY_PATH="$LD_LIBRARY_PATH:/home/daan/Desktop/test"
echo "start" > Schaffers.txt
echo "start" > BentCigar.txt
echo "start" > Katsuura.txt
population=100
learningRate=1.01 #1/0.817 #
updateInterval=5
count=0
for l in 3 # 2 3
do

#echo "parameter: "$l >> Katsuura.txt 

for p in 40
do

for mu in 0 1 2 # experiment mu/lambda: 80.0 100.0 120.0
do

for u in 5
do

for d in 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
do
count=$((count + 1))
echo $count
echo "Run: "$count >> BentCigar.txt
echo "Run: "$count >> Katsuura.txt
echo "Run: "$count >> Schaffers.txt
echo "public class settings {" > settings.java
echo "int population;" >> settings.java
echo "double learningRate;" >> settings.java
echo "double updateInterval;" >> settings.java
echo "double mu;" >> settings.java
echo "settings() {" >> settings.java
echo "this.population =" $p ";" >> settings.java
echo "this.learningRate =" $l ";" >> settings.java
echo "this.updateInterval =" $u ";" >> settings.java
echo "this.mu =" $mu ";" >> settings.java
echo "}" >> settings.java
echo "}" >> settings.java

javac -cp contest.jar player19.java vector.java scalar.java lib.java settings.java
jar cmf MainClass.txt submission.jar player19.class vector.class scalar.class lib.class settings.class
echo 'running Schaffers'
java -jar testrun.jar -submission=player19 -evaluation=SchaffersEvaluation -seed=1 >> Schaffers.txt
echo 'running BentCigar'
java -jar testrun.jar -submission=player19 -evaluation=BentCigarFunction -seed=1  >> BentCigar.txt
echo 'running Katsuura'
java -jar testrun.jar -submission=player19 -evaluation=KatsuuraEvaluation -seed=1 >> Katsuura.txt

#echo "variable learning rate" $l 
#echo "pop" $p 
#echo "update" $u 

done

done

done

done

done
exit 0
