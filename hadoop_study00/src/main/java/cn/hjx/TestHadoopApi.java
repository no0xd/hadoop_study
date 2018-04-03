package cn.hjx;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

public class TestHadoopApi {

    private static final Logger LOG = Logger.getLogger(TestHadoopApi.class);
    FileSystem fs = null;

    @Before
    public void init() throws Exception {
        Configuration conf = new Configuration();
        fs = FileSystem.get(new URI("hdfs://node01:9000"), conf, "root");
        if (fs != null) {
            LOG.info("fs is ready");
        }
    }

    @After
    public void done() throws Exception {
        LOG.info("done");
        fs.close();
    }

    @Test
    public void testConf() {
        Configuration conf = fs.getConf();
        Iterator<Map.Entry<String, String>> iterator = conf.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            LOG.info(entry.getKey() + "--" + entry.getValue());//conf加载的内容
        }
    }

    @Test
    public void testUpload() throws Exception {
        fs.copyFromLocalFile(new Path("c://a.txt"), new Path("aa.txt"));
    }

    @Test
    public void testDownload() throws Exception {
        fs.copyToLocalFile(new Path("aa.txt"), new Path("d://"));
    }

    @Test
    public void makdirTest() throws Exception {
        boolean flag = fs.mkdirs(new Path("/aaa/bbb"));
        LOG.info("创建状态:" + flag);
    }

    @Test
    public void testList() throws Exception {
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : listStatus) {
           getListStatus(fileStatus);
        }
    }

    @Test
    public void testListRecursive() throws IOException {
        //会递归找到所有的文件
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus next = listFiles.next();
            String name = next.getPath().getName();
            Path path = next.getPath();
            LOG.info(name + "---" + path.toString());
        }
    }

    public void getListStatus(FileStatus fileStatus) throws IOException {
        if (fileStatus.isDirectory()) {
            LOG.info(fileStatus.getPath()+"(D)");
            FileStatus[] fileStatuses = fs.listStatus(fileStatus.getPath());
            for(FileStatus fs:fileStatuses){
                getListStatus(fs);
            }
        } else {
            LOG.info(fileStatus.getPath()+"(F)");
        }
    }


    public void testLog4j() throws Exception {
        LOG.info("aaaaaaa");
    }
}
