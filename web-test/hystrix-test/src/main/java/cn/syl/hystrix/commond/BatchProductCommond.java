package cn.syl.hystrix.commond;

import cn.syl.hystrix.entity.Product;
import cn.syl.hystrix.service.ProductService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BatchProductCommond extends HystrixObservableCommand<Product> {
    protected BatchProductCommond() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("库存服务")));
    }

    @Override
    protected Observable<Product> construct() {

        return Observable.create(new Observable.OnSubscribe<Product>() {
            @Override
            public void call(Subscriber<? super Product> subscriber) {

            }
        });
    }

    public static void main(String[] args) {
        //1.execute同步调用，调用完阻塞，知道结果返回
        ProductCommand productCommond = new ProductCommand(1L);
        Product product = productCommond.execute();
        System.out.println(product);
        //2.queue()异步调用，返回Future
        ProductCommand p2 = new ProductCommand(2L);
        Future<Product> future = p2.queue();
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //3.批量获取对象，订阅一个Observable对象
        BatchProductCommond commond = new BatchProductCommond();
        Observable<Product> observable = commond.observe();
        observable.subscribe(new Observer<Product>() {
            @Override
            public void onCompleted() {
                System.out.println("执行完毕");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Product product) {
                System.out.println(product);
            }
        });
        //4.批量获取对象 只有在订阅的时候才开始执行command bpmObs.subscribe
        BatchProductCommond bpm = new BatchProductCommond();
        Observable<Product> bpmObs = commond.toObservable();
        bpmObs.subscribe(new Observer<Product>() {
            @Override
            public void onCompleted() {
                System.out.println("执行完毕");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Product product) {
                System.out.println(product);
            }
        });
    }
}
