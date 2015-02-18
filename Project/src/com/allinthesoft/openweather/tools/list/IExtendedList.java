package com.allinthesoft.openweather.tools.list;

import com.allinthesoft.openweather.tools.list.composant.executor.IExecutor;
import com.allinthesoft.openweather.tools.list.composant.filter.IFilter;
import com.allinthesoft.openweather.tools.list.composant.mapper.IMapper;
import com.allinthesoft.openweather.tools.list.composant.selector.ISelector;

public interface IExtendedList<E> {
	
	<V> IExtendedList<V> map(IMapper<E, V> mapper);
	
	void exec(IExecutor<E> executor);
	
	IExtendedList<E> filtre(IFilter<E> filter);
	
	E select(ISelector<E> selector);
}
