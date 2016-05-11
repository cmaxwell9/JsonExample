package com.cmax.Project4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by Clint on 4/27/2016.
 */

class TaskCollection implements Iterable<Todo> {
    List<Todo> taskc = new ArrayList<>();

    public void addTask(Todo newTask) {
        taskc.add(newTask);
    }

    public List<Todo> getContacts () {
        return taskc;
    }

    @Override
    public Iterator<Todo> iterator() {
        return taskc.iterator();
    }

    @Override
    public void forEach(Consumer<? super Todo> action) {

    }
    /*
    @Override
    public String toString() {
        return "Name: " + Todo + ", Desc: " + Desc;
    }
*/
    @Override
    public Spliterator<Todo> spliterator() {
        return null;
    }
}