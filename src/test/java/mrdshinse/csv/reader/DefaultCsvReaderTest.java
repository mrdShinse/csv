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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author mrdShinse
 */
public class DefaultCsvReaderTest extends AbstractCsvReaderTest {

    public DefaultCsvReaderTest() {
    }

    @Test
    public void カンマのみで区切られた文字と文字列を配列にして返す() {
        File file = createFile(new String[]{"a,b,c", "d,e,f"});
        DefaultCsvReader dcr = new DefaultCsvReader(file);

        int lineCount = 1;
        for (String[] line : dcr) {
            if (lineCount == 2) {
                Assert.assertThat(line, CoreMatchers.is(new String[]{"d", "e", "f"}));
            }
            lineCount++;
        }
    }

    @Test
    public void _２つ以上ダブルクォーテーションがある場合に両端のダブルクォーテーション間を文字列とする() {
        File file = createFile(new String[]{"\", \"b\"　,\"\"c\", d"});
        DefaultCsvReader dcr = new DefaultCsvReader(file);
        for (String[] line : dcr) {
            Assert.assertThat(line, CoreMatchers.is(new String[]{"\"", "b", "\"c", "d"}));
        }
    }

    @Test
    public void 文字列の前に半角スペースやタブがある場合に無視をする() {
        File file = createFile(new String[]{" \ta, b,\tc"});
        DefaultCsvReader dcr = new DefaultCsvReader(file);
        for (String[] line : dcr) {
            Assert.assertThat(line, CoreMatchers.is(new String[]{"a", "b", "c"}));
        }
    }

    @Test
    public void 文字列がない場合_行頭行末のカンマや二連続のカンマ_は空値として読み取る() {
        File file = createFile(new String[]{",\"\","});
        DefaultCsvReader dcr = new DefaultCsvReader(file);
        for (String[] line : dcr) {
            Assert.assertThat(line, CoreMatchers.is(new String[]{"", "", ""}));
        }
    }

    @Test
    public void 一行目よりもカラム数が多い行があった場合に多い方のカラム数に合わせて空値を追加する() {
        File file = createFile(new String[]{"a,b,c", "a,b,c,z"});
        DefaultCsvReader dcr = new DefaultCsvReader(file);
        for (String[] line : dcr) {
            Assert.assertThat(line, CoreMatchers.is(new String[]{"a", "b", "c", ""}));
        }
    }

    @Test
    public void 一行目よりもカラム数が多い行があった場合にログで警告を出す() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        File file = createFile(new String[]{"a,b,c", "a,b,c,z"});
        DefaultCsvReader dcr = new DefaultCsvReader(file);
        for (String[] line : dcr) {
        }
        Assert.assertThat(out.toString(), CoreMatchers.is("The numbers of columns is not flushed"));
    }

    @Test
    public void 一行目よりもカラム数が少ない行があった場合に一行目のカラム数に合わせて空値を追加する() {
        File file = createFile(new String[]{"a,b,c", "a,b"});
        DefaultCsvReader dcr = new DefaultCsvReader(file);

        int lineCount = 1;
        for (String[] line : dcr) {
            if (lineCount == 2) {
                Assert.assertThat(line, CoreMatchers.is(new String[]{"a", "b", ""}));
            }
            lineCount++;
        }
    }

    @Test
    public void 一行目よりもカラム数が少ない行があった場合にログで警告を出す() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        File file = createFile(new String[]{"a,b,c", "a,b"});
        DefaultCsvReader dcr = new DefaultCsvReader(file);
        for (String[] line : dcr) {
        }
        Assert.assertThat(out.toString(), CoreMatchers.is("The numbers of columns is not flushed"));
    }
}
