package com.example.delta_1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class CustomView extends View {

    private RectF mRectF;
    private Paint mPaint;

    private float RECT_WIDTH;
    private float RECT_HEIGHT;

    private int RECT_WIDTH_DEFAULT=200;
    private int RECT_HEIGHT_DEFAULT=100;

    private float RECT_TOP;
    private float RECT_LEFT;
    private float RECT_BOTTOM;
    private float RECT_RIGHT;

    private float RECT_CENTRE_x;
    private float RECT_CENTRE_y;



    public CustomView(Context context) {
        super(context);
        init(null);
    }

 //other constructors can be called at the time of layout inflation

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attributeSet){

        mRectF=new RectF();
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);


        //obtain custom attributes
        if(attributeSet==null)
            return;
        TypedArray typedArray=getContext().obtainStyledAttributes(attributeSet,R.styleable.CustomView);
        RECT_WIDTH=typedArray.getDimensionPixelSize(R.styleable.CustomView_rectangle_width,RECT_WIDTH_DEFAULT);
        RECT_HEIGHT=typedArray.getDimensionPixelSize(R.styleable.CustomView_rectangle_height,RECT_HEIGHT_DEFAULT);
        typedArray.recycle();

    }

    private void values(){
       // double RECT_DIAGONAL_HALF=(Math.sqrt(Math.pow(RECT_WIDTH,2)+Math.pow(RECT_HEIGHT,2)))/2;
        double horizontal=RECT_WIDTH/2;//Math.sqrt(Math.pow(RECT_DIAGONAL_HALF,2)-Math.pow(RECT_HEIGHT/2,2));
        double vertical=RECT_HEIGHT/2;//Math.sqrt(Math.pow(RECT_DIAGONAL_HALF,2)-Math.pow(RECT_WIDTH/2,2));
        RECT_LEFT= (float) (RECT_CENTRE_x-horizontal);
        RECT_TOP= (float) (RECT_CENTRE_y-vertical);
        RECT_RIGHT=(float)(RECT_CENTRE_x+horizontal);
        RECT_BOTTOM=(float)(RECT_CENTRE_y+vertical);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.background_color));

        if(RECT_CENTRE_x==0 && RECT_CENTRE_y==0){

            RECT_CENTRE_x=canvas.getWidth()/2;
            RECT_CENTRE_y=canvas.getHeight()/2;
            values();

        }


        mRectF.top=  RECT_TOP;
        mRectF.left=  RECT_LEFT;
        mRectF.bottom=RECT_BOTTOM;
        mRectF.right=RECT_RIGHT;

        mPaint.setColor(getResources().getColor(R.color.rectangle_color));
        canvas.drawRect(mRectF,mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value=super.onTouchEvent(event);
         switch (event.getAction()){
             case MotionEvent.ACTION_DOWN:
                 //pressed




                 return true;// this action needs to be captured


              case MotionEvent.ACTION_MOVE:

                  float x=event.getX();
                  float y=event.getY();
                  if(x> (RECT_LEFT+50) && x< (RECT_RIGHT-50) && y>(RECT_TOP+50) && y<(RECT_BOTTOM-50))
                  {

                      RECT_CENTRE_x=x;
                      RECT_CENTRE_y=y;
                      values();
                      postInvalidate(); //to call onDraw() method

                      return true;
                  }

                  else if(x>RECT_LEFT && x<(RECT_LEFT+50))
                  {
                      RECT_LEFT=x;
                      RECT_WIDTH=RECT_RIGHT-RECT_LEFT;
                      postInvalidate();
                      return true;
                  }
                  else if( x<RECT_RIGHT && x>(RECT_RIGHT-50)){


                      RECT_RIGHT=x;
                      RECT_WIDTH=RECT_RIGHT-RECT_LEFT;
                      postInvalidate();
                      return true;
                  }

                  else if(y>RECT_TOP && y<(RECT_TOP+50)){

                      RECT_TOP=y;
                      RECT_HEIGHT=RECT_BOTTOM-RECT_TOP;
                      postInvalidate();
                      return true;
              }
                  else if(y<RECT_BOTTOM && y>(RECT_BOTTOM-50)){


                      RECT_BOTTOM=y;
                      RECT_HEIGHT=RECT_BOTTOM-RECT_TOP;
                      postInvalidate();
                      return true;
              }

                  return value;


         }
        return value;
    }
}
