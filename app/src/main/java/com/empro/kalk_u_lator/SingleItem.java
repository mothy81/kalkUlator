package com.empro.kalk_u_lator;

public class SingleItem {
    private int mImageResource;
    private  String mtext1;
    private  String mtext2;
    private  String mtext3;
    private  String mtext4;
    private  String mtext5;

    public SingleItem(int imageResource, String text1, String text2, String text3, String text4, String text5)
    {
        mImageResource = imageResource;
        mtext1 = text1;
        mtext2 = text2;
        mtext3 = text3;
        mtext4 = text4;
        mtext5 = text5;
    }

    public void changeText2(String text)
    {
        mtext2 = text;
    }

    public void changeText4(String text)
    {
        mtext4 = text;
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
    public String getText5()
    {
        return mtext5;
    }
}
