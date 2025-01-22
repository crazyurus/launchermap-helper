package com.crazyurus.launchermap.helper

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity

class JumpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var map = getSharedPreferences("map", MODE_PRIVATE).getString("map", "")
        var newIntent = Intent(Intent.ACTION_VIEW)

        if (map == "com.byd.launchermap" || map == "") {
            var componentName = ComponentName("com.byd.launchermap", "com.byd.automap.activity.EmptyJumpActivity")

            newIntent.component = componentName

            if (intent.data?.host == "navi") {
                var dname = intent.data?.getQueryParameter("dname")
                var lat = intent.data?.getQueryParameter("dlat")
                var lng = intent.data?.getQueryParameter("dlon")

                newIntent.putExtra("protocol_type", "protocol_type_start_navi")
                newIntent.putExtra("poiName", dname)
                newIntent.putExtra("lat", lat)
                newIntent.putExtra("lng", lng)
            }

            if (intent.data?.host == "launch_go_home") {
                newIntent.putExtra("voice_operation_type", "voice_type_go_home")
            }

            if (intent.data?.host == "launch_go_company") {
                newIntent.putExtra("voice_operation_type", "voice_type_go_company")
            }

            newIntent.putExtra("start_from", "voice")
        }

        if (map == "com.baidu.naviauto") {
            if (intent.data?.host == "navi") {
                var dname = intent.data?.getQueryParameter("dname")
                var lat = intent.data?.getQueryParameter("dlat")
                var lng = intent.data?.getQueryParameter("dlon")

                newIntent.setData(Uri.parse("baidumap://map/navi?src=com.crazyurus.launchermap.helper&location=$lat,$lng&coord_type=gcj02&query=$dname"))
            }

            if (intent.data?.host == "launch_go_home") {
                newIntent.setData(Uri.parse("baidumap://map/navi/common?src=com.crazyurus.launchermap.helper&addr=home&coord_type=gcj02"))
            }

            if (intent.data?.host == "launch_go_company") {
                newIntent.setData(Uri.parse("baidumap://map/navi/common?src=com.crazyurus.launchermap.helper&addr=company&coord_type=gcj02"))
            }
        }

        if (map == "com.tencent.wecarnavi") {
            if (intent.data?.host == "navi") {
                var dname = intent.data?.getQueryParameter("dname")
                var lat = intent.data?.getQueryParameter("dlat")
                var lng = intent.data?.getQueryParameter("dlon")

                newIntent.setData(Uri.parse("qqmap://wecarmap/routeplan?to=$dname&tocoord=$lat,$lng"))
            }

            if (intent.data?.host == "launch_go_home") {
               Toast.makeText(this,"腾讯智驾地图暂不支持回家", Toast.LENGTH_SHORT).show()
            }

            if (intent.data?.host == "launch_go_company") {
                Toast.makeText(this,"腾讯智驾地图暂不支持去公司", Toast.LENGTH_SHORT).show()
            }
        }

        startActivity(newIntent)
        finish()
    }
}