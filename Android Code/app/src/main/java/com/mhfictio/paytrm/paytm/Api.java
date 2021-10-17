package com.mhfictio.paytrm.paytm;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("gateway.php")
    Call<ResponsePay> getToken(
            @Field("MID") String mId,
            @Field("ORDERID") String orderId,
            @Field("CUST_ID") String customerId,
            @Field("CHANNEL_ID") String channelId,
            @Field("TXN_AMOUNT") String amount,
            @Field("WEBSITE") String website,
            @Field("CALLBACK_URL") String callbackUrl,
            @Field("INDUSTRY_TYPE_ID") String industrieType
    );

}
