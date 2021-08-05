package com.zhou.autoshell

import android.app.AlertDialog
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var adminDialogTip: AlertDialog? = null
    private var settingEdit: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingEdit = findViewById(R.id.settingEdit)

        // 设置点击事件，点击完成后保存到配置中
        findViewById<Button>(R.id.settingBtn).setOnClickListener {
            val settingCmd = settingEdit?.text
            if (settingCmd.toString().isNotEmpty()) {
                PreUtils.put(this@MainActivity, App.SHELL_KEY, settingCmd.toString())
                Toast.makeText(
                    this@MainActivity, "命令保存成功,下次开机将自动执行", Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this, "请输入需要开机执行的脚本命令", Toast.LENGTH_LONG
                ).show()
            }
        }

        //重置界面数据
        settingEdit?.setText(PreUtils.get(this@MainActivity, App.SHELL_KEY, ""))
    }

    override fun onResume() {
        super.onResume()
        checkAdmin()
    }

    override fun onStop() {
        super.onStop()
        clearDialog()
    }

    private fun checkAdmin() {
        val dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val cn = ComponentName(application, MyAdmin::class.java)
        clearDialog()
        if (!dpm.isAdminActive(cn)) {
            adminDialogTip = AlertDialog.Builder(this)
                .setTitle("设备管理器激活")
                .setMessage("为保证开机执行,请激活设备管理器(确保开机启动)")
                .setPositiveButton("确定") { dialogInterface: DialogInterface, i: Int ->
                    gotoPolicy()
                    dialogInterface.dismiss()
                    adminDialogTip = null
                }
                .setCancelable(false)
                .create()
            adminDialogTip?.show()
        }
    }

    private fun clearDialog() {
        val adminDialogTip = this.adminDialogTip
        if (adminDialogTip != null && adminDialogTip.isShowing()) {
            adminDialogTip.dismiss()
            this.adminDialogTip = null
        }
    }

    private fun gotoPolicy() {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        val cn = ComponentName(this, MyAdmin::class.java)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn)
        //提示用户开启管理员
        intent.putExtra(
            DevicePolicyManager.EXTRA_ADD_EXPLANATION,
            "请激活设备管理器"
        )
        startActivity(intent)
    }
}