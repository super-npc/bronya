package com.okex.open.api.service.funding.impl;

import com.alibaba.fastjson.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;


interface FundingAPI {

    //获取充值地址信息 Get Deposit Address
    @GET("/api/v5/asset/deposit-address")
    Call<JSONObject> getDepositAddress(@Query("ccy") String ccy);

    //获取资金账户余额信息
    @GET("/api/v5/asset/balances")
    Call<JSONObject> getBalance(@Query("ccy") String ccy);

    //资金划转  Funds Transfer
    @POST("/api/v5/asset/transfer")
    Call<JSONObject> fundsTransfer(@Body JSONObject jsonObject);

    //提币 Withdrawal
    @POST("/api/v5/asset/withdrawal")
    Call<JSONObject> Withdrawal(@Body JSONObject jsonObject);

    //充值记录 Get Deposit History
    @GET("/api/v5/asset/deposit-history")
    Call<JSONObject> getDepositHistory(@Query("ccy") String ccy, @Query("state") String state, @Query("after") String after, @Query("before") String before, @Query("limit") String limit);

    //提币记录 Get Withdrawal History
    @GET("/api/v5/asset/withdrawal-history")
    Call<JSONObject> getWithdrawalHistory(@Query("ccy") String ccy, @Query("state") String state, @Query("after") String after, @Query("before") String before, @Query("limit") String limit);

    //获取币种列表 Get Currencies
    @GET("/api/v5/asset/currencies")
    Call<JSONObject> getCurrencies();

    // 余币宝账户
    @GET("/api/v5/asset/saving-balance")
    Call<JSONObject> savingBalance(@Query("ccy") String ccy);

    //余币宝申购/赎回 PiggyBank Purchase/Redemption
    @POST("/api/v5/asset/purchase_redempt")
    Call<JSONObject> piggyBankPurchaseRedemption(@Body JSONObject jsonObject);

    //资金流水查询 Asset Bills Details
    @GET("/api/v5/asset/bills")
    Call<JSONObject> assetBillsDetails(@Query("ccy") String ccy, @Query("type") String type, @Query("after") String after, @Query("before") String before, @Query("limit") String limit);

    /**
     * 获取账户资产估值
     * 资产估值对应的单位 BTC 、USDT
     * USD 、CNY 、JPY、KRW、RUB、EUR
     * VND 、IDR 、INR、PHP、THB、TRY
     * AUD 、SGD 、ARS、SAR、AED、IQD
     * 默认为 BTC 为单位的估值
     */
    @GET("/api/v5/asset/asset-valuation")
    Call<JSONObject> assetValuation(@Query("ccy") String ccy);

}