package com.smoothframe.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2015/10/29 0029.
 */
public class LogUtil {
	/**
	 * 日志输出级别NONE
	 */
	public static final int LEVEL_OFF = 0;
	/**
	 * 日志输出级别NONE
	 */
	public static final int LEVEL_ALL = 7;

	/**
	 * 日志输出级别V
	 */
	public static final int LEVEL_VERBOSE = 1;
	/**
	 * 日志输出级别D
	 */
	public static final int LEVEL_DEBUG = 2;
	/**
	 * 日志输出级别I
	 */
	public static final int LEVEL_INFO = 3;
	/**
	 * 日志输出级别W
	 */
	public static final int LEVEL_WARN = 4;
	/**
	 * 日志输出级别E
	 */
	public static final int LEVEL_ERROR = 5;
	/**
	 * 日志输出级别S,自定义定义的一个级别
	 */
	public static final int LEVEL_SYSTEM = 6;


	/**
	 * 是否允许输出log
	 */
	public static int DEBUGLEVEL = LEVEL_ALL;	//日志开关
	private static int mDebuggable = DEBUGLEVEL;

	/**
	 * 用于记时的变量
	 */
	private static long mTimestamp = 0;
	/**
	 * 写文件的锁对象
	 */
	private static final Object mLogLock = new Object();

	/**
	 * 以级别为 d 的形式输出LOG
	 */
	public static void v(Class<?> getClass, String msg) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.v(getClassName(getClass), msg);
		}
	}

	public static void i(Class<?> getClass, String msg) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.i(getClassName(getClass), msg);
		}
	}

	public static void d(Class<?> getClass, String msg) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.d(getClassName(getClass), msg);
		}
	}

	public static void e(Class<?> getClass, String msg) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.e(getClassName(getClass), msg);
		}
	}

	public static void w(Class<?> getClass, String msg) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.w(getClassName(getClass), msg);
		}
	}

	public static void wtf(Class<?> getClass, String msg) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.wtf(getClassName(getClass), msg);
		}
	}


	/**
	 * 以级别为 i 的形式输出msg信息,附带时间戳，用于输出一个时间段起始点
	 *
	 * @param msg 需要输出的msg
	 */
	public static void msgStartTime(Class<?> getClass, String msg) {
		mTimestamp = System.currentTimeMillis();
		if (!TextUtils.isEmpty(msg)) {
			i(getClass, "[Started：" + mTimestamp + "]" + msg);
		}
	}

private static String getClassName(Class<?> clas){
	String s=clas.getName().toString();
	return s.substring(s.lastIndexOf(".")+1,s.length()-1);
}
}
