package com.ggu.share

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ggu.share.upload.ui.UploadDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        findViewById<FloatingActionButton>(R.id.upload_button).setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.upload_button -> UploadDialogFragment.newInstance().show(supportFragmentManager, UploadDialogFragment.TAG)
        }
    }

}