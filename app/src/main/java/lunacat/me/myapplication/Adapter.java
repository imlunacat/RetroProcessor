package lunacat.me.myapplication;

import java.util.Map;

import lunacat.me.myapplication.model.FreeTrialExpired;
import lunacat.me.myapplication.model.ReturnCode;
import lunacat.me.myapplication.model.Info;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


public interface Adapter {
    @GET("/webapi/exprired")
    Observable<FreeTrialExpired> getIsExpired(@Query("shopId") int shopid);

    @POST("/webapi/register")
    Observable<ReturnCode> getNotifyRegister(@Body Map<String,String> map);

    @FormUrlEncoded
    @POST("/webapi/info")
    Observable<Info> getInfo(@Field("shopId") int shopId, @Field("isBinding") boolean isBinding);
}
