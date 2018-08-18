package travel.com.utility;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import travel.com.R;


/**
 * Created by mostafa_anter on 9/2/16.
 */
public class SweetDialogHelper {
    private FragmentActivity mContext;
    private SweetAlertDialog pDialog;
    public static boolean cancelable;
    public interface DoAction{
        void doSomeThing();
    }
    public SweetDialogHelper(FragmentActivity mContext) {
        this.mContext = mContext;
    }

    public void showMaterialProgress(String message) {
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#69ABB2"));
        pDialog.setTitleText(message);
        pDialog.setCancelable(cancelable);
        pDialog.show();
    }

    public void showBasicMessage(String message) {
        pDialog = new SweetAlertDialog(mContext);
        pDialog.setTitleText(message).show();
    }

    public void showTitleWithATextUnder(String title, String message, final DoAction doAction) {
        pDialog = new SweetAlertDialog(mContext);
        pDialog.setTitleText(title)
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        doAction.doSomeThing();
                    }
                })
                .show();
    }

    public void showWarningMessage(String title, String message, String confirmMessage) {
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText(title)
                .setContentText(message)
                .setConfirmText(confirmMessage)
                .show();
    }

    public void showSuccessfulMessage(String title, String message, Drawable drawable, final DoAction action) {
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText(title)
                .setContentText(message)
                .setCustomImage(drawable)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        action.doSomeThing();
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void showSuccessfulMessage(String title, String message, final DoAction action) {
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText(title)
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        action.doSomeThing();
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void showErrorMessage(String title, String message) {
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText(title)
                .setContentText(message)
                .show();
    }

    public void dismissDialog() {
        pDialog.dismissWithAnimation();
    }

    private int i = -1;
    public void showWithTimmer(final DoAction doAction){
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Loading");
        pDialog.show();
        pDialog.setCancelable(false);
        new CountDownTimer(800 * 5, 800) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i) {
                    case 0:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.success_stroke_color));
                        break;
                }
            }

            public void onFinish() {
                i = -1;
                pDialog.setTitleText("Success!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                doAction.doSomeThing();
                                pDialog.dismissWithAnimation();
                            }
                        })
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        }.start();
    }
}
