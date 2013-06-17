package com.android.taylorzero.test.opengl.square;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.android.taylorzero.test.opengl.graphicsframe.Mesh;

/**
 * SimplePlane is a setup class for Mesh that creates a plane mesh.
 * 
 * @author Per-Erik Bergman (per-erik.bergman@jayway.com)
 * 
 */
public class SimplePlane extends Mesh {
	/**
	 * Create a plane with a default with and height of 1 unit.
	 */
	public SimplePlane() {
		this(1, 1);
	}

	/**
	 * Create a plane.
	 * 
	 * @param width
	 *            the width of the plane.
	 * @param height
	 *            the height of the plane.
	 */
	public SimplePlane(float width, float height) {
		// Mapping coordinates for the vertices
		float textureCoordinates[] = { 0.0f, 2.0f, //
				2.0f, 2.0f, //
				0.0f, 0.0f, //
				2.0f, 0.0f, //
		};

		short[] indices = new short[] { 0, 1, 2, 1, 3, 2 };

		float[] vertices = new float[] { -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f,
				-0.5f, 0.5f, 0.0f, 0.5f, 0.5f, 0.0f };

		setIndices(indices);
		setVertices(vertices);
		setTextureCoordinates(textureCoordinates);
	}

	FloatBuffer mTextureBuffer;

	protected void setTextureCoordinates(float[] textureCoords) {
		// float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer byteBuf = ByteBuffer
				.allocateDirect(textureCoords.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mTextureBuffer = byteBuf.asFloatBuffer();
		mTextureBuffer.put(textureCoords);
		mTextureBuffer.position(0);
	}
}