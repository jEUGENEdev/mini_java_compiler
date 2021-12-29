package com.compiler;

import com.compiler.paths.Paths;
import com.compiler.window.MainWindow;

import java.io.File;
import java.nio.file.Path;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties pr = System.getProperties();
        String nameOC = pr.getProperty("os.name").toLowerCase();
        if(!nameOC.matches("windows.*")) {
            Paths.HOME_COMPILER = Path.of(pr.getProperty("user.home")+"/Documents/JavaCompiler");
        }
        else {
            Paths.HOME_COMPILER = Path.of(pr.getProperty("user.home")+"/JavaCompiler");
        }
        File dir = new File(Paths.HOME_COMPILER.toString());
        if(!dir.exists()) {
            dir.mkdir();
        }
        MainWindow mainWindow = MainWindow.getInstance("Compiler", 600, 470);
        mainWindow.start();
    }
}
