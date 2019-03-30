package com.lzl.design_pattern.K_Composite;

/**
 * @author lizanle
 * @data 2019/3/30 2:55 PM
 */
public class Main {

    public static void main(String[] args) {
        Directory root = new Directory("root");

        Directory bin = new Directory("bin");
        Directory tmp = new Directory("tmp");
        Directory usr = new Directory("usr");

        root.add(bin);
        root.add(tmp);
        root.add(usr);

        bin.add(new File("vi",10));
        bin.add(new File("latex",10));

        usr.add(new File("diary",20));
        usr.add(new File("composite",20));

        root.printList("");
    }
}
