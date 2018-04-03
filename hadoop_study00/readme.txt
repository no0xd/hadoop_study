1.本机环境变量
   HADOOP_HOME
   指向hadoop2.6即可 D:\comm\hadoop2.6

2.本机环境变量
   path 加入 %HADOOP_HOME%\bin


3.如果我们的代码中没有指定fs.defaultFS，并且工程classpath下也没有给定相应的配置，
  conf中的默认值就来自于hadoop的jar包中的core-default.xml，默认值为： file:///，
  则获取的将不是一个DistributedFileSystem的实例，而是一个本地文件系统的客户端对象LocalFileSystem.

4.设置上传时的文件所有者
    conf.set("fs.defaultFS", "hdfs://node01:9000");
    不设置时,默认为admin
    drwxr-xr-x   - 1    supergroup          0 2018-04-03 13:48 /user
    drwxr-xr-x   - 1    supergroup          0 2018-04-03 13:48 /user/1
    -rw-r--r--   3 1    supergroup         35 2018-04-03 13:48 /user/1/aa.txt

    hdfs dfs -rmr /user/1/*
    删除后,回收站结构
    drwx------   - root supergroup          0 2018-04-03 13:50 /user/root/.Trash
    drwx------   - root supergroup          0 2018-04-03 13:50 /user/root/.Trash/Current
    drwx------   - root supergroup          0 2018-04-03 13:50 /user/root/.Trash/Current/user
    drwx------   - root supergroup          0 2018-04-03 13:50 /user/root/.Trash/Current/user/1
    -rw-r--r--   3 1    supergroup         35 2018-04-03 13:48 /user/root/.Trash/Current/user/1/aa.txt
    清空回收站
    hdfs dfs -rmr /user/root/*

    fs = FileSystem.get(new URI("hdfs://node01:9000"), conf, "root");
    该种方式上传时,文件所有者为root


5.删除文件 hdfs dfs -rmr /user/root/*

6.删除回收站文件 hdfs dfs -rmr /user/root/*

