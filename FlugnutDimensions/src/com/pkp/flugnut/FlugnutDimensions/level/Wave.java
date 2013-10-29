package com.pkp.flugnut.FlugnutDimensions.level;

public class Wave {

	public static enum WaveType {
		FIRE, HEAVY_MISSILE, HOVER_CRAFT, PARACHUTER,
		SEEKER, SIMPLE_MISSILE, SUPER_BOMB
	}
	
	public WaveType waveType;
	public float startTime;
    public float nextStartTime;
	public int quantity;
	public boolean started;
	
	public Wave(WaveType waveType, float waveStart, int quantity) {
		this.waveType = waveType;
		this.startTime = waveStart;
		this.quantity = quantity;
        this.nextStartTime = waveStart;
	}
}