package com.empro.kalk_u_lator;

public class SingleItem {
    private int mImageResource;
    private  String mtext1;
    private  String mtext2;
    private  String mtext3;
    private  String mtext4;

    public SingleItem(int imageResource, String text1, String text2, String text3, String text4)
    {
        mImageResource = imageResource;
        mtext1 = text1;
        mtext2 = text2;
        mtext3 = text3;
        mtext4 = text4;
    }

    public void changeText1(String text)
    {
        mtext1 = text;
    }

    public int getmImageResource()
    {
        return mImageResource;
    }

    public String getText1()
    {
        return mtext1;
    }

    public String getText2()
    {
        return mtext2;
    }

    public String getText3()
    {
        return mtext3;
    }
    public String getText4()
    {
        return mtext4;
    }
}
