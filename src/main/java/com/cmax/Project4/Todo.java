
package com.cmax.Project4;

/**
 * Created by Clint on 3/3/2016.
 */

class Todo implements Comparable<Todo>{

    public String Name;
    public String Desc;
    public Integer Pri;


    public Todo(String Name, String Desc, Integer Pri){
        this.Name = Name;
        this.Desc = Desc;
        this.Pri = Pri;

    }

    public void display(){
        System.out.println("Name: " + Name + ", Desc: " + Desc + ", Pri: " + Pri);
    }

    @Override
    public String toString() {
        return "Name: " + Name + ", Desc: " + Desc + "Pri: " + Pri   ; }



    @Override
    public int compareTo(Todo o) {
        if (!Pri.equals(o.Pri)) {
            return Pri.compareTo(o.Pri);
        }
        else {
            return Name.compareTo(o.Name);
        }
    }

}
