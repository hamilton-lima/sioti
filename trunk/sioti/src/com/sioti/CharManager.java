package com.sioti;

import pulpcore.image.CoreImage;
import pulpcore.sprite.Group;
import pulpcore.sprite.ImageSprite;

public class CharManager extends Group{
	
	private CoreImage back;
	private CoreImage front;
	private CoreImage left;
	private CoreImage right;
	
	private static CharManager instance;

	private CharManager(){
		back = CoreImage.load("char_back.png");
		front = CoreImage.load("char_front.png");
		left = CoreImage.load("char_left.png");
		right = CoreImage.load("char_right.png");
	}
	
	public static CharManager getInstance() {
		if( instance == null ){
			instance = new CharManager();
		}
		return instance;
	}

	public CoreImage getBack() {
		return back;
	}

	public CoreImage getFront() {
		return front;
	}

	public CoreImage getLeft() {
		return left;
	}

	public CoreImage getRight() {
		return right;
	}


}
