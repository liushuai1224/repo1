package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyUtil{

   private MathImpl mathImpl;//目标对象

   public ProxyUtil(MathImpl mathImpl) {
      super();
      this.mathImpl = mathImpl;
   }

   //代理对象和目标对象最终实现的是相同的接口
   //代理对象
   public Object getProxy (){
      ClassLoader loader=this.getClass().getClassLoader();//获得当前类的类加载器，
      Class[] interfaces=mathImpl.getClass().getInterfaces();//获取要实现的功能，获取目标对象实现的所有接口的Class，自然就获取了接口内的所有方法，最终通过代理对象实现功能
      return Proxy.newProxyInstance(loader,interfaces, new InvocationHandler(){//InvocationHandler控制代理对象实现功能的方式
         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            MyLogger.before(method.getName(),Arrays.toString(args));
            Object result=method.invoke(mathImpl,args);//动态代理对象实现功能
            MyLogger.after(method.getName(), result);
            return result;
         }
      });
   }


}
