package com.mes.soa.data.cachestore;

import java.util.Collection;
import java.util.Map;
import javax.cache.Cache.Entry;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.lang.IgniteBiInClosure;
import com.mes.soa.data.model.RefBatchSourceCode;

public class RefBatchSourceCodeStore implements CacheStore<Integer, RefBatchSourceCode> {

	@Override
	public RefBatchSourceCode load(Integer key) throws CacheLoaderException {
		return null;
	}

	@Override
	public Map<Integer, RefBatchSourceCode> loadAll(Iterable<? extends Integer> keys) throws CacheLoaderException {
		return null;
	}

	@Override
	public void write(Entry<? extends Integer, ? extends RefBatchSourceCode> entry) throws CacheWriterException {
	}

	@Override
	public void writeAll(Collection<Entry<? extends Integer, ? extends RefBatchSourceCode>> entries)
			throws CacheWriterException {
	}

	@Override
	public void delete(Object key) throws CacheWriterException {
	}

	@Override
	public void deleteAll(Collection<?> keys) throws CacheWriterException {
	}

	@Override
	public void loadCache(IgniteBiInClosure<Integer, RefBatchSourceCode> clo, Object... args)
			throws CacheLoaderException {
	}

	@Override
	public void sessionEnd(boolean commit) throws CacheWriterException {
	}

}
