package com.itheima.rpc.server.registry.zk;

import com.itheima.rpc.server.config.RpcServerConfiguration;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Zookeeper连接操作接口
 */
@Component
public class ServerZKit {

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private RpcServerConfiguration rpcServerConfiguration;

    /***
     * 根节点创建
     */
    public void createRootNode() {
        boolean exists = zkClient.exists(rpcServerConfiguration.getZkRoot());
        if (!exists) {
            zkClient.createPersistent(rpcServerConfiguration.getZkRoot());
        }
    }

    /***
     * 创建持续节点
     * @param path
     */
    public void createPersistentNode(String path) {
        String pathName = rpcServerConfiguration.getZkRoot() + "/" + path;
        boolean exists = zkClient.exists(pathName);
        if (!exists) {
            zkClient.createPersistent(pathName);
        }
    }

    /***
     * 创建临时节点
     * @param path
     */
    public void createNode(String path) {
        String pathName = rpcServerConfiguration.getZkRoot() + "/" + path;
        boolean exists = zkClient.exists(pathName);
        if (!exists) {
            zkClient.createEphemeral(pathName);
        }
    }
}
