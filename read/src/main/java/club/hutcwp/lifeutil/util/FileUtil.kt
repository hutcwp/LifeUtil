package club.hutcwp.lifeutil.util

import android.content.Context
import android.text.TextUtils
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader

import me.hutcwp.BaseConfig

/**
 * Created by liyu on 2016/12/1.
 */

object FileUtil {

    val isExistSDCard: Boolean
        get() = android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED

    /**
     * 如果存在外部SD卡则返回外部缓存路径
     *
     * @param context
     * @return
     */
    fun getAppCacheDir(context: Context): String {
        return if (context.externalCacheDir != null && isExistSDCard) {
            context.externalCacheDir!!.toString()
        } else {
            context.cacheDir.toString()
        }
    }

    fun getInternalCacheDir(context: Context): String {
        return context.cacheDir.toString()
    }

    fun getExternalCacheDir(context: Context): String? {
        return if (context.externalCacheDir != null && isExistSDCard) {
            context.externalCacheDir!!.toString()
        } else
            null
    }

    fun delete(path: String): Boolean {
        return delete(File(path))
    }

    fun delete(file: File): Boolean {
        if (file.isFile) {
            return file.delete()
        }
        if (file.isDirectory) {
            val childFiles = file.listFiles()
            if (childFiles == null || childFiles.size == 0) {
                return file.delete()
            }
            for (childFile in childFiles) {
                delete(childFile)
            }
            return file.delete()
        }
        return false
    }

    fun getFolderName(filePath: String): String? {

        if (TextUtils.isEmpty(filePath)) {
            return filePath
        }

        val filePosi = filePath.lastIndexOf(File.separator)
        return if (filePosi == -1) "" else filePath.substring(0, filePosi)
    }

    fun makeDirs(filePath: String): Boolean {
        val folderName = getFolderName(filePath)
        if (TextUtils.isEmpty(folderName)) {
            return false
        }

        val folder = File(folderName!!)
        return if (folder.exists() && folder.isDirectory) true else folder.mkdirs()
    }

    fun writeFile(filePath: String, content: String, append: Boolean): Boolean {
        if (TextUtils.isEmpty(content)) {
            return false
        }

        var fileWriter: FileWriter? = null
        try {
            makeDirs(filePath)
            fileWriter = FileWriter(filePath, append)
            fileWriter.write(content)
            fileWriter.write("\r\n")
            fileWriter.write("\r\n")
            fileWriter.close()
            return true
        } catch (e: IOException) {
            Log.e("IOException occurred. ", e.message, e)
            return false
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close()
                } catch (e: IOException) {
                    Log.e("IOException occurred. ", e.message, e)
                }

            }
        }
    }

    fun readFile(filePath: String): String? {
        val file = File(filePath)
        val fileContent = StringBuilder("")
        if (!file.isFile) {
            return null
        }

        var reader: BufferedReader? = null
        try {
            val `is` = InputStreamReader(FileInputStream(file))
            reader = BufferedReader(`is`)
            var line: String? = reader.readLine()
            while (line != null) {
                if (fileContent.toString() != "") {
                    fileContent.append("\r\n")
                }
                fileContent.append(line)
            }
            reader.close()
            return fileContent.toString()
        } catch (e: IOException) {
            Log.e("IOException occurred. ", e.message, e)
            return ""
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    Log.e("IOException occurred. ", e.message, e)
                }

            }
        }
    }


    fun getFileDir(filePath: String): String {
        var dir: String
        if (isExistSDCard) {
            dir = BaseConfig.getApplicationContext()!!.getExternalFilesDir("")!!.absolutePath
        } else {
            dir = BaseConfig.getApplicationContext()!!.filesDir.absolutePath
        }

        if (TextUtils.isEmpty(filePath))
            return dir
        else {
            if (filePath.startsWith(File.separator)) {
                dir += filePath
            } else
                dir += File.separator + filePath


            FileUtil.makeDirs(dir)

            return dir
        }
    }
}
