package whitelaning.github.io.spiderwebview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class SpiderWebView extends View {

    private String[] titles = {"A", "B", "C", "D", "E"};//默认标签
    private double[] data = {0.3, 0.5, 0.5, 0.7, 0.9};//默认数值

    private int tagCount = 5; //默认标签数
    private int layers = 5; //默认层数
    private float angle = (float) (Math.PI * 2 / tagCount);//默认角度
    private float radius;  //外接圆半径

    private int centerX;//中心X轴坐标
    private int centerY;//中心Y轴坐标

    private int webColor;
    private int overlayColor;
    private int textColor;
    private int overlayAlpha;
    private int tagSize;

    private SpiderWebViewAttrs mSpiderWebViewAttrs; //自定义属性

    private Paint webPaint; //网格画笔
    private Paint valuePaint; //数值画笔
    private Paint textPaint; //标签画笔

    private Path path; //路线

    private boolean showNumber = true;//默认显示数值

    public SpiderWebView(Context context) {
        this(context, null);
    }

    public SpiderWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpiderWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSpiderWebViewAttrs = new SpiderWebViewAttrs(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {

        //获取XML布局中自定义的参数值
        webColor = mSpiderWebViewAttrs.getWebColor();
        overlayColor = mSpiderWebViewAttrs.getOverlayColor();
        textColor = mSpiderWebViewAttrs.getTextColor();
        overlayAlpha = mSpiderWebViewAttrs.getOverlayAlpha();
        tagSize = mSpiderWebViewAttrs.getTagSize();
        showNumber = mSpiderWebViewAttrs.getShowNumber();

        //初始化网格画笔
        webPaint = new Paint();
        webPaint.setAntiAlias(true);//设置抗锯齿
        webPaint.setColor(webColor);//画笔颜色
        webPaint.setStyle(Paint.Style.STROKE);//画笔风格，描边

        //初始化数值画笔
        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(overlayColor);
        valuePaint.setStyle(Paint.Style.FILL);//画笔风格，填充内部

        //初始化标签画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(tagSize);//画笔文字大小
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);//画笔风格，描边
    }


    @Override
    protected void onSizeChanged(int nowW, int nowh, int oldW, int oldH) {
        //当Size发生变化的时候，重新计算中心点
        radius = Math.min(nowW, nowh) / 2 * 0.7f;
        centerX = nowW / 2;
        centerY = nowh / 2;
        postInvalidate();//在工作者线程中刷新View
        super.onSizeChanged(nowW, nowh, oldW, oldH);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //复写onMeasure，不然WRAP_CONTENT无法使用

        /**
         *  Mode共有三种情况，取值分别为
         *  MeasureSpec.UNSPECIFIED,
         *  MeasureSpec.EXACTLY,
         *  MeasureSpec.AT_MOST
         */

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);//获取宽度的模式
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);//获取宽度的数值

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);//获取高度的模式
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);//获取高度的数值

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            //高宽都未指定尺寸，即宽高都是WRAP_CONTENT
            setMeasuredDimension(400, 400);//设置默认的宽高为400
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, heightSpecSize);//设置默认的宽度为400
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 400);//设置默认的高度为400
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNet(canvas);//绘制网格
        drawText(canvas);//绘制文字
        drawRegion(canvas);//绘制覆盖层
    }

    private void drawNet(Canvas canvas) {

        if (path == null) {
            path = new Path();//新建路径
        }

        float height = radius / (layers - 1); //每层之间的高度

        //绘制网格层层之间的轮廓线
        for (int i = 0; i < layers; i++) {
            float currentRadius = height * i; //获取当前层的半径长度
            path.reset();//清空所有的曲线和直线
            for (int j = 0; j < tagCount; j++) { //绘制当前层的点线
                if (j == 0) {
                    path.moveTo(centerX + currentRadius, centerY);//不进行绘制，移动画笔到指定每一层的开始位置
                } else {
                    //通过正余弦函数计算下一个点的坐标轴
                    float x = (float) (centerX + currentRadius * Math.cos(angle * j));
                    float y = (float) (centerY + currentRadius * Math.sin(angle * j));
                    path.lineTo(x, y);//连接两个坐标轴点，并且移动画笔位置到当前坐标轴
                }
            }

            path.close();//关闭当前轮廓
            canvas.drawPath(path, webPaint);//在画布上用网格笔根据之前所画的path进行绘制
        }

        //绘制轴线
        for (int i = 1; i < tagCount + 1; i++) {
            path.reset();//重置路径
            path.moveTo(centerX, centerY);//移动画笔回到中心轴
            //通过正余弦计算每一层的轴线坐标
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            path.lineTo(x, y); //标识路径
            canvas.drawPath(path, webPaint); //绘制路径
        }
    }

    private void drawText(Canvas canvas) {
        /**
         * 绘制文字具体查看这篇文章：
         * http://blog.csdn.net/harvic880925/article/details/50423762
         */
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent; //文字的高度

        for (int i = 0; i < tagCount; i++) {
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));

            float dis = textPaint.measureText(showNumber ? titles[i] + ":" + data[i] : titles[i]);//获取文本长度

            if (angle * i > 0 && angle * i < Math.PI) {
                canvas.drawText(showNumber ? titles[i] + ":" + data[i] : titles[i], x - dis / 2, y + fontHeight, textPaint);
            } else if (angle * i >= Math.PI && angle * i < 3 * Math.PI / 2) {
                canvas.drawText(showNumber ? titles[i] + ":" + data[i] : titles[i], x - dis, y, textPaint);
            } else {
                canvas.drawText(showNumber ? titles[i] + ":" + data[i] : titles[i], x, y, textPaint);
            }
        }
    }

    private void drawRegion(Canvas canvas) {
        if (path == null) {
            path = new Path();
        }

        for (int i = 0; i < tagCount; i++) {
            if (data[i] > 1) {
                throw new RuntimeException(String.format("数值大小不能大于1，当前第%d个的数值为%s", i++, data[i]));
            }

            //正余弦计算坐标
            float x = (float) (centerX + radius * Math.cos(angle * i) * data[i]);
            float y = (float) (centerY + radius * Math.sin(angle * i) * data[i]);
            //绘制路径
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }

            canvas.drawCircle(x, y, 1, valuePaint);//绘制端点数值的圆点
        }

        path.close();

        valuePaint.setAlpha(overlayAlpha);//设置画笔的透明度 X/255
        canvas.drawPath(path, valuePaint);//绘制覆盖层
    }

    private void checkData() {
        if (titles.length != data.length) {
            throw new RuntimeException("标签数组长度和数值数组长度不一致.");
        } else if (titles.length < 3) {
            throw new RuntimeException("标签数组长度或者数值数组长度不能小于3.");
        } else if (layers <= 2) {
            throw new RuntimeException("层数要大于2.");
        } else if (overlayAlpha > 255 || overlayAlpha < 0) {
            throw new RuntimeException("覆盖层透明度取值范围为0~255");
        } else {
            tagCount = titles.length;//重新计算端点数
            angle = (float) (Math.PI * 2 / tagCount);//重新计算角度
        }
    }

    public void refreshView() {
        checkData();//检测数据是否规范
        postInvalidate();//重绘View
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public void setPercent(double[] data) {
        this.data = data;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }

    public void setWebColor(int webColor) {
        this.webColor = webColor;
        if (webPaint != null) {
            webPaint.setColor(webColor);
        }
    }

    public void setOverlayColor(int overlayColor) {
        this.overlayColor = overlayColor;
        if (valuePaint != null) {
            valuePaint.setColor(overlayColor);
        }
    }

    public void setTagTextColor(int textColor) {
        this.textColor = textColor;
        if (textPaint != null) {
            textPaint.setColor(textColor);
        }
    }

    public void setOverlayAlpha(int overlayAlpha) {
        this.overlayAlpha = overlayAlpha;
        if (valuePaint != null) {
            valuePaint.setAlpha(overlayColor);
        }
    }

    public void setTagSize(int tagSize) {
        this.tagSize = tagSize;
        if (textPaint != null) {
            textPaint.setTextSize(tagSize);
        }
    }

    public void setShowNumber(boolean showNumber) {
        this.showNumber = showNumber;
    }
}
