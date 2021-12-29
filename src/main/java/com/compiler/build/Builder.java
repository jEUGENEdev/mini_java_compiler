package com.compiler.build;

import com.compiler.paths.Paths;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Builder {
    private File runtimeClass;
    private PrintWriter writer;
    private File startBat;
    private PrintWriter startBatWriter;

    public Builder(String className, String text) throws IOException {
        runtimeClass = new File(Paths.HOME_COMPILER+"/"+className+".java");
        startBat = new File(Paths.HOME_COMPILER+"/start.bat");
        if(!runtimeClass.exists()) {
            runtimeClass.createNewFile();
        }
        if(!startBat.exists()) {
            startBat.createNewFile();
        }
        writer = new PrintWriter(runtimeClass);
        writer.println(text);
        writer.close();
        startBatWriter = new PrintWriter(startBat);
    }

    public void compileToClass() throws IOException {
        startBatWriter.println(String.format("cd %s", Paths.HOME_COMPILER.toString()));
        startBatWriter.println(String.format("javac %s", runtimeClass.getName()));
        startBatWriter.println(String.format("java %s", runtimeClass.getName().split("\\.")[0]));
        startBatWriter.close();
    }

    public String startClass() throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(startBat.getAbsolutePath());
        InputStream inputStream = process.getInputStream();
        Scanner scanner = new Scanner(inputStream, "cp866");
        StringBuilder sb = new StringBuilder("Result of program execution:\n\n");
        long count = 1;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(count > 6) {
                sb.append(line).append("\n");
            }
            count++;
        }
        scanner.close();
        inputStream.close();
        runtimeClass.delete();
        startBat.delete();
        return sb.toString();
    }
}
