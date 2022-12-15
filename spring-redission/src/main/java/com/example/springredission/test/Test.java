package com.example.springredission.test;

import com.example.springredission.filter.JWTUtils;
import com.example.springredission.filter.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Api(tags = "redission")
@RestController
@Slf4j
public class Test {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    WebSocket webSocket;

    // 生成签名的时候使用的秘钥secret
    private static final String SECRETKEY = "KJHUhjjJYgYUllVbXhKDHXhkSyHjlNiVkYzWTBac1Yxkjhuad";

    // expirationDate 生成jwt的有效期，单位秒
    private static final long expirationDate = 10;

    // 生成JWT的时间
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    //jwt的有效时间
    long expMillis = expirationDate;
    Date exp = new Date(expMillis);

    // 创建读写锁
    final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    // 创建读锁
    final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    // 创建写锁
    final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    @RequestMapping(value = "/bucket",method = RequestMethod.POST)
    public Object bucket(@RequestBody User user) throws IOException {
        long date=System.currentTimeMillis();
        RBucket<String> bucket = redissonClient.getBucket("test");
//        JWTUtils jwtUtils=new JWTUtils();
//       System.out.println(token);
        String token= bucket.get();
        if(token==null||"".equals(token)){
            //向客户端发送消息
            webSocket.sendMessage("token不存在");
            return "token不存在";
        }
        if(date-nowMillis>expMillis){
            bucket.getAndDelete();
            //向客户端发送消息
            webSocket.sendMessage("jwt超时失效，已删除");
            log.info("jwt超时失效");
            return false ;
        }
        verify(token,user);
       return true;
    }


    @RequestMapping(value = "/token",method = RequestMethod.POST)
    public void token(@RequestBody User user){
//       JWTUtils jwtUtils=new JWTUtils();
       String token=createToken(user);
        RBucket<String> bucket = redissonClient.getBucket("test");
        bucket.set(token);
//       jwtUtils.verify(token, user);
    }

    @RequestMapping(value ="/readWrite",method = RequestMethod.POST)
    public void readWrite(){
        Thread thread=new Thread(()->{
            readLock.lock();
            try {
                //代码部分
                System.out.println("获取du锁");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("释放du锁");
                readLock.unlock();

            }
        });
        thread.start();

        Thread thread1=new Thread(()->{
            writeLock.lock();
            try {
                //代码部分
                System.out.println("获取xie锁1");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
              System.out.println("释放xie锁1");
                writeLock.unlock();
            }
        });
        thread1.start();
    }



    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void test(){
        log.info("输出信息：{}",redissonClient);
        System.out.println(redissonClient);
    }

    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public Object test1(){
        RLock rLock=redissonClient.getLock("zhao");
        rLock.lock();
        try {
            System.out.println("加锁成功，执行后续代码。线程 ID：" + Thread.currentThread().getId());
            log.info("加锁成功，执行后续代码。线程 ID：{}" , Thread.currentThread().getId());
            Thread.sleep(10000);
        } catch (Exception e) {
            return e;
        } finally {
            rLock.unlock();
            // 3.解锁
            System.out.println("Finally，释放锁成功。线程 ID：" + Thread.currentThread().getId());
            log.info("Finally，释放锁成功。线程 ID：{}" , Thread.currentThread().getId());
        }
        return "test lock success";
    }

    @RequestMapping(value = "/test3",method = RequestMethod.POST)
    public void test3() {
        RReadWriteLock rwLock=redissonClient.getReadWriteLock("linhai");
        rwLock.readLock().lock(5, TimeUnit.SECONDS);
        rwLock.writeLock().lock(5,TimeUnit.SECONDS);
        try {
            boolean res = rwLock.readLock().tryLock(100, 10, TimeUnit.SECONDS);

            boolean res1 = rwLock.writeLock().tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/test4",method = RequestMethod.POST)
    public String test4()  {
        try {
            //获取信号量
            RSemaphore semaphore=redissonClient.getSemaphore("zhaolinhai");
            //默认获取一个信号，tryAcquire防止阻塞(当信号量耗用完毕，使用acquire会导致阻塞)
          boolean phore= semaphore.tryAcquire();
          if(!phore){
              return "phone is empty";
          }
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    @RequestMapping(value = "/test5",method = RequestMethod.POST)
    public String test5()  {
        try {
            //获取信号量
            RSemaphore semaphore=redissonClient.getSemaphore("zhaolinhai");
            //默认释放一个信号
            semaphore.release();
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 创建 jwt
     *
     * @param
     * @return jwt token
     */
    public String createToken (User user){

        // 指定签名的时候使用的签名算法，也就是header那部分，jwt已经将这部分内容封装好了
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("userName", user.getUserName());
        claims.put("password", user.getPassword());

        // 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了
        SecretKey key = generalKey(SECRETKEY + user.getPassword());

        // 生成签发人
        // json形式字符串或字符串，增加用户非敏感信息存储，如用户id或用户账号，与token解析后进行对比，防止乱用
        HashMap<String, Object> storeInfo = new HashMap<String, Object>();
        storeInfo.put("userId", user.getUserId());
        storeInfo.put("userName", user.getUserName());
        String subject = storeInfo.toString();

        // 下面就是在为payload添加各种标准声明和私有声明了
        // 这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 唯一随机UUID
                // 设置JWT ID：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击
                .setId(UUID.randomUUID().toString())
                // jwt的签发时间
                .setIssuedAt(now)
                // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);

        if (expirationDate >= 0) {
//                Date exp = new Date(expirationDate);
            builder.setExpiration(exp);
        }
        log.info("生成的token为：{}", builder.compact());
        return builder.compact();
    }

    /**
     * 由字符串生成加密key
     *
     * @return SecretKey
     */
    private static SecretKey generalKey(String stringKey) {
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解密token，获取声明的实体
     *
     * @param token 加密后的token
     * @return claims
     */
    public static Claims parseToken(String token, User user) {
        // 签名秘钥，和生成的签名的秘钥要保持一模一样
        SecretKey key = generalKey(SECRETKEY + user.getPassword());

        // 获取私有声明
//        Claims claims =
        log.info("解析后的token为：{}",Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody());
        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(key)
                // 设置需要解析的token
                .parseClaimsJws(token).getBody();

//        return claims;
    }

    /**
     * 校验token
     *
     * @param token 加密后的token
     * @param user  用户信息
     * @return true|false
     */
    public Boolean verify(String token, User user) {
        // 获取私有声明的实体
        Claims claims = parseToken(token, user);
        log.info(String.valueOf(claims.get("password").equals(user.getPassword())));
        return claims.get("password").equals(user.getPassword());
    }
}
