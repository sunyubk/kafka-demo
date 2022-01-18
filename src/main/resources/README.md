### SpringBoot安装集成kafka,实现消息的发送和接收:     
**启动zoo：**      
`docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper`

**启动kafka：**    
`docker run -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=192.168.224.128:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.224.128:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka`

**进入kafka容器：**      
`docker exec -it kafka /bin/bash`

**进入kafka启动命令所在的目录：**   
`cd opt/kafka_x.xx-x.x.x/bin`

**运行生产者发送消息：**  
`./kafka-console-producer.sh --broker-list localhost:9092 --topic mykafka`

**运行消费者接收消息：**  
`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic mykafka --from-beginning`