/*
 * The MIT License
 *
 * Copyright 2016 mrdShinse.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package mrdshinse.csv.parser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import mrdshinse.csv.configs.Config;

/**
 *
 * @author mrdShinse
 */
public class CsvFileParser implements Iterable<String[]>, Iterator<String[]> {

    private final BufferedReader BR;
    private final CsvLineParser LINE_PARSER;
    private final File CSV_FILE;
    private final Config CONFIG;
    private String nextLine;
    private int maxColumnNum;

    public CsvFileParser(Config config, File file) {
        if (!file.exists()) {
            throw new RuntimeException("File doesn't exist :" + file);
        }
        if (file.isDirectory()) {
            throw new RuntimeException("Cannot read directory :" + file);
        }
        if (!file.canRead()) {
            throw new RuntimeException("Unreadable file :" + file);
        }
        this.CONFIG = config;
        this.CSV_FILE = file;
        try {
            BR = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(CSV_FILE))));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("File does not found :" + this);
        }
        this.maxColumnNum = getMaxColumnNum();
        this.LINE_PARSER = new CsvLineParser(config, maxColumnNum);
    }

    @Override
    public boolean hasNext() {
        try {
            nextLine = BR.readLine();
        } catch (IOException ex) {
            throw new RuntimeException("Couldn't read line :" + CSV_FILE);
        }
        return nextLine != null;
    }

    @Override
    public String[] next() {
        return LINE_PARSER.parse(nextLine);
    }

    @Override
    public Iterator<String[]> iterator() {
        return this;
    }

    private int getMaxColumnNum() {
        int num = 0;
        try {
            String line = BR.readLine();

            while (line != null) {
                if ("".equals(line)) {
                    line = BR.readLine();
                    continue;
                }
                int counter = 1;
                int index = line.indexOf(",");
                while (index != -1) {
                    counter++;
                    index = line.indexOf(",", index + 1);
                }
                if (Integer.compare(counter, num) > 0) {
                    num = counter;
                }
                line = BR.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(CsvFileParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }
}
