/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glview.text;

import com.glview.graphics.font.FontUtils;
import com.glview.hwui.GLCanvas;
import com.glview.hwui.GLPaint;

/**
 * A BoringLayout is a very simple Layout implementation for text that
 * fits on a single line and is all left-to-right characters.
 * You will probably never want to make one of these yourself;
 * if you do, be sure to call {@link #isBoring} first to make sure
 * the text meets the criteria.
 * <p>This class is used by widgets to control text layout. You should not need
 * to use this class directly unless you are implementing your own widget
 * or custom display object, in which case
 * you are encouraged to use a Layout instead of calling
 * {@link android.graphics.Canvas#drawText(CharSequence, int, int, float, float, android.graphics.Paint)
 *  Canvas.drawText()} directly.</p>
 */
public class BoringLayout extends Layout implements TextUtils.EllipsizeCallback {
    public static BoringLayout make(CharSequence source,
                        GLPaint paint, int outerwidth,
                        Alignment align,
                        float spacingmult, float spacingadd,
                        Metrics metrics, boolean includepad, boolean drawDefer) {
        return new BoringLayout(source, paint, outerwidth, align,
                                spacingmult, spacingadd, metrics,
                                includepad, drawDefer);
    }

    public static BoringLayout make(CharSequence source,
    					GLPaint paint, int outerwidth,
                        Alignment align,
                        float spacingmult, float spacingadd,
                        Metrics metrics, boolean includepad,
                        TextUtils.TruncateAt ellipsize, int ellipsizedWidth, boolean drawDefer) {
        return new BoringLayout(source, paint, outerwidth, align,
                                spacingmult, spacingadd, metrics,
                                includepad, ellipsize, ellipsizedWidth, drawDefer);
    }

    /**
     * Returns a BoringLayout for the specified text, potentially reusing
     * this one if it is already suitable.  The caller must make sure that
     * no one is still using this Layout.
     */
    public BoringLayout replaceOrMake(CharSequence source, GLPaint paint,
                                      int outerwidth, Alignment align,
                                      float spacingmult, float spacingadd,
                                      Metrics metrics,
                                      boolean includepad) {
        replaceWith(source, paint, outerwidth, align, spacingmult,
                    spacingadd);

        mEllipsizedWidth = outerwidth;
        mEllipsizedStart = 0;
        mEllipsizedCount = 0;

        init(source, paint, outerwidth, align, spacingmult, spacingadd,
             metrics, includepad, true);
        return this;
    }

    /**
     * Returns a BoringLayout for the specified text, potentially reusing
     * this one if it is already suitable.  The caller must make sure that
     * no one is still using this Layout.
     */
    public BoringLayout replaceOrMake(CharSequence source, GLPaint paint,
                                      int outerwidth, Alignment align,
                                      float spacingmult, float spacingadd,
                                      Metrics metrics,
                                      boolean includepad,
                                      TextUtils.TruncateAt ellipsize,
                                      int ellipsizedWidth) {
        boolean trust;

        if (ellipsize == null || ellipsize == TextUtils.TruncateAt.MARQUEE) {
            replaceWith(source, paint, outerwidth, align, spacingmult,
                        spacingadd);

            mEllipsizedWidth = outerwidth;
            mEllipsizedStart = 0;
            mEllipsizedCount = 0;
            trust = true;
        } else {
            replaceWith(TextUtils.ellipsize(source, paint, ellipsizedWidth,
                                           ellipsize, true, this),
                        paint, outerwidth, align, spacingmult,
                        spacingadd);

            mEllipsizedWidth = ellipsizedWidth;
            trust = false;
        }

        init(getText(), paint, outerwidth, align, spacingmult, spacingadd,
             metrics, includepad, trust);
        return this;
    }

