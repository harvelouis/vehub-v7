package com.verihubs.android.plugin.liveness;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import com.verihubs.msdk_bca.Verihubs;
import static com.verihubs.msdk_bca.VerihubsType.CREATEUSER_CODE;
import static com.verihubs.msdk_bca.VerihubsType.LIVENESS_CODE;

import static com.verihubs.msdk_bca.VerihubsType.RESULT_ACTIVE_FAIL;
import static com.verihubs.msdk_bca.VerihubsType.RESULT_PASSIVE_FAIL;

public class VerihubsCordova extends CordovaPlugin {
  
    private static int RESULT_OK = -1;
    private static int RESULT_CANCELED = 0;
    private static int MODE_PRIVATE = 0;
    private static CallbackContext callback;
    private Verihubs obj;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
        
        this.callback = callbackContext;

        if(action.equals("initClass")){
            obj = new Verihubs(cordova.getActivity());

            PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
            pluginResult.setKeepCallback(true);
            this.callback.sendPluginResult(pluginResult);
            this.callback.success();
            return true;
        }

        if(action.equals("verifyLiveness")){
            int instructions_count;
            int timeout;
            try{
                instructions_count = args.getInt(0);
                timeout = args.getInt(1);
            }
            catch(JSONException e){
                callbackContext.error("Error encountered: " + e.getMessage());
                return false;
            }
            cordova.setActivityResultCallback(this);
            obj.verifyLiveness(instructions_count, timeout);

            PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
            pluginResult.setKeepCallback(true);
            this.callback.sendPluginResult(pluginResult);
            return true;
        }

        if(action.equals("getVersion")){
            PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
            pluginResult.setKeepCallback(true);
            this.callback.sendPluginResult(pluginResult);
            try{
                JSONObject jsonResult = new JSONObject();
                jsonResult.put("version", "1.0.0");    
                this.callback.success(jsonResult);
            }catch(JSONException e){
                this.callback.error("Error encountered: " + e.getMessage());
            }
        }

        callbackContext.error("\"" + action + "\" is not a recognized action.");
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LIVENESS_CODE)
        {
            if(resultCode == RESULT_OK || resultCode == RESULT_PASSIVE_FAIL || resultCode == RESULT_ACTIVE_FAIL || resultCode == RESULT_CANCELED)
            {
                int status = data.getIntExtra("status", 500);
                int total_instruction = data.getIntExtra("total_instruction", 0);
                SharedPreferences sp = cordova.getActivity().getSharedPreferences("verihubs-storage", MODE_PRIVATE);
                try{
                        JSONObject jsonResult = new JSONObject();
                        jsonResult.put("status", status);
                        jsonResult.put("total_instruction", total_instruction);
                        
                        for(int i=1; i<=total_instruction; ++i){
                                String encoded = sp.getString("image" + (i-1), "");
                                jsonResult.put("base64String_" + i, encoded);
                                String instruction = data.getStringExtra("instruction" + i);
                                jsonResult.put("instruction" + i, instruction);
                        }
                        obj.clean(total_instruction);
                        this.callback.success(jsonResult);
                }
                catch(JSONException e){
                        this.callback.error("Error encountered: " + e.getMessage());
                }
            }
        }
    }
}