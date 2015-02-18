package com.allinthesoft.openweather.tools.list.core.array;

import java.util.ArrayList;
import java.util.List;

import com.allinthesoft.openweather.tools.list.IExtendedList;
import com.allinthesoft.openweather.tools.list.composant.executor.IExecutor;
import com.allinthesoft.openweather.tools.list.composant.filter.IFilter;
import com.allinthesoft.openweather.tools.list.composant.mapper.IMapper;
import com.allinthesoft.openweather.tools.list.composant.selector.ISelector;

public abstract class AbstractArrayExtendedList<E> extends ArrayList<E>  implements List<E>, IExtendedList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public <V> IExtendedList<V> map(IMapper<E, V> mapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exec(IExecutor<E> executor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IExtendedList<E> filtre(IFilter<E> filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E select(ISelector<E> selector) {
		// TODO Auto-generated method stub
		return null;
	}

}