    public BoringLayout(CharSequence source,
                        GLPaint paint, int outerwidth,
                        Alignment align,
                        float spacingmult, float spacingadd,
                        Metrics metrics, boolean includepad, boolean drawDeffer) {
        super(source, paint, outerwidth, align, spacingmult, spacingadd, drawDeffer);

        mEllipsizedWidth = outerwidth;
        mEllipsizedStart = 0;
        mEllipsizedCount = 0;

        init(source, paint, outerwidth, align, spacingmult, spacingadd,
             metrics, includepad, true);
    }

    public BoringLayout(CharSequence source,
                        GLPaint paint, int outerwidth,
                        Alignment align,
                        float spacingmult, float spacingadd,
                        Metrics metrics, boolean includepad,
                        TextUtils.TruncateAt ellipsize, int ellipsizedWidth, boolean drawDeffer) {
        /*
         * It is silly to have to call super() and then replaceWith(),
         * but we can't use "this" for the callback until the call to
         * super() finishes.
         */
        super(source, paint, outerwidth, align, spacingmult, spacingadd, drawDeffer);

        boolean trust;

        if (ellipsize == null || ellipsize == TextUtils.TruncateAt.MARQUEE) {
            mEllipsizedWidth = outerwidth;
            mEllipsizedStart = 0;
            mEllipsizedCount = 0;
            trust = true;
        } else {
            replaceWith(TextUtils.ellipsize(source, paint, ellipsizedWidth,
                                           ellipsize, true, this),
                        paint, outerwidth, align, spacingmult,
                        spacingadd);


            mEllipsizedWidth = ellipsizedWidth;
            trust = false;
        }

        init(getText(), paint, outerwidth, align, spacingmult, spacingadd,
             metrics, includepad, trust);
    }

    /* package */ void init(CharSequence source,
                            GLPaint paint, int outerwidth,
                            Alignment align,
                            float spacingmult, float spacingadd,
                            Metrics metrics, boolean includepad,
                            boolean trustWidth) {
        int spacing;

        if (source instanceof String && align == Layout.Alignment.ALIGN_NORMAL) {
            mDirect = source.toString();
        } else {
            mDirect = null;
        }

        mPaint = paint;

        if (includepad) {
            spacing = metrics.bottom - metrics.top;
        } else {
            spacing = metrics.descent - metrics.ascent;
        }

        mBottom = spacing;

        if (includepad) {
            mDesc = spacing + metrics.top;
        } else {
            mDesc = spacing + metrics.ascent;
        }

        if (trustWidth) {
            mMax = metrics.width;
        } else {
            /*
             * If we have ellipsized, we have to actually calculate the
             * width because the width that was passed in was for the
             * full text, not the ellipsized form.
             */
//            TextLine line = TextLine.obtain();
//            line.set(paint, source, 0, source.length(), Layout.DIR_LEFT_TO_RIGHT,
//                    Layout.DIRS_ALL_LEFT_TO_RIGHT, false, null);
//            mMax = (int) FloatMath.ceil(line.metrics(null));
//            TextLine.recycle(line);
        	mMax = FontUtils.measureText(paint, source, 0, source.length());
        }

        if (includepad) {
            mTopPadding = metrics.top - metrics.ascent;
            mBottomPadding = metrics.bottom - metrics.descent;
        }
    }

    /**
     * Returns null if not boring; the width, ascent, and descent if boring.
     */
    public static Metrics isBoring(CharSequence text,
                                   GLPaint paint) {
        return isBoring(text, paint, TextDirectionHeuristics.FIRSTSTRONG_LTR, null);
    }

    /**
     * Returns null if not boring; the width, ascent, and descent if boring.
     * @hide
     */
    public static Metrics isBoring(CharSequence text,
                                   GLPaint paint,
                                   TextDirectionHeuristic textDir) {
        return isBoring(text, paint, textDir, null);
    }
    
