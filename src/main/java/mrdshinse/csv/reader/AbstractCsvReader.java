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
package mrdshinse.csv.reader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import mrdshinse.csv.configs.Config;
import mrdshinse.csv.parser.CsvParser;

/**
 *
 * @author mrdShinse
 */
public abstract class AbstractCsvReader implements Iterable<String[]>, Iterator<String[]> {

    private final CsvParser PARSER;
    private final File CSV_FILE;
    private final BufferedReader br;
    private String nextLine;

    protected AbstractCsvReader(Config config, File file) {
        if (!file.exists()) {
            throw new RuntimeException("File doesn't exist :" + file);
        }
        if (file.isDirectory()) {
            throw new RuntimeException("Cannot read directory :" + file);
        }
        if (!file.canRead()) {
            throw new RuntimeException("Unreadable file :" + file);
        }
        this.PARSER = new CsvParser(config);
        this.CSV_FILE = file;
        try {
            br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(CSV_FILE))));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("File does not found :" + this);
        }
    }

    @Override
    public Iterator<String[]> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        try {
            nextLine = br.readLine();
        } catch (IOException ex) {
            throw new RuntimeException("Couldn't read line :" + CSV_FILE);
        }
        return nextLine != null;
    }

    @Override
    public String[] next() {
        return PARSER.parse(nextLine);
    }
}
