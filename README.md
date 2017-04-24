RetrofitProcessor
===
Play with annotation processor.  
Genrateing API invoke code with annotation processor.

Generated what ?
===

```
# app/build.gradle
apt {
    arguments{
        host "http://www.123456789.host/"
    }
}
```

```java
public interface Adapter {
    @GET("/webapi/exprired")
    Observable<FreeTrialExpired> getIsExpired(@Query("shopId") int shopid);
}
```

generated

```java
public class ApiClient {
	public static rx.Observable<lunacat.me.myapplication.model.FreeTrialExpired>getIsExpired(int shopid) {
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.123456789.host/").build();
		lunacat.me.myapplication.Adapter service = retrofit.create(lunacat.me.myapplication.Adapter.class);
		return service.getIsExpired(shopid).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
	}
}
```