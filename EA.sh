@#!/bin/bash
for number in 3 2 1
do
echo $number
done
echo 'start'
java -jar testrun.jar -submission=player19 -evaluation=SchaffersEvaluation -seed=1 > Schaffers.txt
java -jar testrun.jar -submission=player19 -evaluation=BentCigarFunction -seed=1 > BentCigar.txt
java -jar testrun.jar -submission=player19 -evaluation=KatsuuraEvaluation -seed=1 > Katsuura.txt
exit 0