    /**
     * Returns null if not boring; the width, ascent, and descent in the
     * provided Metrics object (or a new one if the provided one was null)
     * if boring.
     */
    public static Metrics isBoring(CharSequence text, GLPaint paint, Metrics metrics) {
        return isBoring(text, paint, TextDirectionHeuristics.FIRSTSTRONG_LTR, metrics);
    }

    /**
     * Returns null if not boring; the width, ascent, and descent in the
     * provided Metrics object (or a new one if the provided one was null)
     * if boring.
     * @hide
     */
    public static Metrics isBoring(CharSequence text, GLPaint paint,
            TextDirectionHeuristic textDir, Metrics metrics) {
        char[] temp = TextUtils.obtain(500);
        int length = text.length();
        boolean boring = true;

        outer:
        for (int i = 0; i < length; i += 500) {
            int j = i + 500;

            if (j > length)
                j = length;

            TextUtils.getChars(text, i, j, temp, 0);

            int n = j - i;

            for (int a = 0; a < n; a++) {
                char c = temp[a];

                if (c == '\n' || c == '\t') {
                    boring = false;
                    break outer;
                }
            }

        }
        
        TextUtils.recycle(temp);

        if (boring) {
            Metrics fm = metrics;
            if (fm == null) {
                fm = new Metrics();
            }

//            TextLine line = TextLine.obtain();
//            line.set(paint, text, 0, length, Layout.DIR_LEFT_TO_RIGHT,
//                    Layout.DIRS_ALL_LEFT_TO_RIGHT, false, null);
//            fm.width = (int) FloatMath.ceil(line.metrics(fm));
//            TextLine.recycle(line);
            paint.getFontMetricsInt(fm);
            fm.width = (int) FontUtils.measureText(paint, text, 0, text.length());

            return fm;
        } else {
            return null;
        }
    }

    @Override
    public int getHeight() {
        return mBottom;
    }

    @Override
    public int getLineCount() {
        return 1;
    }

    @Override
    public int getLineTop(int line) {
        if (line == 0)
            return 0;
        else
            return mBottom;
    }

    @Override
    public int getLineDescent(int line) {
        return mDesc;
    }

    @Override
    public int getLineStart(int line) {
        if (line == 0)
            return 0;
        else
            return getText().length();
    }

    @Override
    public int getParagraphDirection(int line) {
        return DIR_LEFT_TO_RIGHT;
    }

    @Override
    public boolean getLineContainsTab(int line) {
        return false;
    }

    @Override
    public float getLineMax(int line) {
        return mMax;
    }

    @Override
    public final Directions getLineDirections(int line) {
        return Layout.DIRS_ALL_LEFT_TO_RIGHT;
    }

    @Override
    public int getTopPadding() {
        return mTopPadding;
    }

    @Override
    public int getBottomPadding() {
        return mBottomPadding;
    }

    @Override
    public int getEllipsisCount(int line) {
        return mEllipsizedCount;
    }

    @Override
    public int getEllipsisStart(int line) {
        return mEllipsizedStart;
    }

    @Override
    public int getEllipsizedWidth() {
        return mEllipsizedWidth;
    }

    // Override draw so it will be faster.
    @Override
    public void draw(GLCanvas c) {
        if (mDirect != null) {
            c.drawText(mDirect, 0, mDirect.length(), 0, mBottom - mDesc, mPaint, mDrawDefer);
        } else {
            super.draw(c);
        }
    }

    /**
     * Callback for the ellipsizer to report what region it ellipsized.
     */
    public void ellipsized(int start, int end) {
        mEllipsizedStart = start;
        mEllipsizedCount = end - start;
    }

    private String mDirect;
    private GLPaint mPaint;

    /* package */ int mBottom, mDesc;   // for Direct
    private int mTopPadding, mBottomPadding;
    private float mMax;
    private int mEllipsizedWidth, mEllipsizedStart, mEllipsizedCount;

    public static class Metrics extends GLPaint.FontMetricsInt {
        public int width;

        @Override public String toString() {
            return super.toString() + " width=" + width;
        }
    }
}
