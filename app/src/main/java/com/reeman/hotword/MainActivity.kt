package com.reeman.hotword

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.reeman.hotword.activity.AddressActivity
import com.reeman.hotword.activity.LinkmanActivity
import com.reeman.hotword.activity.WordActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val btWord by lazy { find<Button>(R.id.bt_word) }
    val btLinkman by lazy { find<Button>(R.id.bt_linkman) }
    val btAddress by lazy { find<Button>(R.id.bt_address) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btWord.setOnClickListener(this)
        btLinkman.setOnClickListener(this)
        btAddress.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_word -> startActivity<WordActivity>()
            R.id.bt_linkman -> startActivity<LinkmanActivity>()
            R.id.bt_address -> startActivity<AddressActivity>()
        }
    }
}
