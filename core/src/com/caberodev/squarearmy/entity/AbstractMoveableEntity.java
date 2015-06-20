package com.caberodev.squarearmy.entity;
public abstract class AbstractMoveableEntity extends AbstractEntity {

	protected float dx;
	protected float dy;

	public AbstractMoveableEntity(float x, float y) {
		super(x, y);
		dx = 0;
		dy = 0;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public void update(){
		x += dx;
		y += dy;
	}
}
