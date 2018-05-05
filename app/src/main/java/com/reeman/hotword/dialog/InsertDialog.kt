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
 * Created by YSAN on 2018/05/04
 */
class InsertDialog(private val onListener: InsertClickListener, context: Context?) : Dialog(context), View.OnClickListener {
    override fun onClick(v: View?) {
        onListener.invoke(v, insertInput.text.toString().trim())
    }

    private val insertCancel by lazy { find<Button>(R.id.bt_insert_cancel) }
    private val insertConfirm by lazy { find<Button>(R.id.bt_insert_confirm) }
    private val insertInput by lazy { find<EditText>(R.id.et_insert_word) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_insert)

        setTitle("添加")
        insertCancel.setOnClickListener(this)
        insertConfirm.setOnClickListener(this)
    }

    interface InsertClickListener {
        operator fun invoke(v: View?, word: String)
    }
}