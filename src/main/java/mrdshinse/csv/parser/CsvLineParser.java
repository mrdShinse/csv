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

import mrdshinse.csv.configs.Config;

/**
 *
 * @author mrdShinse
 */
public class CsvLineParser {

    private final Config CONFIG;
    private final int MAX_COLUMN_NUM;

    public CsvLineParser(Config config, int maxColumnNum) {
        this.CONFIG = config;
        this.MAX_COLUMN_NUM = maxColumnNum;
    }

    public String[] parse(String line) {
        //行末がカンマだった場合に空値を出すために、第二引数を負の値にする必要がある。
        String[] data = line.split(",", -1);

        for (int i = 0; i < data.length; i++) {
            String str = data[i];
            if (str.equals("")) {
                continue;
            }

            //_２つ以上ダブルクォーテーションがある場合に両端のダブルクォーテーション間を文字列とする
            if (CONFIG.isSubstringBetweenDubQuo()) {
                data[i] = substringBetweenDubQuo(str);
            }

            //文字列の前後に半角スペースやタブがある場合に無視をする
            if (CONFIG.isTrimFirstBlank() && CONFIG.isTrimLastBlank()) {
                data[i] = trimBothStartEnd(data[i]);
            }
        }

        return data;
    }

    private String substringBetweenDubQuo(String str) {
        int firstDubQuo = str.indexOf("\"");
        int lastDubQuo = str.lastIndexOf("\"");
        if (firstDubQuo != -1 && lastDubQuo != -1 && firstDubQuo != lastDubQuo) {
            str = str.substring(0, lastDubQuo);
            str = str.substring(firstDubQuo + 1);
        }
        return str;
    }

    private String trimBothStartEnd(String str) {
        return str.trim();
    }
}
