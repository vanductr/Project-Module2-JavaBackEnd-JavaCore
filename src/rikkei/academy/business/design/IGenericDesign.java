package rikkei.academy.business.design;

import java.util.List;

public interface IGenericDesign<T, E> {
    List<T> getAll();
    void save(T t);
    T findById(E e);
    void delete(T t);
    E getNewId();
}

