
Installing Spark (on Ubuntu 18.04 LTS, UBuntu 14.04)
---------------------------------------
(wherever the sudo command is used, you may have to provide the admin password).

1. Download Spark:
http://spark.apache.org/downloads.html

Follow Steps 1, 2, 3, 4.
Step 4 is optional.

2. Unzip and move spark to /usr/lib/. Note that the name of tgz file you have downloaded could be different 
    from the one given in step 2a.

2a. machineName:~$tar xzvf spark-2.3.0-bin-hadoop2.7.tgz

2b. machineName:~$mv spark-2.3.0-bin-hadoop2.7 spark

2c. machineName:~$sudo mv spark /usr/lib/


3. Install Java 8 by following the steps at the website below. You may have to use “apt-get” instead of “apt” 
    in some of the commands, e.g. instead of
sudo apt update
You may have to issue the command
sudo apt-get update

http://tipsonubuntu.com/2016/07/31/install-oracle-java-8-9-ubuntu-16-04-linux-mint-18/

4. At the end of ~/.bashrc, add the following and save:

export JAVA_HOME=/usr/lib/jvm/java-8-oracle
export SPARK_HOME=/usr/lib/spark
export PATH=$PATH:$SPARK_HOME/bin

5. Open a new terminal and verify installation by running the command below. The spark-shell should start up.

machineName: spark-shell

6. Configure spark so that only messages of type ERROR are printed in the console.

6a. cd /usr/lib/spark/conf
6b. sudo cp log4j.properties.template log4j.properties
6c. change INFO to ERROR in file log4j.properties as shown below:
sudo vi log4j.properties

change
log4j.rootCategory=INFO, console

to

log4j.rootCategory=ERROR, console

exit log4j.properties after saving it.

Installing Ubuntu on Windows (Warning: The steps in the article below have not been tested)
---------------------------------------------------------------------------------------------------------------------------
Installing Ubuntu on Windows includes three major steps, install (Oracle) VirtualBox,
installing Ubuntu on the vbox, and virtual guest box additions.Details can be found in the article
below:
https://www.lifewire.com/install-ubuntu-linux-windows-10-steps-2202108
