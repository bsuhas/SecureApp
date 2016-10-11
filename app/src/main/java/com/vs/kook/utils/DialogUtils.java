package com.vs.kook.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.vs.kook.R;
import com.vs.kook.view.interfaces.IDialogCallBack;
import com.vs.kook.view.interfaces.IShipmentDialogCallBack;


/**
 * Created by SUHAS on 07/05/2016.
 */
public class DialogUtils {

    public static void showAlertDialog(Context context, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }

    /**
     * Method used to show alert dialog with two buttons
     *
     * @param context
     * @param title
     * @param msg
     */
    public static void showDeleteDialog(final Context context, String title, String msg, final IDialogCallBack mDialogCallBack) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                mDialogCallBack.getDialogCallBack(true);
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mDialogCallBack.getDialogCallBack(false);
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }

    public static void showYesNoConfirmDialog(final Context context, String title, String msg, final IShipmentDialogCallBack callBack) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                callBack.isShipmentWithoutSealNumber(true);
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                callBack.isShipmentWithoutSealNumber(false);
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }

    public static void showYesNoLogoutConfirmDialogAndFinishActivity(final Activity mActivity, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                mActivity.finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }

    public static ProgressDialog showProgressDialog(Activity activity)
    {
        ProgressDialog dialog = ProgressDialog.show(activity, null, "", true);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loaders_progress_dialog);
        return dialog;
    }
}
