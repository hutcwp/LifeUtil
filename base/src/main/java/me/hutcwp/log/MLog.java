package me.hutcwp.log;

import com.socks.library.KLog;

import java.io.File;

/**
 * Created by hutcwp on 2019-06-06 12:12
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class MLog {

    public static void v() {
        KLog.v();
    }

    public static void v(Object msg) {
        KLog.v(msg);
    }

    public static void v(String tag, Object... objects) {
        KLog.v(tag,objects);
    }

    public static void debug() {
        KLog.d();
    }

    public static void debud(Object msg) {
        KLog.d(msg);
    }

    public static void debug(String tag, Object... objects) {
        KLog.d(tag,objects);
    }

    public static void info() {
        KLog.i();
    }

    public static void info(Object msg) {
        KLog.i(msg);
    }

    public static void info(String tag, Object... objects) {
        KLog.i(tag,objects);
    }

    public static void warn() {
        KLog.w();
    }

    public static void warn(Object msg) {
        KLog.w(msg);
    }

    public static void warn(String tag, Object... objects) {
        KLog.w(tag,objects);
    }

    public static void error() {
        KLog.e();
    }

    public static void error(Object msg) {
        KLog.e(msg);
    }

    public static void error(String tag, Object... objects) {
        KLog.w(tag,objects);
    }

    public static void a() {
        KLog.a();
    }

    public static void a(Object msg) {
        KLog.a(msg);
    }

    public static void a(String tag, Object... objects) {
        KLog.a(tag,objects);
    }

    public static void json(String jsonFormat) {
        KLog.json(jsonFormat);
    }

    public static void json(String tag, String jsonFormat) {
        KLog.json(tag,jsonFormat);
    }

    public static void xml(String xml) {
        KLog.xml(xml);
    }

    public static void xml(String tag, String xml) {
        KLog.xml(tag,xml);
    }

    public static void file(File targetDirectory, Object msg) {
        KLog.file(targetDirectory,msg);
    }

    public static void file(String tag, File targetDirectory, Object msg) {
        KLog.file(tag,targetDirectory,msg);
    }

    public static void file(String tag, File targetDirectory, String fileName, Object msg) {
        KLog.file(tag, targetDirectory, fileName, msg);
    }

    public static void printDebug() {
        KLog.debug();
    }

    public static void printDebug(Object msg) {
        KLog.debug(msg);
    }

    public static void printDebug(String tag, Object... objects) {
        KLog.debug(tag,objects);
    }

    public static void trace() {
        KLog.trace();
    }

}
