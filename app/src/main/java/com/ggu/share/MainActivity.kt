package com.ggu.share

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ggu.share.databinding.ActivityMainBinding
import com.ggu.share.upload.ui.UploadDialogFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }


    private fun initView() {
        binding.uploadButton.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.upload_button -> UploadDialogFragment.newInstance().show(supportFragmentManager, UploadDialogFragment.TAG)
        }
    }
}