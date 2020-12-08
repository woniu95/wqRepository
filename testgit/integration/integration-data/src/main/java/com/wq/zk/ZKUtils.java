package com.wq.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-12-04 11:12
 */
public class ZKUtils {

    ZooKeeper zk;

    public void initZooKeeper(String hostPort) throws IOException {
        zk = new ZooKeeper(hostPort, 3000, null);
    }

    public String get(String path) throws KeeperException, InterruptedException {
       return String.valueOf(zk.getChildren(path, false));
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZKUtils zkUtils = new ZKUtils();
        zkUtils.initZooKeeper("127.0.0.1:2181");
        System.out.println(zkUtils.get("/"));
    }
}
