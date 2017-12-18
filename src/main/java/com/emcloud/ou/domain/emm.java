package com.emcloud.ou.domain;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;
import java.util.Queue;

public class emm {
    public void a( ) {
        Config cfg=new Config();
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(cfg);
        Map<Integer, String> customers = hazelcastInstance.getMap( "customers" );
        customers.put( 1, "DaiZiYing" );
        customers.put( 2, "嘻嘻嘻" );
        customers.put( 3, "fairy" );
        customers.putIfAbsent(4, "Azi");
        customers.replace(4, "Azi", "MiRenAzi");
        System.out.println( "Customer with key 1: " + customers.get(4) );
        System.out.println( "Map Size:" + customers.size() );
        Queue<String> queueCustomers = hazelcastInstance.getQueue( "customers" );
        queueCustomers.offer( "Chinese" );
        queueCustomers.offer( "阿紫" );
        queueCustomers.offer( "HanMeiMei" );

        System.out.println( "First customer: " + queueCustomers.poll() );
        System.out.println( "Second customer: "+ queueCustomers.peek() );
        System.out.println( "Queue size: " + queueCustomers.size() );
    }
}
