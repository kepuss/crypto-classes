package com.rabin;

/**
 * Created by kepuss on 24.04.15.
 */
public class EEA {
    static public int[] getEEA(int a ,int b){
        int[] result = new int[2];
        int x2=1, x1=0, y2=0, y1=1,q,r,x,y;
        while(b>0){
            q=(int)Math.floor(a/b);
            r=a-q*b;
            x=x2-q*x1;
            y=y2-q*y1;

            a=b;
            b=r;
            x2=x1;
            x1=x;
            y2=y1;
            y1=y;
        }
        result[0]=x2;
        result[1]=y2;
        return result;
    }
}
