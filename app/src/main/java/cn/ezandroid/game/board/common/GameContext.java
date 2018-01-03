/** Copyright by Barry G. Becker, 2000-2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package cn.ezandroid.game.board.common;

/**
 * 游戏上下文
 *
 * @author Barry Becker
 */
public final class GameContext {

    // 默认关闭， 大于0时，调试模式开启
    private static final int DEBUG_LEVEL = 0;

    // 调试模式
    private static int sDebugLevel = DEBUG_LEVEL;

    private GameContext() {}

    /**
     * 获取当前调试等级
     *
     * @return
     */
    public static int getDebugMode() {
        return sDebugLevel;
    }

    /**
     * 设置调试等级
     *
     * @param debug
     */
    public static void setDebugMode(int debug) {
        sDebugLevel = debug;
    }

    /**
     * 打印
     *
     * @param logLevel
     * @param message
     */
    public static void log(int logLevel, String message) {
        if (sDebugLevel > 0) {
            System.out.println(message);
        }
    }
}
