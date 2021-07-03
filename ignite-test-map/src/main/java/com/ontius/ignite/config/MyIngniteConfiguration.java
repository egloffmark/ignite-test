package com.ontius.ignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ontius.ignite.model.Item;
import com.ontius.ignite.repo.SQLFunctions;


@EnableIgniteRepositories(basePackages = { "com.ontius.ignite.repo" })
@Configuration
public class MyIngniteConfiguration {

	static final String DATA_CONFIG_NAME = "ItemStorage";
	
	@Bean
	public IgniteConfiguration igniteConfig() {
		
		IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        // Enabling peer-class loading feature.
		igniteConfiguration.setPeerClassLoadingEnabled(true);
		igniteConfiguration.setClientMode(false);
		// Data Storage
		DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
	    dataStorageConfiguration.setPageSize(4 * 1024);
	    // Data Region
	    DataRegionConfiguration dataRegionConfiguration = new DataRegionConfiguration();
	    dataRegionConfiguration.setName(DATA_CONFIG_NAME);
	    dataRegionConfiguration.setInitialSize(100 * 1000 * 1000);
	    dataRegionConfiguration.setMaxSize(200 * 1000 * 1000);
	    dataRegionConfiguration.setPersistenceEnabled(true);
	    dataStorageConfiguration.setDataRegionConfigurations(dataRegionConfiguration);
	    igniteConfiguration.setDataStorageConfiguration(dataStorageConfiguration);
	    igniteConfiguration.setConsistentId("ItemFileSystem");
	    
		CacheConfiguration<Long,Item> itemCache=new CacheConfiguration<Long,Item>();
		itemCache.setCopyOnRead(false);
		// as we have one node for now
		itemCache.setBackups(1);
		itemCache.setAtomicityMode(CacheAtomicityMode.ATOMIC);
		itemCache.setName("ItemCache");
		itemCache.setSqlFunctionClasses(SQLFunctions.class);
		itemCache.setDataRegionName(DATA_CONFIG_NAME);
		itemCache.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
		itemCache.setIndexedTypes(Long.class,Item.class);	
		igniteConfiguration.setCacheConfiguration(itemCache);
		return igniteConfiguration;
	}
	
    @Bean(name = "igniteInstance",destroyMethod = "close")
    public Ignite ignite(IgniteConfiguration igniteConfiguration) throws IgniteException {
	    final Ignite ignite = Ignition.start(igniteConfiguration);
	    // Activate the cluster. Automatic topology initialization occurs
	    // only if you manually activate the cluster for the very first time.
	    // ignite.cluster().active(true);
	    ignite.cluster().state(ClusterState.ACTIVE);
	    return ignite;
    }
}
