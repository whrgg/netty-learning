package cn.itcast.config;

import cn.itcast.protocol.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    static Properties properties;
    static {
        try(InputStream in = Config.class.getResourceAsStream("/application.properties")){
            properties = new Properties();
            properties.load(in);
        }catch (IOException e){
            throw new ExceptionInInitializerError(e);
        }
    }

    public static int getServerPort(){
        String value = properties.getProperty("server.port");
        return value==null?8080:Integer.valueOf(value);
    }

    public static Serializer.Algorithm getSerializerAlgorithm(){
        String value = properties.getProperty("serializer.algorithm");
        return value==null? Serializer.Algorithm.Java: Serializer.Algorithm.valueOf(value);
    }
}
