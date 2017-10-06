for number in 3 2 1
do
echo $number
done
echo 'start'
echo 'compiling'
javac -cp contest.jar player19.java vector.java scalar.java lib.java
jar cmf MainClass.txt submission.jar player19.class vector.class scalar.class lib.class
echo 'running Schaffers'
java -jar testrun.jar -submission=player19 -evaluation=SchaffersEvaluation -seed=1 #> Schaffers.txt
echo 'running BentCigar'
java -jar testrun.jar -submission=player19 -evaluation=BentCigarFunction -seed=1 #> BentCigar.txt
echo 'running Katsuura'
java -jar testrun.jar -submission=player19 -evaluation=KatsuuraEvaluation -seed=1 #> Katsuura.txt
exit 0
