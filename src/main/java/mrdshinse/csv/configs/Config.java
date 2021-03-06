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
package mrdshinse.csv.configs;

/**
 *
 * @author mrdShinse
 */
public abstract class Config {

    protected boolean substringBetweenDubQuo;
    protected boolean trimFirstBlank;
    protected boolean trimLastBlank;

    protected Config(boolean substringBetweenDubQuo, boolean trimFirstBlank, boolean trimLastBlank) {
        this.substringBetweenDubQuo = substringBetweenDubQuo;
        this.trimFirstBlank = trimFirstBlank;
        this.trimLastBlank = trimLastBlank;
    }

    protected Config setTrimLastBlank(boolean trimLastBlank) {
        this.trimLastBlank = trimLastBlank;
        return this;
    }

    protected Config setTrimFirstBlank(boolean trimFirstBlank) {
        this.trimFirstBlank = trimFirstBlank;
        return this;
    }

    protected Config setSubstringBetweenDubQuo(boolean substringBetweenDubQuo) {
        this.substringBetweenDubQuo = substringBetweenDubQuo;
        return this;
    }

    public boolean isSubstringBetweenDubQuo() {
        return substringBetweenDubQuo;
    }

    public boolean isTrimLastBlank() {
        return trimLastBlank;
    }

    public boolean isTrimFirstBlank() {
        return trimFirstBlank;
    }

}
