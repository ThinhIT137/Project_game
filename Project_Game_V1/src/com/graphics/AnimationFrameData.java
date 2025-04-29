package com.graphics;

public class AnimationFrameData {
	private int frameStart;
	private int frameEnd;

	public AnimationFrameData(String name, String animation) {
		switch (name) {
		case "player": {
			if (animation == "idle") {
				this.frameStart = 0;
				this.frameEnd = 3;
			}
			if (animation == "run") {
				this.frameStart = 4;
				this.frameEnd = 11;
			}
			if (animation == "jump") {
				this.frameStart = 12;
				this.frameEnd = 14;
			}
			if (animation == "crouch") {
				this.frameStart = 14;
				this.frameEnd = 16;
			}
			if (animation == "attack") {
				this.frameStart = 15;
				this.frameEnd = 31;
			}
			break;
		}
		case "amram": {

		}
		}
	}

	public int getFrameStart() {
		return frameStart;
	}

	public int getFrameEnd() {
		return frameEnd;
	}
}
