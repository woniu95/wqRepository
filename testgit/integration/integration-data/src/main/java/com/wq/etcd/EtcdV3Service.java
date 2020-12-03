package com.wq.etcd;

import com.alibaba.fastjson.JSON;
import io.etcd.jetcd.*;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

/**
 *  etcd v3 api 版本
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-12-03 14:27
 */
public class EtcdV3Service {

    private Client client;

    private KV kvClient;

    private Watch watchClient;

    public  Client initClient(String servierUrl){
        client = Client.builder().endpoints(servierUrl).build();
        watchClient = client.getWatchClient();
        kvClient = client.getKVClient();
        return  client;
    }


    public PutResponse put(String key, String value) throws ExecutionException, InterruptedException {
        if(key == null || value == null)
            return null;
        ByteSequence keySequence = ByteSequence.from(key.getBytes());
        ByteSequence valueSequence = ByteSequence.from(value.getBytes());
        // put the key-value
        return kvClient.put(keySequence, valueSequence).get();
    }

    public KeyValue get(String key) throws ExecutionException, InterruptedException {

        ByteSequence keySequence = ByteSequence.from(key.getBytes());
        // get the CompletableFuture
        CompletableFuture<GetResponse> getFuture = kvClient.get(keySequence);

        // get the value from CompletableFuture
        GetResponse response = getFuture.get();
        List<KeyValue> matchResults =  response.getKvs();
        if(CollectionUtils.isEmpty(matchResults)){

        }
        return !CollectionUtils.isEmpty(matchResults) ?  matchResults.get(0) : null;
    }

    public void watch(String key, Consumer consumer){

        ByteSequence keySequence = ByteSequence.from(key.getBytes());

        watchClient.watch(keySequence, consumer);
    }

    public List<KeyValue> delete(String key) throws ExecutionException, InterruptedException {
        ByteSequence keySequence = ByteSequence.from(key.getBytes());
        // delete the key
        DeleteResponse deleteResponse = kvClient.delete(keySequence).get();
        return deleteResponse.getPrevKvs();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        EtcdV3Service etcdV3Service = new EtcdV3Service();
        etcdV3Service.initClient("http://localhost:2379");
        etcdV3Service.watch("testKey", o -> System.out.println(JSON.toJSONString(o)));
        etcdV3Service.put("testKey", "testValue");
        System.out.println(JSON.toJSONString(etcdV3Service.get("testKey")));
        System.out.println(JSON.toJSONString(etcdV3Service.delete("testKey")));

    }
}
