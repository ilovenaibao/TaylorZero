package com.android.taylorzero.test.opengl.square;

import javax.microedition.khronos.opengles.GL10;

public class FlatColoredSquare extends Square {

	@Override
	public void draw(GL10 gl) {
		gl.glLoadIdentity();
		gl.glTranslatef(0, 1, -4);
		gl.glScalef(0.2f, 0.2f, 1f);
		gl.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
		super.draw(gl);
	}
}
