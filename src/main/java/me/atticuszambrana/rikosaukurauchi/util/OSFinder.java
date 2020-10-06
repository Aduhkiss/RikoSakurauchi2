package me.atticuszambrana.rikosaukurauchi.util;

import me.atticuszambrana.rikosaukurauchi.common.OperatingSystem;

public class OSFinder {
	public static OperatingSystem getOS() {
		String name = System.getProperty("os.name");
		if(name.indexOf("Windows") >= 0) {
			return OperatingSystem.WINDOWS;
		}
		if(name.indexOf("Linux") >= 0) {
			return OperatingSystem.LINUX;
		}
		return null;
	}
}