package org.commonjava.web.backingBeans;


public class MathUtilBack {

	public int roundUp(double d){
		return (int) (d+0.9);
	}
	public double getRandom(){
	    return Math.random();
	}
}
