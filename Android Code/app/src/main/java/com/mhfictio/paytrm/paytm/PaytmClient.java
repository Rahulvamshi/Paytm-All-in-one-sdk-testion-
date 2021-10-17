package com.mhfictio.paytrm.paytm;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaytmClient
{
    private static final String baseurl="https://partner.bunkmeal.com/paytmgateway/";
    private  static  PaytmClient myClient;
    private Retrofit retrofit;

    private  PaytmClient()
    {
        retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public  static synchronized PaytmClient getInstance()
    {
        if(myClient == null)
        {
            myClient = new PaytmClient();
        }
        return  myClient;
    }

    public Api getApi()
    {
        return  retrofit.create(Api.class);
    }
}
