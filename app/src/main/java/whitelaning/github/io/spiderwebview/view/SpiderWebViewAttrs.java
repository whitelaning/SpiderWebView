package whitelaning.github.io.spiderwebview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import whitelaning.github.io.spiderwebview.R;

public class SpiderWebViewAttrs {
    private int webColor; //网格线的颜色
    private int overlayColor; //覆盖层的颜色
    private int overlayAlpha; //覆盖层的透明度
    private int tagSize; //标识文字的大小
    private int textColor; //标识文字的颜色

    //默认值
    private int defaultWebColor = 0xff708090;
    private int defaultOverlayColor = 0xff00ff00;
    private int defaultTextColor = 0xffa0522d;

    private int defaultOverlayAlpha = 100;
    private int defaultTagSize = 22;

    private boolean showNumber = true; //是否显示数值

    //获取各种自定义的属性值
    public SpiderWebViewAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.SpiderWebView, defStyleAttr, 0);

        //布局的颜色等
        webColor = mTypedArray.getColor(R.styleable.SpiderWebView_webColor, defaultWebColor);
        overlayColor = mTypedArray.getColor(R.styleable.SpiderWebView_overlayColor, defaultOverlayColor);
        textColor = mTypedArray.getColor(R.styleable.SpiderWebView_textColor, defaultTextColor);

        //布局的参数等
        overlayAlpha = mTypedArray.getInteger(R.styleable.SpiderWebView_overlayAlpha, defaultOverlayAlpha);
        tagSize = mTypedArray.getInteger(R.styleable.SpiderWebView_tagSize, defaultTagSize);
        showNumber = mTypedArray.getBoolean(R.styleable.SpiderWebView_showNumber, true);

        //及时回收
        mTypedArray.recycle();
    }

    public int getWebColor() {
        return webColor;
    }

    public void setWebColor(int webColor) {
        this.webColor = webColor;
    }

    public int getOverlayColor() {
        return overlayColor;
    }

    public void setOverlayColor(int overlayColor) {
        this.overlayColor = overlayColor;
    }

    public int getOverlayAlpha() {
        return overlayAlpha;
    }

    public void setOverlayAlpha(int overlayAlpha) {
        this.overlayAlpha = overlayAlpha;
    }

    public int getTagSize() {
        return tagSize;
    }

    public void setTagSize(int tagSize) {
        this.tagSize = tagSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(boolean showNumber) {
        this.showNumber = showNumber;
    }
}
