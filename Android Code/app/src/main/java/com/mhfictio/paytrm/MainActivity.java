package com.mhfictio.paytrm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mhfictio.paytrm.paytm.PaytmClient;
import com.mhfictio.paytrm.paytm.ResponsePay;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;


import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static  final String MID="IdKFwp30847595727216";
    private static  final String CHANNEL_ID="WAP";
    private static  final String industrie="Retail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startPayment(View view)
    {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        getToken(uuid);
    }

    void getToken(String orderid)
    {
        String callbackurl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+orderid;
        Call<ResponsePay> call = PaytmClient.getInstance().getApi().getToken(MID,orderid,"customer123",CHANNEL_ID,"100","DEFAULT",callbackurl,industrie);
        //Call<Response> call= PaytmClient.getInstance().getApi().getToken(MID,orderid);


        call.enqueue(new Callback<ResponsePay>() {

            @Override
            public void onResponse(Call<ResponsePay> call, Response<ResponsePay> response) {
                System.out.println("Token"+response.code());

                if(response.isSuccessful())
                {
                    String token = response.body().getToken();
                    startPaytm(orderid,token,callbackurl);
                }
                else
                {
                    System.out.println("*************** "+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponsePay> call, Throwable t)
            {
                System.out.println("***************");
            }
        });
    }

    void startPaytm(String orderid,String txnToken,String callbackurl)
    {

        PaytmOrder paytmOrder = new PaytmOrder(orderid, MID, txnToken, "100",callbackurl);

        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(@Nullable Bundle bundle) {

            }

            @Override
            public void networkNotAvailable() {

            }

            @Override
            public void onErrorProceed(String s) {

            }

            @Override
            public void clientAuthenticationFailed(String s) {

            }

            @Override
            public void someUIErrorOccurred(String s) {

            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {

            }

            @Override
            public void onBackPressedCancelTransaction() {

            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {

            }
        });
        transactionManager.setAppInvokeEnabled(false);
        transactionManager.startTransaction(this, 2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && data != null) {
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }
    }


    public void onTransactionResponse(Bundle inResponse) {
        /*Display the message as below */
        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
    }



}