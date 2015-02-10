package util;

public class Rectangle {

	private int x,y,w,h;

	public Rectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean contains(int a, int b) {
		return x <= a && a < x+w && y <= b && b < w+h;
	}
}
