package service;

import exception.StudentException;
import java.util.List;

public abstract class BaseService<T> {

    protected List<T> list;

    public abstract void add(T t) throws StudentException;

    public abstract void update(String id, T t) throws StudentException;

    public abstract void delete(String id) throws StudentException;

    public void show() {
        if (list == null || list.isEmpty()) {
            System.out.println("Danh sách rỗng");
            return;
        }
        for (T t : list) {
            System.out.println(t);
        }
    }
}

