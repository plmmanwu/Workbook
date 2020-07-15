package com.wlwoon.workbook;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by wxy on 2020/7/10.
 */

public class RxjavaDemo {

    private static final String TAG = "RxjavaDemo";
    static RxjavaDemo mRxjavaDemo = new RxjavaDemo();

    public static RxjavaDemo getInstance() {
        return mRxjavaDemo;
    }


    /**
     * 简单收发
     * <p>
     * 1.当Observable里面onError/onComplete 触发时 ，  Observer收到通知后  订阅关系终止
     * 2.当Observer里面 Disposable.dispose()触发时，    订阅关系立即中断
     */
    public void demo1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {//初始化被观察者
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Observable emit 1" + "\n");
                e.onNext(1);
                Log.e(TAG, "Observable emit 2" + "\n");
                e.onNext(2);
                Log.e(TAG, "Observable emit 3" + "\n");
                e.onNext(3);
                e.onError(new Throwable("Throwable  xxxxxxx"));
                e.onComplete();//
                Log.e(TAG, "Observable emit 4" + "\n");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {//建立订阅关系  并初始化观察者


            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {//Disposable  控制订阅状态
                this.d = d;
            }

            @Override
            public void onNext(Integer integer) {
//                if (integer == 2&&d!=null) {
//                    d.dispose();//中断订阅关系  后续通知就收不到了
//                }
                Log.e(TAG, "Observer onNext " + integer + "\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Observer onError " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "Observer onComplete " + "\n");
            }
        });
    }

    /**
     * 线程调度
     * 1.多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
     * 2.多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。
     * Schedulers.io()  使用缓存线程  RxCachedThreadScheduler  （循环往复  1-2-1-2...）  //适用网络 数据 常规操作
     * Schedulers.newThread()  创建新线程  RxNewThreadScheduler （逐渐递增 1-2-3-4...）
     * Schedulers.single()  使用单个线程  RxSingleScheduler       (一成不变 1-1-1...)
     * Schedulers.computation()  跟newThread模式一样  RxComputationThreadPool       (逐渐递增 1-2-3-4...)  //适用cpu大量计算
     * Schedulers.trampoline()  忽略此线程  直接跳过
     * <p>
     * 不指定线程的话  由调用者决定在哪个线程
     */
    @SuppressLint("CheckResult")
    public void demo2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "After observeOn(mainThread)，Current thread is " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.computation())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "After observeOn(io)，Current thread is " + Thread.currentThread().getName());
                    }
                });
    }

    /**
     * 操作符  map  （对象转换  gson--bean）
     */
    @SuppressLint("CheckResult")
    public void demoMap() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request
                        .Builder()
                        .url("http://gank.io/api/data/Android/10/1")
                        .get()
                        .build();

                Response response = okHttpClient.newCall(request).execute();
                emitter.onNext(response);
            }
        })
                .map(new Function<Response, DemoData>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public DemoData apply(Response response) throws Exception {
                        if (response != null && response.isSuccessful()) {
                            ResponseBody body = response.body();
                            Log.e(TAG, "map:转换前:" + body);
                            return new Gson().fromJson(body.string(), new TypeToken<DemoData>() {
                            }.getType());
                        }
                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<DemoData>() {
                    @Override
                    public void accept(DemoData demoData) throws Exception {
                        Log.e(TAG, "doOnNext: 保存成功：" + demoData.toString() + "\n");
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DemoData>() {
                    @Override
                    public void accept(DemoData data) throws Exception {
                        Log.e(TAG, "成功:" + data.toString() + "\n");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "失败：" + throwable.getMessage() + "\n");
                    }
                });
    }


    /**
     * 操作符 concat
     * 同步发射Observable  等待上一个onComplete 下一个才开始
     * 适用场景：先读取缓存的数据，如果缓存没有数据，再通过网络请求获取，随后在主线程更新我们的UI。
     */
    boolean isFromNet = false;
    @SuppressLint("CheckResult")
    public void demoConcat() {
        Observable<DemoData> cacheData = Observable.create(new ObservableOnSubscribe<DemoData>() {
            @SuppressLint("CheckResult")
            @Override
            public void subscribe(ObservableEmitter<DemoData> emitter) throws Exception {
                DemoData data = CacheManage.getInstance().getDemoData();
                if (data == null) {
                    emitter.onComplete();
                } else {
                    isFromNet = false;
                    emitter.onNext(data);
                }
            }
        });
        Observable<DemoData> netData = Observable.create(new ObservableOnSubscribe<DemoData>() {
            @SuppressLint("CheckResult")
            @Override
            public void subscribe(ObservableEmitter<DemoData> emitter) throws Exception {
                isFromNet = true;
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request
                        .Builder()
                        .url("http://gank.io/api/data/Android/10/1")
                        .get()
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                if (response != null && response.isSuccessful()) {
                    ResponseBody body = response.body();
                    Log.e(TAG, "map:转换前:" + body);
                    DemoData data = new Gson().fromJson(body.string(), new TypeToken<DemoData>() {
                    }.getType());
                    emitter.onNext(data);
                } else {
                    emitter.onError(new Throwable("请求失败"));
                }
            }
        });

        Observable.concat(cacheData, netData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DemoData>() {
                    @Override
                    public void accept(DemoData demoData) throws Exception {
                        if (isFromNet) {
                            Log.e(TAG, "accept : 网络获取数据设置缓存: \n");
                            CacheManage.getInstance().setDemoData(demoData);
                        } else {
                            Log.e(TAG, "accept : 数据来自缓存: \n");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "subscribe 失败:"+Thread.currentThread().getName() );
                        Log.e(TAG, "accept: 读取数据失败："+throwable.getMessage() );
                    }
                });

    }

    /**
     * flatMap 实现多个网络请求依次依赖
     * 例如用户注册成功后需要自动登录，我们只需要先通过注册接口注册用户信息，注册成功后马上调用登录接口进行自动登录即可
     */
    @SuppressLint("CheckResult")
    public void demoFlatMap() {
        Observable.create(new ObservableOnSubscribe<DemoData>() {
            @SuppressLint("CheckResult")
            @Override
            public void subscribe(ObservableEmitter<DemoData> emitter) throws Exception {
                isFromNet = true;
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request
                        .Builder()
                        .url("http://gank.io/api/data/Android/10/1")
                        .get()
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                if (response != null && response.isSuccessful()) {
                    ResponseBody body = response.body();
                    Log.e(TAG, "map:转换前:" + body);
                    DemoData data = new Gson().fromJson(body.string(), new TypeToken<DemoData>() {
                    }.getType());
                    emitter.onNext(data);
                } else {
                    emitter.onError(new Throwable("请求失败"));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<DemoData>() {
                    @Override
                    public void accept(DemoData demoData) throws Exception {

                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<DemoData, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(DemoData demoData) throws Exception {
                        List<DemoData.ResultsBean> results = demoData.getResults();
                        String id = results.get(0).get_id();
                        //拿id去模拟网络请求--
                        return Observable.just(id);
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    /**
     * 将多个Observable 对象拼到一起，待全部拿到结果后再去干一番事业
     */
    @SuppressLint("CheckResult")
    public void demoZip() {
        Observable<Integer> observable = Observable.just(1, 2, 3);
        Observable<String> observable1 = Observable.just("a", "b", "c");
        Observable.zip(observable, observable1, new BiFunction<Integer, String, Map<Integer, String>>() {
            @Override
            public Map<Integer, String> apply(Integer integer, String s) throws Exception {
                Map<Integer, String> map = new HashMap<>();
                map.put(integer, s);
                return map;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<Integer, String>>() {
                    @Override
                    public void accept(Map<Integer, String> integerStringMap) throws Exception {
                        Log.e(TAG, "zip : " + new Gson().toJson(integerStringMap));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    /**
     * interval 定时任务  每隔多久执行一次
     * 可以通过disposable.dispose() 去终止轮询
     */
    public void demoInterval() {
        int count = 0;
        Disposable disposable = Flowable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "interval :" + aLong + "\n");
                    }
                });

//        disposable.dispose();

    }

    /**
     * timer
     * 延时任务
     */
    @SuppressLint("CheckResult")
    public void demoTimer() {
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "Timer :" + aLong + "\n");
                    }
                });
    }

    /**
     * distinct 去重
     */
    @SuppressLint("CheckResult")
    public void demoDistinct() {
        Observable.just(1, 2, 2, 1, 3, 5, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "Distinct :" + integer + "\n");
                    }
                });
    }

    /**
     * filter
     * 根据一定的条件去筛选
     */
    @SuppressLint("CheckResult")
    public void demoFilter() {
        Observable.just(1, 56, 23, 5, 45)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 30;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "Filter :" + integer + "\n");
                    }
                });
    }

    /**
     * take
     * 从index=0开始拿两个
     */
    @SuppressLint("CheckResult")
    public void demoTake() {
        Observable.just(1, 2, 3, 4, 5)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "Take :" + integer + "\n");
                    }
                });
    }

    /**
     * skip
     * 从index=2  往后截取所有onNext成果
     */
    @SuppressLint("CheckResult")
    public void demoSkip() {
        Observable.just(1, 2, 3, 4, 5)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "Skip :" + integer + "\n");
                    }
                });
    }

    /**
     * buffer
     * 从index=0  开始拿count个
     * 再index+2  再拿count个
     * 知道index超出onNext个数
     */
    @SuppressLint("CheckResult")
    public void demoBuffer() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(4, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.e(TAG, "Buffer :" + new Gson().toJson(integers) + "\n");
                    }
                });
    }






}
