package com.floatingball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.floatingball.comunication.ConfirmParameter;
import com.floatingball.comunication.IFacebookAPI;

public class FacebookAPI implements IFacebookAPI {
    
    private final String APPLICATION_ID = "529071227210911";

    private Activity ctx;
    
    public FacebookAPI(Activity ctx) {
        this.ctx = ctx;
    }
    
    @Override
    public void updateStatus(final String status, final String url,  final String success, final String failure, final ConfirmParameter param) {

        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(ctx)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(param.getDialogTitle())
                .setMessage(param.getDialogContent())
                .setPositiveButton(param.getOkBtn(), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                        String[] permissions = {"publish_actions"};
                        Session session = Session.getActiveSession();
                        
                        if (session != null && SessionState.CREATED_TOKEN_LOADED.equals(session.getState())) {
                            session.openForPublish(new Session.OpenRequest(ctx)
                                .setPermissions(permissions)
                                .setCallback(new Session.StatusCallback() {
   
                                   @Override
                                   public void call(Session session, SessionState state, Exception exception) {
                                       if (session.isOpened()) {
                                           doUpdateStatus(session, url, status, success, failure);
                                       }
                                   }
                                }
                            ));
                            
                        } else {
                        
                            session = new Session.Builder(ctx)
                                .setApplicationId(APPLICATION_ID)
                                .build();
    
                            Session.setActiveSession(session);
    
                            Session.openActiveSession(ctx, true, new Session.StatusCallback() {
                                
                                @Override
                                public void call(Session session, SessionState state, Exception exception) {
                                    if (session.isOpened()) {
                                        doUpdateStatus(session, url, status, success, failure);
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton(param.getCancelBtn(), null)
                .show();
            }
        });
    }
    
    private void doUpdateStatus(Session session, String url, String status, final String success, final String failure) {
        
        Request.newStatusUpdateRequest(session, status + " " + url, new Request.Callback() {
            
            @Override
            public void onCompleted(Response response) {
                if (response.getError() == null) {
                    Toast.makeText(ctx, success, Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Failed: "+response.getError());
                    Toast.makeText(ctx, failure, Toast.LENGTH_LONG).show();
                }
            }
        }).executeAsync();
    }   
}
