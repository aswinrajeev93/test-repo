package com.mes.soa.data.config;


import java.util.Arrays;
import java.util.Collections;

import javax.cache.configuration.FactoryBuilder;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.kubernetes.TcpDiscoveryKubernetesIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mes.soa.data.cachestore.BatchStore;
import com.mes.soa.data.model.Batch;

import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;


@Configuration
public class IgniteConfig {
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Bean("ignite")
	public Ignite createConfig() {

		Ignite ignite = null;
		
		CacheConfiguration<Integer, Batch> batchCacheCfg = new CacheConfiguration<>();
		batchCacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(BatchStore.class));
		batchCacheCfg.setReadThrough(true);
		batchCacheCfg.setWriteThrough(true);
		batchCacheCfg.setName("batch");
		batchCacheCfg.setQueryEntities(
				Collections.singleton(new QueryEntity(Integer.class.getName(), Batch.class.getName())));
				
		IgniteConfiguration cfg = new IgniteConfiguration();
		cfg.setIgniteInstanceName("ignite.cfg");
		cfg.setClientMode(true);

		TcpDiscoverySpi spi = new TcpDiscoverySpi();
		if(activeProfile.equals("prod"))
		{
			TcpDiscoveryKubernetesIpFinder ipFinder = new TcpDiscoveryKubernetesIpFinder();
			ipFinder.setNamespace("ignite");
		    ipFinder.setMasterUrl("https://api-mes-k8s-local-3akmf5-47551496.us-east-2.elb.amazonaws.com");
		    spi.setIpFinder(ipFinder);
		}
		else {
		
			TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
			ipFinder.setAddresses(Arrays.asList("52.14.188.8"));
			spi.setIpFinder(ipFinder);
		}
		
		cfg.setDiscoverySpi(spi);		
		cfg.setCacheConfiguration(batchCacheCfg);
		try {
			ignite = Ignition.start(cfg);
		}
		catch(Exception e) {
			System.out.println("Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return ignite;

	}
}
