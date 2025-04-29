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
		case "Demon": {
			if (animation == "idle") {
				this.frameStart = 0;
				this.frameEnd = 7;
			}
			if (animation == "run") {
				this.frameStart = 8;
				this.frameEnd = 15;
			}
			if (animation == "attack") {
				this.frameStart = 16;
				this.frameEnd = 23;
			}
			if (animation == "gotHit") {
				this.frameStart = 24;
				this.frameEnd = 28;
			}
			if (animation == "die") {
				this.frameStart = 29;
				this.frameEnd = 33;
			}
			if (animation == "jump") {
				this.frameStart = 34;
				this.frameEnd = 41;
			}
			break;
		}
		case "Boss": {
			if (animation == "idle") {
				this.frameStart = 0;
				this.frameEnd = 31;
			}
			if (animation == "run") {
				this.frameStart = 32;
				this.frameEnd = 63;
			}
			if (animation == "attack") {
				this.frameStart = 64;
				this.frameEnd = 83;
			}
			if (animation == "die") {
				this.frameStart = 84;
				this.frameEnd = 107;
			}
			if (animation == "buff") {
				this.frameStart = 108;
				this.frameEnd = 129;
			}
			if (animation == "range") {
				this.frameStart = 130;
				this.frameEnd = 143;
			}
			if (animation == "combo") {
				this.frameStart = 144;
				this.frameEnd = 173;
			}
			break;
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
