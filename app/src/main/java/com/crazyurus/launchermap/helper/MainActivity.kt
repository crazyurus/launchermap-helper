package com.crazyurus.launchermap.helper

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crazyurus.launchermap.helper.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun getDefaultAppName(packageName: String?): String {
        if (packageName == "com.byd.automap") {
            return "DiLink 4.0 高德地图定制版（2.x）"
        }

        if (packageName == "com.byd.launchermap") {
            return "DiLink 5.0 高德地图定制版（3.x）"
        }

        if (packageName == "com.crazyurus.launchermap.helper") {
            return "地图语音助手（默认应用正确无需操作）"
        }

        return "未设置默认应用"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var preference = getSharedPreferences("map", MODE_PRIVATE)
        var map = preference.getString("map", "")

        if (map.equals("com.byd.launchermap")) {
            binding.radioButton2.isChecked = true
            binding.radioButton3.isChecked = false
            binding.radioButton4.isChecked = false
        } else if (map.equals("com.baidu.naviauto")) {
            binding.radioButton2.isChecked = false
            binding.radioButton3.isChecked = true
            binding.radioButton4.isChecked = false
        } else if (map.equals("com.tencent.wecarnavi")) {
            binding.radioButton2.isChecked = false
            binding.radioButton3.isChecked = false
            binding.radioButton4.isChecked = true
        }

        var info = packageManager.resolveActivity(Intent(Intent.ACTION_VIEW, Uri.parse("bydautomap://navi")), PackageManager.MATCH_DEFAULT_ONLY)

        if (info != null) {
            binding.textViewApp.text = this.getDefaultAppName(info.activityInfo?.packageName)
        }

        binding.button.isEnabled = info?.activityInfo?.packageName != "android"
        binding.button.setOnClickListener {
            var intent = Intent(Intent.ACTION_DELETE)

            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.setData(Uri.parse("package:" + info?.activityInfo?.packageName))

            startActivity(intent)
        }

        binding.radioButton2.setOnClickListener {
            var editor = preference.edit()

            editor.putString("map", "com.byd.launchermap")
            editor.commit()
        }

        binding.radioButton3.setOnClickListener {
            var editor = preference.edit()

            editor.putString("map", "com.baidu.naviauto")
            editor.commit()
        }

        binding.radioButton4.setOnClickListener {
            var editor = preference.edit()

            editor.putString("map", "com.tencent.wecarnavi")
            editor.commit()
        }
    }
}