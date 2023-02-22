package com.jfcore.orm;

import java.util.List;

public interface IQuery<T> {
	

    

    T Get(Object id);

    T GetUnique(Object unique);
    
    
    List<T> getList(List<Integer> ids);
    

}
