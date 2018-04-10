package com.unidt.http;

import com.google.gson.Gson;
import com.unidt.gson.PosData;
import com.unidt.tools.kafka.FraPipe;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

/**
 * Created by admin on 2018/3/9.
 */


@RestController
@EnableAutoConfiguration
@ComponentScan

public class FraHttpApplication {

    public static final Logger LOG = LoggerFactory.getLogger(FraHttpApplication.class);

    @RequestMapping(value = "/put/{data}", method = RequestMethod.GET)
    public String putString(@PathVariable("data") String data){
        LOG.info(data);
        return data;
    }

    /**
     * 接收POST请求发送过来的数据，并写入Kafka
     * @param data
     * @return
     */
    @PostMapping(value = "/put")
    public String putData(@RequestBody String data) {
        LOG.info("POST请求: " + data);
        Gson gson = new Gson();
        PosData pd = gson.fromJson(data, PosData.class);
        FraPipe pipe = new FraPipe();
        Producer<String, String> producer = pipe.getProducer();

        LOG.info("Recv Post Request:");

        for ( String item: pd.data){
            pipe.pushData(producer, pd.topic, "data", item);
        }
        producer.close();
        return "ok";
    }

    public static void main(String[] args){
        SpringApplication.run(FraHttpApplication.class,args);
    }

}
