package com.lzl.design_pattern.M_Visitor;

/**
 * @author lizanle
 * @data 2019/4/3 10:20 PM
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

        bin.add(new File("vi",100));
        bin.add(new File("di",100));

        root.accept(new ListVisitor());
    }
}
