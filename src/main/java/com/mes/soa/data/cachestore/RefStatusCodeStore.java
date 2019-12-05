package com.mes.soa.data.cachestore;

import java.util.Collection;
import java.util.Map;
import javax.cache.Cache.Entry;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.springframework.lang.Nullable;
import com.mes.soa.data.model.RefStatusCode;

public class RefStatusCodeStore implements CacheStore<Integer ,RefStatusCode >{

	@Override
	public void loadCache(IgniteBiInClosure<Integer, RefStatusCode> clo, @Nullable Object... objects)
			throws CacheLoaderException {
	}
	@Override
	public RefStatusCode load(Integer key) throws CacheLoaderException {
		return null;
	}

	@Override
	public Map<Integer, RefStatusCode> loadAll(Iterable<? extends Integer> keys) throws CacheLoaderException {
		return null;
	}

	@Override
	public void write(Entry<? extends Integer, ? extends RefStatusCode> entry) throws CacheWriterException {
	}

	@Override
	public void writeAll(Collection<Entry<? extends Integer, ? extends RefStatusCode>> entries)
			throws CacheWriterException {
	}

	@Override
	public void delete(Object key) throws CacheWriterException {
	}

	@Override
	public void deleteAll(Collection<?> keys) throws CacheWriterException {
	}

	@Override
	public void sessionEnd(boolean commit) throws CacheWriterException {
	}

}
