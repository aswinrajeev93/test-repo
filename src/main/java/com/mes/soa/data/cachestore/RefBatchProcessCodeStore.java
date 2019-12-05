package com.mes.soa.data.cachestore;

import java.util.Collection;
import java.util.Map;
import javax.cache.Cache.Entry;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.lang.IgniteBiInClosure;
import com.mes.soa.data.model.RefBatchProcessCode;


public class RefBatchProcessCodeStore implements CacheStore<Integer, RefBatchProcessCode> {

	@Override
	public RefBatchProcessCode load(Integer key) throws CacheLoaderException {
		return null;
	}
	@Override
	public Map<Integer, RefBatchProcessCode> loadAll(Iterable<? extends Integer> keys) throws CacheLoaderException {
		return null;
	}
	
	@Override
	public void write(Entry<? extends Integer, ? extends RefBatchProcessCode> entry) throws CacheWriterException {
	}
	
	@Override
	public void writeAll(Collection<Entry<? extends Integer, ? extends RefBatchProcessCode>> entries)
			throws CacheWriterException {
	}
	
	@Override
	public void delete(Object key) throws CacheWriterException {
	}
	
	@Override
	public void deleteAll(Collection<?> keys) throws CacheWriterException {
	}
	
	@Override
	public void loadCache(IgniteBiInClosure<Integer, RefBatchProcessCode> clo, Object... args)
			throws CacheLoaderException {
	}

	@Override
	public void sessionEnd(boolean commit) throws CacheWriterException {
	}

}
