//package com.shelfy;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.HttpHeaderParser;
//import com.android.volley.toolbox.JsonObjectRequest;
//
///**
// * Created by Sarang on 10/10/14.
// */
//public class VotingRequest {
//
//    final static String BASE_URL = ApplicationConstants.BASE_URL;
//    static SharedPreferences prefs;
//
//    public static void postVote(String endpoint,
//                                          Response.Listener<JSONObject> responseListener,
//                                          final String scheme, JSONObject params,
//                                          final String sessionToken, final Context context) {
//
//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.PUT, BASE_URL + endpoint,
//                params, responseListener,
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        try {
//                            String responseBody = new String(error.networkResponse.data, "utf-8");
//                            JSONObject jsonObject = new JSONObject(responseBody);
//
//                            if (error.networkResponse.statusCode == 406 || error.networkResponse.statusCode == 415) {
//                                Intent intent = new Intent(context, ForcedUpgradeActivity.class);
//                                context.startActivity(intent);
//                            }
//
//                            // handle error
//                            String message = ResponseErrorHelper.getMessage(error, context);
//                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                        } catch (Exception e) {
//                        }
//                    }
//                }
//        ) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json; scheme="+scheme+"; version=0");
//                params.put("x-session-token", ""+sessionToken);
//                return params;
//            }
//
//            @Override
//            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//
//                JSONObject MyResponse = new JSONObject();
//                try {
//                    MyResponse.put("status", String.valueOf(response.statusCode));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return Response.success(MyResponse, HttpHeaderParser.parseCacheHeaders(response));
//            }
//        };
//
//        ApplicationController.getInstance().addToRequestQueue(getRequest);
//    }
//}
