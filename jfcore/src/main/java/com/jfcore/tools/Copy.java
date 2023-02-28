package com.jfcore.tools;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

public class Copy {

	public static <T, V> List<V> copyList(List<T> list, Class<T> tcls, Class<V> vcls) {

		List<V> resList = new ArrayList<V>();
		BeanCopier copier = BeanCopier.create(tcls, vcls, false);

		for (T t : list) {
			V v = null;
			try {
				v = vcls.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			copier.copy(t, v, null);

			resList.add(v);
		}
		return resList;
	}

	public static <T, V> V copy(T t, Class<T> tcls, Class<V> vcls) {

		BeanCopier copier = BeanCopier.create(tcls, vcls, false);

		V v = null;
		try {
			v = vcls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		copier.copy(t, v, null);

		return v;

	}

}
