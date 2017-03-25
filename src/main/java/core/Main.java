package main.java.core;

import java.awt.*;

public class Main implements Runnable {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Main());
    }

    @Override
    public void run() {
        new AppConstructor();
    }
}
