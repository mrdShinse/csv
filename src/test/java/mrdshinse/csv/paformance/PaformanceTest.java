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
package mrdshinse.csv.paformance;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mrdshinse.csv.reader.AbstractCsvReaderTest;
import mrdshinse.csv.reader.DefaultCsvReader;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author mrdShinse
 */
public class PaformanceTest extends AbstractCsvReaderTest {

    public PaformanceTest() {
    }

    @Test
    public void _20列x10万行のcsvを1秒以内に読み込む() {
        File file = getGiantFile();
        DefaultCsvReader dcr = new DefaultCsvReader(file);

        //todo to change java8 date api
        Date start = new Date();
        for (String[] line : dcr.read()) {
        }
        Date end = new Date();
        Assert.assertThat(end.getTime() - start.getTime(), CoreMatchers.is(Matchers.lessThan(1000L)));
    }

    private File getGiantFile() {
        List<String> giantList = new ArrayList<>();
        int i = 0;
        while (i < 100000) {
            giantList.add("a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890,a1234567890");
            i++;
        }
        return createFile(giantList.toArray(new String[0]));
    }

}
