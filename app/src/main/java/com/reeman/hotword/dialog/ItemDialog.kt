package com.reeman.hotword.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.reeman.hotword.R
import org.jetbrains.anko.find


/**
 * Created by YSAN on 2018/05/03
 */
class ItemDialog(
        private val word: String,
        private val onListener: DialogClickListener,
        context: Context?) : Dialog(context),
        View.OnClickListener
{
    override fun onClick(v: View?) {
        onListener.invoke(v, itemWord.text.toString().trim())
    }

    private val itemDelete by lazy { find<Button>(R.id.bt_item_delete) }
    private val itemCancel by lazy { find<Button>(R.id.bt_item_cancel) }
    private val itemUpdate by lazy { find<Button>(R.id.bt_item_update) }
    private val itemWord by lazy { find<EditText>(R.id.et_item_word) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_item)

        setTitle("修改/删除")
        itemWord.setText(word)
        itemWord.setSelection(word.length)
        itemDelete.setOnClickListener(this)
        itemCancel.setOnClickListener(this)
        itemUpdate.setOnClickListener(this)
    }

    interface DialogClickListener {
        operator fun invoke(v: View?, word: String)
    }
}