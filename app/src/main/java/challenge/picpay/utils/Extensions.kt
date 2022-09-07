package challenge.picpay.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun Context.createDialog(block: AlertDialog.Builder.() -> Unit = {}): AlertDialog {
    val builder = AlertDialog.Builder(this)
    builder.setPositiveButton(android.R.string.ok, null)
    block(builder)
    return builder.create()
}
