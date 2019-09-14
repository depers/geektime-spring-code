package geektime.spring.reactor.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@Slf4j
public class SimpleReactorDemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(SimpleReactorDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 创建一个1到6的序列
		Flux.range(1, 6)
				// 每次请求的时候打印一个每次请求多少个数的日志
				.doOnRequest(n -> log.info("Request {} number", n)) // 注意顺序造成的区别
				// 该方法会影响后续代码在那个线程上面执行，elastic是在一个缓存的线程池上面去做
				.publishOn(Schedulers.elastic())
				// 当1到6 publish完了之后打印
				.doOnComplete(() -> log.info("Publisher COMPLETE 1"))
				// map的执行是在那个线程上面
				.map(i -> {
					log.info("Publish {}, {}", Thread.currentThread(), i);
					return 10 / (i - 3);
//					return i;
				})
				.doOnComplete(() -> log.info("Publisher COMPLETE 2"))
				// 单独使用一个线程去订阅
				.subscribeOn(Schedulers.single())
				// 处理生产者产生的错误
				.onErrorResume(e -> {
					log.error("Exception {}", e.toString());
					return Mono.just(-1);
				})
				// 如果发生错误就返回-1
				.onErrorReturn(-1)
				// 消费信息，这里调用了subscribe四个参数的方法
				.subscribe(i -> log.info("Subscribe {}: {}", Thread.currentThread(), i),
						e -> log.error("error {}", e.toString()),
						() -> log.info("Subscriber COMPLETE"),
						// 虽然生产了6个但是我只消费4个
						s -> s.request(4)
				);
		Thread.sleep(2000);
	}
}

