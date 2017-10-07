# population
# parameter 2 = 
# 
for number in 20 40 60 80 100 120 160 200
do
echo $number
echo 'start writing'

echo "public class settings {" > settings.java
echo "int population;" >> settings.java
echo "settings() {" >> settings.java
echo "this.population =" $number ";" >> settings.java
echo "}" >> settings.java
echo "}" >> settings.java

echo 'compiling'
javac -cp contest.jar player19.java vector.java scalar.java lib.java settings.java
jar cmf MainClass.txt submission.jar player19.class vector.class scalar.class lib.class settings.class
#echo 'running Schaffers'
java -jar testrun.jar -submission=player19 -evaluation=SchaffersEvaluation -seed=1 #> Schaffers.txt
#echo 'running BentCigar'
java -jar testrun.jar -submission=player19 -evaluation=BentCigarFunction -seed=1 #> BentCigar.txt
#echo 'running Katsuura'
java -jar testrun.jar -submission=player19 -evaluation=KatsuuraEvaluation -seed=1 #> Katsuura.txt

done

# population = 100
# parameter 2 = 
# 


exit 0
