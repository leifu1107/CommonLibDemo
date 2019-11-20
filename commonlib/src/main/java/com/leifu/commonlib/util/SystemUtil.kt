package com.leifu.commonlib.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

/**
 *创建人:雷富
 *创建时间:2019/7/24 13:37
 *描述:
 */
class SystemUtil {
    companion object {


        /**
         * 拨打电话（跳转到拨号界面，用户手动点击拨打）
         *
         * @param phoneNum 电话号码
         */
        fun callPhone(context: Context, phone: String) {
            val intent = Intent(Intent.ACTION_DIAL)
            val data = Uri.parse("tel:$phone")
            intent.data = data
            context.startActivity(intent)
        }

        /**
         * 获取APP版本号
         *
         * @param context
         * @return
         */
        fun getAppVersion(context: Context): String {
            val info = context.packageManager.getPackageInfo(context.packageName, 0)
            return info.versionName
        }

        /**
         * 保存文字到剪贴板
         *
         * @param context
         * @param text
         */
        fun copyToClipBoard(context: Context, text: String) {
            val clipData = ClipData.newPlainText("url", text)
            val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            manager.primaryClip = clipData
            Toast.makeText(context,"已复制到剪贴板",Toast.LENGTH_LONG).show()
        }
    }
}