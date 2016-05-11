package com.cmax.Project4;


public class Main {

    public static void main(String[] args) {

        String userInput;
        userInput = "6";
        while (((int) Double.parseDouble(userInput)) != 0) {
            System.out.println("Menu");
            System.out.println("1) Add a task.");
            System.out.println("2) Remove a task.");
            System.out.println("3) Update a task.");
            System.out.println("4) List all tasks.");
            System.out.println("5) List all tasks of a certain priority.");
            System.out.println("6) Sort tasks by Pri, Name ");
            System.out.println("7) iterate through all the tasks");
            System.out.println("0) Exit.");
            System.out.println("Choose an Option.");
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            userInput = scanner.nextLine();

            try { int x = ((int) Double.parseDouble(userInput));}
            catch(NumberFormatException nfe){userInput = "6";};

            switch ((int) Double.parseDouble(userInput)) {
                case 1:
                    PriList.add();
                    break;
                case 2:
                    PriList.remove();
                    break;

                case 3:
                    PriList.update();
                    break;

                case 4:
                    PriList.list();
                    break;
                case 5:
                    PriList.listpri();
                    break;
                case 6:
                    PriList.sortpri();
                    break;
                case 7:
                    TaskCollection TC = new TaskCollection();
                    int y = 0;
                    for (y = 0; y < PriList.todos.size(); y++) {
                         if (y < PriList.todos.size()) {
                                TC.addTask(PriList.todos.get(y));
                            } else {
                                break;
                            }
                        }

                        for(Todo task: TC) {
                            System.out.println(task);
                    }
                    System.out.println("****** inerated ******");
                case 0:
                    break;
                default:
                    System.out.println("Invalid Entry, Please try again...");
                    break;
            }
        }
    }
}

