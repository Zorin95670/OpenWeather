package com.allinthesoft.openweather.tools.list.composant.executor;

public interface IExecutor<E> {

	void exec();
	void exec(E element);
}
